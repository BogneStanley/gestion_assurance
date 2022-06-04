/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_assurance.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gestion_assurance.models.Assure;
import gestion_assurance.tools.MyTools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Stanley
 */
public class ConsulterAssureController implements Initializable {

    
   @FXML public Label nom_text;
   @FXML public Label prenom_text;
   @FXML public Label date_naissance_text;
   @FXML public Label lieu_naissance_text;
   @FXML public Label phone_text;
   @FXML public Label adresse_text;
   @FXML public Label profession_text;
   @FXML public HBox modifBox;
   
   @FXML public TextField nom_field;
   @FXML public TextField prenom_field;
   @FXML public DatePicker date_naissance_field;
   @FXML public TextField lieu_naissance_field;
   @FXML public TextField phone_field;
   @FXML public TextField adresse_field;
   @FXML public TextField profession_field;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modifBox.setVisible(false);
        LoadAssureInfo();
        
    }    
    
     public void goToAjouterAssure(MouseEvent event) throws IOException{
        new MyTools().changePageAndHide("/gestion_assurance/views/AjouterAssure.fxml", "Ajouter un assurés", event);
    }
     
    public void goToListeAssure(MouseEvent event) throws IOException{
        new MyTools().changePageAndHide("/gestion_assurance/views/Index.fxml", "Liste des assurés", event);
    }
    
    public void LoadAssureInfo(){
        
        Assure assure = Assure.current_assure;
        nom_text.setText(assure.getNom());
        prenom_text.setText(assure.getPrenom());
        date_naissance_text.setText(assure.getDate_naissance());
        lieu_naissance_text.setText(assure.getLieu_naissance());
        phone_text.setText(assure.getPhone());
        adresse_text.setText(assure.getAdresse());
        profession_text.setText(assure.getProfession());
        
        nom_field.setText(assure.getNom());
        prenom_field.setText(assure.getPrenom());
        date_naissance_field.setValue( MyTools.stringToDate(assure.getDate_naissance()));
        lieu_naissance_field.setText(assure.getLieu_naissance());
        phone_field.setText(assure.getPhone());
        adresse_field.setText(assure.getAdresse());
        profession_field.setText(assure.getProfession());
    }
    
    public void delete(MouseEvent event) throws IOException{
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Voulez vous vraiment supprimer cette assuré ?");
        Optional<ButtonType> rs = a.showAndWait();
        
        if (rs.get() == ButtonType.OK) {
            Assure.deleteAssure(Assure.current_assure.getIdassure());
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Suppression réussi");
            goToListeAssure(event);
        }
        
        
    }
    
    public void update(MouseEvent e){
        Alert a = new Alert(Alert.AlertType.NONE);
        if("".equals(nom_field.getText()) || 
                "".equals(prenom_field.getText()) || 
                date_naissance_field.getValue() == null || 
                "".equals(lieu_naissance_field.getText()) || 
                "".equals(adresse_field.getText()) || 
                "".equals(phone_field.getText()) || 
                "".equals(profession_field.getText())){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veillez remplir tous les champs");
            a.show();
            return;
        }
        
        Assure assure = Assure.current_assure;
        
        assure.setNom(nom_field.getText());
        assure.setPrenom(prenom_field.getText());
        assure.setDate_naissance(date_naissance_field.getValue().toString());
        assure.setLieu_naissance(lieu_naissance_field.getText());
        assure.setAdresse(adresse_field.getText());
        assure.setPhone(phone_field.getText());
        assure.setProfession(profession_field.getText());
        
        Assure.updateAssure(assure);
        Assure.current_assure = assure;
        LoadAssureInfo();
    }
    
    public void modifBoxVisible(){
        
        modifBox.setVisible(true);
    }
    
     public void print_assure(MouseEvent event){
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("PDF File (*.pdf)","*.pdf");
        fileChooser.getExtensionFilters().add(extension);
        
        File file = fileChooser.showSaveDialog(((Button)event.getSource()).getScene().getWindow());
       
        
        try {
            Font font = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
            Document document = new Document(PageSize.A4, 30, 30, 30, 30);
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
            
            document.open();
            Assure assure = Assure.current_assure;
            Paragraph p = new Paragraph("Informations de l'assuré",font);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(50);
            table.addCell(new PdfPCell(new Phrase("Nom",font)));            
            table.addCell(assure.getNom());
            table.addCell(new PdfPCell(new Phrase("Prénom",font)));
            table.addCell(assure.getPrenom());
            table.addCell(new PdfPCell(new Phrase("Date de naissance",font)));
            table.addCell(assure.getDate_naissance());
            table.addCell(new PdfPCell(new Phrase("Lieu de naissance",font)));
            table.addCell(assure.getLieu_naissance());
            table.addCell(new PdfPCell(new Phrase("Adresse",font)));
            table.addCell(assure.getAdresse());
            table.addCell(new PdfPCell(new Phrase("Téléphone",font)));
            table.addCell(assure.getPhone());
            table.addCell(new PdfPCell(new Phrase("Profression",font)));
            table.addCell(assure.getProfession());
            
            
           

            document.add(p);
            document.add(new Paragraph(" "));            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(table);

            document.close();
            
            System.out.println("finished");
            
        } catch (Exception e) {
            System.out.println("gestion_assurance.controllers.AssureController.print_assure()");
        }
    }
}
