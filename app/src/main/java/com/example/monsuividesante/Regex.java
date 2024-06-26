package com.example.monsuividesante;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Regex {
    public static boolean estHeureValide(String heure){
        String expr = "([0-1][0-9]|2[0-3]):[0-5][0-9]";

        return heure.matches(expr);
    }

    public static boolean estCaloriesSaisieValide(String calorie){
        if(calorie.matches("[0-9]+"))
            //En moyenne un humain peut consomme au maximum 3000 kcal en une journée
            return Float.parseFloat(calorie) <= 3000;
        return false;
    }

    public static boolean estDateDuJour(String d){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        format.setLenient(false);

        try {
            Date dateATest = format.parse(d);

            Date dateDuJour = format.parse(format.format(new Date()));

            assert dateDuJour != null;
            return dateDuJour.equals(dateATest);
        } catch (Exception e) {
            return true;
        }
    }


    public static boolean estNomPrenomValide(String nom){
        String expr = "([a-zA-Z]|-)+";

        return nom.matches(expr) && nom.length()>2;
    }

    public static boolean estAgeValide(String age){
        //122 ans = age de la personne la plus veille au monde
        return Integer.parseInt(age)<122;
    }

    public static boolean estTailleValide(String taille){
        //251cm = taille de la personne la plus grande au monde
        return Integer.parseInt(taille)<251;
    }

    public static boolean estPoidsValide(String poids){
        return Integer.parseInt(poids)<200;
    }

}
