/**
*	@author Marcos Vinicius Sombra
*/

//imports
import java.util.LinkedList;
	
//classe validador
public class Validador{
	//atributos
	final String[] instTipoR = {"add", "addu", "and", "jr", "nor", "or", "slt", "sltu", "sll", "srl", "sub", 
		"subu"}; 

	final String[] instTipoI = {"beq", "bne", "addi", "addiu", "andi", "ori", "slti", "sltiu", "lbu", "lhu",
		"lui", "lw", "sb", "sh", "sw"};
	
	final String[] instTipoJ = {"j", "jal"};
	
	final String[] pseudoInst = {"blt", "bgt", "ble", "bge", "li", "la"};

	final String[] registradores = {"$zero", "at", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3", "$t0", "$t1","$t2",
		"$t3", "$t4", "$t5", "$t6", "$t7", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7", "$t8", "$t9",
		"$k0", "$k1", "$gp", "$sp", "fp", "$ra"};

	final String[] registradoresNum = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9","10", "11", "12", "13",
		"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "$27", "28", "29", "30",
		"31"};

	final String[] tiposDado = {".asciiz", ".ascii", ".word", ".half", ".byte", ".double", ".float"};

	//métodos
	//retorna o tipo da instrucao
	//presume que é uma instrução sintaticamente válida
	public char tipo(Instrucao inst){
		//string que guarda a instrução (sem parametros)
		String i = inst.getTexto().split(" ")[0];

		//checa se é uma chamada ao sistema
		if(i.equals("syscall"))
			return 's';
		
		//checa se é tipo J
		for(String s : instTipoJ)
			if(i.equals(s))
				return 'J';

		//checa se é tipo R
		for(String s : instTipoR)
			if(i.equals(s))
				return 'R';

		//checa se é tipo I
		for(String s : instTipoI)
			if(i.equals(s))
				return 'I';
		//retorna o caractere '0' caso não seja uma instrução valida
		return '0';
	}

	//checa se a label é válida
	public boolean labelValida(String lbl){
		Character c;
		for(int i = 0; i < lbl.length(); i++){
			c = lbl.charAt(i);
			if(!Character.isLetter(c))
				return false;
		}

		return true;
	}
	
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
	public boolean instValida(String instrucao){
		//checa se possui label e caso sim, ignora
		if(instrucao.contains(":"))
			instrucao = instrucao.substring(instrucao.indexOf(":") + 2);
		//array de strings com cada parte da instrução
		String[] instSplit = instrucao.split(" ");
		//pega apenas a primeira palavra da instrucao
		String inst = instSplit[0];

		//checa se é uma chamada ao sistema
		if(inst.equals("syscall"))
			return true;

		//variaveis para guardar os registradores
		String r1, r2, r3;
		int reg1, reg2, reg3;
		//um loop pra cada array de tipos de instrução
		//retorna true caso a instrução seja reconhecida e sintaticamente válida
		//e false caso não
		for(String s : instTipoJ)
			if(inst.equals(s)){
				if(instSplit.length != 2)
					return false;
				if(!labelValida(instSplit[1]))
					return false;
				return true;
			}

		//identifica o 1º registrador e armazena
		r1 = instSplit[1];
		if(r1.contains(","))
			r1 = r1.substring(0, r1.indexOf(','));
		else
			if(!inst.equals("jr"))
				return false;
		
		reg1 = getNumReg(r1);
		//se o registrador 1 não existir, retorna falso
		if(!existeReg(reg1))
			return false;

		for(String s : instTipoR){
			if(inst.equals(s)){
				//verifica se a intrução é um jr
				//se sim, retorna true se foi passado um unico parametro (válido) e false se não
				if(s.equals("jr")){
					return existeReg(reg1) && instSplit.length == 2 && instrucao.endsWith(r1);
				}else{
					if(instSplit.length != 4)
						return false;
					//caso o r1 não seja alterável, retorna false
					if(!regAlteravel(reg1))
						return false;
					//identifica o 2º registrador e armazena
					//caso seja um registrador inexistente, retorna false
					r2 = instSplit[2];
					if(r2.contains(","))
						r2 = r2.substring(0, r2.indexOf(','));
					else
						return false;
					reg2 = getNumReg(r2);
					if(!existeReg(reg2))
						return false;

					if(instSplit[0].equals("sll") || instSplit[0].equals("sll")){
						//variavel que armazena o valor numerico do imediato
						int num;
						//tenta pegar o valor do imediato
						//retorna false caso não consiga
						try{
							num = Integer.parseInt(instSplit[3]);
						}catch(NumberFormatException e){
							return false;
						}
					}else{
						//identifica o 3º registrador e armazena
						//caso seja um registrador inexistente, retorna false
						r3 = instSplit[3];
						reg3 = getNumReg(r3);
						if(!existeReg(reg3))
							return false;
					}

				}
				return true;
			}
		}

		for(int i = 0; i < instTipoI.length; i++){
			String s = instTipoI[i];
			if(inst.equals(s)){
				//variaveis para armazenar o imediato
				int imm;
				String imediato;
				//checa se é um load / store
				if(i >= 8){
					//retorna false caso haja palavras a mais / a menos
					if(instSplit.length != 3)
						return false;
					//retorna false se for um load e tentar escrever em um registrador inválido
					if(i < 12 && !regAlteravel(reg1))
						return false;
					//seta temporariamente imm como o indice do primeiro (
					imm = instSplit[2].indexOf('(');
					//se não houver um (, retorna false
					if(imm <= 0)
						return false;

					//identifica o 2º registrador e armazena
					//caso seja um registrador inexistente, retorna false
					r2 = instSplit[2];
					if(r2.contains(")"))
						r2 = r2.substring(imm + 1, r2.indexOf(')'));
					else
						return false;
					reg2 = getNumReg(r2);
					if(!existeReg(reg2))
						return false;

					//tenta pegar o valor do imediato
					//retorna false caso não consiga	
					try{
						imediato = instSplit[2].substring(0, imm);
						imm = Integer.parseInt(imediato);
					}catch(NumberFormatException e){
						return false;
					}

					//se houver caracteres a mais, retorna false
					if(2 + r2.length() + imediato.length() != instSplit[2].length())
						return false;

				}else{
					//retorna false caso haja palavras a mais / a menos
					if(instSplit.length != 4)
						return false;
					//identifica o reg2 e armazena (se for válido)
					r2 = instSplit[2];
					if(r2.contains(","))
						r2 = r2.substring(0, r2.indexOf(','));
					else
						return false;
					reg2 = getNumReg(r2);
					//se o registrador não existir, retorna false
					if(!existeReg(r2))
						return false;
					//checa se é um bne/beq
					if(i < 2){
						//
					}else{
						//retorna false se não for possível escrever no reg 1
						if(!regAlteravel(r1))
							return false;
						//tenta pegar o valor do imediato
						//retorna false caso não consiga
						try{
							imm = Integer.parseInt(instSplit[3]);
						}catch(NumberFormatException e){
							return false;
						}
					}
				}
				return true;
			}
		}

		for(String s : pseudoInst)
			if(inst.equals(s))
				return true;
			
		//retorna falso caso não seja valida
		return false;
	}

	//valida dado
	public boolean dadoValido(String dado){
		//array com cada palavra separada
		String[] dadoSplit = dado.split(" ");
		//retorna false caso haja palavras a mais / a menos
		if(dadoSplit.length != 3)
			return false;
		//variavel para armazenar a label e outra para o tamanho
		String label = dadoSplit[0];
		int len = label.length();

		//retorna false se o ultimo caractere não for um ':'
		if(label.charAt(len - 1) != ':')
			return false;
		//checa se há pontuação no nome da label
		//se sim, retorna false
		if(!labelValida(label.substring(0, len - 1)))
			return false;

		//variavel para armazenar o tipo
		String tipo = dadoSplit[1];
		for(int i = 0; i < tiposDado.length; i++){
			String s = tiposDado[i];
			if(s.equals(tipo)){
				//variavel para armazenar o dado propriamente dito
				String info = dadoSplit[2];
				if(i < 2){
					//len passa a ser o tamanho do dado
					len = info.length();
					//se houver menos de dois caracteres no dado, retorna false
					if(len < 2)
						return false;
					//variavel que guarda o indice da primeira aspa
					int quote01 = info.indexOf('"');
					//caso o primeiro caractere não seja uma aspa, retorna false
					if(quote01 != 0)
						return false;

					//variavel que guarda o indice da segunda aspa
					int quote02 = info.substring(quote01 + 1).indexOf('"');
					//caso a segunda aspa não seja o ultimo caractere, retorna false
					if(quote02 != len - 2)
						return false;
				}else if(i < 5){
					//variavel que armazena o valor numerico do dado
					int num;
					//tenta pegar o valor do dado
					//retorna false caso não consiga
					try{
						num = Integer.parseInt(info);
					}catch(NumberFormatException e){
						return false;
					}
				}else{
					//variavel que armazena o valor numerico do dado
					double num;
					//tenta pegar o valor do dado
					//retorna false caso não consiga
					try{
						num = Double.parseDouble(info);
					}catch(NumberFormatException e){
						return false;
					}catch(NullPointerException e){
						return false;
					}
				}

				//retorna true caso seja válido
				return true;
			}
		}
		//retorna false caso seja de um tipo invalido
		return false;
	}
}