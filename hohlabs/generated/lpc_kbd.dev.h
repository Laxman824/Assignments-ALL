#pragma once
/*
 * Device Definition: LPC Keyboard
 * 
 * Automatically Generated from Spec.
 * Using a modified mackerel.
 * 
 * DO NOT EDIT!
 */
#include "util/debug.h"
#include "util/io.h"
#undef __DN
#define __DN(x) lpc_kbd ## _ ## x
/*
 * Constants defn: lpc_kbd.cmd (Command)
 *  - no width specified
 */
HOH_STRONG_TYPEDEF( uint8_t , lpc_kbd_cmd_t);
#define lpc_kbd_rd_ccmd ((lpc_kbd_cmd_t)0x20)
#define lpc_kbd_wr_ccmd ((lpc_kbd_cmd_t)0x60)
#define lpc_kbd_aux_disable ((lpc_kbd_cmd_t)0xa7)
#define lpc_kbd_aux_enable ((lpc_kbd_cmd_t)0xa8)
#define lpc_kbd_kbd_disable ((lpc_kbd_cmd_t)0xad)
#define lpc_kbd_kbd_enable ((lpc_kbd_cmd_t)0xae)
#define lpc_kbd_read_input ((lpc_kbd_cmd_t)0xc0)
#define lpc_kbd_poll_input_low ((lpc_kbd_cmd_t)0xc1)
#define lpc_kbd_poll_input_high ((lpc_kbd_cmd_t)0xc2)
#define lpc_kbd_read_output ((lpc_kbd_cmd_t)0xd0)
#define lpc_kbd_write_output ((lpc_kbd_cmd_t)0xd1)
#define lpc_kbd_write_kbd_out ((lpc_kbd_cmd_t)0xd2)
#define lpc_kbd_write_aux_out ((lpc_kbd_cmd_t)0xd3)
#define lpc_kbd_write_aux ((lpc_kbd_cmd_t)0xd4)

static inline const char* lpc_kbd_cmd_describe(lpc_kbd_cmd_t _e) __attribute__ ((always_inline));
static inline const char* lpc_kbd_cmd_describe(lpc_kbd_cmd_t _e)
{
    switch ((uint8_t )(_e)) {
    case (uint8_t )(0x20):
        return("rd_ccmd: Read controller command byte");
    case (uint8_t )(0x60):
        return("wr_ccmd: Write controller command byte");
    case (uint8_t )(0xa7):
        return("aux_disable: Disable auxiliary device interface");
    case (uint8_t )(0xa8):
        return("aux_enable: Enable auxiliary device interface");
    case (uint8_t )(0xad):
        return("kbd_disable: Disable keyboard interface");
    case (uint8_t )(0xae):
        return("kbd_enable: Enable keyboard interface");
    case (uint8_t )(0xc0):
        return("read_input: Read input port");
    case (uint8_t )(0xc1):
        return("poll_input_low: Poll input port low");
    case (uint8_t )(0xc2):
        return("poll_input_high: Poll input port high");
    case (uint8_t )(0xd0):
        return("read_output: Read output port");
    case (uint8_t )(0xd1):
        return("write_output: Write output port");
    case (uint8_t )(0xd2):
        return("write_kbd_out: Write keyboard output buffer");
    case (uint8_t )(0xd3):
        return("write_aux_out: Write auxiliary device output buffer");
    case (uint8_t )(0xd4):
        return("write_aux: Write to auxiliary device");
    default:
        return(NULL);
    }
}

static inline dout_t& operator<<(dout_t& _os, lpc_kbd_cmd_t _e) __attribute__ ((always_inline));
static inline dout_t& operator<<(dout_t& _os, lpc_kbd_cmd_t _e)
{
    const char* d = lpc_kbd_cmd_describe(_e);
    if (d) {
        return(_os << d);
    } else {
        return((((_os << ("Unknown constant:")) << ("lpc_kbd_cmd_t")) << ("value:")) << ((uint64_t )(_e)));
    }
}

/*
 * Register type: lpc_kbd_status_t
 * Description: Implicit type of Status register
 * Fields:
 *   obf	(size 1, offset 0, init 0):	RO	Output buffer full
 *   ibf	(size 1, offset 1, init 0):	RO	Input buffer full
 *   sf	(size 1, offset 2, init 0):	RO	System flag
 *   cd	(size 1, offset 3, init 0):	RO	Command/data
 *   is	(size 1, offset 4, init 0):	RO	Inhibit switch
 *   aobf	(size 1, offset 5, init 0):	RO	Auxiliary device output buffer full
 *   timeout	(size 1, offset 6, init 0):	RO	General timeout
 *   perr	(size 1, offset 7, init 0):	RO	Parity error
 */
HOH_STRONG_TYPEDEF( uint8_t , lpc_kbd_status_t);
#define lpc_kbd_status_default (lpc_kbd_status_t )(0x0)
static inline uint8_t lpc_kbd_status_obf_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_obf_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x1) >> 0));
}

static inline lpc_kbd_status_t lpc_kbd_status_obf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_obf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0xfe) | (0x1 & (((lpc_kbd_status_t )(_fieldval)) << 0))));
}

static inline uint8_t lpc_kbd_status_ibf_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_ibf_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x2) >> 1));
}

static inline lpc_kbd_status_t lpc_kbd_status_ibf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_ibf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0xfd) | (0x2 & (((lpc_kbd_status_t )(_fieldval)) << 1))));
}

static inline uint8_t lpc_kbd_status_sf_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_sf_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x4) >> 2));
}

static inline lpc_kbd_status_t lpc_kbd_status_sf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_sf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0xfb) | (0x4 & (((lpc_kbd_status_t )(_fieldval)) << 2))));
}

static inline uint8_t lpc_kbd_status_cd_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_cd_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x8) >> 3));
}

static inline lpc_kbd_status_t lpc_kbd_status_cd_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_cd_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0xf7) | (0x8 & (((lpc_kbd_status_t )(_fieldval)) << 3))));
}

static inline uint8_t lpc_kbd_status_is_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_is_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x10) >> 4));
}

static inline lpc_kbd_status_t lpc_kbd_status_is_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_is_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0xef) | (0x10 & (((lpc_kbd_status_t )(_fieldval)) << 4))));
}

static inline uint8_t lpc_kbd_status_aobf_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_aobf_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x20) >> 5));
}

static inline lpc_kbd_status_t lpc_kbd_status_aobf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_aobf_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0xdf) | (0x20 & (((lpc_kbd_status_t )(_fieldval)) << 5))));
}

static inline uint8_t lpc_kbd_status_timeout_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_timeout_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x40) >> 6));
}

static inline lpc_kbd_status_t lpc_kbd_status_timeout_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_timeout_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0xbf) | (0x40 & (((lpc_kbd_status_t )(_fieldval)) << 6))));
}

static inline uint8_t lpc_kbd_status_perr_extract(lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_perr_extract(lpc_kbd_status_t _regval)
{
    return((uint8_t )((_regval & 0x80) >> 7));
}

static inline lpc_kbd_status_t lpc_kbd_status_perr_insert(lpc_kbd_status_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_perr_insert(lpc_kbd_status_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_status_t )((_regval & 0x7f) | (0x80 & (((lpc_kbd_status_t )(_fieldval)) << 7))));
}

static inline dout_t& operator<<(dout_t& _os, lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline dout_t& operator<<(dout_t& _os, lpc_kbd_status_t _regval)
{
    ((_os << (" obf =\t")) << lpc_kbd_status_obf_extract(_regval)) << ("\t(Output buffer full)\n");
    ((_os << (" ibf =\t")) << lpc_kbd_status_ibf_extract(_regval)) << ("\t(Input buffer full)\n");
    ((_os << (" sf =\t")) << lpc_kbd_status_sf_extract(_regval)) << ("\t(System flag)\n");
    ((_os << (" cd =\t")) << lpc_kbd_status_cd_extract(_regval)) << ("\t(Command/data)\n");
    ((_os << (" is =\t")) << lpc_kbd_status_is_extract(_regval)) << ("\t(Inhibit switch)\n");
    ((_os << (" aobf =\t")) << lpc_kbd_status_aobf_extract(_regval)) << ("\t(Auxiliary device output buffer full)\n");
    ((_os << (" timeout =\t")) << lpc_kbd_status_timeout_extract(_regval)) << ("\t(General timeout)\n");
    ((_os << (" perr =\t")) << lpc_kbd_status_perr_extract(_regval)) << ("\t(Parity error)\n");
    return(_os);
}

/*
 * Register type: lpc_kbd_command_t
 * Description: Implicit type of Command register
 * Fields:
 *   cmd	(size 8, offset 0, init 0):	WO	Command
 */
HOH_STRONG_TYPEDEF( uint8_t , lpc_kbd_command_t);
#define lpc_kbd_command_default (lpc_kbd_command_t )(0x0)
static inline lpc_kbd_cmd_t lpc_kbd_command_cmd_extract(lpc_kbd_command_t _regval) __attribute__ ((always_inline));
static inline lpc_kbd_cmd_t lpc_kbd_command_cmd_extract(lpc_kbd_command_t _regval)
{
    return((lpc_kbd_cmd_t )((_regval & 0xff) >> 0));
}

static inline lpc_kbd_command_t lpc_kbd_command_cmd_insert(lpc_kbd_command_t _regval, lpc_kbd_cmd_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_command_t lpc_kbd_command_cmd_insert(lpc_kbd_command_t _regval, lpc_kbd_cmd_t _fieldval)
{
    return((lpc_kbd_command_t )((_regval & 0x0) | (0xff & (((lpc_kbd_command_t )(_fieldval)) << 0))));
}

static inline dout_t& operator<<(dout_t& _os, lpc_kbd_command_t _regval) __attribute__ ((always_inline));
static inline dout_t& operator<<(dout_t& _os, lpc_kbd_command_t _regval)
{
    ((_os << (" cmd =\t")) << lpc_kbd_command_cmd_extract(_regval)) << ("\t(Command)\n");
    return(_os);
}

/*
 * Register type: lpc_kbd_ccmd_t
 * Description: Controller command byte
 * Fields:
 *   kbd_int	(size 1, offset 0, init 0):	NOATTR	Enable keyboard interrupt
 *   aux_int	(size 1, offset 1, init 0):	NOATTR	Enable auxiliary interrupt
 *   sysflg	(size 1, offset 2, init 0):	NOATTR	System flag
 *   _anon3	(size 1, offset 3, init 0):	RSVD	_
 *   kbd_dis	(size 1, offset 4, init 0):	NOATTR	Disable keyboard
 *   aux_dis	(size 1, offset 5, init 0):	NOATTR	Disable auxiliary device
 *   kbd_xl	(size 1, offset 6, init 0):	NOATTR	Keyboard translate
 *   _anon7	(size 1, offset 7, init 0):	RSVD	_
 */
HOH_STRONG_TYPEDEF( uint8_t , lpc_kbd_ccmd_t);
#define lpc_kbd_ccmd_default (lpc_kbd_ccmd_t )(0x0)
static inline uint8_t lpc_kbd_ccmd_kbd_int_extract(lpc_kbd_ccmd_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_ccmd_kbd_int_extract(lpc_kbd_ccmd_t _regval)
{
    return((uint8_t )((_regval & 0x1) >> 0));
}

static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_kbd_int_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_kbd_int_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_ccmd_t )((_regval & 0xfe) | (0x1 & (((lpc_kbd_ccmd_t )(_fieldval)) << 0))));
}

static inline uint8_t lpc_kbd_ccmd_aux_int_extract(lpc_kbd_ccmd_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_ccmd_aux_int_extract(lpc_kbd_ccmd_t _regval)
{
    return((uint8_t )((_regval & 0x2) >> 1));
}

static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_aux_int_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_aux_int_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_ccmd_t )((_regval & 0xfd) | (0x2 & (((lpc_kbd_ccmd_t )(_fieldval)) << 1))));
}

static inline uint8_t lpc_kbd_ccmd_sysflg_extract(lpc_kbd_ccmd_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_ccmd_sysflg_extract(lpc_kbd_ccmd_t _regval)
{
    return((uint8_t )((_regval & 0x4) >> 2));
}

static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_sysflg_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_sysflg_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_ccmd_t )((_regval & 0xfb) | (0x4 & (((lpc_kbd_ccmd_t )(_fieldval)) << 2))));
}

static inline uint8_t lpc_kbd_ccmd_kbd_dis_extract(lpc_kbd_ccmd_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_ccmd_kbd_dis_extract(lpc_kbd_ccmd_t _regval)
{
    return((uint8_t )((_regval & 0x10) >> 4));
}

static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_kbd_dis_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_kbd_dis_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_ccmd_t )((_regval & 0xef) | (0x10 & (((lpc_kbd_ccmd_t )(_fieldval)) << 4))));
}

static inline uint8_t lpc_kbd_ccmd_aux_dis_extract(lpc_kbd_ccmd_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_ccmd_aux_dis_extract(lpc_kbd_ccmd_t _regval)
{
    return((uint8_t )((_regval & 0x20) >> 5));
}

static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_aux_dis_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_aux_dis_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_ccmd_t )((_regval & 0xdf) | (0x20 & (((lpc_kbd_ccmd_t )(_fieldval)) << 5))));
}

static inline uint8_t lpc_kbd_ccmd_kbd_xl_extract(lpc_kbd_ccmd_t _regval) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_ccmd_kbd_xl_extract(lpc_kbd_ccmd_t _regval)
{
    return((uint8_t )((_regval & 0x40) >> 6));
}

static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_kbd_xl_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval) __attribute__ ((always_inline));
static inline lpc_kbd_ccmd_t lpc_kbd_ccmd_kbd_xl_insert(lpc_kbd_ccmd_t _regval, uint8_t _fieldval)
{
    return((lpc_kbd_ccmd_t )((_regval & 0xbf) | (0x40 & (((lpc_kbd_ccmd_t )(_fieldval)) << 6))));
}

static inline dout_t& operator<<(dout_t& _os, lpc_kbd_ccmd_t _regval) __attribute__ ((always_inline));
static inline dout_t& operator<<(dout_t& _os, lpc_kbd_ccmd_t _regval)
{
    ((_os << (" kbd_int =\t")) << lpc_kbd_ccmd_kbd_int_extract(_regval)) << ("\t(Enable keyboard interrupt)\n");
    ((_os << (" aux_int =\t")) << lpc_kbd_ccmd_aux_int_extract(_regval)) << ("\t(Enable auxiliary interrupt)\n");
    ((_os << (" sysflg =\t")) << lpc_kbd_ccmd_sysflg_extract(_regval)) << ("\t(System flag)\n");
    ((_os << (" kbd_dis =\t")) << lpc_kbd_ccmd_kbd_dis_extract(_regval)) << ("\t(Disable keyboard)\n");
    ((_os << (" aux_dis =\t")) << lpc_kbd_ccmd_aux_dis_extract(_regval)) << ("\t(Disable auxiliary device)\n");
    ((_os << (" kbd_xl =\t")) << lpc_kbd_ccmd_kbd_xl_extract(_regval)) << ("\t(Keyboard translate)\n");
    return(_os);
}

/*
 * Device representation structure
 */
struct __DN(t) {
    io_t base;
    uint8_t output_shadow;
    lpc_kbd_command_t command_shadow;
};

/*
 * Initial register values (currently 0)
 */
enum lpc_kbd_initials {
    lpc_kbd_input_initial = 0x0,
    lpc_kbd_status_initial = 0x0,
    lpc_kbd_output_initial = 0x0,
    lpc_kbd_command_initial = 0x0
};

/*
 * Device Initialization function
 */
static inline void lpc_kbd_initialize(__DN(t) *_dev, io_t base) __attribute__ ((always_inline));
static inline void lpc_kbd_initialize(__DN(t) *_dev, io_t base)
{
    _dev->base = base;
}

/*
 * No user-defined spaces
 */
/*
 * Register input: Input
 * Type: lpc_kbd.uint8 (primitive type)
 */
static inline uint8_t lpc_kbd_input_rawrd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_input_rawrd(__DN(t) *_dev)
{
    return((uint8_t )(io::read8(_dev->base, 0x0)));
}

static inline uint8_t lpc_kbd_input_rd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_input_rd(__DN(t) *_dev)
{
    return((uint8_t )(io::read8(_dev->base, 0x0)));
}

static inline void lpc_kbd_input_rawwr(__DN(t) *_dev, uint8_t _regval) __attribute__ ((always_inline));
static inline void lpc_kbd_input_rawwr(__DN(t) *_dev, uint8_t _regval)
{
    io::write8(_dev->base, 0x0, _regval);
}

// Register input is not writeable
static inline dout_t& lpc_kbd_input_pr(dout_t& _os, __DN(t) *_dev) __attribute__ ((always_inline));
static inline dout_t& lpc_kbd_input_pr(dout_t& _os, __DN(t) *_dev)
{
    uint8_t _regval = (uint8_t )(io::read8(_dev->base, 0x0));
    _os << ("Register input (Input): ");
    ((_os << ("\t")) << _regval) << ("");
    return(_os);
}

/*
 * Register status: Status
 * Type: lpc_kbd.status (Implicit type of Status register)
 *   obf	(size 1, offset 0, init 0):	RO	Output buffer full
 *   ibf	(size 1, offset 1, init 0):	RO	Input buffer full
 *   sf	(size 1, offset 2, init 0):	RO	System flag
 *   cd	(size 1, offset 3, init 0):	RO	Command/data
 *   is	(size 1, offset 4, init 0):	RO	Inhibit switch
 *   aobf	(size 1, offset 5, init 0):	RO	Auxiliary device output buffer full
 *   timeout	(size 1, offset 6, init 0):	RO	General timeout
 *   perr	(size 1, offset 7, init 0):	RO	Parity error
 */
static inline lpc_kbd_status_t lpc_kbd_status_rawrd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_rawrd(__DN(t) *_dev)
{
    return((lpc_kbd_status_t )(io::read8(_dev->base, 0x4)));
}

static inline lpc_kbd_status_t lpc_kbd_status_rd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline lpc_kbd_status_t lpc_kbd_status_rd(__DN(t) *_dev)
{
    return((lpc_kbd_status_t )(io::read8(_dev->base, 0x4)));
}

static inline void lpc_kbd_status_rawwr(__DN(t) *_dev, lpc_kbd_status_t _regval) __attribute__ ((always_inline));
static inline void lpc_kbd_status_rawwr(__DN(t) *_dev, lpc_kbd_status_t _regval)
{
    io::write8(_dev->base, 0x4, _regval);
}

// Register status is not writeable
static inline dout_t& lpc_kbd_status_pr(dout_t& _os, __DN(t) *_dev) __attribute__ ((always_inline));
static inline dout_t& lpc_kbd_status_pr(dout_t& _os, __DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    _os << ("Register status (Status): ");
    _os << ("\n");
    ((_os << (" obf =\t")) << lpc_kbd_status_obf_extract(_regval)) << ("\t(Output buffer full)\n");
    ((_os << (" ibf =\t")) << lpc_kbd_status_ibf_extract(_regval)) << ("\t(Input buffer full)\n");
    ((_os << (" sf =\t")) << lpc_kbd_status_sf_extract(_regval)) << ("\t(System flag)\n");
    ((_os << (" cd =\t")) << lpc_kbd_status_cd_extract(_regval)) << ("\t(Command/data)\n");
    ((_os << (" is =\t")) << lpc_kbd_status_is_extract(_regval)) << ("\t(Inhibit switch)\n");
    ((_os << (" aobf =\t")) << lpc_kbd_status_aobf_extract(_regval)) << ("\t(Auxiliary device output buffer full)\n");
    ((_os << (" timeout =\t")) << lpc_kbd_status_timeout_extract(_regval)) << ("\t(General timeout)\n");
    ((_os << (" perr =\t")) << lpc_kbd_status_perr_extract(_regval)) << ("\t(Parity error)\n");
    return(_os);
}

static inline uint8_t lpc_kbd_status_obf_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_obf_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_obf_extract(_regval));
}

static inline uint8_t lpc_kbd_status_ibf_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_ibf_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_ibf_extract(_regval));
}

static inline uint8_t lpc_kbd_status_sf_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_sf_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_sf_extract(_regval));
}

static inline uint8_t lpc_kbd_status_cd_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_cd_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_cd_extract(_regval));
}

static inline uint8_t lpc_kbd_status_is_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_is_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_is_extract(_regval));
}

static inline uint8_t lpc_kbd_status_aobf_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_aobf_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_aobf_extract(_regval));
}

static inline uint8_t lpc_kbd_status_timeout_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_timeout_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_timeout_extract(_regval));
}

static inline uint8_t lpc_kbd_status_perr_rdf(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_status_perr_rdf(__DN(t) *_dev)
{
    lpc_kbd_status_t _regval = (lpc_kbd_status_t )(io::read8(_dev->base, 0x4));
    return(lpc_kbd_status_perr_extract(_regval));
}

/*
 * Register output: Output
 * Type: lpc_kbd.uint8 (primitive type)
 */
static inline uint8_t lpc_kbd_output_rawrd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_output_rawrd(__DN(t) *_dev)
{
    return((uint8_t )(io::read8(_dev->base, 0x0)));
}

static inline uint8_t lpc_kbd_output_rd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline uint8_t lpc_kbd_output_rd(__DN(t) *_dev)
{
    return(_dev->output_shadow);
}

static inline void lpc_kbd_output_rawwr(__DN(t) *_dev, uint8_t _regval) __attribute__ ((always_inline));
static inline void lpc_kbd_output_rawwr(__DN(t) *_dev, uint8_t _regval)
{
    io::write8(_dev->base, 0x0, _regval);
}

static inline void lpc_kbd_output_wr(__DN(t) *_dev, uint8_t _regval) __attribute__ ((always_inline));
static inline void lpc_kbd_output_wr(__DN(t) *_dev, uint8_t _regval)
{
    // No MB0 or RSVD fields present
    // No MB1 fields present
    // No pre-read of register required
    io::write8(_dev->base, 0x0, _regval);
}

static inline dout_t& lpc_kbd_output_pr(dout_t& _os, __DN(t) *_dev) __attribute__ ((always_inline));
static inline dout_t& lpc_kbd_output_pr(dout_t& _os, __DN(t) *_dev)
{
    uint8_t _regval = _dev->output_shadow;
    _os << ("Register output (Output): ");
    ((_os << ("\t")) << _regval) << (" (SHADOW copy)");
    return(_os);
}

/*
 * Register command: Command
 * Type: lpc_kbd.command (Implicit type of Command register)
 *   cmd	(size 8, offset 0, init 0):	WO	Command
 */
static inline lpc_kbd_command_t lpc_kbd_command_rawrd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline lpc_kbd_command_t lpc_kbd_command_rawrd(__DN(t) *_dev)
{
    return((lpc_kbd_command_t )(io::read8(_dev->base, 0x4)));
}

static inline lpc_kbd_command_t lpc_kbd_command_rd(__DN(t) *_dev) __attribute__ ((always_inline));
static inline lpc_kbd_command_t lpc_kbd_command_rd(__DN(t) *_dev)
{
    return(_dev->command_shadow);
}

static inline void lpc_kbd_command_rawwr(__DN(t) *_dev, lpc_kbd_command_t _regval) __attribute__ ((always_inline));
static inline void lpc_kbd_command_rawwr(__DN(t) *_dev, lpc_kbd_command_t _regval)
{
    io::write8(_dev->base, 0x4, _regval);
}

static inline void lpc_kbd_command_wr(__DN(t) *_dev, lpc_kbd_command_t _regval) __attribute__ ((always_inline));
static inline void lpc_kbd_command_wr(__DN(t) *_dev, lpc_kbd_command_t _regval)
{
    // No MB0 or RSVD fields present
    // No MB1 fields present
    // No pre-read of register required
    io::write8(_dev->base, 0x4, _regval);
}

static inline dout_t& lpc_kbd_command_pr(dout_t& _os, __DN(t) *_dev) __attribute__ ((always_inline));
static inline dout_t& lpc_kbd_command_pr(dout_t& _os, __DN(t) *_dev)
{
    lpc_kbd_command_t _regval = _dev->command_shadow;
    _os << ("Register command (Command): ");
    _os << ("\n");
    ((_os << (" cmd =\t")) << lpc_kbd_command_cmd_extract(_regval)) << ("\t(Command)\n");
    return(_os);
}

static inline lpc_kbd_cmd_t lpc_kbd_command_cmd_rd_shadow(__DN(t) *_dev) __attribute__ ((always_inline));
static inline lpc_kbd_cmd_t lpc_kbd_command_cmd_rd_shadow(__DN(t) *_dev)
{
    return(lpc_kbd_command_cmd_extract(_dev->command_shadow));
}

static inline void lpc_kbd_command_cmd_wrf(__DN(t) *_dev, lpc_kbd_cmd_t _fieldval) __attribute__ ((always_inline));
static inline void lpc_kbd_command_cmd_wrf(__DN(t) *_dev, lpc_kbd_cmd_t _fieldval)
{
    lpc_kbd_command_t _regval = (lpc_kbd_command_t )(0xff & (((lpc_kbd_command_t )(_fieldval)) << 0));
    // No pre-read of register required
    // No read of register shadow required
    // No MB0 fields present
    // No MB1 fields present
    io::write8(_dev->base, 0x4, _regval);
    _dev->command_shadow = _regval;
}

static inline dout_t& operator<<(dout_t& _os, __DN(t) *_dev) __attribute__ ((always_inline));
static inline dout_t& operator<<(dout_t& _os, __DN(t) *_dev)
{
    _os << ("-------------------------\n");
    _os << ("Dump of device lpc_kbd (LPC Keyboard):\n");
    lpc_kbd_input_pr(_os, _dev);
    lpc_kbd_status_pr(_os, _dev);
    lpc_kbd_output_pr(_os, _dev);
    lpc_kbd_command_pr(_os, _dev);
    _os << ("End of dump of device lpc_kbd\n");
    _os << ("-------------------------\n");
    return(_os);
}

#undef __DN
