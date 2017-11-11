add 	$t0, 	$zero, 	$zero
addi	$s0,	$t0, 	20
lw		$zero, 	kga0($s0)
beq		$s0,	$s6,	oi		
j 		LOOP
jr 		$zero