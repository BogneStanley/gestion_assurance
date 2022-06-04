/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_assurance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Stanley
 */
public class DBInit {
    static PreparedStatement pst;
    static Connection con;
    
    public static void driver(){
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("driver ok");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void connexion(String dbpath){
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
            System.out.println("connexion ok");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void insert(String sql){
        try {
            pst = con.prepareStatement(sql);
            pst.execute();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public static ResultSet select(String sql){
        
        ResultSet resultSet = null ;
        
        try {
            pst = con.prepareStatement(sql);
            resultSet = pst.executeQuery();
            
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return resultSet;
    
    }

}
