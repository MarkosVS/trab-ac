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
	private void setLabel(String lbl){
		this.label = lbl;
	}

	private void setTexto(String txt){
		this.texto = txt;
	}

	private void setTipo(char t){
		this.tipo = t;
	}

	//metodos
	public void corrigirInstrucao(){
		//checa se possui label
		//se sim, separa a label e o texto
		if(this.getTexto().contains(":")){
			this.setLabel(this.getTexto().substring(0, this.getTexto().indexOf(':')));
			this.setTexto(this.getTexto().substring(this.getTexto().indexOf(':') + 1).trim());
		}

		//identifica o tipo da instrução
		this.setTipo((new Validador()).tipo(this));
	}
}