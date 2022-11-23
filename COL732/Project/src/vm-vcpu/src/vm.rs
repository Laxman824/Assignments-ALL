// Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// Copyright 2017 The Chromium OS Authors. All rights reserved.
// SPDX-License-Identifier: Apache-2.0 OR BSD-3-Clause

use std::io::{self, ErrorKind};
use std::sync::mpsc::{self, Receiver};
use std::sync::{Arc, Barrier, Mutex};
use std::thread::{self, JoinHandle};

use versionize::{VersionMap, Versionize, VersionizeResult};
use versionize_derive::Versionize;

use kvm_bindings::kvm_userspace_memory_region;
#[cfg(target_arch = "x86_64")]
use kvm_bindings::{
    kvm_clock_data, kvm_irqchip, kvm_pit_config, kvm_pit_state2, KVM_CLOCK_TSC_STABLE,
    KVM_IRQCHIP_IOAPIC, KVM_IRQCHIP_PIC_MASTER, KVM_IRQCHIP_PIC_SLAVE, KVM_PIT_SPEAKER_DUMMY,
};

use kvm_ioctls::{Kvm, VmFd};
use vm_device::device_manager::IoManager;
use vm_memory::{Address, GuestAddress, GuestMemory, GuestMemoryRegion};
use vmm_sys_util::errno::Error as Errno;
use vmm_sys_util::eventfd::EventFd;
use vmm_sys_util::signal::{Killable, SIGRTMIN};

use crate::vcpu::{self, KvmVcpu, VcpuConfigList, VcpuRunState, VcpuState};

#[cfg(target_arch = "aarch64")]
use vm_vcpu_ref::aarch64::interrupts::{self, Gic, GicConfig};
#[cfg(target_arch = "x86_64")]
use vm_vcpu_ref::x86_64::mptable::{self, MpTable};

/// Defines the configuration of this VM.
#[derive(Clone, Versionize)]
pub struct VmConfig {
    pub num_vcpus: u8,
    pub vcpus_config: VcpuConfigList,
}

impl VmConfig {
    /// Creates a default `VmConfig` for `num_vcpus`.
    pub fn new(kvm: &Kvm, num_vcpus: u8, starter_path: String) -> Result<Self> {
        Ok(VmConfig {
            num_vcpus,
            vcpus_config: VcpuConfigList::new(kvm, num_vcpus, starter_path).map_err(Error::CreateVmConfig)?,
        })
    }
}

#[cfg(target_arch = "x86_64")]
#[derive(Clone, Versionize)]
pub struct VmState {
    pub pitstate: kvm_pit_state2,
    pub clock: kvm_clock_data,
    pub pic_master: kvm_irqchip,
    pub pic_slave: kvm_irqchip,
    pub ioapic: kvm_irqchip,
    pub config: VmConfig,
    pub vcpus_state: Vec<VcpuState>,
}

#[cfg(target_arch = "aarch64")]
#[derive(Clone)]
pub struct VmState {
    pub config: VmConfig,
    pub vcpus_state: Vec<VcpuState>,
}

/// A KVM specific implementation of a Virtual Machine.
///
/// Provides abstractions for working with a VM. Once a generic Vm trait will be available,
/// this type will become on of the concrete implementations.
pub struct KvmVm<EH: ExitHandler + Send> {
    pub fd: Arc<VmFd>,
    pub config: VmConfig,
    // Only one of `vcpus` or `vcpu_handles` can be active at a time.
    // To create the `vcpu_handles` the `vcpu` vector is drained.
    // A better abstraction should be used to represent this behavior.
    pub vcpus: Vec<KvmVcpu>,
    pub vcpu_handles: Vec<JoinHandle<()>>,
    pub exit_handler: EH,
    pub vcpu_barrier: Arc<Barrier>,
    pub vcpu_run_state: Arc<VcpuRunState>,
    pub vcpu_rx: Option<Receiver<i32>>,
    pub vcpu_states: Vec<Arc<Mutex<Option<VcpuState>>>>,
}

#[derive(Debug, thiserror::Error)]
pub enum Error {
    /// Failed to create the VM Configuration.
    #[error("Failed to create the VM Configuration: {0}")]
    CreateVmConfig(vcpu::Error),
    /// Failed to create a VM.
    #[error("Failed to create a VM: {0}")]
    CreateVm(kvm_ioctls::Error),
    /// Failed to setup the user memory regions.
    #[error("Failed to setup the user memory regions: {0}")]
    SetupMemoryRegion(kvm_ioctls::Error),
    /// Not enough memory slots.
    #[error("Not enough memory slots.")]
    NotEnoughMemorySlots,
    /// Failed to setup the interrupt controller.
    #[error("Failed to setup the interrupt controller: {0}")]
    #[cfg(target_arch = "x86_64")]
    SetupInterruptController(kvm_ioctls::Error),
    #[error("Failed to setup the interrupt controller: {0}")]
    #[cfg(target_arch = "aarch64")]
    SetupInterruptController(interrupts::Error),
    /// Failed to create the vcpu.
    #[error("Failed to create the vcpu: {0}")]
    CreateVcpu(vcpu::Error),
    /// Failed to register IRQ event.
    #[error("Failed to register IRQ event: {0}")]
    RegisterIrqEvent(kvm_ioctls::Error),
    /// Failed to run the vcpus.
    #[error("Failed to run the vcpus: {0}")]
    RunVcpus(io::Error),
    /// Failed to configure mptables.
    #[error("Failed to configure mptables.")]
    #[cfg(target_arch = "x86_64")]
    Mptable(mptable::Error),
    /// Failed to pause the vcpus.
    #[error("Failed to pause the vcpus: {0}")]
    PauseVcpus(Errno),
    /// Failed to resume vcpus.
    #[error("Failed to resume vcpus: {0}")]
    ResumeVcpus(Errno),
    /// Failed to get KVM vm pit state.
    #[error("Failed to get KVM vm pit state: {0}")]
    VmGetPit2(kvm_ioctls::Error),
    /// Failed to get KVM vm clock.
    #[error("Failed to get KVM vm clock: {0}")]
    VmGetClock(kvm_ioctls::Error),
    /// Failed to get KVM vm irqchip.
    #[error("Failed to get KVM vm irqchip: {0}")]
    VmGetIrqChip(kvm_ioctls::Error),
    /// Failed to set KVM vm pit state.
    #[error("Failed to set KVM vm pit state: {0}")]
    #[cfg(target_arch = "x86_64")]
    VmSetPit2(kvm_ioctls::Error),
    #[cfg(target_arch = "x86_64")]
    /// Failed to set KVM vm clock.
    #[error("Failed to set KVM vm clock: {0}")]
    VmSetClock(kvm_ioctls::Error),
    #[cfg(target_arch = "x86_64")]
    /// Failed to set KVM vm irqchip.
    #[error("Failed to set KVM vm irqchip: {0}")]
    VmSetIrqChip(kvm_ioctls::Error),
    /// Failed to save the state of vCPUs.
    #[error("Failed to save the state of vCPUs: {0}")]
    SaveVcpuState(vcpu::Error),
}

#[cfg(target_arch = "x86_64")]
impl From<mptable::Error> for Error {
    fn from(err: mptable::Error) -> Self {
        Error::Mptable(err)
    }
}

#[cfg(target_arch = "aarch64")]
impl From<interrupts::Error> for Error {
    fn from(inner: interrupts::Error) -> Self {
        Error::SetupInterruptController(inner)
    }
}

/// Dedicated [`Result`](https://doc.rust-lang.org/std/result/) type.
pub type Result<T> = std::result::Result<T, Error>;

/// Trait that allows the VM to signal that the VCPUs have exited.
// This trait needs Clone because each VCPU needs to be able to call the `kick` function.
pub trait ExitHandler: Clone {
    fn kick(&self) -> io::Result<()>;
}

/// Represents the current state of the VM.
#[derive(Debug, PartialEq)]
pub enum VmRunState {
    Running,
    Suspending,
    Exiting,
}

impl Default for VmRunState {
    fn default() -> Self {
        Self::Running
    }
}

impl<EH: 'static + ExitHandler + Send> KvmVm<EH> {
    // Helper function for creating a default VM.
    // This is needed as the same initializations needs to happen when creating
    // a fresh vm as well as a vm from a previously saved state.
    fn create_vm<M: GuestMemory>(
        kvm: &Kvm,
        config: VmConfig,
        exit_handler: EH,
        guest_memory: &M,
    ) -> Result<Self> {
        let vm_fd = Arc::new(kvm.create_vm().map_err(Error::CreateVm)?);
        let vcpu_run_state = Arc::new(VcpuRunState::default());

        let vm = KvmVm {
            vcpu_barrier: Arc::new(Barrier::new(config.num_vcpus as usize)),
            config,
            fd: vm_fd,
            vcpus: Vec::new(),
            vcpu_handles: Vec::new(),
            exit_handler,
            vcpu_run_state,
            vcpu_rx: None,
            vcpu_states: Vec::new(),
        };
        vm.configure_memory_regions(guest_memory, kvm)?;

        Ok(vm)
    }

    /// Create a new `KvmVm`.
    pub fn new<M: GuestMemory>(
        kvm: &Kvm,
        vm_config: VmConfig,
        guest_memory: &M,
        exit_handler: EH,
        bus: Arc<Mutex<IoManager>>,
    ) -> Result<Self> {
        let vcpus_config = vm_config.vcpus_config.clone();
        let mut vm = Self::create_vm(kvm, vm_config, exit_handler, guest_memory)?;

        #[cfg(target_arch = "x86_64")]
        MpTable::new(vm.config.num_vcpus)?.write(guest_memory)?;

        #[cfg(target_arch = "x86_64")]
        vm.setup_irq_controller()?;

        vm.create_vcpus(bus, vcpus_config, guest_memory)?;

        Ok(vm)
    }

    // FIXME: Take input parameter as file where it needs to be saved
    pub fn snapshot_and_resume(&self, cpu_snapshot_path: String, memory_snapshot_path: String) {
        // NOTE: 1. Kicking all the vcpus out of their run loop in suspending state
        self.vcpu_run_state.set_and_notify(VmRunState::Suspending);
        for handle in self.vcpu_handles.iter() {
            let _ = handle.kill(SIGRTMIN() + 0);
        }

        for i in 0..self.config.num_vcpus {
            let r = self.vcpu_rx.as_ref().unwrap();
            r.recv().unwrap();
            println!("Received message from {i}th cpu");
        }
        self.vcpu_run_state.set_and_notify(VmRunState::Running);
    }

    pub fn snapshot_and_pause(&self, cpu_snapshot_path: String, memory_snapshot_path: String) {
        // NOTE: 1. Kicking all the vcpus out of their run loop in suspending state
        self.vcpu_run_state.set_and_notify(VmRunState::Exiting);
        for handle in self.vcpu_handles.iter() {
            let _ = handle.kill(SIGRTMIN() + 0);
        }

        for i in 0..self.config.num_vcpus {
            let r = self.vcpu_rx.as_ref().unwrap();
            match r.recv() {
                Ok(_) => {}
                Err(e) => {
                    println!("Error:{:?}", e);
                }
            }
            println!("Received message from {i}th cpu");
        }

        let _ = self.exit_handler.kick();
    }

    #[cfg(target_arch = "x86_64")]
    // Set the state of this `KvmVm`. Errors returned from this function
    // MUST not be ignored because they can lead to undefined behavior when
    // the state of the VM is only partially set.
    fn set_state(&mut self, state: VmState) -> Result<()> {
        self.fd
            .set_pit2(&state.pitstate)
            .map_err(Error::VmSetPit2)?;
        self.fd.set_clock(&state.clock).map_err(Error::VmSetClock)?;
        self.fd
            .set_irqchip(&state.pic_master)
            .map_err(Error::VmSetIrqChip)?;
        self.fd
            .set_irqchip(&state.pic_slave)
            .map_err(Error::VmSetIrqChip)?;
        self.fd
            .set_irqchip(&state.ioapic)
            .map_err(Error::VmSetIrqChip)?;

        Ok(())
    }

    #[cfg(target_arch = "aarch64")]
    fn set_state(&mut self, _state: VmState) -> Result<()> {
        Ok(())
    }

    /// Create a VM from a previously saved state.
    pub fn from_state<M: GuestMemory>(
        kvm: &Kvm,
        state: VmState,
        guest_memory: &M,
        exit_handler: EH,
        bus: Arc<Mutex<IoManager>>,
    ) -> Result<Self> {
        // Restoring a VM from a previously saved state needs to happen in the following order:
        // 1. we first need to create the VM fd (from KVM).
        // 2. On x86_64, we need to create the in-kernel IRQ chip so we can then create the vCPUs.
        // 3. Create the vCPUs.
        // 4. Restore the vCPU state.
        let mut vm = Self::create_vm(kvm, state.config.clone(), exit_handler, guest_memory)?;
        #[cfg(target_arch = "x86_64")]
        vm.setup_irq_controller()?;
        let vcpus_state = state.vcpus_state.clone();
        vm.set_state(state)?;
        vm.create_vcpus_from_state::<M>(bus, vcpus_state)?;
        Ok(vm)
    }

    /// Retrieve the associated KVM VM file descriptor.
    pub fn vm_fd(&self) -> Arc<VmFd> {
        self.fd.clone()
    }

    // Create the kvm memory regions based on the configuration passed as `guest_memory`.
    fn configure_memory_regions<M: GuestMemory>(&self, guest_memory: &M, kvm: &Kvm) -> Result<()> {
        if guest_memory.num_regions() > kvm.get_nr_memslots() {
            return Err(Error::NotEnoughMemorySlots);
        }

        // Register guest memory regions with KVM.
        for (index, region) in guest_memory.iter().enumerate() {
            let memory_region = kvm_userspace_memory_region {
                slot: index as u32,
                guest_phys_addr: region.start_addr().raw_value(),
                memory_size: region.len() as u64,
                // It's safe to unwrap because the guest address is valid.
                userspace_addr: guest_memory.get_host_address(region.start_addr()).unwrap() as u64,
                flags: 0,
            };

            // Safe because:
            // * userspace_addr is a valid address for a memory region, obtained by calling
            //   get_host_address() on a valid region's start address;
            // * the memory regions do not overlap - there's either a single region spanning
            //   the whole guest memory, or 2 regions with the MMIO gap in between.
            unsafe { self.fd.set_user_memory_region(memory_region) }
                .map_err(Error::SetupMemoryRegion)?;
        }

        Ok(())
    }

    // Configures the in kernel interrupt controller.
    // This function should be reused to configure the aarch64 interrupt controller (GIC).
    #[cfg(target_arch = "x86_64")]
    fn setup_irq_controller(&self) -> Result<()> {
        // First, create the irqchip.
        // On `x86_64`, this _must_ be created _before_ the vCPUs.
        // It sets up the virtual IOAPIC, virtual PIC, and sets up the future vCPUs for local APIC.
        // When in doubt, look in the kernel for `KVM_CREATE_IRQCHIP`.
        // https://elixir.bootlin.com/linux/latest/source/arch/x86/kvm/x86.c
        self.fd
            .create_irq_chip()
            .map_err(Error::SetupInterruptController)?;

        // The PIT is used during boot to configure the frequency.
        // The output from PIT channel 0 is connected to the PIC chip, so that it
        // generates an "IRQ 0" (system timer).
        // https://wiki.osdev.org/Programmable_Interval_Timer
        let pit_config = kvm_pit_config {
            // Set up the speaker PIT, because some kernels are musical and access the speaker port
            // during boot. Without this, KVM would continuously exit to userspace.
            flags: KVM_PIT_SPEAKER_DUMMY,
            ..Default::default()
        };
        self.fd
            .create_pit2(pit_config)
            .map_err(Error::SetupInterruptController)
    }

    #[cfg(target_arch = "aarch64")]
    pub fn setup_irq_controller(&self) -> Result<()> {
        // TODO: we need a reference to the GIC to be able to implement save/restore.
        // TODO: find an abstraction that make this possible.

        let _gic = Gic::new(
            GicConfig {
                num_cpus: self.config.num_vcpus,
                ..Default::default()
            },
            &self.vm_fd(),
        )?;
        Ok(())
    }

    /// Creates vcpus based on the passed configuration.
    ///
    /// Once this function is called, no more calls to `create_vcpu` are
    /// allowed.
    fn create_vcpus<M: GuestMemory>(
        &mut self,
        bus: Arc<Mutex<IoManager>>,
        vcpus_config: VcpuConfigList,
        memory: &M,
    ) -> Result<()> {
        let (tx, rx) = mpsc::channel::<i32>();
        self.vcpu_rx = Some(rx);
        self.vcpus = vcpus_config
            .configs
            .iter()
            .map(|config| {
                let tx = tx.clone();
                KvmVcpu::new(
                    &self.fd,
                    bus.clone(),
                    config.clone(),
                    self.vcpu_barrier.clone(),
                    self.vcpu_run_state.clone(),
                    memory,
                    tx,
                )
            })
            .collect::<vcpu::Result<Vec<KvmVcpu>>>()
            .map_err(Error::CreateVcpu)?;
        #[cfg(target_arch = "aarch64")]
        self.setup_irq_controller()?;

        Ok(())
    }

    fn create_vcpus_from_state<M: GuestMemory>(
        &mut self,
        bus: Arc<Mutex<IoManager>>,
        vcpus_state: Vec<VcpuState>,
    ) -> Result<()> {
        let (tx, rx) = mpsc::channel::<i32>();
        self.vcpu_rx = Some(rx);
        self.vcpus = vcpus_state
            .iter()
            .map(|state| {
                let tx = tx.clone();
                KvmVcpu::from_state::<M>(
                    &self.fd,
                    bus.clone(),
                    state.clone(),
                    self.vcpu_barrier.clone(),
                    self.vcpu_run_state.clone(),
                    tx,
                )
            })
            .collect::<vcpu::Result<Vec<KvmVcpu>>>()
            .map_err(Error::CreateVcpu)?;

        Ok(())
    }

    /// Let KVM know that instead of triggering an actual interrupt for `irq_number`, we will
    /// just write on the specified `event`.
    pub fn register_irqfd(&self, event: &EventFd, irq_number: u32) -> Result<()> {
        self.fd
            .register_irqfd(event, irq_number)
            .map_err(Error::RegisterIrqEvent)
    }

    /// Run the `Vm` based on the passed `vcpu` configuration.
    ///
    /// Returns an error when the number of configured vcpus is not the same as the number
    /// of created vcpus (using the `create_vcpu` function).
    ///
    /// # Arguments
    ///
    /// * `vcpu_run_addr`: address in guest memory where the vcpu run starts. This can be None
    ///  when the IP is specified using the platform dependent registers.
    pub fn run(&mut self, vcpu_run_addr: Option<GuestAddress>, is_resume: bool) -> Result<()> {
        if self.vcpus.len() != self.config.num_vcpus as usize {
            return Err(Error::RunVcpus(io::Error::from(ErrorKind::InvalidInput)));
        }

        KvmVcpu::setup_signal_handler().unwrap();

        for (id, mut vcpu) in self.vcpus.drain(..).enumerate() {
            let vcpu_exit_handler = self.exit_handler.clone();
            self.vcpu_states.push(vcpu.vcpu_state.clone());
            let vcpu_handle = thread::Builder::new()
                .name(format!("vcpu_{}", id))
                .spawn(move || {
                    // TODO: Check the result of both vcpu run & kick.
                    let _ = vcpu.run(vcpu_run_addr, is_resume).unwrap();
                    let _ = vcpu_exit_handler.kick();
                    vcpu.run_state.set_and_notify(VmRunState::Exiting);
                })
                .map_err(Error::RunVcpus)?;
            self.vcpu_handles.push(vcpu_handle);
        }
        self.vcpu_run_state.set_and_notify(VmRunState::Running);
        // let _ = self.exit_handler.kick();
        Ok(())
    }

    /// Shutdown a VM by signaling the running VCPUs.
    pub fn shutdown(&mut self) {
        self.vcpu_run_state.set_and_notify(VmRunState::Exiting);
        self.vcpu_handles.drain(..).for_each(|handle| {
            #[allow(clippy::identity_op)]
            let _ = handle.kill(SIGRTMIN() + 0);
            let _ = handle.join();
        })
    }

    /// Pause a running VM.
    ///
    /// If the VM is already paused, this is a no-op.
    pub fn pause(&mut self) -> Result<()> {
        todo!();
    }

    #[cfg(target_arch = "aarch64")]
    pub fn save_state(&mut self) -> Result<VmState> {
        let vcpus_state = self
            .vcpus
            .iter_mut()
            .map(|vcpu| vcpu.save_state())
            .collect::<vcpu::Result<Vec<VcpuState>>>()
            .map_err(Error::SaveVcpuState)?;

        Ok(VmState {
            config: self.config.clone(),
            vcpus_state,
        })
    }

    /// Retrieve the state of a `paused` VM.
    ///
    /// Returns an error when the VM is not paused.
    #[cfg(target_arch = "x86_64")]
    pub fn save_state(&mut self) -> Result<VmState> {
        use std::{fs::File, io::Read};

        let pitstate = self.fd.get_pit2().map_err(Error::VmGetPit2)?;

        let mut clock = self.fd.get_clock().map_err(Error::VmGetClock)?;
        // This bit is not accepted in SET_CLOCK, clear it.
        clock.flags &= !KVM_CLOCK_TSC_STABLE;

        let mut pic_master = kvm_irqchip {
            chip_id: KVM_IRQCHIP_PIC_MASTER,
            ..Default::default()
        };
        self.fd
            .get_irqchip(&mut pic_master)
            .map_err(Error::VmGetIrqChip)?;

        let mut pic_slave = kvm_irqchip {
            chip_id: KVM_IRQCHIP_PIC_SLAVE,
            ..Default::default()
        };
        self.fd
            .get_irqchip(&mut pic_slave)
            .map_err(Error::VmGetIrqChip)?;

        let mut ioapic = kvm_irqchip {
            chip_id: KVM_IRQCHIP_IOAPIC,
            ..Default::default()
        };
        self.fd
            .get_irqchip(&mut ioapic)
            .map_err(Error::VmGetIrqChip)?;

        let mut vcpus_state = Vec::new();
        for vcpu_state in &self.vcpu_states {
            vcpus_state.push(vcpu_state.lock().unwrap().clone().unwrap());
        }

        Ok(VmState {
            pitstate,
            clock,
            pic_master,
            pic_slave,
            ioapic,
            config: self.config.clone(),
            vcpus_state,
        })
    }
}
