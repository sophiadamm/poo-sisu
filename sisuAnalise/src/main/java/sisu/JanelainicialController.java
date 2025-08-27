/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author sophi
 */
public class JanelainicialController implements Initializable {

    @FXML
    private ComboBox<String> filtroAno;
    @FXML
    private ComboBox<String> filtroCurso;
    @FXML
    private ComboBox<String> filtroCampus;
    @FXML
    private ComboBox<String> filtroDemanda;
    @FXML
    private Button botao1;
    @FXML
    private Button botao2;
    @FXML
    private Button botao3;
    @FXML
    private Button botao4;
    @FXML
    private Button botao5;
    @FXML
    private Button botao6;
    @FXML
    private Button botao7;
    @FXML
    private Button botao8;
    @FXML
    private Button botao9;
    @FXML
    private Button botao10;
    @FXML
    private Button botao11;
    @FXML
    private Button botao12;
    @FXML
    private TextArea txtInfo;
    @FXML
    private Button botaoAjuda;
 
    @FXML private Button Lano;
    @FXML private Button Lcampus;
    @FXML private Button Ldemanda;
    
    @FXML private TabPane tabPane;
    @FXML private Tab tabPrincipal;
 
    private ArrayList<Candidato> dadosSisu;
    private Set<String> campus, demandas, cursos;
    
    private boolean mark = false;
   
    private void preencherFiltros(){
        List<String> anos = Arrays.asList("2019", "2020", "2021", "2022", "2023", "2024", "2025");
        filtroAno.getItems().addAll(anos);
        
        campus = new TreeSet<>();
        demandas = new TreeSet<>();
        cursos = new TreeSet<>();
        
        if (dadosSisu != null && !dadosSisu.isEmpty()) {
            for (Candidato candidato : dadosSisu) {
                campus.add(candidato.campus);
                demandas.add(candidato.demanda);
                cursos.add(candidato.curso);
            }
            
            filtroDemanda.getItems().addAll(demandas);
            filtroCampus.getItems().addAll(campus);
            filtroCurso.getItems().addAll(cursos);

        }
        
    }
    
    private ArrayList<Candidato> filtrarDados() {
        String cursoSelecionado = filtroCurso.getSelectionModel().getSelectedItem();
        String anoSelecionado = filtroAno.getSelectionModel().getSelectedItem();
        String demandaSelecionada = filtroDemanda.getSelectionModel().getSelectedItem();
        String campusSelecionado = filtroCampus.getSelectionModel().getSelectedItem();

        ArrayList<Candidato> dadosFiltrados = new ArrayList<>();

        for (Candidato candidato : dadosSisu) {
            boolean validaAno = (anoSelecionado == null || String.valueOf(candidato.ano).equals(anoSelecionado));
            boolean validaCurso = (cursoSelecionado == null || candidato.curso.equals(cursoSelecionado));
            boolean validaCampus = (campusSelecionado == null || candidato.campus.equals(campusSelecionado));
            boolean validaDemanda = (demandaSelecionada == null || candidato.demanda.equals(demandaSelecionada));

            if (validaAno && validaCurso && validaCampus && validaDemanda) {
                dadosFiltrados.add(candidato);
            }
        }
        return dadosFiltrados;
    }
    
    private ArrayList<String> filtrosSelecionados(){
        String cursoSelecionado = filtroCurso.getSelectionModel().getSelectedItem();
        String anoSelecionado = filtroAno.getSelectionModel().getSelectedItem();
        String demandaSelecionada = filtroDemanda.getSelectionModel().getSelectedItem();
        String campusSelecionado = filtroCampus.getSelectionModel().getSelectedItem();

        ArrayList<String> filtros = new ArrayList<>();
        
        if(cursoSelecionado != null && cursos.contains(cursoSelecionado)) filtros.add(cursoSelecionado);
        if(demandaSelecionada != null) filtros.add(demandaSelecionada);
        if(anoSelecionado != null) filtros.add(anoSelecionado);
        if(campusSelecionado != null) filtros.add(campusSelecionado);
        
        return filtros;
    }
        
    private void atualizar(){ 
        String cursoSelecionado = filtroCurso.getSelectionModel().getSelectedItem();
        String anoSelecionado = filtroAno.getSelectionModel().getSelectedItem();
        String demandaSelecionada = filtroDemanda.getSelectionModel().getSelectedItem();
        String campusSelecionado = filtroCampus.getSelectionModel().getSelectedItem();
        
        boolean validaAno = (anoSelecionado != null);
        boolean validaCampus = ( campusSelecionado != null);
        boolean validaDemanda = (demandaSelecionada != null);
        boolean validaCurso = (cursoSelecionado != null && cursos.contains(cursoSelecionado));

        botao1.setDisable(!validaAno);
        botao2.setDisable(!validaCampus);
        botao3.setDisable(!validaDemanda);
        botao4.setDisable(!validaCurso);
        
        botao10.setDisable(!validaAno || !validaDemanda);
        botao11.setDisable(!validaAno || validaDemanda);
    }
    
    private void adiocionarListeners(){
    
        filtroAno.valueProperty().addListener((obs, oldVal, newVal) ->  atualizar());
        filtroCampus.valueProperty().addListener((obs, oldVal, newVal) -> atualizar());
        filtroDemanda.valueProperty().addListener((obs, oldVal, newVal) ->  atualizar());
        filtroCurso.valueProperty().addListener((obs, oldVal, newVal) -> {
                        atualizar();
                        System.out.println(1);
                        mark = true;
                        });
        
        filtroCurso.getEditor().textProperty().addListener((obs, oldV, newV) -> {
            if(mark == true){
                System.out.println("Entrou");
                mark = false;
                return;
            }
            System.out.println(2);
            filtroCurso.show();
            String txt = (newV == null ? "" : newV).toLowerCase();
            if (txt.isEmpty()) {
                filtroCurso.getItems().setAll(cursos);
            } else {
                Set<String> filtrado = new TreeSet<>();
                for(String c : cursos){
                    if(c.toLowerCase().contains(txt)) filtrado.add(c);
                }
                filtroCurso.getItems().setAll(filtrado);
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.dadosSisu = Dados.getInstancia().getListaCandidatos();
        botao12.setDisable(false);
        preencherFiltros();
        adiocionarListeners();
    }    


    @FXML
    private void abrirAjuda(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("help.fxml"));
            AnchorPane abaContent = loader.load();

            Tab novaAba = new Tab("Ajuda");
            novaAba.setContent(abaContent);
            tabPane.getTabs().add(novaAba);
            tabPane.getSelectionModel().select(novaAba);

            HelpController controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirF1(ActionEvent event) {
        try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("pizzademanda.fxml")); 
            AnchorPane abaContent = loader.load();

            Tab novaAba = new Tab("Gráfico ...");
            novaAba.setContent(abaContent);
            tabPane.getTabs().add(novaAba);
            tabPane.getSelectionModel().select(novaAba);

            PizzademandaController controllerF11 = loader.getController();
            controllerF11.setDados(filtrarDados(), filtrosSelecionados(), demandas);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    @FXML
    private void abrirF2(ActionEvent event) {
    }

    @FXML
    private void abrirF3(ActionEvent event) {
    }

    @FXML
    private void abrirF4(ActionEvent event) {
    }

    @FXML
    private void abrirF5(ActionEvent event) {
    }

    @FXML
    private void abrirF6(ActionEvent event) {
    }

    @FXML
    private void abrirF7(ActionEvent event) {
    }

    @FXML
    private void abrirF8(ActionEvent event) {
    }

    @FXML
    private void abrirF9(ActionEvent event) {
    }

    @FXML
    private void abrirF10(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulacaoCursos.fxml")); 
            AnchorPane abaContent = loader.load();

            Tab novaAba = new Tab("Simulação");
            novaAba.setContent(abaContent);
            tabPane.getTabs().add(novaAba);
            tabPane.getSelectionModel().select(novaAba);

            SimulacaoCursosController controllerF10 = loader.getController();
            controllerF10.setDados(filtrarDados());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirF11(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pizzademanda.fxml")); 
            AnchorPane abaContent = loader.load();

            Tab novaAba = new Tab("N sei ainda");
            novaAba.setContent(abaContent);
            tabPane.getTabs().add(novaAba);
            tabPane.getSelectionModel().select(novaAba);

            PizzademandaController controllerF11 = loader.getController();
            controllerF11.setDados(filtrarDados(), filtrosSelecionados(), demandas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     @FXML
    private void abrirF12(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("consultanome.fxml")); 
            AnchorPane abaContent = loader.load();

            Tab novaAba = new Tab("Consultar Nome");
            novaAba.setContent(abaContent);
            tabPane.getTabs().add(novaAba);
            tabPane.getSelectionModel().select(novaAba);

            ConsultanomeController controllerF12 = loader.getController();
            controllerF12.setDados(filtrarDados());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    
    @FXML
    void limparAno(ActionEvent event) {
        filtroAno.getSelectionModel().clearSelection();
        atualizar();
    }

    @FXML
    void limparCampus(ActionEvent event) {
        filtroCampus.getSelectionModel().clearSelection();
        atualizar();
    }

    @FXML
    void limparDemanda(ActionEvent event) {
        filtroDemanda.getSelectionModel().clearSelection();
        atualizar();
    }
    


}