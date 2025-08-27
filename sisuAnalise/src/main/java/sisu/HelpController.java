/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sisu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
/**
 * FXML Controller class
 *
 * @author sophi
 */
public class HelpController implements Initializable {
    private Tab minhaAba;

    public void setTab(Tab aba){
        this.minhaAba = aba;
    }

    @FXML
    private Button voltar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void fecharAba(ActionEvent event) {
    minhaAba.getTabPane().getTabs().remove(minhaAba);
    }


}
