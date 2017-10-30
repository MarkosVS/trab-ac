/**
*	@author Marcos Vinicius Sombra
*/


//classe instrucao
public class Dado{
	//atributos
	private String label;
	private String tipo;
	private Object conteudo;

	//construtor
	public Dado(Object cont, String lbl, String tipo){
		this.conteudo = cont;
		this.label = lbl;
		this.tipo = tipo;
	}

	//getters
	public String getLabel(){
		return this.label;
	}

	public String getTipo(){
		return this.tipo;
	}

	public Object getConteudo(){
		return this.conteudo;
	}
}