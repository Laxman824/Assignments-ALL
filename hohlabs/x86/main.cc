#include "labs/vgatext.h"
#include "util/debug.h"
#include "labs/keyboard.h"
#include "labs/shell.h"

struct core_t{
  addr_t        vgatext_base;   //=addr_t(0xb8000);
  int           vgatext_width;  //=80;
  int           vgatext_height; //=25;

  shellstate_t  shell_state;

  renderstate_t render_state; //separate renderstate from shellstate

  lpc_kbd_t     lpc_kbd;
};

static core_t   s_core;

extern "C" void core_boot();
extern "C" void core_init(core_t& core);
extern "C" void core_reset(core_t& core);
extern "C" void core_loop(core_t* p_core);
static     void core_loop_step(core_t& core);


//
// main
//
extern "C" void core_boot(){
  core_init(s_core);
  core_reset(s_core);

  { // Hello, world!
    const char* p="Hello, world!";
    for(int loc=0;*p;loc++,p++){
      vgatext::writechar(loc,*p,0,7,s_core.vgatext_base);
    }
  }

  hoh_debug("Hello, serial!");

  core_loop(&s_core);
}




//
// initialize core
//
extern "C" void core_init(core_t& core){
  core.vgatext_base   = (addr_t)0xb8000;
  core.vgatext_width  = 80;
  core.vgatext_height = 25;

  lpc_kbd_initialize(&core.lpc_kbd,0x60);

  shell_init(core.shell_state);

  shell_render(core.shell_state, core.render_state);
}


//
// reset core to a known state
//
extern "C" void core_reset(core_t& core){
  //serial::reset();
  //lpc_kbd::reset();
}


//
// main loop
//
extern "C" void core_loop(core_t* p_core){
  core_t& core=*p_core;
  hoh_debug("core_loop: esp=");
  render(core.render_state, core.vgatext_width, core.vgatext_height, core.vgatext_base);
  for(;;){
    core_loop_step(core);
  }
}



//
// step
//
static void core_loop_step(core_t& core){
  uint8_t       input;
  renderstate_t rendertmp;


  if(!lpc_kbd::has_key(core.lpc_kbd)){
    goto nokey;
  }

  input=lpc_kbd::get_key(core.lpc_kbd);

  if(input & 0x80){
    goto nokey;
  }

  // on key: pass the key to shell
  shell_update(input, core.shell_state);

nokey:
  // execute shell for one time slot to do the computation, if required.
  shell_step(core.shell_state);

  // shellstate -> renderstate: compute render state from shell state
  shell_render(core.shell_state, rendertmp);

  //
  // optimization: render() is pure/referentially transparent.
  // ie. r1 == r2 ==> render(r1) == render(r2).
  // ie. For the same render state, render() will always generate the same UI.
  // So, If the render state is same as last render state, let's optimize it away.
  //
  if(render_eq(core.render_state, rendertmp)){
    goto done;
  }

  // renderstate -> vgatext: given renderstate, render to vgatext buffer.
  core.render_state=rendertmp;
  render(core.render_state, core.vgatext_width, core.vgatext_height, core.vgatext_base);

done:
  return;
}
