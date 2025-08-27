package sisu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//padrão Singleton : permite criar objetos únicos para os quais há apenas uma instância
public class Dados {
  
    private static Dados instancia;
    private ArrayList<Candidato> listaCandidatos;
    
    //Construtor que faz a leitura do arquivo
    private Dados() {
        this.listaCandidatos = new ArrayList<>();
        String caminhoArquivo = "sisuDados.csv";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(caminhoArquivo);
        
        if (is == null) {
            System.err.println("Erro: O arquivo " + caminhoArquivo + " não foi encontrado no classpath.");
            return; 
        }

        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(is))) {
            String linha;
            leitor.readLine();
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");
                Candidato candidato = new Candidato(dados); 
                this.listaCandidatos.add(candidato);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + caminhoArquivo);
            e.printStackTrace(); 
        }
    }
    
    // Método estático para obter a única instância e, portanto, os dados já carregados
    public static Dados getInstancia() {
        if (instancia == null) {
            instancia = new Dados();
        }
        return instancia;
    }
    // Método para obter a lista de candidatos
    public ArrayList<Candidato> getListaCandidatos() {
        return this.listaCandidatos;
    }

}

