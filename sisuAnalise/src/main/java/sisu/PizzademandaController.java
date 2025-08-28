/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.net.URL;
import java.util.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author sophi
 */
public class PizzademandaController implements Initializable {
    @FXML
    private PieChart graficoPizza;
    @FXML
    private ListView<String> listaFiltros;
    private ArrayList<Candidato> dados;
    private Set<String> demandas;
    
    private Map<String, Integer> findFrequence(){
        
        Map<String, Integer> freq = new HashMap<>();
        
        for(String s : demandas){
            freq.put(s, 0);
        }
        
        for (Candidato c : dados) {
            freq.put(c.getDemanda(), freq.get(c.getDemanda()) + 1);
        }
        return freq;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

     public void setDados(ArrayList<Candidato> dados, ArrayList<String> filtros, Set<String> demandas) {
        this.dados = dados;
        this.demandas = demandas;

        Map<String, Integer> frequencias = findFrequence();
        ObservableList<String> dadosListaView = FXCollections.observableArrayList(filtros);

        int total = dados.size();
        dadosListaView.add(String.format("Total: %s", total));

        for (Map.Entry<String, Integer> entry : frequencias.entrySet()) {
            String txt = String.format("%s : %d", entry.getKey(), entry.getValue());
            dadosListaView.add(txt);
        }

        listaFiltros.setItems(dadosListaView);
        criarGrafico(frequencias, total);
    }
     
    private void criarGrafico(Map<String, Integer> frequencias, int total) {
        ObservableList<PieChart.Data> grafDados = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : frequencias.entrySet()) {
            grafDados.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        grafDados.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(
                    data.getName(), " ",
                    String.format("%.1f%%", (data.getPieValue() / (double) total) * 100)
                )
            )
        );

        graficoPizza.getData().setAll(grafDados);
    }
    
}
