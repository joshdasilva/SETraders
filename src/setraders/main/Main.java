/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage; 
import javafx.stage.StageStyle;
import setraders.database.DatabaseHandler;
import static javafx.application.Application.launch;

/**
 *
 * @author Josh Da Silva
 */
public class Main extends Application {
    

    
    @Override
    public void start(Stage stage) throws Exception {
        
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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}