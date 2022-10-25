#pragma once

#if 1 || defined(SIMULATOR)
#include <stdint.h>

#else
typedef unsigned char       uint8_t;
typedef short               int16_t;
typedef unsigned short      uint16_t;
typedef int                 int32_t;
typedef unsigned int        uint32_t;
typedef long long           int64_t;
typedef unsigned long long  uint64_t;
typedef int32_t             intptr_t;
typedef uint32_t            uintptr_t;
typedef unsigned int        size_t;

inline void* operator new(size_t, void* __p) noexcept { return __p; }
inline void* operator new[](size_t, void* __p) noexcept { return __p; }
inline void operator delete  (void*, void*) noexcept{}
inline void operator delete[](void*, void*) noexcept{}



#endif

typedef unsigned char*      addr_t;
typedef const unsigned char*constaddr_t;
typedef uint16_t            io_t;
typedef unsigned char*      msr_t;
struct pci_t{
  uint8_t bus, slot, func;
  pci_t() : bus(0),slot(0),func(0){}
  pci_t(uint8_t b,uint8_t s, uint8_t f):bus(b),slot(s),func(f){}
};

namespace std{
  typedef intptr_t            ptrdiff_t;
}


typedef uint32_t Dir;

#ifndef NULL
#define NULL 0
#endif

#ifndef offsetof
 #define offsetof(_t,_x)  __builtin_offsetof(_t, _x)
#endif


#define ALIGN(x) __attribute__((aligned(x)))


