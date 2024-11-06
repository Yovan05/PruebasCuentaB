
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.itson.bank.persistence.MySQLConnection;
/**
 *
 * @author alexi
 */
public class Account {
    
    private int id;
    private int numClient;
    private double balance;

    public Account(int id, int numClient, double balance) {
        this.id = id;
        this.numClient = numClient;
        this.balance = balance;
    }

     public Account(){
         
     }
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the numClient
     */
    public int getNumClient() {
        return numClient;
    }

    /**
     * @param numClient the numClient to set
     */
    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    // Método para crear una cuenta
    public static void createAccount(int numClient, double balance) {
        String sql = "INSERT INTO accounts (numClient, balance) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.get();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numClient);
            pstmt.setDouble(2, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para leer una cuenta por ID
    public static Account readAccount(int id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (Connection conn = MySQLConnection.get();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int numClient = rs.getInt("numClient");
                double balance = rs.getDouble("balance");
                return new Account(id, numClient, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar una cuenta
    public static void updateAccount(int id, int numClient, double balance) {
        String sql = "UPDATE accounts SET numClient = ?, balance = ? WHERE id = ?";
        try (Connection conn = MySQLConnection.get();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numClient);
            pstmt.setDouble(2, balance);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar una cuenta
    public static void deleteAccount(int id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = MySQLConnection.get();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
