/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ListaTop10Controller implements Initializable {

    /*@FXML
    private ListView<String> listview;
    
    private List<Candidato> dados;
    private int anoSelecionado;
    
    private static class NotadeCorte implements Comparable<NotaDeCorte>{
        private String curso;
        private Double nota;
        
        public NotaDeCorte(String curso, Double nota){
            this.curso = curso;
            this.nota = nota;
        }
        
        public String getCurso(){
            return curso;
        }
        
        public Double getNota(){
            return nota;
        }
        
        @Override
        public int compareTo(NotaDeCorte outra){
            return Double.compare(this.nota, outra.nota);
        }
        
        @Override
        public String toString(){
            return String.format("%s  -  %.2f", curso, nota);
        }
    }
    
    public void setDados(ArrayList<Candidato> dados, int ano){
        this.dados = dados;
        this.anoSelecionado = ano;
        processarEExibirdados();
    }
    
    private void processarEExibirDados(){
        if(dados == null || dados.isEmpty()){
            return;
        }
        Map<String, Double> maioresNotas = new HashMap<>();
        
        for (Candidato c : dados){
            if(c.getAno() == anoSelecionado){
                if(!maioresNotas.containsKey(c.getCurso()) || c.getMedia() > maioresNotas.get(c.getCurso())){
                    maioresNotas.put(c.getCurso(), c.getMedia());
                }
            }
        }
        
        List<Map.Entry<String, Double>> listaDeEntradas = new ArrayList<>(maioresNotas.entrySet());
        Collections.sort(listaDeEntradas, Map.Entry.comparingByKey());
        
        ObservableList<String> itensListView = FXCollections.observableArrayList();
        for(Map.Entry<String, Double> entry : listaDeEntradas){
            itensListView.add(String.format("%s  -  %.2f", entry.getKey(), entry.getValue()));
        }
        
        listview.setItems(itensListView);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    */
}
