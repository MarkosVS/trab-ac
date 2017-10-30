/**
*	@author Marcos Vinicius Sombra
*/

//imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class Assembler{
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
						else if(inst)
							instrucoes.add(new Instrucao(linha));
						else
							dados.add(new Dado(linha));
					}
					
					br.close();
				}catch(IOException e){
					System.out.println("Não foi possível ler o arquivo");
				}
				//variavel para armazenar o tamanho da lista de instruções
				int len = instrucoes.size();
				//remove coisas desnecessárias da lista de instrucoes
				for(int i = 0; i < len; i++){
					//remove comentarios
					if(instrucoes.get(i).getTexto().contains("#")){
						int index = instrucoes.get(i).getTexto().indexOf('#');
						String s = instrucoes.get(i).getTexto().substring(0, index);
						instrucoes.remove(instrucoes.get(i));
						instrucoes.add(i, new Instrucao(s));
					}
					//remove linhas nulas
					if(instrucoes.get(i).getTexto().equals("")){
						instrucoes.remove(instrucoes.get(i));
						i--;
						len = instrucoes.size();
					}					
				}
				//len passa a ser o tamanho da lista de dados
				len = dados.size();
				//remove coisas desnecessárias da lista de dados
				for(int i = 0; i < len; i++){
					//remove comentarios
					if(dados.get(i).getConteudo().contains("#")){
						int index = dados.get(i).getConteudo().indexOf('#');
						String s = dados.get(i).getConteudo().substring(0, index);
						dados.remove(dados.get(i));
						dados.add(i, new Dado(s));
					}
					//remove linhas nulas
					if(dados.get(i).getConteudo().equals("")){
						dados.remove(dados.get(i));
						i--;
						len = dados.size();
					}					
				}

				//teste instruções
				System.out.println("Instruções:");
				for(Instrucao i : instrucoes)
					System.out.println(i.getTexto());
				//teste dados
				System.out.println("Dados:");
				for(Dado d : dados)
					System.out.println(d.getConteudo());
			}catch(FileNotFoundException e){
				System.out.println("Arquivo não encontrado");
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Arquivo assembly não definido");
		}
	}
}