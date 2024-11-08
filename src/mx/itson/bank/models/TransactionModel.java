package mx.itson.bank.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;
import mx.itson.bank.persistence.MySQLConnection;

public class TransactionModel {

    // Método para realizar un depósito
    public static boolean deposit(int accountId, BigDecimal amount, String description, Date date) {
        boolean success = false;
        try (Connection connection = MySQLConnection.get()) {
            // Verificar si la cuenta existe
            if (!accountExists(accountId)) {
                return false;
            }

            // Actualizar saldo
            String updateBalanceQuery = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateBalanceQuery)) {
                updateStmt.setBigDecimal(1, amount);
                updateStmt.setInt(2, accountId);
                updateStmt.executeUpdate();
            }

            // Registrar la transacción
            String insertTransactionQuery = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_description, date) VALUES (?, 'DEPOSIT', ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQuery)) {
                insertStmt.setInt(1, accountId);
                insertStmt.setBigDecimal(2, amount);
                insertStmt.setString(3, description);
                insertStmt.setDate(4, date);
                insertStmt.executeUpdate();
            }

            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    // Método para realizar un retiro
    public static boolean withdraw(int accountId, BigDecimal amount, String description, Date date) {
        boolean success = false;
        try (Connection connection = MySQLConnection.get()) {
            // Verificar si la cuenta existe y tiene saldo suficiente
            if (!accountExists(accountId) || !hasSufficientBalance(accountId, amount)) {
                return false;
            }

            // Actualizar saldo
            String updateBalanceQuery = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateBalanceQuery)) {
                updateStmt.setBigDecimal(1, amount);
                updateStmt.setInt(2, accountId);
                updateStmt.executeUpdate();
            }

            // Registrar la transacción
            String insertTransactionQuery = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_description, date) VALUES (?, 'WITHDRAW', ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQuery)) {
                insertStmt.setInt(1, accountId);
                insertStmt.setBigDecimal(2, amount);
                insertStmt.setString(3, description);
                insertStmt.setDate(4, date);
                insertStmt.executeUpdate();
            }

            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }
    
    

    // Método para realizar una transferencia
    public static boolean transfer(int fromAccountId, int toAccountId, BigDecimal amount, String description, Date date) {
       String transactionType = "Tipo de transacción";
        System.out.println("Valor de transaction_type: " + transactionType);
        boolean success = false;
        try (Connection connection = MySQLConnection.get()) {
            // Verificar si las cuentas existen y si la cuenta de origen tiene saldo suficiente
            if (!accountExists(fromAccountId) || !accountExists(toAccountId) || !hasSufficientBalance(fromAccountId, amount)) {
                return false;
            }

            // Retirar de la cuenta de origen
            String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            try (PreparedStatement withdrawStmt = connection.prepareStatement(withdrawQuery)) {
                withdrawStmt.setBigDecimal(1, amount);
                withdrawStmt.setInt(2, fromAccountId);
                withdrawStmt.executeUpdate();
            }

            // Depositar en la cuenta de destino
            String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            try (PreparedStatement depositStmt = connection.prepareStatement(depositQuery)) {
                depositStmt.setBigDecimal(1, amount);
                depositStmt.setInt(2, toAccountId);
                depositStmt.executeUpdate();
            }

            // Registrar la transacción de origen
            String insertTransactionQueryFrom = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_description, date, related_account_id) VALUES (?, 'TRANSFER', ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQueryFrom)) {
                insertStmt.setInt(1, fromAccountId);
                insertStmt.setBigDecimal(2, amount);
                insertStmt.setString(3, description);
                insertStmt.setDate(4, date);
                insertStmt.setInt(5, toAccountId);
                insertStmt.executeUpdate();
            }

            // Registrar la transacción 
            String insertTransactionQueryTo = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_description, date, related_account_id) VALUES (?, 'TRANSFER_RECEIVED', ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQueryTo)) {
                insertStmt.setInt(1, toAccountId);
                insertStmt.setBigDecimal(2, amount);
                insertStmt.setString(3, "Received: " + description);
                insertStmt.setDate(4, date);
                insertStmt.setInt(5, fromAccountId);
                insertStmt.executeUpdate();
            }

            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    // Método para saber si la cuenta existe 
    public static boolean accountExists(int accountId) {
        boolean exists = false;
        try (Connection connection = MySQLConnection.get()) {
            String query = "SELECT COUNT(*) FROM accounts WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, accountId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exists;
    }
    

public static Integer getAccountIdByCriteria(String criteriaColumn, String criteriaValue) {
    Integer accountId = null;
    try (Connection connection = MySQLConnection.get()) {
        String query = "SELECT account_id FROM transactions WHERE " + criteriaColumn + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, criteriaValue); // Considera el tipo correcto de dato aquí
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                accountId = rs.getInt("account_id");
                System.out.println("Account ID found: " + accountId);
            } else {
                System.out.println("No matching account ID found for criteria: " + criteriaValue);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return accountId;
}



    // Método para ver si el saldo es suficiente o no tiene dineroxddd
    public static boolean hasSufficientBalance(int accountId, BigDecimal amount) {
        boolean sufficient = false;
        try (Connection connection = MySQLConnection.get()) {
            String query = "SELECT balance FROM accounts WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, accountId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    BigDecimal balance = rs.getBigDecimal("balance");
                    sufficient = balance.compareTo(amount) >= 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sufficient;
    }
}
