package com.example.monsuividesante;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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


    public static boolean estSemaineCourante(int semaine){
        Calendar calendrier = Calendar.getInstance(Locale.FRANCE);
        int semaineCourante = calendrier.get(Calendar.WEEK_OF_MONTH);

        return semaineCourante == semaine;
    }

    public static boolean estMoisCourant(int mois){
        Calendar calendrier = Calendar.getInstance(Locale.FRANCE);
        int moisCourante = calendrier.get(Calendar.MONTH);

        return moisCourante == mois;
    }
}
