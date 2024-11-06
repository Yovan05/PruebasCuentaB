/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *mysql://uirqb1xi6anizmbm:KAL6hWV6ExHcHbKNWTbo@bys3drn156ac3amr2lnd-mysql.services.clever-cloud.com:3306/bys3drn156ac3amr2lnd
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
            connection = DriverManager.getConnection("jdbc:mysql://uirqb1xi6anizmbm:KAL6hWV6ExHcHbKNWTbo@bys3drn156ac3amr2lnd-mysql.services.clever-cloud.com:3306/bys3drn156ac3amr2lnd","uirqb1xi6anizmbm","KAL6hWV6ExHcHbKNWTbo");
        }catch(Exception ex){
            System.err.print("Error: "+ex.getMessage());
        }
        return connection;
    }
}
