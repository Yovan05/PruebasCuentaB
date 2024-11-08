/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/**

 * @author alexi
 */
public class MySQLConnection {
    
    /**
     * Connect to database
     * @return the connection
     */
    public static Connection get(){
        Connection connection =null;
        try{
           connection = DriverManager.getConnection("jdbc:mysql://itsonappweb.com/itsonapp_244601","itsonapp_244601","244601db#934R");
        }catch(Exception ex){
            System.err.print("Error: "+ex.getMessage());
        }
        return connection;
    }
}
