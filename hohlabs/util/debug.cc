#include "util/debug.h"
#include "util/io.h"
#include "labs/serial.h"

extern "C" void die(){
#ifdef SIMULATOR
  throw "DIE!!";
#else
  for(;;){
    asm volatile("cli;hlt;":::"memory");
  }
#endif
}

namespace serial{
  enum {
    serial_port=0x3f8,
  };


  void print(char c){
    for(;;){
      if(serial::is_transmitter_ready(serial_port)){
        break;
      }
    }
    serial::writechar(c,serial_port);
  }
}//namespace serial


