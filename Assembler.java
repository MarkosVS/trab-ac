/**
*	@author Marcos Vinicius Sombra
*/

//imports
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

public class Assembler{
	//lista de instruções
	static LinkedList<Instrucao> instrucoes = new LinkedList<Instrucao>();
	//lista de dados
	static LinkedList<Dado> dados = new LinkedList<Dado>();
	//validador
	static Validador valid = new Validador();
	//nome do arquivo
	static String asm;

	//metodo que gera saida
	static void gerarArquivo(String nomeSaida) throws FileNotFoundException{
		PrintStream ps = new PrintStream(nomeSaida);
		FileOutputStream out = new FileOutputStream("saida.txt");
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
	}

	//metodo que remove espaços e tabulações
	static String removeEspacos(String str){
		str = str.trim();
		str = str.replaceAll("\t", " ");
		while(str.contains("  "))
			str = str.replaceAll("  ", " ");
		return str;

	}

	//metodo que remove comentarios
	static String removeComentarios(String str){
		if(str.contains("#")){
			int index = str.indexOf('#');
			str = str.substring(0, index);
		}
		return str;
	}

	//metodo main
	public static void main(String[] args){
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
					//variavel que conta a linha atual
					int numLinha = 0;
					while(br.ready()){
						//incrementa o contador de linha
						numLinha++;
						//string que salva a linha lida
						String linha = br.readLine();
						//remover espaços
						linha = removeEspacos(linha);
						//interpreta a linha e adiciona na lista correspontente
						if(linha.equals(".text"))
							inst = true;
						else if(linha.equals(".data"))
							inst = false;
						else{
							//remove comentarios
							linha = removeComentarios(linha);
							//caso a linha não seja nula, insere na lista correspondente
							if(!linha.equals("")){
								if(inst){
									//checa se é uma instrução válida
									//se for, adiciona na lista
									//se não for, gera uma mensagem de erro e encerra o programa
									if(valid.eValida(linha))
										instrucoes.add(new Instrucao(linha));
									else{
										System.out.println("Instrucao inválida encontrada na linha " + numLinha);
										System.out.println(linha);
										return;
									}
								}
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
				String nomeSaida = asm.substring(0, asm.indexOf('.'));
				nomeSaida += ".txt";
				//chama metodo que gera o arquivo
				gerarArquivo(nomeSaida);
				
			}catch(FileNotFoundException e){
				System.out.println("Arquivo não encontrado");
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Arquivo assembly não definido");
		}
	}
}