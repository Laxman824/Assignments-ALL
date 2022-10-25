.extern prints,fgets    @to use another modules from outside we use extern
.text		     @code strts here

ldr r0, =st1         @loading  str1 in r0
mov r1, #40    	     @here giving r1 40 memory
mov r2, #0    	     @setting 0 for lettingknow of input in stdin
bl fgets	     @branch nad link fgets takes input from terminal
add r3,r0,#0         @adding pointer to oth location i.e in r3 first string address
ldr r0,=Noted	     @noted will store the noted strings which defined below
bl prints	     @to print the noted


ldr r0, =st2
mov r1, #40
mov r2, #0
bl fgets
add r4,r0,#0         @in r4 stored the string2
ldr r0,=Noted
bl prints      

	             @in r3 string1 and r4 string2 		
cmploop:
	ldrb r1,[r3]   @lding byte by byte str1 using r3 address
	ldrb r2,[r4]   @lding byte by byte str2 using r4 address
	cmp r1,#'\0'   @compare str1 with null
	beq L		@branch equal then goto L
	cmp r2,#'\0'   @@compare str2 with null
	beq label      @branch equal then goto Label
	cmp r1,r2      @cmpare both strings 
	bgt label	@branchifgreatrthan  goto Lbael
	blt L		
	add r3,r3,#4   @ str1++
	add r4,r4,#4	@str2++
	b cmploop	@again compare for condition in cmploop
	
label:
	mov r1,#1  	@true  returns 1
	ldr r0,=Yes
	bl prints
	mov r0,#0x18
	swi 0x123456
L:			@false  returns 0
	mov r1,#0 
	ldr r0,=No
	bl prints	
	mov r0,#0x18
	swi 0x123456
.data
Noted:
	.asciz "I have saved  ur string\n"

Yes:
	.asciz "Yes true   first string >= second string\n"
No:
	.asciz "False first string < second string\n"
st1: .space 10
st2: .space 10
.end



.end
