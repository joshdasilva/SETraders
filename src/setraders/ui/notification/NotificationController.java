
package setraders.ui.notification;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Josh Da Silva
 */
public class NotificationController implements Initializable {


    @FXML
    private AnchorPane apane;
    @FXML
    private StackPane spane;
    @FXML
    private JFXTextField notificationText;
    @FXML
    private JFXRadioButton lessThanId;
    @FXML
    private JFXRadioButton greaterThanId;
    @FXML
    private JFXRadioButton equalsToId;


    @FXML
    void setNotificationHandle(ActionEvent event) {

    }

    @FXML
    void setCancelHandle(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void radioSelect(ActionEvent event) {
        if (lessThanId.isSelected()) {
            /*double demostock=200;
        Notifications notificationBuilder = Notifications.create()
        .title("Price Alert")
        .text("Your stock has hit £"+ demostock)
        .graphic(null)
        .hideAfter(Duration.seconds(5))
        .position(Pos.BOTTOM_RIGHT)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event1) {
                System.out.println("Clicked on notification");
            }
        });
        notificationBuilder.darkStyle();
        notificationBuilder.showInformation();*/
        }
        if (greaterThanId.isSelected()) {
         
        }
        if (equalsToId.isSelected()) {

        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}