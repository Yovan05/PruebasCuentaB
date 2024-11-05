/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.models;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mx.itson.bank.entities.Client;
import mx.itson.bank.persistence.MySQLConnection;

/**
 *
 * @author alexi
 */
public class ClientModel {
    
    /**
     * Get the Client object with the name enter
     * @param name of the User to search
     * @return the requested User
     */
    public static Client getUser(String name){
        Client client = new Client();
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select * FROM bank_db where name=?;");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
                client.setName(resultSet.getString(2));
                client.setPassword(resultSet.getString(3));
            }    
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: "+ex.getMessage());
        }
        
        return client;
    }
    
    /**
     * Add a new client to the database
     * @param name of the new Client
     * @param password of the new Client
     * @return boolean where if true, a new client has been successfully added to the database and if false, a failure has occurred
     */
    public static boolean save(String name, String password){
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query ="INSERT INTO clients (name, password) VALUES (?, ?)";
            PreparedStatement statament = connection.prepareStatement(query);
            statament.setString(1, name);
            statament.setString(2, password);
            statament.execute();
            
            result = statament.getUpdateCount() == 1;
        } catch (SQLException ex) {
            System.err.print("Error: "+ex.getMessage());
        }

        return result;
    }
}
