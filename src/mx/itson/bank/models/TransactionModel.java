package mx.itson.bank.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mx.itson.bank.entities.Transaction;
import mx.itson.bank.enums.TransactionType;
import mx.itson.bank.persistence.MySQLConnection;

public class TransactionModel {

    

    // Método para realizar un depósito
    public static boolean deposit(int accountId, BigDecimal amount) {
        boolean success = false;
        Date date = new Date(new java.util.Date().getTime());
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
            String insertTransactionQuery = "INSERT INTO transactions (account_id, transaction_type, amount, date) VALUES (?, 'DEPOSIT', ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQuery)) {
                insertStmt.setInt(1, accountId);
                insertStmt.setBigDecimal(2, amount);
                insertStmt.setDate(3, date);
                insertStmt.executeUpdate();
                connection.close();
            }

            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    // Método para realizar un retiro
    public static boolean withdraw(int accountId, BigDecimal amount) {
        boolean success = false;
        Date date = new Date(new java.util.Date().getTime());
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
            String insertTransactionQuery = "INSERT INTO transactions (account_id, transaction_type, amount, date) VALUES (?, 'WITHDRAW', ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQuery)) {
                insertStmt.setInt(1, accountId);
                insertStmt.setBigDecimal(2, amount);
                insertStmt.setDate(3, date);

                insertStmt.executeUpdate();
                connection.close();
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


    public static List<Transaction> getTransactionHistory(int accountId) {
       List<Transaction> transactions = new ArrayList<>();
    try {
        Connection connection = MySQLConnection.get();
        PreparedStatement statement = connection.prepareStatement("Select * FROM transactions where account_id=?;");
        statement.setInt(1, accountId);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            Transaction t = new Transaction();
            t.setId(resultSet.getInt(1));
            t.setAccountId(resultSet.getInt(2));
            
            // Convertir el tipo de transacción a mayúsculas y mapearlo al valor correcto del enum
            String transactionTypeStr = resultSet.getString(3).toUpperCase();
            TransactionType transactionType = mapTransactionType(transactionTypeStr);
            
            // Si el tipo de transacción es inválido, omitir esta entrada
            if (transactionType == null) {
                System.err.println("Warning: Unknown transaction type '" + transactionTypeStr + "'. Skipping this entry.");
                continue;
            }
            t.setTransactionType(transactionType);

            t.setAmount(resultSet.getDouble(4));
            t.setDescription(resultSet.getString(5));
            t.setDate(resultSet.getDate(6));
            t.setRelatedAccountId(resultSet.getInt(7));
            
            transactions.add(t);
        }
        
        connection.close();
    } catch (SQLException ex) {
        System.err.println("Error: " + ex.getMessage());
    }
    
    return transactions;
}

// Método auxiliar para mapear los valores de la base de datos al enum
private static TransactionType mapTransactionType(String transactionTypeStr) {
    switch (transactionTypeStr) {
        case "DEPOSIT":
        case "DEPOSITS":
            return TransactionType.DEPOSITS;
        case "WITHDRAW":
        case "WITHDRAWALS":
            return TransactionType.WITHDRAWALS;
        case "TRANSFER":
        case "TRANSFER_RECEIVED":
        case "TRANSFERS":
            return TransactionType.TRANSFERS;
        default:
            return null; // Devuelve null si no hay coincidencia
    }
}

}