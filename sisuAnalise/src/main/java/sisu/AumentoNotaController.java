/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author sophi
 */
public class AumentoNotaController implements Initializable {

    @FXML
    private TableView<CursoAumento> tabela;
    @FXML
    private TableColumn<CursoAumento, String> colunaCurso;
    @FXML
    private TableColumn<CursoAumento, Double> colunaPercentual;
    @FXML
    private TableColumn<CursoAumento, Double> colunaBruto;
    @FXML
    private TableColumn<CursoAumento, Double> colunaNotaI;
    @FXML
    private TableColumn<CursoAumento, Double> colunaNotaF;
    @FXML
    private ComboBox<String> anoInicial;
    @FXML
    private ComboBox<String> anoFinal;
    @FXML
    private Button botaoResultado;
    
    private ArrayList<Candidato> dados;
    
    private String anoISelecionado, anoFSelecionado;

    public void initialize(URL url, ResourceBundle rb) {
        List<String> anosCompletos = Arrays.asList("2019", "2020", "2021", "2022", "2023", "2024", "2025");
        List<String> anosIniciais = new ArrayList<>(anosCompletos);
        anosIniciais.remove("2025");
        anoInicial.getItems().addAll(anosIniciais);
        List<String> anosFinais = new ArrayList<>(anosCompletos);
        anosFinais.remove("2019");
        anoFinal.getItems().addAll(anosFinais);
        
        colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colunaPercentual.setCellValueFactory(new PropertyValueFactory<>("aumentoPercent"));
        colunaBruto.setCellValueFactory(new PropertyValueFactory<>("aumentoBruto"));
        colunaNotaI.setCellValueFactory(new PropertyValueFactory<>("notaInicial"));
        colunaNotaF.setCellValueFactory(new PropertyValueFactory<>("notaFinal"));
       
        botaoResultado.setDisable(true);
        anoInicial.valueProperty().addListener((obs, oldVal, newVal) ->  atualizar());
        anoFinal.valueProperty().addListener((obs, oldVal, newVal) -> atualizar());
    }

    private void atualizar() {
        anoISelecionado = anoInicial.getSelectionModel().getSelectedItem();
        anoFSelecionado = anoFinal.getSelectionModel().getSelectedItem();

        boolean estaoSelecionados = anoISelecionado != null && anoFSelecionado != null;
        boolean ordemCorreta = false;
        if(estaoSelecionados){
            ordemCorreta = anoISelecionado.compareTo(anoFSelecionado) < 0;
        }
        botaoResultado.setDisable(!(estaoSelecionados && ordemCorreta));
    }
    
  
    public void setDados(ArrayList<Candidato> dados) {
       this.dados = dados;
    }
     
    @FXML
    void exibirResultado(ActionEvent event){
        
        List<Candidato> listaAnoInicial = new ArrayList<>();
        List<Candidato> listaAnoFinal = new ArrayList<>();
        
        ObservableList<CursoAumento> dadosTabela = FXCollections.observableArrayList();
        
        
        for(Candidato c : dados){
            if(c.getAno() ==  Integer.parseInt(anoISelecionado)) listaAnoInicial.add(c);
            if(c.getAno() ==  Integer.parseInt(anoFSelecionado)) listaAnoFinal.add(c);
        }
        
        Map<String, Double> notasCorteI = CalculadoraNotaCorte.calcularNotaCorte(listaAnoInicial, false);
        Map<String, Double> notasCorteF = CalculadoraNotaCorte.calcularNotaCorte(listaAnoFinal, false);
        
            
        for (String curso : notasCorteI.keySet()) {
            if (!notasCorteF.containsKey(curso)) continue;
            double notaI = notasCorteI.get(curso);
            double notaF = notasCorteF.get(curso);
            dadosTabela.add(new CursoAumento(curso, notaI, notaF));
        }
        tabela.setItems(dadosTabela);
    }

}
