/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sophi
 */
public class SimulacaoEspecificaController implements Initializable {


    @FXML
    private TextField txtnota;
    @FXML
    private Button botao;
    @FXML
    private TextArea txtDiff;
    @FXML
    private TextArea txtColoc;
    @FXML
    private TextArea txtF;
    @FXML
    private TextArea txtinfo;
    
    private List<Candidato> dados;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void exibirResultado(ActionEvent event) {
        
        txtinfo.setDisable(false);
        txtDiff.setDisable(false);
        txtColoc.setDisable(false);
        txtF.setDisable(false);

        double nota;
        try {
            nota = Double.parseDouble(txtnota.getText().trim());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Formato inválido");
            alert.setHeaderText("Nota inválida");
            alert.setContentText("Por favor insira uma nota válida (ex: 750.5).");
            alert.showAndWait();
            return;
        }

        int vagas = dados.size();
        double notaCorte = dados.get(dados.size() - 1).getMedia();
        boolean aprovado = (nota >= notaCorte); 
        double diferencaBruta = nota - notaCorte;
        double diferencaPercentual = (diferencaBruta / notaCorte) * 100;
        double percDoCorte = 100.0 * nota / notaCorte;

        int coloc = 1;
        for (Candidato c : dados) {
            if (c.getMedia() > nota) coloc++;
            else break;
        }
        
        double percentSupera = 100.0 * (vagas - coloc) / vagas;    
            

        // Monta textos
        String info = String.format("Curso: %s\nCampus: %s\nTipo: %s\nAno: %d\nVagas (aprovados): %d\n",
                dados.get(0).getCurso(),
                dados.get(0).getCampus(),
                dados.get(0).getDemanda(),
                dados.get(0).getAno(),
                vagas
        );
        txtinfo.setText(info);

        String comparacao = String.format(
                "Sua nota: %.2f\n"
                        + "Nota de corte (último aprovado): %.2f\n"
                        + "Diferença (sua - corte): %.2f\n"
                        + "Diferença percentual: %.2f%%",
                nota, notaCorte, diferencaBruta, diferencaPercentual
        );
        txtDiff.setText(comparacao);

        String situacao;
        if (aprovado) {
            situacao = String.format("Parabéns — você estaria APROVADO(A)!\n"
                    + "Posição entre aprovados: %dª de %d.\n"
                    + "Você supera %.1f%% dos aprovados.",
            coloc, vagas, percentSupera
            );
            
        } else {
            situacao = String.format(
                "Infelizmente, você não estaria aprovado(a).\n"
                + "Faltam %.2f pontos para o corte.\n"
                + "Você já atingiu %.1f%% da nota de corte.",
                notaCorte - nota, percDoCorte
            );
        }
        txtColoc.setText(situacao);
        
        double notaMaxima = dados.get(0).getMedia();
        double soma = dados.stream().mapToDouble(Candidato::getMedia).sum();
        double notaMedia = soma / dados.size();
        double diffMedia = nota - notaMedia;
        double percMedia = (diffMedia / notaMedia) * 100;
        
        String estatisticas = String.format(
             "Média: %.2f\n"
            + "Máxima: %.2f\n"
            + "Sua nota é %.2f pontos (%.2f%%) %s da média.",
            notaMedia,
            notaMaxima,
            Math.abs(diffMedia),
            Math.abs(percMedia),
            diffMedia >= 0 ? "ACIMA" : "ABAIXO"
        );
        txtF.setText(estatisticas);
    }

    public void setDados(ArrayList<Candidato> dados) {
        if (dados == null) {
            this.dados = Collections.emptyList();
            return;
        }
        this.dados = dados;
        this.dados.sort((c1, c2) -> Double.compare(c2.getMedia(), c1.getMedia()));
    }

}
