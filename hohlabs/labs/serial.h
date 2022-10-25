#pragma once

#include "util/io.h"

namespace serial{

  static inline bool is_transmitter_ready(io_t baseport){
    //insert your code here
    uint8_t a = io::read8(baseport, 5);
    int b = (int) a ;
    int c = b>>5;
    if(c%2==1){
       return 1;
    }
    return 0;
  }

  static inline void writechar(uint8_t c, io_t baseport){
    //insert your code here
    io::write8(baseport, 0, c);
    return;
  }

} //end serial
