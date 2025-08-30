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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ListaTop10Controller implements Initializable {

    @FXML
    private ListView<String> listview;
    
    @FXML
    private Label TituloLista;
    
    private ArrayList<Candidato> dados;
    private int anoSelecionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void setDados(ArrayList<Candidato> dados, int ano){
        this.dados = dados;
        this.anoSelecionado = ano;
        processarEExibirDados();
    }
    
    private void processarEExibirDados(){
        if(dados == null || dados.isEmpty()){
            return;
        }
        
        TituloLista.setText("Lista Top 10 Maiores Notas por Curso em " + anoSelecionado);
        Map<String, Double> maioresNotas = new HashMap<>();
        
        for (Candidato c : dados){
            if(c.getAno() == anoSelecionado){
                maioresNotas.put(c.getCurso(), Math.max(maioresNotas.getOrDefault(c.getCurso(), 0.0), c.getMedia()));
            }
        }
        
        List<NotadeCorte> listadeNotas = new ArrayList<>();
        for (Map.Entry<String, Double> entry : maioresNotas.entrySet()) {
            listadeNotas.add(new NotadeCorte(entry.getKey(), entry.getValue()));
        }
        
        Collections.sort(listadeNotas, Collections.reverseOrder());
        
        List<NotadeCorte> top10 = listadeNotas.stream().limit(10).collect(Collectors.toList());
        
        ObservableList<String> itensListView = FXCollections.observableArrayList();
        for(NotadeCorte nc : top10){
            itensListView.add(nc.toString());
        }
        listview.setItems(itensListView);
    }  
    
    private static class NotadeCorte implements Comparable<NotadeCorte>{
        private String curso;
        private Double nota;
        
        public NotadeCorte(String curso, Double nota){
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
        public int compareTo(NotadeCorte outra){
            return Double.compare(this.nota, outra.nota);
        }
        
        @Override
        public String toString(){
            return String.format("%s  -  %.2f", curso, nota);
        }
    }
}
