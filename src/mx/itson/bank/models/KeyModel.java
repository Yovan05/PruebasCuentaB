/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.itson.bank.entities.Key;
import mx.itson.bank.persistence.MySQLConnection;

/**
 *
 * @author alexi
 */
public class KeyModel {
    
    /**
     * Get the id of the user_key
     * @param publicKey of the key to search
     * @return the requested id 
     */
    public static int getKeyId(String publicKey){
        int id=0;
        try {
             Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select * FROM client_keys where public_key=?");
            statement.setString(1, publicKey);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            id= resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: "+ex.getMessage());
        }
        
        
        return id;
    }
    
    
    /**
     * Add a new key to the database
     * @param privateKey of the new Key
     * @param publicKey of the new Key
     * @return boolean where if true, a new client has been successfully added to the database and if false, a failure has occurred
     */
    public static boolean save(String publicKey, String privateKey){
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query ="INSERT INTO client_keys (public_key, private_key) VALUES (?, ?)";
            PreparedStatement statament = connection.prepareStatement(query);
            statament.setString(1, publicKey);
            statament.setString(2, privateKey);
            statament.execute();
    
            result = statament.getUpdateCount() == 1;
            connection.close();
        } catch (SQLException ex) {
            System.err.print("Error: "+ex.getMessage());
        }

        return result;
    }
    
    /**
     * Get the Key object with the id enter
     * @param id of the Key to search
     * @return the requested client
     */
    public static Key getKey(int id){
        Key key = new Key();
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("Select * FROM client_keys where id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                key.setId(resultSet.getInt(1));
                key.setPublicKey(resultSet.getString(2));
                key.setPrivateKey(resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.err.println("Error: "+ex.getMessage());
        }
        
        
        return key;
    }
    
}
