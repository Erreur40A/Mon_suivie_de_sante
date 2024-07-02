package com.example.monsuividesante;

import android.util.Log;

import java.security.MessageDigest;


public class Hashage {

    private static byte[] hasherMdpByte(String pw) {
        byte[] pwByte = pw.getBytes();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(pwByte);
            pwByte = md.digest();
        } catch (Exception e) {
            Log.e("hasherMdpByte", e.getMessage(), e);
        }

        return pwByte;
    }

    public static String hasherMdpHexa(String pw) {
        byte[] pwByte = hasherMdpByte(pw);

        return Hexa.byteToHexa(pwByte);
    }

    public static boolean isEquals(String hash1, String hash2) {
        return MessageDigest.isEqual(hash1.getBytes(), hash2.getBytes());
    }
}
