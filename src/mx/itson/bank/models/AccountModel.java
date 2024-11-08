package mx.itson.bank.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import mx.itson.bank.entities.Account;
import mx.itson.bank.persistence.MySQLConnection;

public class AccountModel {

    // guardar una nueva cuenta
    public static boolean save(int clientId, BigDecimal balance) {
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query = "INSERT INTO accounts (client_id, balance) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, clientId);
            statement.setBigDecimal(2, balance);
            statement.execute();

            result = statement.getUpdateCount() == 1;
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return result;
    }

    // obtener una cuenta por su ID
    public static Account getAccountById(int accountId) {
        Account account = null;
        try {
            Connection connection = MySQLConnection.get();
            String query = "SELECT * FROM accounts WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int clientId = resultSet.getInt("client_id");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                account = new Account(accountId, clientId, balance);
            }
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return account;
    }

    // actualizar el saldo de una cuenta
    public static boolean updateBalance(int accountId, BigDecimal newBalance) {
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query = "UPDATE accounts SET balance = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, accountId);
            statement.executeUpdate();

            result = statement.getUpdateCount() == 1;
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return result;
    }

    // eliminar una cuenta
    public static boolean deleteAccount(int accountId) {
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query = "DELETE FROM accounts WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountId);
            statement.executeUpdate();

            result = statement.getUpdateCount() == 1;
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return result;
    }
    
    public static Account getAccountByClientId(int clientId) {
        Account account = new Account();
        try {
            Connection connection = MySQLConnection.get();
            String query = "SELECT * FROM accounts WHERE client_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account.setId(resultSet.getInt(1));
                account.setClientId(resultSet.getInt(2));
                account.setBalance(resultSet.getBigDecimal(3));
            }
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return account;
    }
    
}
