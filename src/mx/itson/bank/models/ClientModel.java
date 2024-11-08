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
     * Check if there is another client with the same name
     * @param user of the client to search
     * @return boolean where if true, a user with that name already exists and if false, no one with the same name exists
     */
    public static boolean searchUser(String user){
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select COUNT(*) FROM clients where user=?;");
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int numberUsers = resultSet.getInt(1);
                if(numberUsers>0){
                    result = true;
                }
            }
            connection.close();
   
        } catch (SQLException ex) {
            System.err.println("Error: "+ex.getMessage());
        }
        
        
        return result;
    }
    
    
    /**
     * Add a new Client to the database
     * @param name of the new Client
     * @param password of the new Client
     * @param keyId of the new Client
     * @param user of the new Client
     * @return boolean where if true, a new client has been successfully added to the database and if false, a failure has occurred
     */
    public static boolean save(String name, String password, int keyId, String user){
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query ="INSERT INTO clients (name, password, key_id, user) VALUES (?, ?, ?, ?);";
            PreparedStatement statament = connection.prepareStatement(query);
            statament.setString(1, name);
            statament.setString(2, password);
            statament.setInt(3, keyId);
            statament.setString(4, user);
            statament.execute();
            
            result = statament.getUpdateCount() == 1;
            connection.close();
        } catch (SQLException ex) {
            System.err.print("Error: "+ex.getMessage());
        }

        return result;
    }
    
    /**
     * Get the Client object with the user enter
     * @param user of the Client to search
     * @return the requested Client
     */
    public static Client getUser(String user){
        Client client = new Client();
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select * FROM clients where user=?;");
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
                client.setName(resultSet.getString(2));
                client.setPassword(resultSet.getString(3));
                client.setKeyId(resultSet.getInt(4));
                client.setUser(resultSet.getString(5));
            }
            
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: "+ex.getMessage());
        }
        
        return client;
    }  
    
    /**
     * Get the Client object with the id
     * @param id of the Client to search
     * @return the requested Client
     */
    public static Client getUserByID(int id){
        Client client = new Client();
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select * FROM clients where id=?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
                client.setName(resultSet.getString(2));
                client.setPassword(resultSet.getString(3));
                client.setKeyId(resultSet.getInt(4));
                client.setUser(resultSet.getString(5));
            }
            
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: "+ex.getMessage());
        }
        
        return client;
    }  
    
}