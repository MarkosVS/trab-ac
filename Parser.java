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

	//construtor
	public Parser(LinkedList<Instrucao> i, LinkedList<Dado> d, LinkedList<String> l){
		this.instrucoes = i;
		this.dados = d;
		this.labels = l;
	}

	//metodos
	//metodo para gerar o cabeçalho
	public LinkedList<String> geraCabecalho(){
		//cria uma lista pra colocar o cabecalho
		LinkedList<String> cabecalho = new LinkedList<String>();
		//adicionando linhas
		cabecalho.add("11110000111100001111000011110000"); 			//delimitador
		cabecalho.add(Integer.toBinaryString(dados.size()));		//tamanho da parte de dados
		cabecalho.add(Integer.toBinaryString(instrucoes.size()));	//tamanho da parte de instrucoes
		cabecalho.add("11110000111100001111000011110000"); 			//delimitador
		return cabecalho;
	}

	//metodo para gerar a parte de dados


}