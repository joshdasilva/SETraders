/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.client;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import setraders.settings.Credentials;
import setraders.main.Main;
import setraders.ui.tradingaccount.TradingAccountController;
import org.apache.commons.codec.digest.DigestUtils;     //password protection


/**
 * FXML Controller class
 *
 * @author Josh Da Silva
 */
public class ClientController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label label;
    @FXML 
    private AnchorPane anchorPane;
    
    private  double xOffset = 0;
    private  double yOffset = 0;
    
    Credentials credential;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        credential = Credentials.getCredentials();
    }    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = DigestUtils.shaHex(password.getText());

        if (uname.equals(credential.getUsername()) && pword.equals(credential.getPassword())) {
            closeStage();
            loadMain();
        } else {
            
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
    }
    
    @FXML
    private void handleFAQButtonAction(ActionEvent event){
        
    }
    @FXML
    private void handleClose(MouseEvent event){
    System.exit(0);
    }
   
    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }
    
    @FXML
    private void handleMin(MouseEvent event){
    getStage().setIconified(true);
    }
   
    @FXML
    private void handleDrag(MouseEvent event) {
            
    getStage().setX(event.getScreenX()- xOffset);
    getStage().setY(event.getScreenY() -yOffset);
        
    }
    
    @FXML
    private void handleDragValue(MouseEvent event) {
        
          xOffset = event.getSceneX();
          yOffset = event.getSceneY();
        
    }

    private void closeStage() {
        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    void loadMain() {
       
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Important Disclaimer");
        alert.setHeaderText("Disclaimer");
        alert.setContentText("Capital is at risk, losses are not liable to SE Trader and or its affiliates. Futures, stocks and options trading involves substantial risk of loss and is not suitable for every investor. The valuation of futures, stocks and options may fluctuate, and, as a result, clients may lose more than their original investment.");
        alert.initStyle(StageStyle.UNDECORATED);
        
        Optional<ButtonType> option = alert.showAndWait();
 
        if (option.get() == ButtonType.OK) { 
        try {
            
            Parent parent = FXMLLoader.load(getClass().getResource("/setraders/ui/tradingaccount/TradingAccount.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("SE Traders");
            stage.setScene(new Scene(parent));
            
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    }    
}
