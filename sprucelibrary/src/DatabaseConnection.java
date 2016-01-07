/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */
import java.sql.*;

public class DatabaseConnection {

    static Connection con = null;
    static Statement stmt = null;
    
    String url = "jdbc:mysql://localhost:3306/spruce_library_database";
    String driverClass = "com.mysql.jdbc.Driver";
    String username = "root";
    String password = "";

    public void connectDatabase() throws SQLException {
        try {
            Class.forName(driverClass).newInstance();
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            
            //System.out.println("Spruce_Library_Database Connected");
        }catch (Exception e){
            //System.out.println("Database Connection Error: "+e);
        }

    }
}
