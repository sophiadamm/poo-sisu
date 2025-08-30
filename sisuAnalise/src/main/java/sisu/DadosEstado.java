/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisu;

/**
 *
 * @author pc
 */
public class DadosEstado {
    private String estado;
    private int numeroAlunos;
    private double media;
    private double porcentagem;
    
    public DadosEstado(String estado, int numeroaluno, double media, double porcentagem){
        this.estado = estado;
        this.numeroAlunos = numeroaluno;
        this.media = media;
        this.porcentagem = porcentagem;
    }
    
    public int getNumeroAlunos(){
        return numeroAlunos;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public double getMedia(){
        return media;
    }
    
    public double getPorcentagem(){
        return porcentagem;
    }
}
