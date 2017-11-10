.text
		#	for(int i = 0; i < n; i++)
		#	a saída desejada é:
		#	0123456789
		#	cabou
		
		addi	$v0, $zero, 1		#seta 1 em $v0			(para que a syscall imprima um int)
		addi 	$s0, $zero, 0		#seta 0 em $s0 			(i = 0)
		lw 	$s1, n			#seta n em $s1 			(n = 10)
	LOOP:	bge 	$s0, $s1, EXIT		#sai do loop se $s0 >= $s1 	(quebra o loop se i >= n)
		add	$a0, $s0, $zero		#salva $s0 em $a0		(salva i em $a0 para que a syscall imprima i)
		syscall				#imprime $a0			(print i)
		addi	$s0, $s0, 1		#soma 1 em $s0 			(i++)
		j	LOOP			#salta para LOOP		(volta pro começo do loop)
	EXIT:	addi	$v0, $zero, 4		#seta 4 em $v0			(para que a syscall imprima uma string)
		la	$a0, fim		#salva fim em $a0		(salva o endereço de fim para que a syscall imprima fim)
		syscall				#imprime $a0			(print fim)

.data
	n: .word 10				#variavel n salva na memoria
	fim: .asciiz "\ncabou"			#variavel fim salva na memoria