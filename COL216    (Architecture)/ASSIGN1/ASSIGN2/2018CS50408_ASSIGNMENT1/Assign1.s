.extern prints,fgets
.text

ldr r0, =st1
mov r1, #40    
mov r2, #0    
bl fgets
add r3,r0,#0
ldr r0,=Noted
bl prints


ldr r0, =st2
mov r1, #40
mov r2, #0
bl fgets
add r4,r0,#0
ldr r0,=Noted
bl prints      

			@in r3 string1 and r4 string2 		
cmploop:
	ldrb r1,[r3] 
	ldrb r2,[r4] 
	cmp r1,#'\0'
	beq L
	cmp r2,#'\0'
	beq label
	cmp r1,r2
	bgt label
	blt L
	add r3,r3,#4
	add r4,r4,#4
	b cmploop
	
label:
	mov r1,#1  	@true  returns 1
	ldr r0,=Yes
	bl prints
	mov r0,#0x18
	swi 0x123456
L:			@false  returns 0
	cmp r2,#'\0'
	beq equal
	mov r1,#0 
	ldr r0,=No
	bl prints	
	mov r0,#0x18
	swi 0x123456

equal:
	mov r1,#2 	@ equal case  returns 2
	ldr r0,=str_equal
	bl prints
	mov r0,#0x18
	swi 0x123456
	

.data
Noted:
	.asciz "I have saved  ur string\n"

Yes:
	.asciz "Yes true   first string > second string\n"
No:
	.asciz "False first string < second string\n"
str_equal:
	.asciz "False first string = second string\n"
st1: .space 10
st2: .space 10
.end
