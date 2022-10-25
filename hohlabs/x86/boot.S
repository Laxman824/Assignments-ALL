#define MB_FLAGS                (1|2)
#define STACK_SIZE              0x4000
#define MULTIBOOT_HEADER_MAGIC  0x1BADB002



.text

#
# multiboot header: interface between bootloader <-> kernel
#

.align  4
multiboot_header:
   .long   MULTIBOOT_HEADER_MAGIC
   .long   MB_FLAGS
   .long   -(MULTIBOOT_HEADER_MAGIC + MB_FLAGS)


#
# Assembly code to call C function
#
# Steps:
#
#  1. When an OS is booted, stack is not set
#     so we need to set stack
#  2. We need to clear the FLAGS register to a known value
#  3. we can call any C function
#  4. Incase by mistake if that C function returned, enter infinite loop
#
#  That's it.
#
#


.globl start, _start
.align  16
.code32
start:
_start:

   #set stack
   movl  $tmp_stackbottom, %esp

   #clear flags
   pushl $0
   popf
   
   #boot kernel
   call  core_boot

   #core_boot returned!: disable interrupt and enter infinite loop
   cli
loop:   
   hlt
   jmp   loop
 

#STACK
   .zero STACK_SIZE
.align 64
tmp_stackbottom:
   .zero 32

