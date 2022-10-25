#include "util/io.h"

namespace vgatext{

   static inline void writechar(int loc, uint8_t c, uint8_t bg, uint8_t fg, addr_t base){
     //your code goes here
    mmio::write8(base, 2*loc, c);

   }

}//namespace vgatext
