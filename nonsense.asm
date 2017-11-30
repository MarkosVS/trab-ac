.data
ags: .ascii   "sd"
ad: .double   3.14159
adg: .asciiz   "gas"
dgas: .word   57

.text
loop: add 	$t0, 	$zero, 	$zero
haha: addi	$s0,	$t0, 	20
lw		$t1, 	0($s0)
sw		$zero, 	40($s0)
top: beq		$s0,	$s6,	oi		
j 		LOOP
jr 		$zero