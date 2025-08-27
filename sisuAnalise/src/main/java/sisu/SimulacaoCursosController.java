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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author sophi
 */
public class SimulacaoCursosController implements Initializable {


    @FXML
    private TextField txtnota;
    @FXML
    private Button botao;
    @FXML
    private ListView<String> listaCursos;
    @FXML
    private TextArea mensagem;
    
    private ArrayList<Candidato> dados;
    
    private Map<String, Double> notasCorte;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    void BuscaCursos(ActionEvent event) {
        Double nota = null;
        try {
            nota = Double.parseDouble(txtnota.getText());
        } catch (NumberFormatException e) {
            System.err.println("Erro: Formato de número inválido.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Formato de número inválido");
            alert.setContentText("Erro: Por favor, insira uma nota válida (ex: 750.5).");
            alert.showAndWait();
        }
        
        int quant = 0;
        
        ArrayList<String> listaC = new ArrayList<>();
        
        for (Map.Entry<String, Double> e : notasCorte.entrySet()) {
            if (e.getValue() < nota) {
                listaC.add(e.getKey());
                quant++;
            }
        }
        ObservableList<String> dadosListaView = FXCollections.observableArrayList(listaC);
        listaCursos.setItems(dadosListaView);
        System.out.println(quant);
        System.out.println(nota);
        
    }

    public void setDados(ArrayList<Candidato> dados) {
        this.dados = dados;    
        this.notasCorte = CalculadoraNotaCorte.calcularNotaCorte(dados, false);
    }
    
}
