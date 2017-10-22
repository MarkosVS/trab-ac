/**
*	@author Marcos Vinicius Sombra
*/


//classe instrucao
public class Instrucao{
	//atributos
	private String label;
	private char tipo;
	private String instrucao;

	//construtores
	public Instrucao(String inst){
		this.instrucao = inst;
		this.label = "";
		this.tipo = '0';
	}

	public Instrucao(String inst, String lbl){
		this.instrucao = inst;
		this.label = lbl;
		this.tipo = '0';
	}

	//getters
	public String getLabel(){
		return this.label;
	}

	public char getTipo(){
		return this.tipo;
	}

	public String getInstrucao(){
		return this.instrucao;
	}

	//setters
	public void setLabel(String lbl){
		this.label = lbl;
	}
}