.global stage1
.extern prints,fgets
.text

ldr r0,=Noted
bl prints
ldr r0, =str1        		 @take iput store in str1 wheter 0 or 1
mov r1, #40    
mov r2, #0    
bl fgets			@prompts on terminal 

stage1:
stmfd	sp!, {r1-r10,lr}
cmp r0,#48   			 @cmpare with 0 
beq sens1 			 @if user gives 0 then selcted sensitive and jumps to sensitive function
b insens1       
				 @else user will give insensitive


				 @in r3 string1 and r4 string2 

	
greater:
	mov r0,#1  		@true  returns 1 so this is basically a[i]>b[i] 
	ldmfd	sp!, {r1-r10,pc}
	ldr r0,=Yes		@if its true then prints the yes stmt(yes greater)
	bl prints	
	mov r0,#0x18
	swi 0x123456
lesser:				@false  returns 0   this is basically a[i]<b[i]
	
	mov r0,#0   
	ldmfd	sp!, {r1-r10,pc}   
	ldr r0,=No
	bl prints	
	mov r0,#0x18  
	swi 0x123456    

equal:
	mov r0,#2 		@ equal case  returns 2
	ldmfd	sp!, {r1-r10,pc}
	ldr r0,=str_equal       @ prints equal msg 
	bl prints
	swi 0x123456  
	

sensitive:
	ldr r0,=note1
	ldr r3,=st1		@taking input r3=string1 and r4 =string2
	mov r0,r3
	mov r1,#40
	mov r0,#0x18
	mov r2,#0
	bl fgets       		
	mov r3,r0	
	
	
	
	ldr r4, =st2
	mov r0,r4 
	mov r1, #40             @stored srrings in r3,r4
	mov r2, #0
	bl fgets
	mov r4,r0		 @add r4,r0,#0
	b sens1
sens1:
	ldrb r5,[r3]        	 @ storing in r5 and loading bytes using addrss of string
	ldrb r6,[r4] 
	cmp r5,#0
	beq equal
	b comp

comp:
	cmp r5,r6		@compare both strings and sort acccordingly
	blt lesser
	bgt greater
	b goto1   

goto1:
	adds r3,r3,#1
	adds r4,r4,#1
	b sens1

				@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insensitive:
	ldr r3,=st1
	mov r0,r3
	mov r1,#40
	mov r2,#0
	bl fgets 
				@add r3,r0,#0      		
	mov r3,r0	

	ldr r4, =st2
	mov r0,r4
	mov r1, #40           	 @stored srrings in r3,r4
	mov r2, #0
	bl fgets
	mov r0,r4		@add r4,r0,#0
	b insens1
insens1:
	ldrb r5,[r3]         	@ storing in r5 and loaing bytes 
	ldrb r6,[r4] 
	cmp r5,#0
	beq equal
	b comp1

comp1:
	cmp r5,#96        	 @comapre with 96 ascii and  
	blt change1	   
comp2:
	cmp r6,#96
	blt change2
	bgt main_		@ as comparision of both strings done and changing to one case goto main_


change1:	
	add r5,r5,#32    
	b comp2
change2:
	add r6,r6,#32 
	b main_       		 @both strings are  changed to CAPITALs for same case...	
	
main_:
	
	cmp r5,r6
	blt lesser	
	bgt greater
	b  goto2
	

goto2:
	adds r3,r3,#1
	adds r4,r4,#1   
 	b insens1

mov r0,#0x18
swi 0x123456

.data
Noted:
	.asciz "enter 0 for case sensitive and 1 for incasensitive\n"
note1:
	.asciz "you have entered 0 for sensitive\n"

Yes:
	.asciz "first string > second string\n"
No:
	.asciz "first string < second string\n"
str_equal:
	.asciz " first string = second string\n"
str1: .space 10
st1: .space 10
st2: .space 10
.end