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
     * @param name of the user to search
     * @return boolean where if true, a user with that name already exists and if false, no one with the same name exists
     */
    public static boolean searchUser(String name){
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select COUNT(*) FROM clients where name=?;");
            statement.setString(1, name);
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
     * Add a new client to the database
     * @param name of the new user
     * @param password of the new user
     * @param keyId of the new user
     * @return boolean where if true, a new client has been successfully added to the database and if false, a failure has occurred
     */
    public static boolean save(String name, String password, int keyId){
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query ="INSERT INTO clients (name, password, key_id) VALUES (?, ?, ?);";
            PreparedStatement statament = connection.prepareStatement(query);
            statament.setString(1, name);
            statament.setString(2, password);
            statament.setInt(3, keyId);
            statament.execute();
            
            result = statament.getUpdateCount() == 1;
            connection.close();
        } catch (SQLException ex) {
            System.err.print("Error: "+ex.getMessage());
        }

        return result;
    }
    
    /**
     * Get the Client object with the name enter
     * @param name of the User to search
     * @return the requested User
     */
    public static Client getUser(String name){
        Client user = new Client();
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select * FROM clients where name=?;");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setUser(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setKeyId(resultSet.getInt(4));
            }
            
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: "+ex.getMessage());
        }
        
        
        return user;
    }  
}