/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author muham
 */
public class DatabaseHelper {

//    public static boolean insertNewProject(File selectedImage) {
//        try {
//            FileInputStream image = new FileInputStream(selectedImage);
//            String sql = "INSERT INTO tb_proyek (nama_proyek, owner_proyek, pj_proyek, lokasi_proyek, thn_proyek, image) VALUES (?, ?, ?, ?, ?, ?)";
//            PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
//            stmt.setString(1, selectedImage.getName());
//            stmt.setBlob(2, image);
//            stmt.execute();
//            return true;
//        } catch (SQLException | FileNotFoundException ex) {
//            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
    public static boolean insertNewProject(String namaProyek, String ownerProyek, String lokasiProyek, Double persenKontraktor, Double persenPajak, File selectedImage){
        try{
            FileInputStream image = new FileInputStream(selectedImage);
            String sql = "INSERT INTO tb_proyek (nama_proyek, owner_proyek, lokasi_proyek, persen_kontraktor, persen_pajak, image) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, namaProyek);
            stmt.setString(2, ownerProyek);
            stmt.setString(3, lokasiProyek);
            stmt.setDouble(4, persenKontraktor);
            stmt.setDouble(5, persenPajak);
            stmt.setBlob(6, image);
            stmt.execute();
            return true;
            
        }catch(SQLException | FileNotFoundException ex){
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
//    public static boolean updateProject(int id, File imageSelected) {
//        try {
//            FileInputStream image = new FileInputStream(imageSelected);
//            String sql = "UPDATE Images SET nameImage = ?, image = ? WHERE id = ?";
//            PreparedStatement preparedStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, imageSelected.getName());
//            preparedStatement.setBlob(2, image);
//            preparedStatement.setInt(3, id);
//            preparedStatement.execute();
//            return true;
//        } catch (SQLException | FileNotFoundException ex) {
//            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
//
    public static boolean deleteProject(int id) {
        try {
            String sql = "DELETE FROM tb_proyek WHERE id = ?";
            PreparedStatement preparedStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//
    public static boolean getProject(int id) {
        Image image = null;
        try {
            String sql = "SELECT nama_proyek, owner_proyek, pj_proyek, lokasi_proyek, thn_proyek, image FROM tb_proyek WHERE id = ?";
            PreparedStatement preparedStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                InputStream imageFile = rs.getBinaryStream("image");
                image = new Image(imageFile);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
