/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_assurance.models;

import gestion_assurance.database.DBInit;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 *
 * @author Stanley
 */
public class Assure {
   public static ObservableList<Assure> assures = FXCollections.observableArrayList();;
   public static Assure current_assure ;


    
   private int idassure;
   private String nom;
   private String prenom;
   private String date_naissance;
   private String lieu_naissance;
   private String phone;
   private String adresse;
   private String profession;
   private Button consulter;
   private Button supprimer;

    public Assure() {
    }

    public Assure(int idassure, String nom, String prenom, String date_naissance, String lieu_naissance, String phone, String adresse, String profession) {
        this.idassure = idassure;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.lieu_naissance = lieu_naissance;
        this.phone = phone;
        this.adresse = adresse;
        this.profession = profession;
    }
    
    public Assure(String nom, String prenom, String date_naissance, String lieu_naissance, String phone, String adresse, String profession) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.lieu_naissance = lieu_naissance;
        this.phone = phone;
        this.adresse = adresse;
        this.profession = profession;
    }

    

    public static void creer(Assure assure){
        String sql = "INSERT INTO `assure` (`id`, `nom`, `prenom`, `date_naissance`, `lieu_naissance`, `phone`, `adresse`, `profession`) "
                + "VALUES (NULL, "
                + "'"+assure.getNom()+"', "
                + "'"+ assure.getPrenom()+"', "
                + "'"+assure.getDate_naissance()+"', "
                + "'"+ assure.getLieu_naissance()+"', "
                + "'"+ assure.getPhone()+"', "
                + "'"+ assure.getAdresse()+"', "
                + "'"+ assure.getProfession()+"')";
        
        DBInit.insert(sql);
        
        assures = getAssures();
    }

    public String getAdresse() {
        return adresse;
    }

    public Button getConsulter() {
        return consulter;
    }

    public Button getSupprimer() {
        return supprimer;
    }
    
    

    public String getDate_naissance() {
        return date_naissance;
    }

    public int getIdassure() {
        return idassure;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public String getNom() {
        return nom;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getProfession() {
        return profession;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setIdassure(int idassure) {
        this.idassure = idassure;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setConsulter(Button consulter) {
        this.consulter = consulter;
    }

    public void setSupprimer(Button supprimer) {
        this.supprimer = supprimer;
    }
    
    
    
   public static Assure getOneAssure(int idAssure){
        
        Assure assure = new Assure() ;
        
        ResultSet resultSet = DBInit.select("SELECT * FROM `assure` WHERE `id` = '"+idAssure+"'");
        
        try {
            while (resultSet.next()) {
                assure.setIdassure(resultSet.getInt("id"));
                assure.setNom(resultSet.getString("nom"));
                assure.setPrenom(resultSet.getString("prenom"));
                assure.setDate_naissance(resultSet.getString("date_naissance"));
                assure.setLieu_naissance(resultSet.getString("lieu_naissance"));
                assure.setPhone(resultSet.getString("phone"));
                assure.setAdresse(resultSet.getString("adresse"));
                assure.setProfession(resultSet.getString("profession"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return assure;
    }
    
    public static ObservableList<Assure> getAssures(){
        assures.clear();
        
        ResultSet resultSet = DBInit.select("SELECT * FROM `assure`");
        
        try {
            while (resultSet.next()) {                
                assures.add(new Assure(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("date_naissance"), resultSet.getString("lieu_naissance"), resultSet.getString("phone"), resultSet.getString("adresse"), resultSet.getString("profession")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return assures;
    }
    
    public static void updateAssure(Assure assure) {
        String sql = "UPDATE `assure` SET "
                + "`nom`='"+assure.getNom()+"',"
                + "`prenom`='"+assure.getPrenom()+"',"
                + "`date_naissance`='"+assure.getDate_naissance()+"',"
                + "`lieu_naissance`='"+assure.getLieu_naissance()+"',"
                + "`phone`='"+assure.getPhone()+"',"
                + "`adresse`='"+assure.getAdresse()+"',"
                + "`profession`='"+assure.getProfession()+"'"
                + "WHERE `id` = '"+assure.getIdassure()+"'";
        
        DBInit.insert(sql);    

    }
    public static void deleteAssure(int idAssure) {
        String sql = "DELETE FROM assure WHERE id='"+idAssure+"'";
        
        DBInit.insert(sql);
    }

}
