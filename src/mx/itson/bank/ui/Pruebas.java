/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mx.itson.bank.ui;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import mx.itson.bank.entities.Client;
import mx.itson.bank.entities.ClientKey;
import mx.itson.bank.models.AccountModel;
import mx.itson.bank.models.ClientModel;
import mx.itson.bank.models.Encrypt;
import mx.itson.bank.models.KeyModel;

/**
 *
 * @author alexi
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        
        /*String name = "jshdb";
        String password = "hola";
        String user ="tatata";
        ClientKey clienKey = new ClientKey();
        clienKey = Encrypt.keys(password);
        KeyModel.save(clienKey.getPublicKey(), clienKey.getPrivateKey());
        int keyId = KeyModel.getKeyId(clienKey.getPublicKey());
        boolean register =ClientModel.save(name, clienKey.getPassword(), keyId, user);
        
        Client client = new Client();
        client =ClientModel.getUser(user);
        AccountModel.save(client.getId(), BigDecimal.valueOf(0));*/
        
     //   boolean register = ClientModel.save("testeo", "Contra123");
//        System.out.println(AccountModel.searchUser(20));
        
        
        
    }
    
}
