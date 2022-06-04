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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Stanley
 */
public class AssureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TableView assure_table;    
    @FXML TableColumn nom_column;    
    @FXML TableColumn prenom_column;
    @FXML TableColumn phone_column;
    @FXML TableColumn adresse_column;
    @FXML TableColumn del_column;
    @FXML TableColumn consulte_column;
    @FXML TextField search_field;
    
    public ObservableList<Assure> liste_assures = FXCollections.observableArrayList();
    
    public void chargerAssure(){
        liste_assures.clear();
        Assure.getAssures().forEach((assure)->{
            Button consulter = new Button("Consulter");            
            Button supprimer = new Button("Supprimer");
            
            consulter.getStyleClass().add("button_class");
            consulter.setUserData(assure);
            
            supprimer.getStyleClass().add("btn_red");
            supprimer.setUserData(assure);
            
            consulter.setOnAction(new EventHandler<ActionEvent>(){
                
                @Override
                public void handle(ActionEvent event) {
                    Assure.current_assure = (Assure) ((Button)event.getSource()).getUserData();
                    try {
                        
                        new MyTools().changePageAndHide("/gestion_assurance/views/ConsulterAssure.fxml", "Information d'assure", event);
                        
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
            
            });
            
            
            
            supprimer.setOnAction(new EventHandler<ActionEvent>(){
                
                @Override
                public void handle(ActionEvent event) {
                    Assure assure = (Assure)((Button)event.getSource()).getUserData();
                    
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Voulez vous vraiment supprimer cette assuré ?");
                    Optional<ButtonType> rs = a.showAndWait();

                    if (rs.get() == ButtonType.OK) {
                        Assure.deleteAssure(assure.getIdassure());
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setContentText("Suppression réussi");
                        liste_assures.remove(assure);
                    }
                     
                }
            
            });
            
            assure.setConsulter(consulter);
            assure.setSupprimer(supprimer);
            liste_assures.add(assure);
        });
        
        
    }
    
    
    public void filterList(){
        chargerAssure();
        
        ObservableList temp_list = FXCollections.observableArrayList() ;
        
        liste_assures.forEach((Assure assure)->{
            if(assure.getNom().toLowerCase().contains(search_field.getText().toLowerCase()) ||
                    assure.getPrenom().toLowerCase().contains(search_field.getText().toLowerCase()) ||
                    assure.getPhone().toLowerCase().contains(search_field.getText().toLowerCase()) ||
                    assure.getAdresse().toLowerCase().contains(search_field.getText().toLowerCase())){
                
                temp_list.add(assure);
            }
        });
        
        liste_assures.clear();
        
        liste_assures.addAll(temp_list);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nom_column.setCellValueFactory(new PropertyValueFactory("nom"));
        prenom_column.setCellValueFactory(new PropertyValueFactory("prenom"));
        adresse_column.setCellValueFactory(new PropertyValueFactory("adresse"));        
        phone_column.setCellValueFactory(new PropertyValueFactory("phone"));
        del_column.setCellValueFactory(new PropertyValueFactory("consulter"));
        consulte_column.setCellValueFactory(new PropertyValueFactory("supprimer"));
     
        chargerAssure();
        
        assure_table.setItems(liste_assures);
    }    
    
    

    
    public void goToAjouterAssure(MouseEvent event) throws IOException{
        new MyTools().changePageAndHide("/gestion_assurance/views/AjouterAssure.fxml", "Ajouter un assurés", event);
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
            
            Paragraph p = new Paragraph("Liste des Assurés",font);
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Nom",font)));            
            table.addCell(new PdfPCell(new Phrase("Prénom",font)));
            table.addCell(new PdfPCell(new Phrase("Date de naissance",font)));
            table.addCell(new PdfPCell(new Phrase("Lieu de naissance",font)));
            table.addCell(new PdfPCell(new Phrase("Adresse",font)));
            table.addCell(new PdfPCell(new Phrase("Téléphone",font)));
            table.addCell(new PdfPCell(new Phrase("Profression",font)));
            
            
            Assure.getAssures().forEach((assure)->{
            
                table.addCell(assure.getNom());
                table.addCell(assure.getPrenom());
                table.addCell(assure.getDate_naissance());
                table.addCell(assure.getLieu_naissance());
                table.addCell(assure.getAdresse());
                table.addCell(assure.getPhone());
                table.addCell(assure.getProfession());
            
            });
            

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
