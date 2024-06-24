package com.example.monsuividesante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAccess {

    private DatabaseOpenhelper openhelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    private Cursor c = null;

    /*Syntaxe des constantes des nom des tables : nom de la table*/
    /*Sa va nous permettre d'éviter les fautes de frappes*/
    private static final String MESSAGE = "message";
    private static final String DUREE = "duree";
    private static final String ACTIVITE_CALORIE = "activite_calories";
    private static final String SOMMEIL = "sommeil";
    private static final String CONNEXION = "connexion";
    private static final String APPORT_EN_ENERGIE = "apport_en_energie";
    private static final String IDENTITE = "identite";
    private static final String PAS_HEBDOMADAIRE = "pas_hebdo";
    private static final String PAS_JOURNALIERS = "pas_journaliers";
    private static final String PAS_MENSUELS = "pas_mensuels";

    /*Syntaxe des constantes des colonnes : nom de la table suivit de $ puis du nom de la colonne*/
    /*Sa va nous permettre d'éviter les fautes de frappes*/
    private static final String MESSAGE$ID = "id";
    private static final String MESSAGE$CONTENU = "contenu";
    private static final String DUREE$DUREE = "duree";
    private static final String ACTIVITE_CALORIE$NOM_ACTIVITE = "nom_activites";
    private static final String ACTIVITE_CALORIE$CALORIES_PAR_KG = "calories_par_kg";

    /*
    * Syntaxe pour la table identite*/
    private static final String COL_USER_ID = "user_id";
    private static final String COL_NAME = "nom";
    private static final String COL_FIRST_NAME = "prenom";
    private static final String COL_AGE = "age";
    private static final String COL_POIDS = "poids";
    private static final String COL_TAILLE = "taille";
    private static final String COL_GENRE = "genre";
    private static final String COL_TYPE_DE_PERS = "type_de_pers";


    private DatabaseAccess(Context context) {

        this.openhelper = new DatabaseOpenhelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openhelper.getWritableDatabase();
    }

    public void close() {
        if(db != null) {
            this.db.close();
        }
    }

    public String getMsgMotivation(int id) {
        if(id < 0) return null;

        String requete = "SELECT " + MESSAGE$CONTENU +
                         " FROM " + MESSAGE +
                         " WHERE " + MESSAGE$ID + " = ?";

        c = db.rawQuery(requete, new String[]{Integer.toString(id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(MESSAGE$CONTENU));
    }

    public ArrayList<String> getDuree(){
        String requete = "SELECT * FROM " + DUREE;

        c = db.rawQuery(requete, new String[]{});
        ArrayList<String> res = new ArrayList<String>();

        while (c.moveToNext()){
            res.add(c.getString(c.getColumnIndexOrThrow(DUREE$DUREE)));
        }

        return res;
    }

    public HashMap<String, Float> getActiviteCalories(){
        String requete = "SELECT * FROM " + ACTIVITE_CALORIE;

        c = db.rawQuery(requete, new String[]{});
        HashMap<String, Float> res = new HashMap<String, Float>();
        String activite;
        float calories;

        while (c.moveToNext()){
            activite = c.getString(c.getColumnIndexOrThrow(ACTIVITE_CALORIE$NOM_ACTIVITE));
            calories = c.getFloat(c.getColumnIndexOrThrow(ACTIVITE_CALORIE$CALORIES_PAR_KG));

            res.put(activite, calories);
        }

        return res;
    }

    /**
     * Pour obtenir le nom de l'utilisateur dans la base de donnees
     */

    public String getUserLastName(int user_id) {
        String query = "SELECT " + COL_NAME + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";

        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        Log.println(Log.INFO, "azertyuiop", c.getString(c.getColumnIndexOrThrow(COL_NAME)));

        return c.getString(c.getColumnIndexOrThrow(COL_NAME));
        /*String userLastName = null;
        if (c != null && c.moveToFirst()) {
            userLastName = c.getString(c.getColumnIndexOrThrow(COL_NAME));
        }
        return userLastName;*/


        /*String query = "SELECT " + COL_FIRST_NAME + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";
        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        String userFirstName = null;
        if (c != null && c.moveToFirst()) {
            userFirstName = c.getString(c.getColumnIndexOrThrow(COL_FIRST_NAME));
        }
        return userFirstName;
        */
    }

    public String getUserFirstName(int user_id) {
        String query = "SELECT " + COL_FIRST_NAME + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";
        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        String userFirstName = null;
        if (c != null && c.moveToFirst()) {
            userFirstName = c.getString(c.getColumnIndexOrThrow(COL_FIRST_NAME));
        }
        return userFirstName;
    }

    public int getUserAge(int user_id) {
        String query = "SELECT " + COL_AGE + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";
        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        int userAge = -1;
        if (c != null && c.moveToFirst()) {
            userAge = c.getInt(c.getColumnIndexOrThrow(COL_AGE));
        }
        return userAge;
    }

    public int getUserWeight(int user_id) {
        String query = "SELECT " + COL_POIDS + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";
        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        int userWeight = -1;
        if (c != null && c.moveToFirst()) {
            userWeight = c.getInt(c.getColumnIndexOrThrow(COL_POIDS));
        }
        return userWeight;
    }

    public int getUserHeight(int user_id) {
        String query = "SELECT " + COL_TAILLE + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";
        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        int userHeight = -1;
        if (c != null && c.moveToFirst()) {
            userHeight = c.getInt(c.getColumnIndexOrThrow(COL_TAILLE));
        }
        return userHeight;
    }

    public Genre getUserGender(int user_id) {
        String query = "SELECT " + COL_GENRE + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";
        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        Genre userGender = null;
        if (c != null && c.moveToFirst()) {
            String genderString = c.getString(c.getColumnIndexOrThrow(COL_GENRE));
            if (genderString.equalsIgnoreCase(Genre.HOMME.getGenre())) {
                userGender = Genre.HOMME;
            }
            else if (genderString.equalsIgnoreCase(Genre.FEMME.getGenre())) {
                userGender = Genre.FEMME;
            }
        }
        return userGender;
    }

    public String getUserType(int user_id) {
        String query = "SELECT " + COL_TYPE_DE_PERS + " FROM " + IDENTITE + " WHERE " + COL_USER_ID + "=?";
        c = db.rawQuery(query, new String[]{Integer.toString(user_id)});
        String userType = null;
        if (c != null && c.moveToFirst()) {
            userType = c.getString(c.getColumnIndexOrThrow(COL_TYPE_DE_PERS));
        }
        return userType;
    }

    public void setUserFirstName(int user_id, String firstName) {
        String query = "UPDATE " + IDENTITE + " SET " + COL_FIRST_NAME + " = ? WHERE " + COL_USER_ID + " = ?";
        db.execSQL(query, new Object[]{firstName, user_id});
    }

    public void setUserAge(int user_id, int age) {
        String query = "UPDATE " + IDENTITE + " SET " + COL_AGE + " = ? WHERE " + COL_USER_ID + " = ?";
        db.execSQL(query, new Object[]{age, user_id});
    }

    public void setUserWeight(int user_id, int weight) {
        String query = "UPDATE " + IDENTITE + " SET " + COL_POIDS + " = ? WHERE " + COL_USER_ID + " = ?";
        db.execSQL(query, new Object[]{weight, user_id});
    }

    public void setUserHeight(int user_id, int height) {
        String query = "UPDATE " + IDENTITE + " SET " + COL_TAILLE + " = ? WHERE " + COL_USER_ID + " = ?";
        db.execSQL(query, new Object[]{height, user_id});
    }

    public void setUserGender(int user_id, Genre gender) {
        String query = "UPDATE " + IDENTITE + " SET " + COL_GENRE + " = ? WHERE " + COL_USER_ID + " = ?";
        db.execSQL(query, new Object[]{gender.getGenre(), user_id});
    }

    public void setUserType(int user_id, String userType) {
        String query = "UPDATE " + IDENTITE + " SET " + COL_TYPE_DE_PERS + " = ? WHERE " + COL_USER_ID + " = ?";
        db.execSQL(query, new Object[]{userType, user_id});
    }




}