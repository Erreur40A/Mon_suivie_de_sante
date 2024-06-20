package com.example.monsuividesante;

public class Regex {
    public static boolean isValideHeure(String heure){
        String expr = "([0-1][0-9]|2[0-3]):[0-5][0-9]";

        return heure.matches(expr);
    }
}
