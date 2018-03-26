package setraders.ui.deposit;

import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import setraders.alert.AlertMaker;
import setraders.database.DataHelper;
import setraders.database.DatabaseHandler;
import setraders.ui.tradingaccount.TradingAccountController;

/**
 *
 * @author Josh Da Silva
 */
public class DepositController implements Initializable {


    @FXML
    private Pane depositPane;
    @FXML
    private JFXTextField bal;
    @FXML
    private StackPane spane;
    private Boolean isInEditMode = Boolean.FALSE;
    @FXML
    private AnchorPane apane;
    @FXML
    private JFXTextField paypalEmail;
    @FXML
    private JFXPasswordField paypalPassword;
    @FXML
    private JFXTextField ccNumber;
    @FXML
    private JFXTextField ccCvv;
    @FXML
    private JFXTextField ccExp;
    @FXML
    private Label label;


    DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
        loadbal();
    }

    @FXML //cancel button
    void handleDepositCancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreditCardButtonAction(ActionEvent event) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("CreditCard.fxml"));

            depositPane.getChildren().setAll(pane);

        } catch (IOException ex) {
            Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handlePaypalButtonAction(ActionEvent event) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("Paypal.fxml"));

            depositPane.getChildren().setAll(pane);

        } catch (IOException ex) {
            Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handlePaypalDoneButtonAction(ActionEvent event) {
        double balance = 0;
        try {
            balance = Double.parseDouble(bal.getText());
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Invalid input", "Please input a number value in deposit field");
            clearEntries();
            return;
        }

        String accountid = "user1";
        String paypalMail = paypalEmail.getText();
        String paypalPass = paypalPassword.getText();


        try {
            balance = Double.parseDouble(bal.getText());
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Invalid input", "Please input a number value");
            clearEntries();
            return;
        }

        if (paypalMail.isEmpty() || paypalPass.isEmpty()) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Insufficient Data", "Please enter data in all fields.");
            return;
        }
        setraders.data.wrapper.Balance bal1 = new setraders.data.wrapper.Balance(accountid, balance);
        boolean result = DataHelper.updateBalanceplus(bal1);
        if (result) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Amount ", "£" + balance + " has been Deposited");
            clearEntries();
            refresh();
            // closestage();
        } else {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Failed to deposit amount", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleCCDoneButtonAction(ActionEvent event) {
        double balance = 0;
        try {
            balance = Double.parseDouble(bal.getText());
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Invalid input", "Please input a number value in deposit field");
            clearEntries();
            return;
        }


        String t1 = ccNumber.getText();
        String t2 = ccCvv.getText();
        String t3 = ccExp.getText();

        try {
            int ccnumber = Integer.parseInt(ccNumber.getText());
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Invalid input", "Please input a number value in Credit card number field");
            ccNumber.clear();
            return;
        }
        try {
            int cccvv = Integer.parseInt(ccCvv.getText());
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Invalid input", "Please input a number value in cvv field");
            ccCvv.clear();
            return;
        }
        try {
            int ccexp = Integer.parseInt(ccExp.getText());
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Invalid input", "Please input a number value in credit card expiry field");
            ccExp.clear();
            return;
        }

        String accountid = "user1";

        if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty()) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        setraders.data.wrapper.Balance bal1 = new setraders.data.wrapper.Balance(accountid, balance);
        boolean result = DataHelper.updateBalanceplus(bal1);
        if (result) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Amount ", "£" + balance + " has been Deposited");
            refresh();

        } else {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Failed to deposit amount", "Check all the entries and try again");
        }
    }

    @FXML
    private void getBalance(double balance) {

    }

    private void refresh() {
        loadbal();
    }

    private void closestage() {
        ((Stage) apane.getScene().getWindow()).close();
    }

    private Stage getStage() {
        return (Stage) apane.getScene().getWindow();
    }

    private void loadbal() {

        DatabaseHandler handler = DatabaseHandler.getInstance();


        String qu = "SELECT * FROM bal";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String transidx = rs.getString("balance");
                label.setText(transidx);
               


            }
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearEntries() {
        bal.clear();

    }

}