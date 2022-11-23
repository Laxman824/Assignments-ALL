// Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
//
// Portions Copyright 2017 The Chromium OS Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the THIRD-PARTY file.

use std::io::Error as IoError;
use std::os::unix::io::AsRawFd;

use vm_memory_upstream::bitmap::AtomicBitmap;
pub use vm_memory_upstream::bitmap::Bitmap;
use vm_memory_upstream::mmap::{check_file_offset, NewBitmap};
pub use vm_memory_upstream::mmap::{MmapRegionBuilder, MmapRegionError};
pub use vm_memory_upstream::{
    address, Address, ByteValued, Bytes, Error, FileOffset, GuestAddress, GuestMemory,
    GuestMemoryError, GuestMemoryRegion, GuestUsize, MemoryRegionAddress, MmapRegion,
    VolatileMemory, VolatileMemoryError,
};

pub type GuestMemoryMmap = vm_memory_upstream::GuestMemoryMmap<Option<AtomicBitmap>>;
pub type GuestRegionMmap = vm_memory_upstream::GuestRegionMmap<Option<AtomicBitmap>>;
pub type GuestMmapRegion = vm_memory_upstream::MmapRegion<Option<AtomicBitmap>>;

const GUARD_PAGE_COUNT: usize = 1;
pub fn get_page_size() -> usize {
    match unsafe { libc::sysconf(libc::_SC_PAGESIZE) } {
        -1 => panic!("failed to get page size"),
        ps => ps as usize,
    }
}

/// Build a `MmapRegion` surrounded by guard pages.
///
/// Initially, we map a `PROT_NONE` guard region of size:
/// `size` + (GUARD_PAGE_COUNT * 2 * page_size).
/// The guard region is mapped with `PROT_NONE`, so that any access to this region will cause
/// a SIGSEGV.
///
/// The actual accessible region is going to be nested in the larger guard region.
/// This is done by mapping over the guard region, starting at an address of
/// `guard_region_addr + (GUARD_PAGE_COUNT * page_size)`.
/// This results in a border of `GUARD_PAGE_COUNT` pages on either side of the region, which
/// acts as a safety net for accessing out-of-bounds addresses that are not allocated for the
/// guest's memory.
fn build_guarded_region(
    maybe_file_offset: Option<FileOffset>,
    size: usize,
    prot: i32,
    flags: i32,
    track_dirty_pages: bool,
) -> Result<GuestMmapRegion, MmapRegionError> {
    let page_size = get_page_size();
    // Create the guarded range size (received size + X pages),
    // where X is defined as a constant GUARD_PAGE_COUNT.
    let guarded_size = size + GUARD_PAGE_COUNT * 2 * page_size;

    // Map the guarded range to PROT_NONE
    let guard_addr = unsafe {
        libc::mmap(
            std::ptr::null_mut(),
            guarded_size,
            libc::PROT_NONE,
            libc::MAP_ANONYMOUS | libc::MAP_PRIVATE | libc::MAP_NORESERVE,
            -1,
            0,
        )
    };

    if guard_addr == libc::MAP_FAILED {
        return Err(MmapRegionError::Mmap(IoError::last_os_error()));
    }

    let (fd, offset) = match maybe_file_offset {
        Some(ref file_offset) => {
            check_file_offset(file_offset, size)?;
            (file_offset.file().as_raw_fd(), file_offset.start())
        }
        None => (-1, 0),
    };

    let region_start_addr = guard_addr as usize + page_size * GUARD_PAGE_COUNT;

    // Inside the protected range, starting with guard_addr + PAGE_SIZE,
    // map the requested range with received protection and flags
    let region_addr = unsafe {
        libc::mmap(
            region_start_addr as *mut libc::c_void,
            size,
            prot,
            flags | libc::MAP_FIXED,
            fd,
            offset as libc::off_t,
        )
    };

    if region_addr == libc::MAP_FAILED {
        return Err(MmapRegionError::Mmap(IoError::last_os_error()));
    }

    let bitmap = match track_dirty_pages {
        true => Some(AtomicBitmap::with_len(size)),
        false => None,
    };

    unsafe {
        MmapRegionBuilder::new_with_bitmap(size, bitmap)
            .with_raw_mmap_pointer(region_addr as *mut u8)
            .with_mmap_prot(prot)
            .with_mmap_flags(flags)
            .build()
    }
}

/// Helper for creating the guest memory.
pub fn create_guest_memory(
    regions: &[(Option<FileOffset>, GuestAddress, usize)],
    track_dirty_pages: bool,
) -> std::result::Result<GuestMemoryMmap, Error> {
    let prot = libc::PROT_READ | libc::PROT_WRITE;
    let mut mmap_regions = Vec::with_capacity(regions.len());

    for region in regions {
        let flags = match region.0 {
            None => libc::MAP_NORESERVE | libc::MAP_PRIVATE | libc::MAP_ANONYMOUS,
            Some(_) => libc::MAP_NORESERVE | libc::MAP_PRIVATE,
        };

        let mmap_region =
            build_guarded_region(region.0.clone(), region.2, prot, flags, track_dirty_pages)
                .map_err(Error::MmapRegion)?;

        mmap_regions.push(GuestRegionMmap::new(mmap_region, region.1)?);
    }

    GuestMemoryMmap::from_regions(mmap_regions)
}

pub fn mark_dirty_mem(mem: &GuestMemoryMmap, addr: GuestAddress, len: usize) {
    let _ = mem.try_access(len, addr, |_total, count, caddr, region| {
        if let Some(bitmap) = region.bitmap() {
            bitmap.mark_dirty(caddr.0 as usize, count);
        }
        Ok(count)
    });
}

pub mod test_utils {
    use super::*;

    /// Test helper used to initialize the guest memory without adding guard pages.
    /// This is needed because the default `create_guest_memory`
    /// uses MmapRegionBuilder::build_raw() for setting up the memory with guard pages, which would
    /// error if the size is not a multiple of the page size.
    /// There are unit tests which need a custom memory size, not a multiple of the page size.
    pub fn create_guest_memory_unguarded(
        regions: &[(GuestAddress, usize)],
        track_dirty_pages: bool,
    ) -> std::result::Result<GuestMemoryMmap, Error> {
        let prot = libc::PROT_READ | libc::PROT_WRITE;
        let flags = libc::MAP_NORESERVE | libc::MAP_PRIVATE | libc::MAP_ANONYMOUS;
        let mut mmap_regions = Vec::with_capacity(regions.len());

        for region in regions {
            mmap_regions.push(GuestRegionMmap::new(
                MmapRegionBuilder::new_with_bitmap(
                    region.1,
                    match track_dirty_pages {
                        true => Some(AtomicBitmap::with_len(region.1)),
                        false => None,
                    },
                )
                .with_mmap_prot(prot)
                .with_mmap_flags(flags)
                .build()
                .map_err(Error::MmapRegion)?,
                region.0,
            )?);
        }
        GuestMemoryMmap::from_regions(mmap_regions)
    }

    /// Test helper used to initialize the guest memory, without the option of file-backed mmap.
    /// It is just a little syntactic sugar that helps deduplicate test code.
    pub fn create_anon_guest_memory(
        regions: &[(GuestAddress, usize)],
        track_dirty_pages: bool,
    ) -> std::result::Result<GuestMemoryMmap, Error> {
        create_guest_memory(
            &regions.iter().map(|r| (None, r.0, r.1)).collect::<Vec<_>>(),
            track_dirty_pages,
        )
    }
}
