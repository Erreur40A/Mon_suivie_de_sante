package com.example.monsuividesante;

import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean estHeureValide(String heure) {
        String expr = "([0-1][0-9]|2[0-3]):[0-5][0-9]";

        return heure.matches(expr);
    }

    public static boolean estCaloriesSaisieValide(String calorie) {
        if (calorie.matches("[0-9]+"))
            // En moyenne un humain peut consomme au maximum 3000 kcal en une journée
            return Float.parseFloat(calorie) <= 3000;
        return false;
    }

    public static boolean estDateDuJour(String d) {
        String[] tab = d.split("/");

        Calendar calendrier1 = Calendar.getInstance(Locale.FRANCE);
        calendrier1.set(Calendar.HOUR_OF_DAY, 0);
        calendrier1.set(Calendar.MINUTE, 0);
        calendrier1.set(Calendar.SECOND, 0);
        calendrier1.set(Calendar.MILLISECOND, 0);
        calendrier1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tab[0]));
        calendrier1.set(Calendar.MONTH, Integer.parseInt(tab[1]));
        calendrier1.set(Calendar.YEAR, Integer.parseInt(tab[2]));

        Calendar calendrier2 = Calendar.getInstance(Locale.FRANCE);
        calendrier2.set(Calendar.HOUR_OF_DAY, 0);
        calendrier2.set(Calendar.MINUTE, 0);
        calendrier2.set(Calendar.SECOND, 0);
        calendrier2.set(Calendar.MILLISECOND, 0);

        // Comparer les deux calendriers
        return calendrier1.equals(calendrier2);
    }

    public static boolean estMdpValide(String mdp){
        String exprLettre = "[a-z]+";
        String exprLettreMaj = "[A-Z]+";
        String exprChiffre = "[0-9]+";

        Pattern patternLettre = Pattern.compile(exprLettre);
        Matcher matcherLettre = patternLettre.matcher(mdp);

        Pattern patternLettreMaj = Pattern.compile(exprLettreMaj);
        Matcher matcherLettreMaj = patternLettreMaj.matcher(mdp);

        Pattern patternChiffre = Pattern.compile(exprChiffre);
        Matcher matcherChiffree = patternChiffre.matcher(mdp);

        return mdp.length() >= 8 && matcherChiffree.find() && matcherLettre.find() && matcherLettreMaj.find();
    }

    public static boolean estNomPrenomValide(String nom){
        String expr = "([a-zA-Z]|-)+";

        return nom.matches(expr) && nom.length()>2;
    } 

    public static boolean estAgeValide(String age){
        if(age.matches("[0-9]+")) {
            int ageInt = Integer.parseInt(age);
            return !age.isEmpty() && ageInt <= 110 && ageInt >= 13;
        }
        return false;
    }

    public static boolean estTailleValide(String taille){
        if(taille.matches("[0-9]+")) {
            int tailleInt = Integer.parseInt(taille);

            return tailleInt <= 230 && tailleInt >= 50;
        }
        return false;
    }

    public static boolean estPoidsValide(String poids){
        if(poids.matches("[0-9]+")) {
            int poidsInt = Integer.parseInt(poids);

            return poidsInt <= 300 && poidsInt >= 50;
        }
        return false;

    }

    public static boolean estSemaineCourante(int semaine){
        Calendar calendrier = Calendar.getInstance(Locale.FRANCE);
        int semaineCourante = calendrier.get(Calendar.WEEK_OF_MONTH);

        return semaineCourante == semaine;  
    }

    public static boolean estMoisCourant(int mois) {
        Calendar calendrier = Calendar.getInstance(Locale.FRANCE);
        int moisCourante = calendrier .get(Calendar.MONTH);

        return moisCourante == mois;
    }

    public static boolean estNombrePasSaisieValide(String pas){
        String expr = "[0-9]+";
        return  pas.matches(expr);
    }
} 
       