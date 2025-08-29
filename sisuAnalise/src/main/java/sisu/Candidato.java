package sisu;

public class Candidato{
    private String numero_enem, nome, curso, campus, demanda, estado, colocacao;
    private double media;
    private int ano;
    
    public Candidato(String [] c){        
        numero_enem = c[0];
        nome = c[1];
        curso = tratamentoCurso(c[2]);
        
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
    
     public String tratamentoCurso(String s) {
        //padronizacao do nome dos cursos
        s = s.trim();
        s = s.replace(" - ", " ");
        s = s.replace("BACHARELADO", "");
        s = s.replace("LICENCIATURA", "LIC");
        s = s.replace(" BAC ", " ");
        s = s.replace("-", "");
        s = s.replace("BAC", "");
        s = s.replace("INTEGRAL", "INT");
        s = s.replace("NOTURNO", "NOT");
        s = s.replace("VESPERTINO", "VESP");
        s = s.replace("(VES)", "(VESP)");
        s = s.replace("MATUTINO", "MAT");
        
        if(s.equals("MEDICINA VETERINARIA(INT)")){
            return "MEDICINA VETERINARIA (INT)";
        }else if(s.equals("MATEMATICALIC (VESP)")){
            return "MATEMATICA LIC (VESP)";
        }else if(s.equals("LETRAS PORTESPANHOLLIC(VESP)")){
            return "LETRAS PORTESPANHOL LIC (VESP)";
        } else if(s.startsWith("C. D")){
            return "CIENCIA" + s.substring(2);
        }else if(s.startsWith("C.")){
            return "CIENCIAS" + s.substring(2);
        }else return s;
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
    
    public String getDemanda(){
        return this.demanda;
    }
    
    public double getMedia(){
        return this.media;
    }
    
    public String getNumero(){
        return this.numero_enem;
    }
    
    public String getEstado(){
        return this.estado;
    }
    
    public String getColocacao(){
        return this.colocacao;
    }
    
    public String getNome(){
        return this.nome;
    }
    
}