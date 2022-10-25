.extern stage1,prints,fgets
.text

	ldr r0,=s1
	mov r1,#20
	mov r2,#0
	bl fgets
	ldrb r7,[r0]	     
					@r7 has l1 size 
	ldr r0,=s2
	mov r1,#20
	mov r2,#0
	bl fgets
	ldrb r8,[r0]
				    @r8 has l2 size
	
					 
	mov r9,#48
	ldr r5,=p1    
        ldr r6,=l1			@r6 stored l1 and r5 stored the pointer for l1 i.e. p1
	mov r1,#20
	mov r2,#0
forl1:
	cmp r9,r7
	beq exit
	mov r0,r6
	bl fgets			
	str r0,[r5],#4
	add r6,r6,#20
	add r9,r9,#1
	b forl1

exit:
	mov r9,#48
	ldr r5,=p2
	ldr r6,=l2
	mov r1,#20		@storing pointer for list2
	mov r2,#0
forl2:
	cmp r9,r8
	beq exit2
	mov r0,r6
	bl fgets
	str r0,[r5],#4		@taking list2 from user
	add r6,r6,#20
	add r9,r9,#1
	b forl2

exit2:
	ldr r0,=cmp
	mov r1,#20
	mov r2,#0
	bl fgets
	ldrb r5,[r0] 
	sub r5,r5,#48        
					@r5 has cmp type
	ldr r0,=dup
	mov r1,#20
	mov r2,#0
	bl fgets
	ldrb r6,[r0]	
	sub r6,r6,#48        

					@r6 has duplicate type
												
	ldr r1,=p1
	ldr r2,=p2
	mov r0,r5
	mov r5,r6
	bl stage2

	mov r7,r0
	mov r5,r1
	mov r9,#0
forl3:
	cmp r9,r7
	beq exit3
	ldr r0,[r5],#4
	bl prints
	add r9,r9,#1
	b forl3
exit3:
	add r7,r7,#48
	ldr r9, =num
    str r7, [r9]
    ldr r0,=num
    bl prints
	mov	r0, #0x18
	swi	0x123456


stage2:  @inputs are r1- pointer to list1 ,r2- pointer to list2,r5 -duplicate removal,r8- comparision mode
	stmfd	sp!, {r2-r10,lr}
	ldr r6,=pointer
	mov r7,#0
	bl loadpointer1
	bl loadpointer2
	
callstage1:
	bl stage1
	cmp r0,#1
	beq greater
	bgt equal
	blt lesser

greater:
	bl storepointer2
	bl loadpointer2
	b callstage1

lesser:
	bl storepointer1
	bl loadpointer1
	b callstage1

equal:
	cmp r5,#0
	beq skip
	bl storepointer2

skip:
	bl storepointer1
	bl loadpointer1
	bl loadpointer2
	b callstage1

endlist1:
	bl storepointer2
	ldr r4,[r2],#4
	cmp r4,#0
	beq exits
	b endlist1

endlist2:
	bl storepointer1
	ldr r3,[r1],#4
	cmp r3,#0
	beq exits
	b endlist2

exits:
	mov r0,r7
	ldr r1,=pointer
	ldmfd	sp!, {r2-r10,pc}


loadpointer1:	
	ldr r3,[r1],#4
	cmp r3,#0
	beq endlist1
	mov pc,lr

loadpointer2:	
	ldr r4,[r2],#4
	cmp r4,#0
	beq endlist2
	mov pc,lr
 
storepointer1:
	str r3,[r6],#4
	add r7,r7,#1
	mov pc,lr

storepointer2:
	str r4,[r6],#4
	add r7,r7,#1
	mov pc,lr

merge_sort:
	


	
.data
	pointer: .space 400
	s1:  .space 10
	s2:  .space 10
	cmp: .space 10
	dup: .space 10
	num: .word 	0
	l1:  .space 10000   @ for storing strings
    p1:  .space 500     @ for storing pointers
	l2:  .space 10000   @ for storing strings
    p2:  .space 500     @ for storing pointers
.end
