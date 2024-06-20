package com.example.monsuividesante;

public class Regex {
    public static boolean isValideHeureSaisie(String heure){
        String expr = "([0-1][0-9]|2[0-3]):[0-5][0-9]";

        return heure.matches(expr);
    }

    public static boolean isValideCalorieSaisie(String calorie){
        if(calorie.matches("[0-9]*"))
            return Integer.parseInt(calorie)<=2000;
        return false;
    }
}
