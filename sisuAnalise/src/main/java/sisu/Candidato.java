package sisu;

public class Candidato{
    String numero_enem, nome, curso, campus, demanda, estado, colocacao;
    double media;
    int ano;
    
    public Candidato(String [] c){        
        numero_enem = c[0];
        nome = c[1];
        
        c[2] = c[2].replace(" - ", " ");
        
        if(c[2].startsWith("C. D")){
            curso = "CIENCIA" + c[2].substring(2);
        }else if(c[2].startsWith("C.")){
            curso = "CIENCIAS" + c[2].substring(2);
        }else curso = c[2];
 
        
        if (c[3].equals("CAMPUS DO SER")) {
            campus = "CAMPUS DO SERTAO";
        } else {
             campus = c[3];
        }
        demanda = tratamentoDemanda(c[4]);
        colocacao = c[6];
        estado = c[7];
        
        media = Double.parseDouble(c[5]);
        ano = Integer.parseInt(c[8]);
    }    

    public String toString() {
        return "Numero Enem: "+ this.numero_enem + "\n"+
               "Nome: " + this.nome + "\n" +
               "Curso: " + this.curso + "\n" +
               "Campus: " + this.campus + "\n" +
               "Colocação: " + this.colocacao + "\n" +
               "Média: " + this.media + "\n"+
               "Estado: " + this.estado + "\n" +
               "Demanda: " + this.demanda + "\n";
    }
    
    public String tratamentoDemanda(String s) {
        if (s.startsWith("A")) { 
            return "Ampla Concorrencia";
        }
        if (s.startsWith("V")) { 
            return "PcD";
        }
        
        if (s.equals("L1") || s.equals("LB_EP")) {
            return "Escola pública, baixa renda";
        }
                
        if (s.equals("L2") || s.equals("LB_PPI")) {
            return "Escola pública, baixa renda, PPI";
        }
        
        if (s.equals("L5") || s.equals("LI_EP")) {
            return "Escola pública";
        }  
       
        if (s.equals("L6") || s.equals("LI_PPI")) {
            return "Escola pública, PPI";
        }  
        
        if (s.equals("L6") || s.equals("LI_PPI")) {
            return "Escola pública, PPI";
        }  
        if (s.equals("L9") || s.equals("LB_PCD")) {
            return "Escola pública, baixa renda, PcD";
        }  
        if (s.equals("L10")) {
            return "Escola pública, baixa renda, PcD, PPI";
        }  
        if (s.equals("L13") || s.equals("LI_PCD")) {
            return "Escola pública, PcD";
        }  
        if (s.equals("L14")) {
            return "Escola pública, PcD, PPI";
        }  
        if (s.equals("LI_Q")) {
            return "Escola pública, quilombola";
        }  
        if (s.equals("LB_Q")) {
            return "Escola pública, baixa renda, quilombola";
        }  
        return "Demanda não Identificada";
    }
    
    public String getCurso(){
        return this.curso;
    }
    
    public String getCampus(){
        return this.campus;
    }
    
    public int getAno(){
        return this.ano;
    }
    
    public double getMedia(){
        return this.media;
    }
}