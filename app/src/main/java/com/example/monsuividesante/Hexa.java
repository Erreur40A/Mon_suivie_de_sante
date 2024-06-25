package com.example.monsuividesante;

import java.nio.charset.StandardCharsets;

public class Hexa {

    public static String byteToHexa(byte[] tab){
        if(tab==null) return null;

        StringBuilder res = new StringBuilder();

        for (byte b : tab) {
            res.append(Character.forDigit((b >>> 4) & 0x0F, 16)).append(Character.forDigit(b & 0x0F, 16));
        }

        return res.toString();
    }

    public static byte[] hexaToByte(String hexa){
        if(hexa==null) return null;

        int l = hexa.length();
        byte[] res = new byte[l/2];

        for (int i = 0; i < l; i+=2) {
            res[i / 2] = (byte) ((Character.digit(hexa.charAt(i), 16) << 4) | Character.digit(hexa.charAt(i+1), 16));
        }

        return res;
    }

    public static String hexaToString(String hexa){
        if(hexa==null) return null;

        byte[] resByte = hexaToByte(hexa);

        return new String(resByte, StandardCharsets.UTF_8);
    }

    public static String stringToHexa(String str){
        if(str==null) return null;

        byte[] tab = str.getBytes();
        return byteToHexa(tab);
    }
}