
package setraders.ui.client;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import setraders.settings.Credentials;
import setraders.main.Main;
import setraders.ui.tradingaccount.TradingAccountController;
import org.apache.commons.codec.digest.DigestUtils; //password protection


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
    private Label invalidCredentials;
    @FXML
    private AnchorPane anchorPane;

    private double xOffset = 0;
    private double yOffset = 0;

    Credentials credential;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        credential = Credentials.getCredentials();
        invalidCredentials.setVisible(false);
    }
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = DigestUtils.shaHex(password.getText());

        if (uname.equals(credential.getUsername()) && pword.equals(credential.getPassword())) {

            loadMain();

        } else {
            invalidCredentials.setVisible(true);
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
    }

    @FXML
    private void handleFAQButtonAction(ActionEvent event) {

    }
    @FXML
    private void handleClose(MouseEvent event) {
        System.exit(0);
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    @FXML
    private void handleMin(MouseEvent event) {
        getStage().setIconified(true);
    }

    @FXML
    private void handleDrag(MouseEvent event) {

        getStage().setX(event.getScreenX() - xOffset);
        getStage().setY(event.getScreenY() - yOffset);

    }

    @FXML
    private void handleDragValue(MouseEvent event) {

        xOffset = event.getSceneX();
        yOffset = event.getSceneY();

    }

    private void closeStage() {
        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    public static void createObjects(String[] args) {

    }

    void loadMain() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Important Disclaimer");
        alert.setHeaderText("Disclaimer");
        alert.setContentText("Capital is at risk, losses are not liable to SE Trader and or its affiliates. Futures, stocks and options trading involves substantial risk of loss and is not suitable for every investor. The valuation of futures, stocks and options may fluctuate, and, as a result, clients may lose more than their original investment.");
        alert.initStyle(StageStyle.UNDECORATED);


        Optional < ButtonType > option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            try {


                List < String > choices = new ArrayList < > ();
                choices.add("USD");
                choices.add("GBP");

                ChoiceDialog < String > dialog = new ChoiceDialog < > ("USD", choices);
                dialog.setTitle("Trading Currency Choice");
                dialog.setHeaderText("Please choose your trading currency for this session. The default trading currency is USD.");
                dialog.setContentText("Choose your prefered currency");
                dialog.initStyle(StageStyle.UNDECORATED);

                Optional < String > result = dialog.showAndWait();

                if (result.isPresent() == true) {
                    if (result.isPresent() && result.get().equals("USD")) {
                        setraders.tradingitem.CryptoCurrency.selectedcryptoUSDCurrency();
                        setraders.tradingitem.Forex.selectedforexUSDCurrency();
                    } else if (result.isPresent() && result.get().equals("GBP")) {
                        setraders.tradingitem.CryptoCurrency.selectedcryptoGBPCurrency();
                        setraders.tradingitem.Forex.selectedforexGBPCurrency();
                    }

                    closeStage();
                    Parent parent = FXMLLoader.load(getClass().getResource("/setraders/ui/tradingaccount/TradingAccount.fxml"));
                    Stage stage = new Stage(StageStyle.UNDECORATED);
                    stage.setTitle("SE Traders");
                    stage.setScene(new Scene(parent));

                    stage.show();
                }
                if (result.isPresent() == false) {
                    dialog.setHeaderText("Your currency is set to USD. Please press on Ok button to continue");
                }

            } catch (IOException ex) {
                Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}