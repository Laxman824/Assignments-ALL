#pragma once

#include "util/config.h"
#include "util/strong_typedef.hpp"

namespace debug{
  enum { enable=1 };
} //namespace debug

extern "C" void die();



//
//
// Helper function: Bits
//
template<bool algo, int N, typename T>
struct ones_t{
  static T ones() {
    static_assert(N<(sizeof(T)*8), "Invalid args");
    return (T(1)<<N)-1;
  }

};

template<int N, typename T>
struct ones_t<true,N,T>{
  static T ones() {
    static_assert(N==(sizeof(T)*8), "Invalid args");
    return ~T(0);
  }
};

static inline bool test_bit(uint32_t x,int k){ return x&(1<<k);}
template<int A, int B, typename T>
static inline T ones() {
  return ones_t<A-B+1 == (sizeof(T)*8),A-B+1,T>::ones();
}
template<int A,int B,typename T>
static inline T  get_bits(T x){
  static_assert( ((sizeof(T)*8)>A) && (A>=B),"Invalid args");
  return (x>>B)&ones<A,B,T>();
}
template<int A,int B,typename T,typename T2>
static inline T  set_bits(T x,T2 y){
  static_assert( ((sizeof(T)*8)>A) && (A>=B),"Invalid args");
  return (x&~(ones<A,B,T>()<<B)) | (T(y&ones<A,B,T2>())<<B) ;
}
//template<typename T>
//static inline T  get_bits(T x,int A,int B){ return (x>>B)&ones<T>(A,B);}








//
//
// hoh_debug and hoh_assert
//
//
//



#ifdef SIMULATOR

#include <iostream>

#define hoh_debug(x)      do{ if(debug::enable){ std::cout<<x<<'\n';} }while(false)
#define hoh_debug_nl(x)   do{ if(debug::enable){ std::cout<<x;      } }while(false)
#define hoh_debug_if(c,x) do{ if(c){             std::cout<<x<<'\n';} }while(false)
#define hoh_assert(c,x)   do{ if(c){}else{       std::cout<<"ASSERT failed: "<<x<<"\nCondition:"<<#c<<"\nLocation: "<<__FILE__<<":"<<__LINE__<<":"<<__PRETTY_FUNCTION__<<"\n"; die();} }while(false)

typedef std::ostream dout_t;
static inline dout_t& operator<<(dout_t& os,constaddr_t x){
  return os<<(uintptr_t)x;
}

#else

namespace serial{ void print(char x); }

#define hoh_debug(x)      do{ if(debug::enable){ dout_t dout; dout<<x<<'\n';}  }while(false)
#define hoh_debug_nl(x)   do{ if(debug::enable){ dout_t dout; dout<<x;      }  }while(false)
#define hoh_debug_if(c,x) do{ if(c){             dout_t dout; dout<<x<<'\n';}  }while(false)
#define hoh_assert(c,x)   do{ if(c){}else{       dout_t dout; dout<<"ASSERT failed: "<<x<<"\nCondition:"<<#c<<"\nLocation: "<<__FILE__<<":"<<__LINE__<<":"<<__PRETTY_FUNCTION__<<"\n"; die();} }while(false)


struct dout_t{};

static inline dout_t& operator <<(dout_t& os,char x){
  if(debug::enable){
    serial::print(x);
  }
  return os;
}


static inline dout_t& operator<<(dout_t& os,const char* s){
  for(int i=0;s[i]!=0;i++){
    os<<s[i];
  }
  return os;
}

static inline char hex2char(unsigned int i){
  return  "0123456789abcdef"[i%16];
}

static inline dout_t& operator<<(dout_t& os,uint8_t x){
  enum {max=sizeof(uint8_t)*2+1};
  char a[max];
  for(int i=0;i<max-1;i++){
    a[max-1-i-1]=hex2char(x%16);
    x=x/16;
  }
  a[max-1]='\0';
  return os<<a;
}


static inline dout_t& operator<<(dout_t& os,uint16_t x){
  enum {max=sizeof(uint16_t)*2+1};
  char a[max];
  for(int i=0;i<max-1;i++){
    a[max-1-i-1]=hex2char(x%16);
    x=x/16;
  }
  a[max-1]='\0';
  return os<<a;
}


static inline dout_t& operator<<(dout_t& os,uint32_t x){
  enum {max=sizeof(uint32_t)*2+1};
  char a[max];
  for(int i=0;i<max-1;i++){
    a[max-1-i-1]=hex2char(x%16);
    x=x/16;
  }
  a[max-1]='\0';
  return os<<a;
}

static inline dout_t& operator<<(dout_t& os,uint64_t x){
  return os<<uint32_t(get_bits<63,32>(x))<<uint32_t(get_bits<31,0>(x));
}

static inline dout_t& operator<<(dout_t& os,constaddr_t x){
  return os<<(uintptr_t)x;
}


static inline dout_t& operator<<(dout_t& os,int32_t x){
  if(x<0){
    os<<"-";
    x=-x;
  }
  return os<<uint32_t(x);
}


#endif

