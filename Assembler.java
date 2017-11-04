/**
*	@author Marcos Vinicius Sombra
*/

//imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

public class Assembler{
	//vetores de instruções válidas (um pra cada tipo de instrução)
	static String[] instTipoR = {"add", "addu", "and", "jr", "nor", "or", "slt", "sltu", "sll", "srl", "sub", 
		"subu"}; 
	static String[] instTipoI = {"addi", "addiu", "andi", "beq", "bne", "lbu", "lhu", "lui", "lw", "ori", "slti",
		"sltiu", "sb", "sh", "sw"};
	static String[] instTipoJ = {"j", "jal"}; 
	//metodo main
	public static void main(String[] args){
		//lista de instruções
		LinkedList<Instrucao> instrucoes = new LinkedList<Instrucao>();
		//lista de dados
		LinkedList<Dado> dados = new LinkedList<Dado>();
		//nome do arquivo
		String asm;
		//tenta utilizar o arquivo passado por parametro
		//caso falhe, exibe uma mensagem de erro e encerra o programa
		try{
			//utiliza a primeira String de args como arquivo
			asm = args[0];
			//caso o arquivo não esteja no formato .asm, imprime uma mensagem de erro e encerra o programa
			if(!asm.endsWith(".asm")){
				System.out.println("O arquivo informado não é do formato .asm");
				return;
			}
			//tenta ler o arquivo informado
			//caso falhe, exibe uma mensagem de erro e encerra o programa
			try{
				//cria um objeto BufferedReader pra ler o arquivo
				BufferedReader br = new BufferedReader(new FileReader(asm));
				//tenta salvar as linhas do arquivo numa lista
				//caso falhe, exibe uma mensagem de erro
				try{
					//flag que controla se a linha será interpretada como instrução ou dado
					boolean inst = true;
					while(br.ready()){
						//string que salva a linha lida (sem espaços adicionais)
						String linha = br.readLine().trim();
						//interpreta a linha e adiciona na lista correspontente
						if(linha.equals(".text"))
							inst = true;
						else if(linha.equals(".data"))
							inst = false;
						else{
							//remove comentarios
							if(linha.contains("#")){
								int index = linha.indexOf('#');
								linha = linha.substring(0, index);
							}
							//caso a linha não seja nula, insere na lista correspondente
							if(!linha.equals("")){
								if(inst)
									instrucoes.add(new Instrucao(linha));
								else
									dados.add(new Dado(linha));
							}
						}
					}
					//fechar o buffer para ler o arquivo
					br.close();
				}catch(IOException e){
					System.out.println("Não foi possível ler o arquivo");
				}
				//escreve o arquivo executavel
				//PS: por enquanto gera um txt
				//variavel que guarda o nome do arquivo saída
				String nomeSaida;
				//tenta usar o nome passado como parametro
				//caso não consiga, utiliza o msm nome do arquivo
				try{
					nomeSaida = args[1];
				}catch(ArrayIndexOutOfBoundsException e){
					nomeSaida = asm.substring(0, asm.indexOf('.'));
				}
				nomeSaida += ".txt";
				//objeto PrintStream para escrever o arquivo
				PrintStream ps = new PrintStream(nomeSaida);
				//teste instruções
				System.out.println("Instruções:");
				ps.println("Instruções:");
				for(Instrucao i : instrucoes){
					ps.println(i.getTexto());
					System.out.println(i.getTexto());
				}
				//teste dados
				System.out.println("Dados:");
				ps.println("Dados:");
				for(Dado d : dados){
					ps.println(d.getConteudo());
					System.out.println(d.getConteudo());
				}
				
			}catch(FileNotFoundException e){
				System.out.println("Arquivo não encontrado");
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Arquivo assembly não definido");
		}
	}
}