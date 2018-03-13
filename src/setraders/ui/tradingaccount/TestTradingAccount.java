

package setraders.ui.tradingaccount;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class TestTradingAccount extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
       Parent root = FXMLLoader.load(getClass().getResource("TradingAccount.fxml"));
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("SE Traders Login");
    } 

    public static void main(String[] args) {
        launch(args);
    }

    
} 

