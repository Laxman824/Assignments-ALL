// Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

//! Defines functionality for creating guest memory snapshots.

use std::fs::File;

// use utils::{errno, get_page_size};
use versionize::{VersionMap, Versionize, VersionizeResult};
use versionize_derive::Versionize;
use vm_memory::{
    Bytes, FileOffset, GuestAddress, GuestMemory, GuestMemoryMmap,
    GuestMemoryRegion, MemoryRegionAddress,
};

// use crate::DirtyBitmap;

/// State of a guest memory region saved to file/buffer.
#[derive(Debug, PartialEq, Eq, Versionize)]
// NOTICE: Any changes to this structure require a snapshot version bump.
pub struct GuestMemoryRegionState {
    // This should have been named `base_guest_addr` since it's _guest_ addr, but for
    // backward compatibility we have to keep this name. At least this comment should help.
    /// Base GuestAddress.
    pub base_address: u64,
    /// Region size.
    pub size: usize,
    /// Offset in file/buffer where the region is saved.
    pub offset: u64,
}

/// Describes guest memory regions and their snapshot file mappings.
#[derive(Debug, Default, PartialEq, Eq, Versionize)]
// NOTICE: Any changes to this structure require a snapshot version bump.
pub struct GuestMemoryState {
    /// List of regions.
    pub regions: Vec<GuestMemoryRegionState>,
}

/// Defines the interface for snapshotting memory.
pub trait SnapshotMemory
where
    Self: Sized,
{
    /// Describes GuestMemoryMmap through a GuestMemoryState struct.
    fn describe(&self) -> GuestMemoryState;
    /// Dumps all contents of GuestMemoryMmap to a writer.
    fn dump<T: std::io::Write>(&self, writer: &mut T) ;
    /// Dumps all pages of GuestMemoryMmap present in `dirty_bitmap` to a writer.
    /// Creates a GuestMemoryMmap given a `file` containing the data
    /// and a `state` containing mapping information.
    fn restore(
        file: Option<&File>,
        state: &GuestMemoryState,
        track_dirty_pages: bool,
    ) -> GuestMemoryMmap;
}

impl SnapshotMemory for GuestMemoryMmap {
    /// Describes GuestMemoryMmap through a GuestMemoryState struct.
    fn describe(&self) -> GuestMemoryState {
        let mut guest_memory_state = GuestMemoryState::default();
        let mut offset = 0;
        self.iter().for_each(|region| {
            guest_memory_state.regions.push(GuestMemoryRegionState {
                base_address: region.start_addr().0,
                size: region.len() as usize,
                offset,
            });

            offset += region.len();
        });
        guest_memory_state
    }

    /// Dumps all contents of GuestMemoryMmap to a writer.
    fn dump<T: std::io::Write>(&self, writer: &mut T)  {
        self.iter()
            .try_for_each(|region| {
                region.write_all_to(MemoryRegionAddress(0), writer, region.len() as usize)
            }).unwrap()
    }


    /// Creates a GuestMemoryMmap backed by a `file` if present, otherwise backed
    /// by anonymous memory. Memory layout and ranges are described in `state` param.
    fn restore(
        file: Option<&File>,
        state: &GuestMemoryState,
        track_dirty_pages: bool,
    ) -> GuestMemoryMmap {
        let mut regions = vec![];
        for region in state.regions.iter() {
            let f = match file {
                Some(f) => Some(FileOffset::new(f.try_clone().unwrap(), region.offset)),
                None => None,
            };

            regions.push((f, GuestAddress(region.base_address), region.size));
        }

        vm_memory::create_guest_memory(&regions, track_dirty_pages).unwrap()
    }
}
