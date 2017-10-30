/**
*	@author Marcos Vinicius Sombra
*/


//classe instrucao
public class Instrucao{
	//atributos
	private String label;
	private char tipo;
	private String texto;

	//construtores
	public Instrucao(String inst){
		this.texto = inst;
		this.label = "";
		this.tipo = '0';
	}

	public Instrucao(String inst, String lbl){
		this.texto = inst;
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

	public String getTexto(){
		return this.texto;
	}

	//setters
	public void setLabel(String lbl){
		this.label = lbl;
	}
}