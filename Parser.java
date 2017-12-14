/**
*	@author Marcos Vinicius Sombra
*/

//imports
import java.util.LinkedList;

//classe parser
public class Parser{
	//atributos
	//lista de instruções
	private LinkedList<Instrucao> instrucoes = new LinkedList<Instrucao>();
	//lista de dados
	private LinkedList<Dado> dados = new LinkedList<Dado>();
	//lista de labels
	private LinkedList<String> labels = new LinkedList<String>();
	//validador
	private Validador valid = new Validador();

	//construtor
	public Parser(LinkedList<Instrucao> i, LinkedList<Dado> d, LinkedList<String> l){
		this.instrucoes = i;
		this.dados = d;
		this.labels = l;
	}

	//metodos
	//metodo auxiliar que completa os bits
	private String completaBits(String bin, int bits){
		while(bin.length() < bits)
			bin = "0" + bin;

		return bin;
	}
	//metodo para gerar o cabeçalho
	public LinkedList<String> geraCabecalho(){
		//cria uma lista pra colocar o cabecalho
		LinkedList<String> cabecalho = new LinkedList<String>();
		//adicionando linhas
		cabecalho.add("11110000111100001111000011110000"); 							//delimitador
		cabecalho.add(completaBits(Integer.toBinaryString(dados.size()), 32));		//tamanho da parte de dados
		cabecalho.add(completaBits(Integer.toBinaryString(instrucoes.size()), 32));	//tamanho da parte de instrucoes
		return cabecalho;
	}

	//metodo para gerar a parte de dados
	public LinkedList<String> geraDados(){
		//cria uma lista pra colocar os dados
		LinkedList<String> pDados = new LinkedList<String>();
		//retorna null se a lista não possuir ao menos um elemento
		if(dados.size() < 1)
			return null;

		//retorna a parte de dados (em binario)
		return pDados;
	}

	//metodo para gerar a parte de instrucoes
	public LinkedList<String> geraInstrucoes(){
		//cria uma lista pra colocar as instrucoes
		LinkedList<String> pInst = new LinkedList<String>();
		//retorna null se a lista não possuir ao menos um elemento
		if(instrucoes.size() < 1)
			return null;

		//variavel string que guarda o binário a ser inserida
		String bin = "";
		//variaveis que guardam os registradores
		int rd = 0, rs = 0, rt = 0;
		//percorre a lista de instrucoes para converter
		for(Instrucao i : instrucoes){
			String[] instSplit = i.getTexto().split(" ");
			//identifica o tipo da instrução
			if(i.getTipo() == 'R'){
				//variaveis para guardar campos
				String func = "", shift = "";
				//identifica o valor numerico do registrador1
				int index = instSplit[1].indexOf(',');
				if(index != -1)
					rd = valid.getNumReg(instSplit[1].substring(0, index));
				else{
					shift = "00000";
					func = "001000";
					rs = valid.getNumReg(instSplit[1]);
				}

				//junta tudo em bin
				bin += "000000";
				bin += completaBits(Integer.toBinaryString(rs), 5);
				bin += completaBits(Integer.toBinaryString(rt), 5);
				bin += completaBits(Integer.toBinaryString(rd), 5);
				bin += shift;
				bin += func;
				//adiciona na lista
				pInst.add(bin);
			}else if(i.getTipo() == 'J'){
				//
			}else if(i.getTipo() == 'I'){
				//
			}else if(i.getTipo() == 's'){
				//
			}else
				return null;
		}
		//retorna a parte de dados (em binario)
		return pInst;
	}


}