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
    
    public void setCurso(String curso){
        this.cursosel = curso;
        grafico();
    }
    
    public void grafico(){
        Map<String, Map<Integer, Double>> dadosAgrupados = new HashMap<>();
        
        for(Candidato c : dados) {
            if(!c.getCurso().equalsIgnoreCase(cursosel)){
                continue;
            }
            
            if(!dadosAgrupados.containsKey(c.getCampus())) {
                dadosAgrupados.put(c.getCampus(), new HashMap<>());
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
    
    public void setDados(ArrayList<Candidato> dados) {
        this.dados= dados;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}