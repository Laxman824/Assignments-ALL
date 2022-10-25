
OUTPUT_FORMAT(elf32-i386)
OUTPUT_ARCH(i386)
ENTRY (_start)

SECTIONS{
    . = 0+0x100000;
    g_start = .;
    . = ALIGN(0x40);

    .text :
    {
        *(.text)
        *(.gnu.linkonce.t*)
    }

    . = ALIGN (0x1000) ;

    .rodata :
    {
        *(.rodata)
        *(.gnu.linkonce.r*)
    }

    . = ALIGN (0x1000) ;

    .data :
    {
        *(.data)
        *(.gnu.linkonce.d*)
    }

    g_bss_start = .;
    .bss : 
    {
      *(COMMON) 
      *(.bss) 
      *(.gnu.linkonce.b*)
    }
    g_bss_end = .;

    /DISCARD/ :        
    { 
      *(.comment) 
      *(.eh_frame) 
    }

    g_end = .;
}

