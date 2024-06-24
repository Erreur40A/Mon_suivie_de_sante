package com.example.monsuividesante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseAccess {

    private DatabaseOpenhelper openhelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

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
    private static final String SOMMEIL$USER_ID = "user_id";
    private static final String SOMMEIL$HEURE_REVEIL_REEL = "heure_de_reveil_reelle";
    private static final String SOMMEIL$HEURE_REVEIL_PREVUE = "heure_de_reveil_prevue";
    private static final String SOMMEIL$HEURE_COUCHER_REEL = "heure_de_coucher_reelle";
    private static final String SOMMEIL$HEURE_COUCHER_PREVUE = "heure_de_coucher_prevue";

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

        c = db.rawQuery(requete, null);
        ArrayList<String> res = new ArrayList<String>();

        while (c.moveToNext()){
            res.add(c.getString(c.getColumnIndexOrThrow(DUREE$DUREE)));
        }

        return res;
    }

    public HashMap<String, Float> getActiviteCalories(){
        String requete = "SELECT * FROM " + ACTIVITE_CALORIE;

        c = db.rawQuery(requete, null);
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

    public String getHeureCoucherReel(int user_id){
        String requete="SELECT " + SOMMEIL$HEURE_COUCHER_REEL +
                       " FROM " + SOMMEIL +
                       " WHERE " + SOMMEIL$USER_ID + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_COUCHER_REEL));
    }

    public String getHeureCoucherPrevue(int user_id){
        String requete="SELECT " + SOMMEIL$HEURE_COUCHER_PREVUE +
                       " FROM " + SOMMEIL +
                       " WHERE " + SOMMEIL$USER_ID + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_COUCHER_PREVUE));
    }

    public String getHeureReveilReel(int user_id){
        String requete="SELECT " + SOMMEIL$HEURE_REVEIL_REEL +
                       " FROM " + SOMMEIL +
                       " WHERE " + SOMMEIL$USER_ID + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});

        return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_REVEIL_REEL));
    }

    public String getHeureReveilPrevue(int user_id){
        String requete="SELECT " + SOMMEIL$HEURE_REVEIL_PREVUE +
                " FROM " + SOMMEIL +
                " WHERE " + SOMMEIL$USER_ID + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_REVEIL_PREVUE));
    }
}