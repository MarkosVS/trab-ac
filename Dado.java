/**
*	@author Marcos Vinicius Sombra
*/


//classe instrucao
public class Dado{
	//atributos
	private String label;
	private String tipo;
	private String conteudo;

	//construtores
	public Dado(String cont, String lbl, String tipo){
		this.conteudo = cont;
		this.label = lbl;
		this.tipo = tipo;
	}

	public Dado(String cont){
		this.conteudo = cont;
		this.label = "[sem label]";
		this.tipo = "[sem tipo]";
	}

	//getters
	public String getLabel(){
		return this.label;
	}

	public String getTipo(){
		return this.tipo;
	}

	public String getConteudo(){
		return this.conteudo;
	}

	//metodos
	public void corrigirDado(){
		//variavel que guarda o indice do :
		int index = this.getConteudo().indexOf(':');
		//separando a label do resto do conteudo
		this.label = this.getConteudo().substring(0, index);
		this.conteudo = this.getConteudo().substring(index + 1).trim();
		//separando o tipo do resto do conteudo
		String[] split = this.getConteudo().split(" ");
		this.tipo = split[0];
		this.conteudo = split[1];
	}
}