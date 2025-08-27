package sisu;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        String nome = nomeBusca.getText().toUpperCase();
        
        for(Candidato candidato : dados){
            if(candidato.nome.equals(nome)){
                resultadoNome.setText(candidato.toString());
                achou = true;
                break;
            }
        }
        
        if(!achou)resultadoNome.setText("Nome não encontrado.");
        
    }
    
    public void setDados(ArrayList<Candidato> dados) {
        this.dados= dados;
    }
    
    

}
