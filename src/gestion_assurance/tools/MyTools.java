/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_assurance.tools;


import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author Stanley
 */
public class MyTools {
    public void changePage(String path, String title) throws IOException{
      
        Parent root = FXMLLoader.load(getClass().getResource(path));
        
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    public void changePageAndHide(String path, String title, Event event) throws IOException{
        
        ((Node) event.getSource()).getScene().getWindow().hide();
        System.out.println(path);
        Parent root = FXMLLoader.load(getClass().getResource(path));

        Scene scene = new Scene(root);
        String data = "test";
        Stage stage = new Stage();
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setUserData(data);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    public static LocalDate stringToDate(String dateString){
       // String[] tmp_date = dateString.split("-");
       // dateString = String.join("/", tmp_date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        return LocalDate.parse(dateString,formatter);
    }
    
    public static LocalDate convertToLocalDate(Date date){
    
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
