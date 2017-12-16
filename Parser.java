/**
*	@author Marcos Vinicius Sombra
*/

//imports
import java.util.LinkedList;
import java.util.Random;

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
	//objeto para gerar valores aleatórios
	private Random jv = new Random();
	//delimitador
	private final String delimitador = "11110000111100001111000011110000";

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
		cabecalho.add(delimitador); 												//delimitador
		cabecalho.add(completaBits(Integer.toBinaryString(dados.size()), 32));		//tamanho da parte de dados
		cabecalho.add(completaBits(Integer.toBinaryString(instrucoes.size()), 32));	//tamanho da parte de instrucoes
		return cabecalho;
	}

	//metodo para gerar a parte de dados
	public LinkedList<String> geraDados(){
		//cria uma lista pra colocar os dados
		LinkedList<String> pDados = new LinkedList<String>();
		//variavel que guarda o endereço do dado
		String endereco, qtdBytes, dado;
		//percorre a lista de dados
		for(Dado d : dados){
			//gera um endereço aleatório
			endereco = Integer.toBinaryString(jv.nextInt(1073741824));
			endereco += "00";
			endereco = completaBits(endereco, 32);
			//adiciona o endereço
			pDados.add(endereco);
			//checa o tipo de dado e adiciona a qtd de bytes relacionada na lista
			if(d.getTipo().equals("word") || d.getTipo().equals("half") || d.getTipo().equals("byte")){
				qtdBytes = completaBits(Integer.toBinaryString(4), 32);
				dado = completaBits(Integer.toBinaryString(Integer.parseInt(d.getConteudo())), 32);
			}else if(d.getTipo().equals("ascii") || d.getTipo().equals("asciiz")){
				qtdBytes = completaBits(Integer.toBinaryString(d.getConteudo().length() * 2), 32);
				dado = "00000000000000000000000000000000";
			}else{
				qtdBytes = "00000000000000000000000000000000";
				dado = "00000000000000000000000000000000";
			}

			pDados.add(qtdBytes);
			pDados.add(dado);

		}
		//adiciona o delimitador
		pDados.add(delimitador);
		//retorna a parte de dados (em binario)
		return pDados;
	}

	//metodo para gerar a parte de instrucoes
	public LinkedList<String> geraInstrucoes(){
		//cria uma lista pra colocar as instrucoes
		LinkedList<String> pInst = new LinkedList<String>();
		//variavel string que guarda o binário a ser inserida
		String bin;
		//variaveis que guardam os registradores
		int rd = 0, rs = 0, rt = 0;
		//percorre a lista de instrucoes para converter
		for(Instrucao i : instrucoes){
			String[] instSplit = i.getTexto().split(" ");
			bin = "";
			//identifica o tipo da instrução
			if(i.getTipo() == 'R'){
				//variaveis para guardar campos
				String func = "000000", shift = "00000";
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

		pInst.add(delimitador);
		//retorna a parte de dados (em binario)
		return pInst;
	}

	//metodo que gera o binario completo
	public LinkedList<String> geraBinario(){
		LinkedList<String> retorno = new LinkedList<>();
		retorno.addAll(geraCabecalho());
		retorno.addAll(geraDados());
		retorno.addAll(geraInstrucoes());

		return retorno;
	}
}