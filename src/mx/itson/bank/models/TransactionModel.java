package mx.itson.bank.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;
import mx.itson.bank.entities.Transaction;
import mx.itson.bank.enums.TransactionType;
import mx.itson.bank.persistence.MySQLConnection;

public class TransactionModel {

    public static boolean save(int accountId, String transactionType, BigDecimal amount, String transactionDescription, Date date, Integer relatedAccountId) {
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            String query = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_description, date, related_account_id) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountId);
            statement.setString(2, transactionType);
            statement.setBigDecimal(3, amount);
            statement.setString(4, transactionDescription);
            statement.setDate(5, date);
            if (relatedAccountId != null) {
                statement.setInt(6, relatedAccountId);
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }
            statement.execute();
            
            result = statement.getUpdateCount() == 1;
            connection.close();
        } catch (SQLException ex) {
            System.err.print("Error: " + ex.getMessage());
        }
        return result;
    }

   public static Transaction getTransaction(int transactionId) {
    Transaction transaction = null;
    try {
        Connection connection = MySQLConnection.get();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions WHERE id = ?;");
        statement.setInt(1, transactionId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            transaction = new Transaction();
            transaction.setId(resultSet.getInt("id"));
            transaction.setAccountId(resultSet.getInt("account_id"));
            // Convertir el String a TransactionType usando valueOf
            transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
            transaction.setAmount(resultSet.getDouble("amount"));
            transaction.setDescription(resultSet.getString("transaction_description"));
            transaction.setDate(resultSet.getDate("date"));
            
            // Manejo de relatedAccountId
            int relatedAccountId = resultSet.getInt("related_account_id");
            if (!resultSet.wasNull()) {
                transaction.setRelatedAccountId(relatedAccountId);
            } else {
                transaction.setRelatedAccountId(null);
            }
        }
        connection.close();
    } catch (SQLException ex) {
        System.err.println("Error: " + ex.getMessage());
    }
    return transaction;
}


    public static boolean transactionExists(int transactionId) {
        boolean result = false;
        try {
            Connection connection = MySQLConnection.get();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM transactions WHERE id = ?;");
            statement.setInt(1, transactionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                result = count > 0;
            }
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return result;
    }
}
