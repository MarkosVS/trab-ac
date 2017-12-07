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
}