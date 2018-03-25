package setraders.ui.withdraw;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
public class WithdrawController implements Initializable {


    @FXML
    private JFXTextField withdrawtxt;
    @FXML
    private StackPane spane;
    @FXML
    private AnchorPane apane;
    @FXML
    private Label label;

    DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
        loadbalance();
    }

    @FXML
    private void handleDoneButtonAction(ActionEvent event) {
        double balance = 0;
        try {
            balance = Double.parseDouble(withdrawtxt.getText());
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Invalid input", "Please input a number value");
            clearEntries();
            return;
        }

        String accountid = "user1";

        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM bal";
        ResultSet rs = handler.execQuery(qu);
        String balancex = new String();
        double check = 0;

        try {
            while (rs.next()) {
                balancex = rs.getString("balance");
                check = Double.parseDouble(balancex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (check >= balance) {
            setraders.data.wrapper.Balance bal1 = new setraders.data.wrapper.Balance(accountid, balance);
            boolean result = DataHelper.updateBalanceminus(bal1);
            if (result) {
                AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Amount", "£" + balance + " has been withdrawn");
                clearEntries();
                refresh();
            } else {
                AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Failed to withdraw amount", "Check all the entries and try again");
            }

        } else if (check <= balance) {
            AlertMaker.showMaterialDialog(spane, apane, new ArrayList < > (), "Failed to withdraw amount", "You dont have enough funds, Try a lower amount!");
        }
        clearEntries();
    }

    private void refresh() {
        loadbalance();
    }

    private void loadbalance() {

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
        withdrawtxt.clear();

    }

    @FXML //cancel button
    void handleWithdrawCancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}