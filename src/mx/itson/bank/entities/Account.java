package mx.itson.bank.entities;

import java.math.BigDecimal; // Importación de BigDecimal para el balance
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.itson.bank.persistence.MySQLConnection;

public class Account {
    
    private int id;
    private int clientId; // Cambio de numClient a clientId
    private BigDecimal balance; // Cambio de double a BigDecimal para mayor precisión

    // Constructor con parámetros
    public Account(int id, int clientId, BigDecimal balance) {
        this.id = id;
        this.clientId = clientId;
        this.balance = balance;
    }

    // Constructor vacío
    public Account() {}

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // Método para crear una cuenta
    public static void createAccount(int clientId, BigDecimal balance) {
        String sql = "INSERT INTO accounts (client_id, balance) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.get();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            pstmt.setBigDecimal(2, balance);
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
                int clientId = rs.getInt("client_id");
                BigDecimal balance = rs.getBigDecimal("balance");
                return new Account(id, clientId, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar una cuenta
    public static void updateAccount(int id, int clientId, BigDecimal balance) {
        String sql = "UPDATE accounts SET client_id = ?, balance = ? WHERE id = ?";
        try (Connection conn = MySQLConnection.get();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            pstmt.setBigDecimal(2, balance);
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
