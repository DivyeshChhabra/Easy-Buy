package com.mycompany.easybuy.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {
    public static Connection connect(){
        
        Connection myCon1 = null;
        
        try {
            /* Connecting MySQL DataBase with JAVA application */
            Class.forName("com.mysql.cj.jdbc.Driver");
            myCon1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return myCon1;
    }
}
