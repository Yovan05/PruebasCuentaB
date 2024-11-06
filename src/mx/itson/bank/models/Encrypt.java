/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.bank.models;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import mx.itson.bank.entities.ClientKey;

/**
 *
 * @author alexi
 */
public class Encrypt {
    
    /**
     * Get the ClientKey objet with the password enter
     * @param password to encrypt
     * @return the UserKey object
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException 
     */
    public static ClientKey keys(String password) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
    
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
    
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        
        signature.update(password.getBytes());

        byte[] digitalSignature = signature.sign();
        
        String encryptedPassword = Base64.getEncoder().encodeToString(digitalSignature);
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        
        ClientKey userKey = new ClientKey(encryptedPassword, publicKeyString, privateKeyString);
        
        return userKey;
    }
    
    /**
     * Check if the password is correct
     * @param publicKeyString the publicKey of the user
     * @param password to check
     * @param encryptedPassword encrypted true password
     * @return true if the password is correct, and returns false if it is incorrect
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException 
     */
    public static boolean login(String publicKeyString, String password, String encryptedPassword) throws SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException{
        boolean result = false;
        
        byte[] digitalSignature =Base64.getDecoder().decode(encryptedPassword);
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(password.getBytes());
        boolean isValid = verifier.verify(digitalSignature);

        if (isValid) {
            result = true;
        }
        
        return result;
    }
    
}
