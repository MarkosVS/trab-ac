.data
ags: dga   sdgadsg
ad: sga   sdg
adg: asd   gas
dgas: dga   da
.text
add 	$t0, 	$zero, 	$zero
addi	$s0,	$t0, 	20
lw		$t1, 	0($s0)
sw		$zero, 	40($s0)
beq		$s0,	$s6,	oi		
j 		LOOP
jr 		$zero