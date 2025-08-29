/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.collections.FXCollections;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class GraficoNotasController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;
    
    @FXML 
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    private Map<Integer, Double> menorNota;
    private Map<Integer, Double> maiorNota;
    private Map<Integer, Double> valorMedio;
    
    private ArrayList<Candidato> dados;
    
    private String curso;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setDados(ArrayList<Candidato> dados, String curso){
        this.dados = dados;
        this.curso = curso;
        gerarChart();
    }
    
    private void gerarChart(){
        maiorNota = new HashMap<>();
        menorNota = new HashMap<>();
        valorMedio = new HashMap<>();

        Map<Integer, Double> somaNotas = new HashMap<>();
        Map<Integer, Integer> contagemCandidatos = new HashMap<>();
        
        for(Candidato c : dados){
            int ano = c.getAno();
            double media = c.getMedia();
            
            if(!maiorNota.containsKey(ano) || media > maiorNota.get(ano)){
                maiorNota.put(ano, media);
            }
            
            if(!menorNota.containsKey(ano) || media < menorNota.get(ano)){
                menorNota.put(ano, media);
            }
            
            somaNotas.put(ano, somaNotas.getOrDefault(ano, 0.0) + media);
            contagemCandidatos.put(ano, contagemCandidatos.getOrDefault(ano, 0) + 1);
        }
        
        for(Integer ano : somaNotas.keySet()){
            double mediaCalculada = somaNotas.get(ano)/contagemCandidatos.get(ano);
            valorMedio.put(ano, mediaCalculada);
        }
        
        barChart.getData().clear();
       
        xAxis.setLabel("Ano"); 
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1000);
        yAxis.setTickUnit(100);
        yAxis.setLabel("Nota");
        
        curso = curso.toLowerCase();
        barChart.setTitle("Análise de Notas do Curso de " + this.curso);
        barChart.setLegendVisible(true);
        
        XYChart.Series<String, Number> serieMaiorNota = new XYChart.Series<>();
        serieMaiorNota.setName("Maior Nota");
        maiorNota.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(entry -> {
            serieMaiorNota.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        });

        
        XYChart.Series<String, Number> serieMenorNota = new XYChart.Series<>();
        serieMenorNota.setName("Menor Nota");
        menorNota.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                serieMenorNota.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
            });
        
         XYChart.Series<String, Number> serieValorMedio = new XYChart.Series<>();
        serieValorMedio.setName("Média");
        valorMedio.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                serieValorMedio.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
            });
        
        barChart.getData().addAll(serieMaiorNota, serieMenorNota, serieValorMedio);
    }
    
}
