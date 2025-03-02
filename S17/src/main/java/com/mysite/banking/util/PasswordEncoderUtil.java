package com.mysite.banking.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoderUtil {
    public static String encodePassword(String password, Integer salt){
        try{
            String passToEncode= password + salt;
            MessageDigest digest= MessageDigest.getInstance("SHA-256");
            byte[] hash= digest
                    .digest(passToEncode.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);

        }catch(NoSuchAlgorithmException exception){
            exception.printStackTrace();
            return null;
        }
    }

    //byte to hex str
    private static String bytesToHex(byte[] hash){
        StringBuilder hexString= new StringBuilder(2 * hash.length);
        for(byte b: hash){
            hexString.append(String.format("%02x" , b ^ 0xFF));
        }
        return hexString.toString();
    }

}
