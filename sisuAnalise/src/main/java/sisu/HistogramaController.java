/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author sophi
 */
public class HistogramaController implements Initializable {
@FXML
    private ListView<String> filtrosSelecionados;
    @FXML
    private BarChart<String, Number> histograma;
    
    private List<Candidato> dados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization can be left empty for now, as data is set later
    }    
    
    public void setDados(ArrayList<Candidato> dados, ArrayList<String> filtros) {
        this.dados = dados;
        
        if (filtros != null && !filtros.isEmpty()) {
            ObservableList<String> filtrosObservable = FXCollections.observableArrayList(filtros);
            filtrosSelecionados.setItems(filtrosObservable);
        }else{
            ObservableList<String> mensagemVazia = FXCollections.observableArrayList("Nenhum filtro foi selecionado.");
            filtrosSelecionados.setItems(mensagemVazia);
        }

        if (this.dados != null && !this.dados.isEmpty()) {
            criarHistograma();
        }
    }

    private void criarHistograma() {
        int range = 25;
        Map<String, Integer> frequenciaPorFaixa = new TreeMap<>();
        
        for (int i = 400; i < 900; i += range) {
            String faixa = i + " - " + (i + range - 1);
            frequenciaPorFaixa.put(faixa, 0);
        }
        
        // Count the frequency for each range
        for (Candidato c : dados) {
            double nota = c.getMedia();
            int faixaInicial = (int) (Math.floor(nota / range) * range);
            int faixaFinal = faixaInicial + range;
            
            String faixa = faixaInicial + " - " + (faixaFinal - 1);
            if(frequenciaPorFaixa.containsKey(faixa)){
                frequenciaPorFaixa.put(faixa, frequenciaPorFaixa.get(faixa) + 1);
            }
        }
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Frequência de Notas");
        
        for (Map.Entry<String, Integer> entry : frequenciaPorFaixa.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        histograma.getData().add(series);
    }
}
