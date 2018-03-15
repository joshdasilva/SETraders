/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.deposit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author alton
 */
public class DepositController implements Initializable{
   
    
    @FXML
    private Pane depositPane;
    
    @FXML //cancel button
    void handleDepositCancel(ActionEvent event) {
    Node  source = (Node)  event.getSource(); 
    Stage stage  = (Stage) source.getScene().getWindow();
    stage.close();
    }
    
    @FXML
    private void handleCreditCardButtonAction( ActionEvent event){
                      try {
        Pane pane = FXMLLoader.load(getClass().getResource("CreditCard.fxml"));
        
        depositPane.getChildren().setAll(pane);

    } catch (IOException ex) {
        Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    @FXML
    private void handlePaypalButtonAction( ActionEvent event){
                      try {
        Parent pane = FXMLLoader.load(getClass().getResource("Paypal.fxml"));
        
        depositPane.getChildren().setAll(pane);

    } catch (IOException ex) {
        Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    @FXML
    private void handleDoneButtonAction( ActionEvent event){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
}
