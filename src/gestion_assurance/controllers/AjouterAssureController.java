/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_assurance.controllers;

import gestion_assurance.models.Assure;
import gestion_assurance.tools.MyTools;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Stanley
 */
public class AjouterAssureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField nom_field;
    @FXML TextField prenom_field;
    @FXML DatePicker date_naissance_field;
    @FXML TextField lieu_naissance_nom_field;
    @FXML TextField phone_nom_field;
    @FXML TextField adresse_nom_field;
    @FXML TextField profession_field;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void goToListeAssure(MouseEvent event) throws IOException{
        new MyTools().changePageAndHide("/gestion_assurance/views/Index.fxml", "Liste des assurés", event);
    }
    
    public void AjouterAssure(){
        Alert a = new Alert(Alert.AlertType.NONE);
        if("".equals(nom_field.getText()) || "".equals(prenom_field.getText()) || date_naissance_field.getValue() == null || "".equals(lieu_naissance_nom_field.getText()) || "".equals(adresse_nom_field.getText()) || "".equals(phone_nom_field.getText()) || "".equals(profession_field.getText())){
            
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veillez remplir tous les champs");
            a.show();
            return;
        }
        
        Assure assure = new Assure(nom_field.getText(), prenom_field.getText(), date_naissance_field.getValue().toString(), lieu_naissance_nom_field.getText(), phone_nom_field.getText(), adresse_nom_field.getText(), profession_field.getText());
        Assure.creer(assure);
        
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Assuré enregistré avec succes");
        a.show();
    }
    
}
