.text
	addi $v0, $zero, 2		#seta 2 em $v0 (para que syscall imprima um float)
	lwc1 $f0, PI			#seta pi em $f0
	lwc1 $f2, radius		#seta radius em $f2
	mul.s $f2, $f2, $f2		#eleva o raio ao quadrado e salva em $f2
	mul.s $f12, $f0, $f2		#faz raio² x pi e salva em $f12
	syscall				#chama a system call (imprimindo um float)
.data
	PI: .float 3.1416		#cria uma posição de memória para guardar o valor de pi
	radius: .float 2.5		#cria uma posição de memória pra guardar o calor do raio