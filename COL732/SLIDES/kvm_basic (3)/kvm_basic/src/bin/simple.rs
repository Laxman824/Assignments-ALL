extern crate kvm_ioctls;
extern crate kvm_bindings;

use kvm_ioctls::VcpuExit;
use kvm_ioctls::{Kvm, VcpuFd, VmFd};

fn main() {
    use std::io::Write;
    use std::ptr::null_mut;
    use std::slice;

    use kvm_bindings::kvm_userspace_memory_region;

    let mem_size = 0x4000;
    let guest_addr = 0x1000;

    // Setting up architectural dependent values.
    let asm_code: &[u8] = &[
        0xba, 0xf8, 0x03, 
        0x00, 0xd8, 
        0x04, '0' as u8,
        0xee,
        0xb0, '\n' as u8,
        0xee,
        0xf4];
        
    // 1. Instantiate KVM.
    let kvm = Kvm::new().unwrap();

    // 2. Create a VM.
    let vm = kvm.create_vm().unwrap();

    // 3. Initialize Guest Memory.
    let load_addr: *mut u8 = unsafe {
        libc::mmap(
            null_mut(),
            mem_size,
            libc::PROT_READ | libc::PROT_WRITE,
            libc::MAP_ANONYMOUS | libc::MAP_SHARED | libc::MAP_NORESERVE,
            -1,
            0,
        ) as *mut u8
    };

    let slot = 0;
    let mem_region = kvm_userspace_memory_region {
        slot,
        guest_phys_addr: guest_addr,
        memory_size: mem_size as u64,
        userspace_addr: load_addr as u64,
        flags: 0,
    };
    unsafe { vm.set_user_memory_region(mem_region).unwrap() };

    // Write the code in the guest memory. This will generate a dirty page.
    unsafe {
        let mut slice = slice::from_raw_parts_mut(load_addr, mem_size);
        slice.write(&asm_code).unwrap();
    }

    // 4. Create one vCPU.
    let vcpu_fd = vm.create_vcpu(0).unwrap();

    // 5. Initialize general purpose and special registers.
    // x86_64 specific registry setup.
    let mut vcpu_sregs = vcpu_fd.get_sregs().unwrap();
    vcpu_sregs.cs.base = 0;
    vcpu_sregs.cs.selector = 0;
    vcpu_fd.set_sregs(&vcpu_sregs).unwrap();

    let mut vcpu_regs = vcpu_fd.get_regs().unwrap();
    vcpu_regs.rip = guest_addr;
    vcpu_regs.rax = 2;
    vcpu_regs.rbx = 2;
    vcpu_regs.rflags = 2;
    vcpu_fd.set_regs(&vcpu_regs).unwrap();

    // 6. Run code on the vCPU.
    loop {
        match vcpu_fd.run().expect("run failed") {
            VcpuExit::IoOut(addr, data) => {
                // println!(
                //     "Received an I/O out exit. Address: {:#x}. Data: {:#x}",
                //     addr, data[0],
                // );
                println!("{}", data[0] as char);
            }
            VcpuExit::Hlt => {
                // let vcpu_regs = vcpu_fd.get_regs().unwrap();
                // println!("cpu_regs {:#?}", vcpu_regs);
                break;
            }
            r => panic!("Unexpected exit reason: {:?}", r),
        }
    }
}