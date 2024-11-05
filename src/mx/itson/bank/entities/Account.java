/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.entities;

/**
 *
 * @author alexi
 */
public class Account {
    
    private int id;
    private int numClient;
    private double balance;

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
    
    
    
    
}
