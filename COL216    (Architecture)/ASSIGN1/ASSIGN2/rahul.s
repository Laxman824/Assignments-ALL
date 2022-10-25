.extern prints,fgets,strlen
.text

ldr r0, =st0
mov r1, #40
mov r2, #0
bl fgets
ldrb r0,[r0]
cmp r0,#48   			@compare teh r0 wiht 48(ascill 0)
beq casesen  			@take as sensitive
bne case_inse			@in sensitive 
 

casesen:
ldr r3, =st1   
mov r0,r3
mov r1, #40
mov r2, #0
bl fgets
mov r3,r0      @ r3 string1

ldr r5, =st2
mov r0,r5
mov r1, #40
mov r2, #0
bl fgets
mov r5,r0       @ r5 string2
b line

line:
ldrb r4,[r3]   @loadig byte by byte
ldrb r6,[r5]
b compare

compare:
cmp r4,r6		@compare dng
blt lessthan
bgt greaterthan

beq line1        @ oka vela character equal aithe next charactert ki incerment in line1 

line1:
adds r3,r3,#1       @incrememnt 
adds r5,r5,#1
b line

lessthan:
ldr r0, =yes
bl prints
mov	r0, #0x18
swi	0x123456	@ exit

greaterthan:
ldr r0, =no
bl prints
mov	r0, #0x18
swi	0x123456	@ exit


case_inse:
ldr r3, =st1   		@ insensitive aithe ekkada povali
mov r0,r3
mov r1, #40
mov r2, #0
bl fgets
mov r3,r0

ldr r5, =st2
mov r0,r5
mov r1, #40
mov r2, #0
bl fgets
mov r5,r0
b line_case

line_case:		@load by byte 
ldrb r4,[r3]
ldrb r6,[r5]
b frst_compare

frst_compare:		@smaller aithe change to  
cmp r4,#96
blt inc                
bgt sec_compare

sec_compare:           @ 2nd string cmpare 
cmp r6,#96 
blt inc2
bgt main_compare

inc:
add r4,r4,#32
b main_compare

inc2:
add r6,r6,#32
b main_compare

main_compare:
cmp r4,r6
blt lessthan
bgt greaterthan
beq line1_case

line1_case:
adds r3,r3,#1
adds r5,r5,#1
b line_case

mov r0,#0x18
swi 0x123456
.data

yes: .asciz "first string is less than second string\r\n"
no: .asciz "first string is greater than second string\r\n"
noyes: .asciz "first string is equal second string\r\n"
st0: .space 10
st1: .space 10
st2: .space 10
.end
