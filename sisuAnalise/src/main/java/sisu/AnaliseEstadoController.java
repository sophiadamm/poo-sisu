/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;
import javafx.scene.control.TableCell;
import javafx.scene.control.Label;



/**
 * FXML Controller class
 *
 * @author pc
 */
public class AnaliseEstadoController implements Initializable {
    @FXML
    private TableView<DadosEstado> tableView;
    @FXML
    private TableColumn<DadosEstado, String> estados;
    @FXML
    private TableColumn<DadosEstado, Integer> numerodeAlunos;
    @FXML
    private TableColumn<DadosEstado, Double> media;
    @FXML
    private TableColumn<DadosEstado, Double> porcentagem;
    @FXML
    private Label titulo;
    @FXML
    private Label label_filtros;
    
    private ArrayList<Candidato> dados;
    private int total;
    private int ano;   
    
    public class DadosTemporarios {
        public int contagem = 0;
        public double somaMedias = 0.0;
    }  
    
    public void setDados(ArrayList<Candidato> dados, int ano,  List<String> filtros){
        this.dados = dados;
        this.ano = ano;
        String filtrosFormatados = String.join(", ", filtros);
        label_filtros.setText("Filtros Aplicados: " + filtrosFormatados);
        gerarTabela();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estados.setCellValueFactory(new PropertyValueFactory<>("estado"));
        numerodeAlunos.setCellValueFactory(new PropertyValueFactory<>("numeroAlunos"));
        media.setCellValueFactory(new PropertyValueFactory<>("media"));
        porcentagem.setCellValueFactory(new PropertyValueFactory<>("porcentagem"));
        
        media.setCellFactory(column -> new TableCell<DadosEstado, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(null);
                }else{
                    setText(String.format("%.2f", item));
                }
            }
        });
        porcentagem.setCellFactory(column -> new TableCell<DadosEstado, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(null);
                } else{
                    setText(String.format("%.2f%%", item));
                }
            }
        });
        
    } 
    
    public void gerarTabela(){
        Map<String, DadosTemporarios> Inicial = new HashMap<>();
        total = dados.size();
        
        for(Candidato c : dados){
            String estado = c.getEstado();
            
            DadosTemporarios data = Inicial.computeIfAbsent(estado, k -> new DadosTemporarios());
            data.contagem++;
            data.somaMedias += c.getMedia();
        }
        
        titulo.setText("Análise por Estado em " + ano);
        ArrayList<DadosEstado> listaParaTabela = new ArrayList<>();
        for(Map.Entry<String, DadosTemporarios> entry : Inicial.entrySet()){
            String estado = entry.getKey();
            DadosTemporarios data = entry.getValue();
            
            double media = data.somaMedias/data.contagem;
            double porcentagem = ((double) data.contagem/total) * 100;
            
            listaParaTabela.add(new DadosEstado(estado, data.contagem, media, porcentagem));
        }
        
        Collections.sort(listaParaTabela, new Comparator<DadosEstado>(){
            @Override
            public int compare(DadosEstado d1, DadosEstado d2){
                return Integer.compare(d2.getNumeroAlunos(), d1.getNumeroAlunos());
            }
        });
        
        ObservableList<DadosEstado> dadosObservaveis = FXCollections.observableArrayList(listaParaTabela);
        tableView.setItems(dadosObservaveis);
        
    }
}
