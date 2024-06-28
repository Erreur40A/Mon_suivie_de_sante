package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAccess {
    private DatabaseOpenhelper openhelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    Cursor c;

    /*Syntaxe des constantes des nom des tables : nom de la table*/
    /*Sa va nous permettre d'éviter les fautes de frappes*/
    private final String MESSAGE = "message";
    private final String DUREE = "duree";
    private final String ACTIVITE_CALORIE = "activite_calories";
    private final String SOMMEIL = "sommeil";
    private final String CONNEXION = "connexion";
    private final String APPORT_EN_ENERGIE = "apport_en_energie";
    private final String IDENTITE = "identite";
    private final String PAS_HEBDOMADAIRE = "pas_hebdo";
    private final String PAS_JOURNALIERS = "pas_journaliers";
    private final String PAS_MENSUELS = "pas_mensuels";

    /*Syntaxe des constantes des colonnes : nom de la table suivit de $ puis du nom de la colonne*/
    /*Sa va nous permettre d'éviter les fautes de frappes*/
    private final String MESSAGE$ID = "id";
    private final String MESSAGE$CONTENU = "contenu";
    private final String DUREE$DUREE = "duree";
    private final String ACTIVITE_CALORIE$NOM_ACTIVITE = "nom_activites";
    private final String ACTIVITE_CALORIE$CALORIES_PAR_KG = "calories_par_kg";
    private final String APPORT_EN_ENERGIE$DATE = "date";
    private final String APPORT_EN_ENERGIE$CALORIE_DEPENSE = "calories_depensees";
    private final String APPORT_EN_ENERGIE$CALORIE_CONSOMME = "calories_consommees";
    private final String APPORT_EN_ENERGIE$VARIATION = "variation";
    private final String APPORT_EN_ENERGIE$USER_ID = "user_id";

    private final String SOMMEIL$USER_ID = "user_id";
    private final String SOMMEIL$HEURE_REVEIL_REEL = "heure_de_reveil_reelle";
    private final String SOMMEIL$HEURE_REVEIL_PREVUE = "heure_de_reveil_prevue";
    private final String SOMMEIL$HEURE_COUCHER_REEL = "heure_de_coucher_reelle";
    private final String SOMMEIL$HEURE_COUCHER_PREVUE = "heure_de_coucher_prevue";

    /*
     * Syntaxe pour la table identite*/
    private final String COL_USER_ID = "user_id";
    private final String COL_NAME = "nom";
    private final String COL_FIRST_NAME = "prenom";
    private final String COL_AGE = "age";
    private final String COL_POIDS = "poids";
    private final String COL_TAILLE = "taille";
    private final String COL_GENRE = "genre";
    private final String COL_TYPE_DE_PERS = "type_de_pers";
    private final String PAS$USER_ID = "user_id";
    private final String PAS$OBJECTIFS = "objectifs";
    private final String PAS$NB_PAS_EFFECTUES = "nb_pas_effectues";

    private final String PAS_HEBDOMADAIRE$NO_SEMAINE = "no_semaine";
    private final String PAS_MENSUELS$NO_MOIS = "no_mois";
    private final String PAS_JOURNALIERS$DATE = "date";

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
        if (db != null) {
            this.db.close();
        }
    }

    public String getMsgMotivation(int id) {
        if (id < 0) id = 0;
        if (id > 20) id = 20;

        String requete = "SELECT " + MESSAGE$CONTENU +
                " FROM " + MESSAGE +
                " WHERE " + MESSAGE$ID + " = ?";

        c = null;
        c = db.rawQuery(requete, new String[]{Integer.toString(id)});
        try {
            c.moveToFirst();
            return c.getString(c.getColumnIndexOrThrow(MESSAGE$CONTENU));
        } catch (Exception e) {
            Log.e("getMsgMotivation", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public ArrayList<String> getDuree() {
        String requete = "SELECT * FROM " + DUREE + " ORDER BY " + DUREE$DUREE + " ASC";

        c = null;
        c = db.rawQuery(requete, null);

        ArrayList<String> res = new ArrayList<String>();

        try {
            while (c.moveToNext()) {
                res.add(c.getString(c.getColumnIndexOrThrow(DUREE$DUREE)));
            }

            return res;
        } catch (Exception e) {
            Log.e("getDuree", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public HashMap<String, Float> getActiviteCalories() {

        String requete = "SELECT * FROM " + ACTIVITE_CALORIE + " ORDER BY " + ACTIVITE_CALORIE$NOM_ACTIVITE + " ASC";

        c = null;
        c = db.rawQuery(requete, null);

        HashMap<String, Float> res = new HashMap<String, Float>();
        String activite;
        float calories;

        try {
            while (c.moveToNext()) {
                activite = c.getString(c.getColumnIndexOrThrow(ACTIVITE_CALORIE$NOM_ACTIVITE));
                calories = c.getFloat(c.getColumnIndexOrThrow(ACTIVITE_CALORIE$CALORIES_PAR_KG));

                res.put(activite, calories);
            }
            return res;
        } catch (Exception e) {
            Log.e("getActiviteCalories", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public String getHeureCoucherReel(int user_id) {
        String requete = "SELECT " + SOMMEIL$HEURE_COUCHER_REEL +
                " FROM " + SOMMEIL +
                " WHERE " + SOMMEIL$USER_ID + "=?";

        c = null;
        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();

            return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_COUCHER_REEL));
        } catch (Exception e) {
            Log.e("getHeureCoucherReel", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }

    }

    public String getHeureCoucherPrevue(int user_id) {
        String requete = "SELECT " + SOMMEIL$HEURE_COUCHER_PREVUE +
                " FROM " + SOMMEIL +
                " WHERE " + SOMMEIL$USER_ID + "=?";


        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        try {
            c.moveToFirst();

            return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_COUCHER_PREVUE));
        } catch (Exception e) {
            Log.e("getHeureCoucherPrevue", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public String getHeureReveilReel(int user_id) {
        String requete = "SELECT " + SOMMEIL$HEURE_REVEIL_REEL +
                " FROM " + SOMMEIL +
                " WHERE " + SOMMEIL$USER_ID + "=?";

        c = null;
        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();

            return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_REVEIL_REEL));
        } catch (Exception e) {
            Log.e("getHeureReveilReel", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public String getHeureReveilPrevue(int user_id) {
        String requete = "SELECT " + SOMMEIL$HEURE_REVEIL_PREVUE +
                " FROM " + SOMMEIL +
                " WHERE " + SOMMEIL$USER_ID + "=?";
        c = null;
        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});
        try {
            c.moveToFirst();

            return c.getString(c.getColumnIndexOrThrow(SOMMEIL$HEURE_REVEIL_PREVUE));
        } catch (Exception e) {
            Log.e("getHeureReveilPrevue", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public String getDateApportEnEnergie(int user_id) {
        //Le ORDER BY sert a trier les dates du plus récent au plus ancien
        String requete = "SELECT " + APPORT_EN_ENERGIE$DATE +
                " FROM " + APPORT_EN_ENERGIE +
                " WHERE user_id = ?" +
                " ORDER BY SUBSTR(date, 7, 4) || '/' || " +
                "SUBSTR(date, 4, 2) || '/' || " +
                "SUBSTR(date, 1, 2) DESC";

        c = null;
        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();

            return c.getString(c.getColumnIndexOrThrow(APPORT_EN_ENERGIE$DATE));
        } catch (Exception e) {
            Log.e("getDateApportEnEnergie", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public float getCalorieVariation(int user_id) {
        String date = getDateApportEnEnergie(user_id);

        String requete = "SELECT " + APPORT_EN_ENERGIE$VARIATION +
                " FROM " + APPORT_EN_ENERGIE +
                " WHERE " + APPORT_EN_ENERGIE$USER_ID + "=? AND " + APPORT_EN_ENERGIE$DATE + "=?";

        c = null;
        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), date});

        try {
            c.moveToFirst();

            return c.getFloat(c.getColumnIndexOrThrow(APPORT_EN_ENERGIE$VARIATION));
        } catch (Exception e) {
            Log.e("getCalorieVariation", e.getMessage(), e);
            return -1;
        } finally {
            c.close();
        }
    }

    public boolean existUserConnexion(String utilisateur, String mdp) {
        c = null;
        c = db.rawQuery("SELECT nom_utilisateur FROM connexion WHERE nom_utilisateur = ? AND mot_de_passe = ?", new String[]{utilisateur, mdp});
        try {
            return c.getCount() == 1;
        } catch (Exception e) {
            Log.e("existUserConnexion", e.getMessage(), e);
            return false;
        } finally {
            c.close();
        }
    }

    public boolean existUserSommeil(int user_id) {
        c = null;
        c = db.rawQuery("SELECT " + SOMMEIL$USER_ID + " FROM " + SOMMEIL + " WHERE " + SOMMEIL$USER_ID + "= ?", new String[]{Integer.toString(user_id)});
        try {
            return c.getCount() == 1;
        } catch (Exception e) {
            Log.e("existUserSommeil", e.getMessage(), e);
            return false;
        } finally {
            c.close();
        }
    }

    public boolean existUser(String utilisateur) {
        c = null;
        c = db.rawQuery("SELECT nom_utilisateur FROM connexion WHERE nom_utilisateur = ?", new String[]{utilisateur});

        try {
            return c.getCount() == 1;
        } catch (Exception e) {
            Log.e("existUser", e.getMessage(), e);
            return false;
        } finally {
            c.close();
        }
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
        c = null;
        c = db.rawQuery("SELECT identifiant from connexion WHERE nom_utilisateur = ?", new String[]{nom_utilisateur});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow("identifiant"));
        } catch (Exception e) {
            Log.e("getIdUtilisateur", e.getMessage(), e);
            return -1;
        } finally {
            c.close();
        }
    }

    public String getNomUtilisateur(int user_id) {
        c = null;
        c = db.rawQuery("SELECT nom from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});
        try {
            c.moveToFirst();
            return c.getString(c.getColumnIndexOrThrow("nom"));
        } catch (Exception e) {
            Log.e("getNomUtilisateur", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public String getPrenomUtilisateur(int user_id) {
        c = null;
        c = db.rawQuery("SELECT prenom from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();
            return c.getString(c.getColumnIndexOrThrow("prenom"));
        } catch (Exception e) {
            Log.e("getPrenomUtilisateur", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public int getAge(int user_id) {
        c = null;
        c = db.rawQuery("SELECT age from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow("age"));
        } catch (Exception e) {
            Log.e("getAge", e.getMessage(), e);
            return -1;
        } finally {
            c.close();
        }
    }

    public int getPoids(int user_id) {
        c = null;
        c = db.rawQuery("SELECT poids from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow("poids"));
        } catch (Exception e) {
            Log.e("getPoids", e.getMessage(), e);
            return -1;
        } finally {
            c.close();
        }
    }

    public int getTaille(int user_id) {
        c = null;
        c = db.rawQuery("SELECT taille from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow("taille"));
        } catch (Exception e) {
            Log.e("getTaille", e.getMessage(), e);
            return -1;
        } finally {
            c.close();
        }
    }

    public String getGenre(int user_id) {
        c = null;
        c = db.rawQuery("SELECT genre from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();
            return c.getString(c.getColumnIndexOrThrow("genre"));
        } catch (Exception e) {
            Log.e("getGenre", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
    }

    public int getTypeDePersonne(int user_id) {
        c = null;
        c = db.rawQuery("SELECT type_de_pers from identite WHERE user_id = ?", new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow("type_de_pers"));
        } catch (Exception e) {
            Log.e("getTypeDePersonne", e.getMessage(), e);
            return -1;
        } finally {
            c.close();
        }
    }

    public String getHashMdp(String nom_utilisateur) {
        c = null;
        c = db.rawQuery("SELECT mot_de_passe from connexion WHERE nom_utilisateur = ?", new String[]{nom_utilisateur});

        try {
            c.moveToFirst();
            return c.getString(c.getColumnIndexOrThrow("mot_de_passe"));
        } catch (Exception e) {
            Log.e("getHashMdp", e.getMessage(), e);
            return null;
        } finally {
            c.close();
        }
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

    public String getDateJournalier(int user_id) {
        c = null;

        String requete = "SELECT " + PAS_JOURNALIERS$DATE +
                " FROM " + PAS_JOURNALIERS +
                " WHERE " + PAS$USER_ID + " = ?" +
                " ORDER BY SUBSTR(date, 7, 4) || '/' || " +
                "SUBSTR(date, 4, 2) || '/' || " +
                "SUBSTR(date, 1, 2) DESC";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id)});

        try {
            c.moveToFirst();

            return c.getString(c.getColumnIndexOrThrow(PAS_JOURNALIERS$DATE));
        }catch (Exception e){
            Log.e("getDateJournalier", e.getMessage(), e);
            return null;
        }finally {
            c.close();
        }

    }

    public int getSemaineHebdomadaire(int user_id) {
        int semaine = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
        c = null;

        String requete = "SELECT " + PAS_HEBDOMADAIRE$NO_SEMAINE +
                " FROM " + PAS_HEBDOMADAIRE +
                " WHERE " + PAS$USER_ID + " = ? AND " + PAS_HEBDOMADAIRE$NO_SEMAINE + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(semaine)});

        try {
            c.moveToFirst();

            return c.getInt(c.getColumnIndexOrThrow(PAS_HEBDOMADAIRE$NO_SEMAINE));
        }catch (Exception e){
            Log.e("getSemaineHebdomadaire", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }


    }

    public int getMoisMensuelle(int user_id) {
        int mois = Calendar.getInstance().get(Calendar.MONTH);
        c = null;

        String requete = "SELECT " + PAS_MENSUELS$NO_MOIS +
                " FROM " + PAS_MENSUELS +
                " WHERE " + PAS$USER_ID + " = ? AND " + PAS_MENSUELS$NO_MOIS + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(mois)});
        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow(PAS_MENSUELS$NO_MOIS));
        }catch (Exception e){
            Log.e("getMoisMensuelle", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }
    }

    public int getObjectifJournalier(int user_id) {
        String date = getDateJournalier(user_id);
        c = null;

        String requete = "SELECT " + PAS$OBJECTIFS +
                " FROM " + PAS_JOURNALIERS +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_JOURNALIERS$DATE + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), date});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow(PAS$OBJECTIFS));
        }catch (Exception e){
            Log.e("getObjectifJournalier", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }
    }

    public int getObjectifMensuelle(int user_id) {
        int mois = getMoisMensuelle(user_id);
        c = null;

        String requete = "SELECT " + PAS$OBJECTIFS +
                " FROM " + PAS_MENSUELS +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_MENSUELS$NO_MOIS + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(mois)});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow(PAS$OBJECTIFS));
        }catch (Exception e){
            Log.e("getObjectifMensuelle", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }
    }

    public int getObjectifHedbomadaire(int user_id) {
        int semaine = getSemaineHebdomadaire(user_id);
        c = null;

        String requete = "SELECT " + PAS$OBJECTIFS +
                " FROM " + PAS_HEBDOMADAIRE +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_HEBDOMADAIRE$NO_SEMAINE + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(semaine)});
        try{
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow(PAS$OBJECTIFS));
        }catch (Exception e){
            Log.e("getObjectifHedbomadaire", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }
    }

    public int getPasJournalier(int user_id) {
        String date = getDateJournalier(user_id);
        c = null;

        String requete = "SELECT " + PAS$NB_PAS_EFFECTUES +
                " FROM " + PAS_JOURNALIERS +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_JOURNALIERS$DATE + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), date});

        try{
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow(PAS$NB_PAS_EFFECTUES));
        }catch (Exception e){
            Log.e("getPasJournalier", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }
    }

    public int getPasHebdomadaire(int user_id) {
        int semaine = getSemaineHebdomadaire(user_id);

        c = null;

        String requete = "SELECT " + PAS$NB_PAS_EFFECTUES +
                " FROM " + PAS_HEBDOMADAIRE +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_HEBDOMADAIRE$NO_SEMAINE + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(semaine)});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow(PAS$NB_PAS_EFFECTUES));
        }catch (Exception e){
            Log.e("getPasHebdomadaire", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }
    }

    public int getPasMensuelle(int user_id) {
        int mois = getMoisMensuelle(user_id);

        c = null;

        String requete = "SELECT " + PAS$NB_PAS_EFFECTUES +
                " FROM " + PAS_MENSUELS +
                " WHERE " + PAS$USER_ID + "=? AND " + PAS_MENSUELS$NO_MOIS + "=?";

        c = db.rawQuery(requete, new String[]{Integer.toString(user_id), Integer.toString(mois)});

        try {
            c.moveToFirst();
            return c.getInt(c.getColumnIndexOrThrow(PAS$NB_PAS_EFFECTUES));
        }catch (Exception e){
            Log.e("getPasMensuelle", e.getMessage(), e);
            return -1;
        }finally {
            c.close();
        }
}
}