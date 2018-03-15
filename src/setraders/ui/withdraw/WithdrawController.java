/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.withdraw;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 *
 * @author alton
 */
public class WithdrawController implements Initializable{
   
    
    
    @FXML
    private void handleDoneButtonAction( ActionEvent event){
        
    }
    
    @FXML //cancel button
    void handleWithdrawCancel(ActionEvent event) {
    Node  source = (Node)  event.getSource(); 
    Stage stage  = (Stage) source.getScene().getWindow();
    stage.close();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
}
