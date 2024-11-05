/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mx.itson.bank.ui;

import mx.itson.bank.models.ClientModel;

/**
 *
 * @author alexi
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        boolean register = ClientModel.save("Yovan Rodriguez", "Contra123");
        System.out.println(register);
    }
    
}
