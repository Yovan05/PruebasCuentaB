/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.entities;

import java.util.Date;
import mx.itson.bank.enums.TransactionType;

/**
 *
 * @author alexi
 */
public class Transaction {
    
    private int id;
    private int accountId;
    private TransactionType transactionType;
    private double amount;
    private String description;
    private Date date;
    private Integer relatedAccountId;
    
    

    public Transaction(int id, int accountId, TransactionType transactionType, double amount, String description, Date date, Integer relatedAccountId) {
        this.id = id;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.relatedAccountId = relatedAccountId;
    }

    public Transaction(){
        
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
     * @return the accountId
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the transactionType
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the relatedAccountId
     */
    public Integer getRelatedAccountId() {
        return relatedAccountId;
    }

    /**
     * @param relatedAccountId the relatedAccountId to set
     */
    public void setRelatedAccountId(Integer relatedAccountId) {
        this.relatedAccountId = relatedAccountId;
    }
    
    
}
