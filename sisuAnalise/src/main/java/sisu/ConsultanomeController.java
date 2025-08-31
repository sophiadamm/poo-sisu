package sisu;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ConsultanomeController {

    @FXML
    private Button botaoBusca;

    @FXML
    private TextField nomeBusca;

    @FXML
    private TextArea resultadoNome;
    
    private ArrayList<Candidato> dados;

    @FXML
    void BuscarNome(ActionEvent event) {
        
        boolean achou = false;
        String nome = nomeBusca.getText().trim().toUpperCase();
        if (nome == null || nome.isEmpty()) {
            resultadoNome.setText("Por favor, digite um nome para buscar.");
            return;
        }
        
        List<Candidato> encontrados = new ArrayList<>();
        for(Candidato candidato : dados){
            if(candidato.getNome().contains(nome)){
                encontrados.add(candidato);
            }
        }  
        if (encontrados.isEmpty()) {
            resultadoNome.setText("Nome não encontrado.");
            return;
        }
        if (encontrados.size() > 100) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Muitos Resultados");
            alert.setHeaderText("Muitos Resultados foram encontrados");
            alert.setContentText(String.format(
                "Foram encontrados %d candidatos. Refine sua busca (ex.: adicionar sobrenome).",
                encontrados.size()
            ));
            alert.showAndWait();
            return;
        }
        
        resultadoNome.clear();
        for (Candidato c : encontrados) {
            resultadoNome.appendText(c.toString());
        }
        
    }
    
    public void setDados(ArrayList<Candidato> dados) {
        this.dados= dados;
    }
    
    

}
