/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.deposit;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import setraders.alert.AlertMaker;
import setraders.database.DataHelper;
import setraders.database.DatabaseHandler;
import setraders.ui.tradingaccount.TradingAccountController;

/**
 *
 * @author alton
 */
public class DepositController implements Initializable{
   
    
    @FXML
    private Pane depositPane;
    
     @FXML
    private JFXTextField bal;
    @FXML
    private StackPane spane;
    private Boolean isInEditMode = Boolean.FALSE;
    @FXML
    private AnchorPane apane;
    DatabaseHandler databaseHandler;
    
    @FXML
    private Label label;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    databaseHandler = DatabaseHandler.getInstance();
    loadbal();
    }
    
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
        
            double balance = Double.parseDouble(bal.getText());
        String accountid = "user1";

        setraders.data.wrapper.Balance bal1 = new  setraders.data.wrapper.Balance(accountid, balance);
        boolean result = DataHelper.updateBalanceplus(bal1);
        if (result) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList<>(), "Amount ", balance + " has been withdrawn");
            clearEntries();
            refresh();
        } else {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList<>(), "Failed to add new book", "Check all the entries and try again");
        }    
    }
    
    private void refresh(){
        loadbal();
    }
    
    private void loadbal(){
        
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        
        String qu = "SELECT * FROM bal";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String transidx = rs.getString("balance");
                label.setText(transidx);
                //System.out.println(transidx);
                

            }
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    private void clearEntries() {
        bal.clear();

    }


    
}
