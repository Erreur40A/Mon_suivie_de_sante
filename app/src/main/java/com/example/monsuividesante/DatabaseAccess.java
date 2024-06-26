
package com.example.monsuividesante;

import android.content.ContentValues;
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

    private static final String APPORT_EN_ENERGIE$DATE = "date";
    private static final String APPORT_EN_ENERGIE$CALORIE_DEPENSE ="calories_depensees";
    private static final String APPORT_EN_ENERGIE$CALORIE_CONSOMME = "calories_consommees";
    private static final String APPORT_EN_ENERGIE$VARIATION = "variation";
    private static final String APPORT_EN_ENERGIE$USER_ID = "user_id";
    private static final String SOMMEIL$USER_ID = "user_id";
    private static final String SOMMEIL$HEURE_REVEIL_REEL = "heure_de_reveil_reelle";
    private static final String SOMMEIL$HEURE_REVEIL_PREVUE = "heure_de_reveil_prevue";
    private static final String SOMMEIL$HEURE_COUCHER_REEL = "heure_de_coucher_reelle";
    private static final String SOMMEIL$HEURE_COUCHER_PREVUE = "heure_de_coucher_prevue";



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
        String requete = "SELECT * FROM " + DUREE +
                " ORDER BY " + DUREE$DUREE + " ASC";

        c = db.rawQuery(requete, null);

        ArrayList<String> res = new ArrayList<String>();

        while (c.moveToNext()){
            res.add(c.getString(c.getColumnIndexOrThrow(DUREE$DUREE)));
        }

        return res;
    }

    public HashMap<String, Float> getActiviteCalories(){

        String requete = "SELECT * FROM " + ACTIVITE_CALORIE +
                " ORDER BY " + ACTIVITE_CALORIE$NOM_ACTIVITE + " ASC";

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

    public String getHeureReveilReel(int user_id) {
        String requete = "SELECT " + SOMMEIL$HEURE_REVEIL_REEL +
                " FROM " + SOMMEIL +
                " WHERE " + SOMMEIL$USER_ID + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_REVEIL_REEL));
    }

    public String getHeureReveilPrevue(int user_id){
        String requete = "SELECT " + SOMMEIL$HEURE_REVEIL_PREVUE +
                " FROM " + SOMMEIL +
                " WHERE " + SOMMEIL$USER_ID + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_REVEIL_PREVUE));
    }

    public String getDateApportEnEnergie(int user_id){
        //Le ORDER BY sert a trier les dates du plus récent au plus ancien
        String requete = "SELECT " + APPORT_EN_ENERGIE$DATE +
                " FROM " + APPORT_EN_ENERGIE +
                " WHERE user_id = ?" +
                " ORDER BY SUBSTR(date, 7, 4) || '/' || " +
                "SUBSTR(date, 4, 2) || '/' || " +
                "SUBSTR(date, 1, 2) DESC";

        c=db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(APPORT_EN_ENERGIE$DATE));
    }

    public float getCalorieConsomme(int user_id){
        String date = getDateApportEnEnergie(user_id);

        String requete = "SELECT " + APPORT_EN_ENERGIE$CALORIE_CONSOMME +
                " FROM " + APPORT_EN_ENERGIE +
                " WHERE " + APPORT_EN_ENERGIE$USER_ID + "=? AND " + APPORT_EN_ENERGIE$DATE + "=?";

        c= db.rawQuery(requete, new String[]{Integer.toString(user_id), date});
        c.moveToFirst();

        return c.getFloat(c.getColumnIndexOrThrow(APPORT_EN_ENERGIE$CALORIE_CONSOMME));
    }

    public float getCalorieDepense(int user_id){
        String date = getDateApportEnEnergie(user_id);

        String requete = "SELECT " + APPORT_EN_ENERGIE$CALORIE_DEPENSE +
                " FROM " + APPORT_EN_ENERGIE +
                " WHERE " + APPORT_EN_ENERGIE$USER_ID + "=? AND " + APPORT_EN_ENERGIE$DATE + "=?";

        c= db.rawQuery(requete, new String[]{Integer.toString(user_id), date});
        c.moveToFirst();

        return c.getFloat(c.getColumnIndexOrThrow(APPORT_EN_ENERGIE$CALORIE_DEPENSE));
    }

    public float getCalorieVariation(int user_id){
        String date = getDateApportEnEnergie(user_id);

        String requete = "SELECT " + APPORT_EN_ENERGIE$VARIATION +
                " FROM " + APPORT_EN_ENERGIE +
                " WHERE " + APPORT_EN_ENERGIE$USER_ID + "=? AND " + APPORT_EN_ENERGIE$DATE + "=?";

        c= db.rawQuery(requete, new String[]{Integer.toString(user_id), date});
        c.moveToFirst();

        return c.getFloat(c.getColumnIndexOrThrow(APPORT_EN_ENERGIE$VARIATION));
    }

    public boolean existUser(String utilisateur, String mdp) {
        c = db.rawQuery("SELECT nom_utilisateur FROM connexion WHERE nom_utilisateur = ? AND mot_de_passe = ?", new String[]{utilisateur, mdp});
        return c.getCount() == 1;
    }

    public boolean existUser(String utilisateur) {
        c = db.rawQuery("SELECT nom_utilisateur FROM connexion WHERE nom_utilisateur = ?", new String[]{utilisateur});

        return c.getCount() == 1;
    }

    public void addUser(String id_inscr, String mdp) {
        ContentValues value = new ContentValues();
        value.put("nom_utilisateur", id_inscr);
        value.put("mot_de_passe", mdp);
        long l = db.insert("connexion", null, value);
    }

    public void addInfo(int user_id, String nom, String prenom, int age, int poids, int taille, String genre, int type_de_pers) {
        ContentValues value = new ContentValues();
        value.put("user_id", user_id);
        value.put("nom", nom);
        value.put("prenom", prenom);
        value.put("age", age);
        value.put("poids", poids);
        value.put("taille", taille);
        value.put("genre", genre);
        value.put("type_de_pers", type_de_pers);
        long l = db.insert("identite", null, value);
    }

    public int getIdUtilisateur(String nom_utilisateur) {
        c = db.rawQuery("SELECT identifiant from connexion WHERE nom_utilisateur = ?", new String[]{nom_utilisateur});
        c.moveToFirst();
        return  c.getInt(c.getColumnIndexOrThrow("user_id"));
    }

    public String getNomUtilisateur(int user_id) {
        c = db.rawQuery("SELECT nom from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        c.moveToFirst();
        return  c.getString(c.getColumnIndexOrThrow("nom"));
    }

    public String getPrenomUtilisateur(int user_id) {
        c = db.rawQuery("SELECT prenom from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        c.moveToFirst();
        return  c.getString(c.getColumnIndexOrThrow("prenom"));
    }

    public int getAge(int user_id) {
        c = db.rawQuery("SELECT age from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        c.moveToFirst();
        return  c.getInt(c.getColumnIndexOrThrow("age"));
    }

    public int getPoids(int user_id) {
        c = db.rawQuery("SELECT poids from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        c.moveToFirst();
        return  c.getInt(c.getColumnIndexOrThrow("poids"));
    }

    public int getTaille(int user_id) {
        c = db.rawQuery("SELECT taille from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        c.moveToFirst();
        return  c.getInt(c.getColumnIndexOrThrow("taille"));
    }

    public String getGenre(int user_id) {
        c = db.rawQuery("SELECT genre from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        c.moveToFirst();
        return  c.getString(c.getColumnIndexOrThrow("genre"));
    }

    public int getTypeDePersonne(int user_id) {
        c = db.rawQuery("SELECT type_de_pers from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        c.moveToFirst();
        return  c.getInt(c.getColumnIndexOrThrow("type_de_pers"));
    }

    public String getHashMdp(String nom_utilisateur) {
        c = db.rawQuery("SELECT mot_de_passe from connexion WHERE nom_utilisateur = ?", new String[]{nom_utilisateur});
        c.moveToFirst();
        return  c.getString(c.getColumnIndexOrThrow("mot_de_passe"));
    }


    public void setUserLastName(int user_id, String lastName) {
        String query = "UPDATE " + IDENTITE + " SET " + COL_NAME + " = ? WHERE " + COL_USER_ID + " = ?";
        db.execSQL(query, new Object[]{lastName, user_id});
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
