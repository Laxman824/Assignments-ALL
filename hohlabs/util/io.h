#pragma once

#include "util/config.h"

namespace io {

  static inline uint8_t read8(io_t p, uint16_t i){
    io_t pp=p+i;
    uint8_t value;
    asm volatile ("inb %1, %0" : "=a"(value) : "d"(pp) );
    return value;
  }
  static inline void write8(io_t p, uint16_t i, uint8_t value){
    io_t pp=p+i;
    asm volatile ("outb %0, %1" :: "a"(value) , "d"(pp));
  }



  static inline uint16_t read16(io_t p, uint16_t i){
    io_t pp=p+i;
    uint16_t value;
    asm volatile ("inw %1, %0" : "=a"(value) : "d"(pp) );
    return value;
  }
  static inline void write16(io_t p, uint16_t i, uint16_t value){
    io_t pp=p+i;
    asm volatile ("outw %0, %1" :: "a"(value) , "d"(pp));
  }


  static inline uint32_t read32(io_t p, uint16_t i){
    io_t pp=p+i;
    uint32_t value;
    asm volatile ("inl %1, %0" : "=a"(value) : "d"(pp) );
    return value;
  }
  static inline void write32(io_t p, uint16_t i, uint32_t value){
    io_t pp=p+i;
    asm volatile ("outl %0, %1" :: "a"(value) , "d"(pp));
  }


} //namespace io

namespace mmio{

  static inline uint8_t read8(addr_t p, uint32_t index){
    volatile uint8_t* pp=(uint8_t*)(p+index);
    uint8_t value=*pp;
    asm volatile ("mfence":::"memory");
    return value;
  }
  static inline void write8(addr_t p, uint32_t index, uint8_t value){
    volatile uint8_t* pp=(uint8_t*)(p+index);
    *pp=value;
    asm volatile ("mfence":::"memory");
  }



  static inline uint16_t read16(addr_t p, uint32_t index){
    volatile uint16_t* pp=(uint16_t*)(p+index);
    uint16_t value=*pp;
    asm volatile ("mfence":::"memory");
    return value;
  }
  static inline void write16(addr_t p, uint32_t index, uint16_t value){
    volatile uint16_t* pp=(uint16_t*)(p+index);
    *pp=value;
    asm volatile ("mfence":::"memory");
  }



  static inline uint32_t read32(addr_t p, uint32_t index){
    volatile uint32_t* pp=(uint32_t*)(p+index);
    uint32_t value=*pp;
    asm volatile ("mfence":::"memory");
    return value;
  }
  static inline void write32(addr_t p, uint32_t index, uint32_t value){
    volatile uint32_t* pp=(uint32_t*)(p+index);
    *pp=value;
    asm volatile ("mfence":::"memory");
  }


} //namespace mmio


