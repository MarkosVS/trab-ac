/**
*	@author Marcos Vinicius Sombra
*/


//classe validador
public class Validador{
	//atributos
	final String[] instTipoR = {"add", "addu", "and", "jr", "nor", "or", "slt", "sltu", "sll", "srl", "sub", 
		"subu"}; 

	final String[] instTipoI = {"addi", "addiu", "andi", "beq", "bne", "lbu", "lhu", "lui", "lw", "ori", "slti",
		"sltiu", "sb", "sh", "sw"};
	
	final String[] instTipoJ = {"j", "jal"};
	
	final String[] pseudoInst = {"blt", "bgt", "ble", "bge", "li", "la"};

	//métodos
	public boolean eValida(String instrucao){
		//checa se possui label e caso sim, ignora
		if(instrucao.contains(":"))
			instrucao = instrucao.substring(instrucao.indexOf(":") + 2);
		//pega apenas a primeira palavra da instrucao
		String inst = instrucao.split(" ")[0];

		//checa se é uma chamada ao sistema
		if(inst.equals("syscall"))
			return true;

		//variaveis para guardar os registradores
		String r1, r2, r3;
		//um loop pra cada array de tipos de instrução
		//retorna verdadeiro caso a instrução seja reconhecida
		//e falso caso não
		for(String s : instTipoJ)
			if(inst.equals(s))
				return true;

		//identifica o 1º registrador e armazena
		r1 = instrucao.split(" ")[1];
		r1 = r1.substring(0, r1.indexOf(','));

		for(String s : instTipoR){
			//caso seja uma instrução que tenta salvar em $zero, retorna false
			if(!s.equals("jr") && (r1.equals("$zero") || r1.equals("0")))
				return false;

			if(inst.equals(s))
				return true;
		}

		for(String s : instTipoI)
			if(inst.equals(s))
				return true;

		for(String s : pseudoInst)
			if(inst.equals(s))
				return true;
			
		//retorna falso caso não seja valida
		return false;
	}
}