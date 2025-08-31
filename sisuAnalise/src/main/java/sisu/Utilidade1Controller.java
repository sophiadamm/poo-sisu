/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.util.*;

/**
 * FXML Controller class
 *
 * @author pc
 */

public class Utilidade1Controller implements Initializable {

    @FXML
    private LineChart<Number, Number> lineChart;
    
    @FXML
    private NumberAxis eixoX;
    
    @FXML
    private NumberAxis eixoY;
    
    private List<Candidato> dados;
    
    private String cursosel;
    private String demandasel;
    
    public void setDados(ArrayList<Candidato> dados, String curso, String demanda){
        this.dados = dados;
        this.cursosel = curso;
        this.demandasel = demanda;
        grafico();
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void grafico(){
        Map<String, Map<Integer, Double>> dadosAgrupados = new HashMap<>();
        
        for(Candidato c : dados) {
            if(!c.getCurso().equalsIgnoreCase(cursosel)){
                continue;
            }
            
            if(!dadosAgrupados.containsKey(c.getCampus())) {
                dadosAgrupados.put(c.getCampus(), new HashMap<>());
                System.out.println(c.getCampus());
            }
            
            Map<Integer, Double> notaDeCorte = dadosAgrupados.get(c.getCampus());
            
            if(!notaDeCorte.containsKey(c.getAno())){
                notaDeCorte.put(c.getAno(), c.getMedia());
            }else{
                double notaAtual = notaDeCorte.get(c.getAno());
                if(c.getMedia() < notaAtual){
                    notaDeCorte.put(c.getAno(), c.getMedia());
                }
            }
        }
        
        lineChart.getData().clear();
        
        eixoX.setAutoRanging(false);
        eixoX.setLowerBound(2019);
        eixoX.setUpperBound(2025);
        eixoX.setTickUnit(1);
        
        eixoY.setAutoRanging(false);
        eixoY.setLowerBound(0);
        eixoY.setUpperBound(1000);
        eixoY.setTickUnit(100);
        
        cursosel = cursosel.toLowerCase();
        lineChart.setTitle("Gráfico da Evolução das Notas de Corte de " + cursosel + " na Demanda " + demandasel + " em Cada Campus Disponível");
        
        for(String campus : dadosAgrupados.keySet()){
            XYChart.Series<Number, Number> serie = new XYChart.Series<>();
            serie.setName(campus);
            
            Map<Integer, Double> notaDeCorte = dadosAgrupados.get(campus);
            
            List<Integer> anos = new ArrayList<>(notaDeCorte.keySet());
            Collections.sort(anos);
            
            for(Integer ano : anos){
                Double nota = notaDeCorte.get(ano);
                serie.getData().add(new XYChart.Data<>(ano, nota));
            }
            
            lineChart.getData().add(serie);
        }
       
    }   

}