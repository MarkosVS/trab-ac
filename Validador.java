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

	final String[] registradores = {"$zero", "at", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3", "$t0", "$t1","$t2",
		"$t3", "$t4", "$t5", "$t6", "$t7", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7", "$t8", "$t9",
		"$k0", "$k1", "$gp", "$sp", "fp", "$ra"};

	final String[] registradoresNum = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9","10", "11", "12", "13",
		"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "$27", "28", "29", "30",
		"31"};

	//métodos
	//retorna o valor numerico do registrador
	public int getNumReg(String reg){
		for(int i = 0; i < 32; i++)
			if(registradores[i].equals(reg) || registradoresNum[i].equals(reg))
				return i;

		return -1;
	}

	//checa se registrador existe
	private boolean existeReg(int reg){
		return reg >= 0 && reg <= 31;
	}

	private boolean existeReg(String registrador){
		int reg = getNumReg(registrador);
		return reg >= 0 && reg <= 31;
	}

	//checa se o programador pode alterar o registrador
	public boolean regAlteravel(int reg){
		return reg >= 2 && reg != 26 && reg != 27;
	}

	public boolean regAlteravel(String registrador){
		int reg = getNumReg(registrador);
		return reg >= 2 && reg != 26 && reg != 27;
	}

	//valida a instrução
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
		int reg1, reg2, reg3;
		//um loop pra cada array de tipos de instrução
		//retorna verdadeiro caso a instrução seja reconhecida
		//e falso caso não
		for(String s : instTipoJ)
			if(inst.equals(s))
				return true;

		//identifica o 1º registrador e armazena
		r1 = instrucao.split(" ")[1];
		r1 = r1.substring(0, r1.indexOf(','));
		reg1 = getNumReg(r1);
		//se o registrador 1 não existir, retorna falso
		if(!existeReg(r1))
			return false;

		for(String s : instTipoR){
			//caso seja uma instrução que tenta salvar em registradores reservados, retorna false
			if(!s.equals("jr") && !regAlteravel(reg1))
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