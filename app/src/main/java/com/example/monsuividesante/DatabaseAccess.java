package com.example.monsuividesante;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseAccess{

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

    private static final String PAS$USER_ID = "user_id";
    private static final String PAS$ANNEE = "annee";
    private static final String PAS$OBJECTIFS = "objectifs";
    private static final String PAS$NB_PAS_EFFECTUES = "nb_pas_effectues";
    private static final String PAS$DIFF_PAS = "diff_pas";
    private static final String PAS_HEBDOMADAIRE$MOIS = "mois";

    private static final String PAS_HEBDOMADAIRE$NO_SEMAINE = "no_semaine";
    private static final String PAS_MENSUELS$NO_MOIS = "no_mois";
    private static final String PAS_JOURNALIERS$DATE = "date";

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

        Log.println(Log.INFO, "getMsgMotivation", Integer.toString(id));

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

    public HashMap<String, String> getObjectifDates(int user_id) {
        HashMap<String, String> objectifDates = new HashMap<>();

        // Récupérer la date du journalier
        String dateJournalier = getDateJournalier(user_id);

        // Récupérer le mois du mensuel
        String dateMensuel = Integer.toString(getMoisMensuelle(user_id));

        // Récupérer la semaine de l'hebdomadaire
        String dateHebdomadaire = Integer.toString(getSemaineHebdomadaire(user_id));

        // Mettre les résultats dans la HashMap
        objectifDates.put("journalier", dateJournalier);
        objectifDates.put("hebdomadaire", dateHebdomadaire);
        objectifDates.put("mensuel", dateMensuel);

        return objectifDates;
    }

    public String getDateJournalier(int user_id){
        String requete = "SELECT " + PAS_JOURNALIERS$DATE +
                         " FROM " + PAS_JOURNALIERS +
                         " WHERE " + PAS$USER_ID + " = ?" +
                         " ORDER BY SUBSTR(date, 7, 4) || '/' || " +
                                    "SUBSTR(date, 4, 2) || '/' || " +
                                    "SUBSTR(date, 1, 2) DESC";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getString(c.getColumnIndexOrThrow(PAS_JOURNALIERS$DATE));
    }

    public int getSemaineHebdomadaire(int user_id){
        String requete = "SELECT " + PAS_HEBDOMADAIRE$NO_SEMAINE +
                         " FROM " + PAS_HEBDOMADAIRE +
                         " WHERE " + PAS$USER_ID + " = ?" +
                         " ORDER BY " + PAS_HEBDOMADAIRE$MOIS + ", " + PAS_HEBDOMADAIRE$NO_SEMAINE + " DESC";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getInt(c.getColumnIndexOrThrow(PAS_HEBDOMADAIRE$NO_SEMAINE));
    }

    public int getMoisMensuelle(int user_id){
        String requete = "SELECT " + PAS_MENSUELS$NO_MOIS +
                         " FROM " + PAS_MENSUELS +
                         " WHERE " + PAS$USER_ID + " = ?" +
                         " ORDER BY " + PAS$ANNEE + ", " + PAS_MENSUELS$NO_MOIS + " DESC";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getInt(c.getColumnIndexOrThrow(PAS_MENSUELS$NO_MOIS));
    }

    public int getObjectifJournalier(int user_id){
        String date = getDateJournalier(user_id);

        String requete = "SELECT " + PAS$OBJECTIFS +
                         " FROM " + PAS_JOURNALIERS +
                         " WHERE " + PAS$USER_ID + "=? AND " + PAS_JOURNALIERS$DATE + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), date});
        c.moveToFirst();

        return c.getInt(c.getColumnIndexOrThrow(PAS$OBJECTIFS));
    }

    public int getObjectifMensuelle(int user_id){
        //le mois est entre 0 et 11
        int mois = getMoisMensuelle(user_id);

        String requete = "SELECT " + PAS$OBJECTIFS +
                " FROM " + PAS_MENSUELS +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_MENSUELS$NO_MOIS + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(mois)});
        c.moveToFirst();

        return c.getInt(c.getColumnIndexOrThrow(PAS$OBJECTIFS));
    }

    public int getObjectifHedbomadaire(int user_id){
        //le mois est entre 0 et 11
        int semaine = getSemaineHebdomadaire(user_id);

        String requete = "SELECT " + PAS$OBJECTIFS +
                " FROM " + PAS_HEBDOMADAIRE +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_HEBDOMADAIRE$NO_SEMAINE + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(semaine)});
        c.moveToFirst();

        return c.getInt(c.getColumnIndexOrThrow(PAS$OBJECTIFS));
    }

    // Méthode auxiliaire pour récupérer la date depuis une table spécifique
    private String getDateFromTable(String tableName, String type) {
        String requete = "SELECT date FROM " + tableName +
                         " ORDER BY SUBSTR(date, 7, 4) || '/' || " +
                                    "SUBSTR(date, 4, 2) || '/' || " +
                                    "SUBSTR(date, 1, 2) DESC LIMIT 1";

        String date = null;

        try {
            c = db.rawQuery(requete, null);
            if (c.moveToFirst()) {
                date = c.getString(c.getColumnIndexOrThrow("date"));
            }
        } catch (SQLException e) {
            Log.e("DatabaseAccess", "Erreur lors de la récupération de la date pour " + type + ": " + e.getMessage());
        }

        return date;
    }

    public HashMap<String, Integer> getNombreDePas() {
        HashMap<String, Integer> nombreDePas = new HashMap<>();

        // Récupérer les pas journalier
        int pasJournalier = getPasFromTable(PAS_JOURNALIERS, "journalier");

        // Récupérer les pas hebdomadaire
        int pasHebdomadaire = getPasFromTable(PAS_HEBDOMADAIRE, "hebdomadaire");

        // Récupérer les pas mensuel
        int pasMensuel = getPasFromTable(PAS_MENSUELS, "mensuel");

        // Mettre les résultats dans la HashMap
        nombreDePas.put("journalier", pasJournalier);
        nombreDePas.put("hebdomadaire", pasHebdomadaire);
        nombreDePas.put("mensuel", pasMensuel);

        return nombreDePas;
    }

    public int getPas(int user_id, String table){
        String requete = "SELECT " + PAS$NB_PAS_EFFECTUES +
                         " FROM " + table +
                         " WHERE " + PAS$USER_ID + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        c.moveToFirst();

        return c.getInt(c.getColumnIndexOrThrow(PAS$NB_PAS_EFFECTUES));
    }

    // Méthode auxiliaire pour récupérer le nombre de pas depuis une table spécifique
    //A modifier ou supprimer
    private int getPasFromTable(String tableName, String type) {
        String requete = "SELECT SUM(pas) AS total_pas FROM " + tableName;
        int totalPas = 0;

        try {
            c = db.rawQuery(requete, null);
            if (c.moveToFirst()) {
                totalPas = c.getInt(c.getColumnIndexOrThrow("total_pas"));
            }
        } catch (SQLException e) {
            Log.e("DatabaseAccess", "Erreur lors de la récupération des pas pour " + type + ": " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return totalPas;
    }
}