/**
*	@author Marcos Vinicius Sombra
*/

//imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class Main{
	//metodo main
	public static void main(String[] args){
		//lista de instruções
		LinkedList<Instrucao> instrucoes = new LinkedList<Instrucao>();
		//nome do arquivo
		String asm;
		//tenta utilizar o arquivo passado por parametro
		//caso falhe, exibe uma mensagem de erro e encerra o programa
		try{
			//utiliza a primeira String de args como arquivo
			asm = args[0];
			//variável para armazenar o tamanho do nome do arquivo
			int len = asm.length();
			//caso o arquivo não esteja no formato .asm, imprime uma mensagem de erro e encerra o programa
			if(asm.charAt(len - 1) != 'm' || asm.charAt(len - 2) != 's' || asm.charAt(len - 3) != 'a' || asm.charAt(len - 4) != '.'){
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
					while(br.ready())
						instrucoes.add(new Instrucao(br.readLine().trim()));
					
					br.close();
				}catch(IOException e){
					System.out.println("Não foi possível ler o arquivo");
				}
				//len passa a ser o tamanho da lista de instruções
				len = instrucoes.size();
				//remove coisas desnecessárias
				for(int i = 0; i < len; i++){
					//remove comentarios
					if(instrucoes.get(i).getInstrucao().contains("#")){
						int index = instrucoes.get(i).getInstrucao().indexOf('#');
						String s = instrucoes.get(i).getInstrucao().substring(0, index);
						instrucoes.remove(instrucoes.get(i));
						instrucoes.add(i, new Instrucao(s));
					}
					//remove linhas nulas
					if(instrucoes.get(i).getInstrucao().equals("")){
						instrucoes.remove(instrucoes.get(i));
						i--;
						len = instrucoes.size();
					}					
				}

				//teste
				for(Instrucao i : instrucoes)
					System.out.println(i.getInstrucao());
			}catch(FileNotFoundException e){
				System.out.println("Arquivo não encontrado");
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Arquivo assembly não definido");
		}
	}
}