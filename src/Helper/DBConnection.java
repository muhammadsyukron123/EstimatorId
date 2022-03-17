/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author muham
 */
public class DBConnection {
    
    Connection conn = null;
    public static Connection DBConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String host = "jdbc:mysql://localhost/estimator_id";
        String user = "root";
        String pass = "";
        try {
            Class.forName(driver);
            Connection conn = (Connection) DriverManager.getConnection(host,user,pass);
            System.out.println("Koneksi Berhasil");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi Gagal "+e);
        }
        return null;
    }
    
}
