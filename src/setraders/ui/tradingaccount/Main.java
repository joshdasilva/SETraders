/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tradingaccount;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage; 
import javafx.stage.StageStyle;
import setraders.database.DatabaseHandler;

/**
 *
 * @author Josh Da Silva
 */
public class Main extends Application {
    
    private static Stage primaryStageObj;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        primaryStageObj = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("/setraders/ui/client/Client.fxml"));
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("SE Traders Login");
    
            
    new Thread(() -> {
            DatabaseHandler.getInstance();
        }).start();
    }
    
    public static Stage getStageObj()
    {
        return primaryStageObj;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
