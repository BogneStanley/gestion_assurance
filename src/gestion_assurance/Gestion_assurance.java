/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_assurance;

import gestion_assurance.database.DBInit;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Stanley
 */
public class Gestion_assurance extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gestion_assurance/views/Index.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Path p = Paths.get("");
        System.out.println(p.toAbsolutePath().toString());
        DBInit.driver();
        DBInit.connexion(p.toAbsolutePath().toString() + "\\database\\gestion_assure.db");
        launch(args);
    }
    
}
