package mx.itson.bank.entities;

import java.math.BigDecimal; // Importación de BigDecimal para el balance

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

   
}
