
package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseOpenhelper extends SQLiteAssetHelper {


    /*Syntaxe des constantes des nom des tables : nom de la table*/
    /*Sa va nous permettre d'éviter les fautes de frappes*/
    private final String MESSAGE = "message";
    private final String DUREE = "duree";
    private final String ACTIVITE_CALORIE = "activite_calories";
    private final String SOMMEIL = "sommeil";
    private final String CONNEXION = "connexion";
    private final String APPORT_EN_ENERGIE = "apport_en_energie";
    private final String IDENTITE = "identite";

    private final String SOMMEIL$USER_ID = "user_id";
    private final String SOMMEIL$HEURE_REVEIL_REEL = "heure_de_reveil_reelle";
    private final String SOMMEIL$HEURE_REVEIL_PREVUE = "heure_de_reveil_prevue";
    private final String SOMMEIL$HEURE_COUCHER_REEL = "heure_de_coucher_reelle";
    private final String SOMMEIL$HEURE_COUCHER_PREVUE = "heure_de_coucher_prevue";
    private final String MESSAGE$ID = "id";
    private final String MESSAGE$CONTENU = "contenu";
    private final String DUREE$DUREE = "duree";
    private final String ACTIVITE_CALORIE$NOM_ACTIVITE = "nom_activites";
    private final String ACTIVITE_CALORIE$CALORIES_PAR_KG = "calories_par_kg";
    private final String APPORT_EN_ENERGIE$DATE = "date";
    private final String APPORT_EN_ENERGIE$CALORIE_DEPENSE ="calories_depensees";
    private final String APPORT_EN_ENERGIE$CALORIE_CONSOMME = "calories_consommees";
    private final String APPORT_EN_ENERGIE$VARIATION = "variation";
    private final String APPORT_EN_ENERGIE$USER_ID = "user_id";

    private final String IDENTITE$USER_ID="user_id";
    private final String IDENTITE$NOM="nom";
    private final String IDENTITE$PRENOM="prenom";
    private final String IDENTITE$AGE="age";
    private final String IDENTITE$POIDS="poids";
    private final String IDENTITE$TAILLE="taille";
    private final String IDENTITE$GENRE="genre";
    private final String IDENTITE$TYPE_DE_PERSONNE="type_de_pers";

    private final String PAS_HEBDOMADAIRE = "pas_hebdo";
    private final String PAS_JOURNALIERS = "pas_journaliers";
    private final String PAS_MENSUELS = "pas_mensuels";

    private final String PAS$USER_ID = "user_id";
    private final String PAS$OBJECTIFS = "objectifs";
    private final String PAS$NB_PAS_EFFECTUES = "nb_pas_effectues";
    private final String PAS$DIFF_PAS = "diff_pas";

    private final String PAS_HEBDOMADAIRE$NO_SEMAINE = "no_semaine";
    private final String PAS_MENSUELS$NO_MOIS = "no_mois";
    private final String PAS_JOURNALIERS$DATE = "date";

    private static final String DATABASE_NAME = "mon_suivi_de_sante_db.db";


    public DatabaseOpenhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void updatePrenom(String prenom, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDENTITE$PRENOM, prenom);
        db.update(IDENTITE, values,  IDENTITE$USER_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateNom(String nom, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDENTITE$NOM, nom);
        db.update(IDENTITE, values,  IDENTITE$USER_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateAge(int age, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDENTITE$AGE, age);
        db.update(IDENTITE, values,  IDENTITE$USER_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateTaille(int taille, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDENTITE$TAILLE, taille);
        db.update(IDENTITE, values,  IDENTITE$USER_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updatePoids(int poids, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDENTITE$POIDS, poids);
        db.update(IDENTITE, values,  IDENTITE$USER_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateGenre(String genre, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDENTITE$GENRE, genre);
        db.update(IDENTITE, values,  IDENTITE$USER_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateTypeDePersonne(int type, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IDENTITE$TYPE_DE_PERSONNE, type);
        db.update(IDENTITE, values,  IDENTITE$USER_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateHeureReveilPrevue(int user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_REVEIL_PREVUE, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{Integer.toString(user_id)});
    }

    public void addLigneSommeil(int user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            ContentValues ligne = new ContentValues();

            ligne.put(SOMMEIL$USER_ID, user_id);

            db.insert(SOMMEIL, null, ligne);
        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLigneSommeil");
        }
    }

    public void deleteConnexion(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONNEXION, null, null);
        db.close();
    }

    public void deleteSommeil(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SOMMEIL, null, null);
        db.close();
    }

    public void addLigneIdentite(int user_id, String prenom, String nom, int age, int poids, int taille, int type_pers, String genre){
        try(SQLiteDatabase db = getWritableDatabase()){

            ContentValues ligne = new ContentValues();


            ligne.put(IDENTITE$USER_ID, user_id);
            ligne.put(IDENTITE$PRENOM, prenom);
            ligne.put(IDENTITE$NOM, nom);
            ligne.put(IDENTITE$AGE, age);
            ligne.put(IDENTITE$POIDS, poids);
            ligne.put(IDENTITE$TAILLE, taille);
            ligne.put(IDENTITE$TYPE_DE_PERSONNE, type_pers);
            ligne.put(IDENTITE$GENRE, genre);

            db.insert(IDENTITE, null, ligne);

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLigneSommeil");
        }
    }

    public void updateHeureCoucherReel(int user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_COUCHER_REEL, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{Integer.toString(user_id)});
    }

    public void updateHeureCoucherPrevue(int user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_COUCHER_PREVUE, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{Integer.toString(user_id)});
    }

    public void updateHeureReveilReel(int user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_REVEIL_REEL, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{Integer.toString(user_id)});
    }

    public void addLigneActiviteCalorie(int user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            String nouvelle_date = null;

            Calendar calendrier = Calendar.getInstance(Locale.FRANCE);
            int jour = calendrier.get(Calendar.DAY_OF_MONTH);
            int mois = calendrier.get(Calendar.MONTH);
            int annee = calendrier.get(Calendar.YEAR);

            if(jour<10) nouvelle_date += "0" + jour + "/";
            else nouvelle_date = jour + "/";

            if(mois<10) nouvelle_date += "0" + mois + "/";
            else nouvelle_date += mois + "/";

            nouvelle_date += annee;

            ContentValues ligne = new ContentValues();
            ligne.put(APPORT_EN_ENERGIE$USER_ID, user_id);
            ligne.put(APPORT_EN_ENERGIE$DATE, nouvelle_date);
            ligne.put(APPORT_EN_ENERGIE$VARIATION, 0);

            db.insert(APPORT_EN_ENERGIE, null, ligne);

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLigneActiviteCalorie");
        }
    }

    public void deleteApportEnEnergie(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(APPORT_EN_ENERGIE, null, null);
        db.close();
    }

    public void updateCaloriesVariation(float variation, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(APPORT_EN_ENERGIE$VARIATION, variation);

        String condition = APPORT_EN_ENERGIE$DATE + "=?";

        db.update(APPORT_EN_ENERGIE, maj, condition, new String[]{date});
    }

    public void deleteIdentite(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(IDENTITE, null, null);
        db.close();
    }

    public void addLignePasJournaliers(int user_id, int objectif){
        try(SQLiteDatabase db = getWritableDatabase()){

            Date aujourdhui = new Date();

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String dateAujourdhui = format.format(aujourdhui);

            ContentValues ligne = new ContentValues();
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS$NB_PAS_EFFECTUES,10);
            ligne.put(PAS$OBJECTIFS, objectif);
            ligne.put(PAS$DIFF_PAS, 0);
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS_JOURNALIERS$DATE, dateAujourdhui);

            db.insert(PAS_JOURNALIERS, null, ligne);

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLignePasJournalier");
        }
    }

    public void addLignePasHebdomadaire(int user_id, int objectif){
        try(SQLiteDatabase db = getWritableDatabase()){

            Calendar calendrier = Calendar.getInstance(Locale.FRANCE);

            ContentValues ligne = new ContentValues();
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS$NB_PAS_EFFECTUES, 10);
            ligne.put(PAS$OBJECTIFS, objectif);
            ligne.put(PAS$DIFF_PAS, 0);
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS_HEBDOMADAIRE$NO_SEMAINE, calendrier.get(Calendar.WEEK_OF_MONTH));

            db.insert(PAS_HEBDOMADAIRE, null, ligne);

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLignePasHebdomadaire");
        }
    }

    public void addLignePasMensuelle(int user_id, int objectif){
        try(SQLiteDatabase db = getWritableDatabase()){

            Calendar calendrier = Calendar.getInstance(Locale.FRANCE);

            ContentValues ligne = new ContentValues();
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS$NB_PAS_EFFECTUES, 10);
            ligne.put(PAS$OBJECTIFS, objectif);
            ligne.put(PAS$DIFF_PAS, 0);
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS_MENSUELS$NO_MOIS, calendrier.get(Calendar.MONTH));

            db.insert(PAS_MENSUELS, null, ligne);

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLignePasMensuelle");
        }
    }

    public void deletePasMensuelle(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PAS_MENSUELS, null, null);
        db.close();
    }

    public void deletePasHebdomadaire(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PAS_HEBDOMADAIRE, null, null);
        db.close();
    }

    public void deletePasJournalier(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PAS_JOURNALIERS, null, null);
        db.close();
    }

    public void updateObjectifJournalier(int user_id, int nv_obj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAS$OBJECTIFS, nv_obj);
        db.update(PAS_JOURNALIERS, values, "user_id = ?", new String[]{Integer.toString(user_id)});
        db.close();
    }

    public void updateObjectifHebdomadaire(int user_id, int nv_obj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAS$OBJECTIFS, nv_obj);
        db.update(PAS_HEBDOMADAIRE, values, "user_id = ?", new String[]{Integer.toString(user_id)});
        db.close();
    }

    public void updateObjectifMensuelle(int user_id, int nv_obj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAS$OBJECTIFS, nv_obj);
        db.update(PAS_MENSUELS, values, "user_id = ?", new String[]{Integer.toString(user_id)});
        db.close();
    }

    public void updateNombrePasJournalier(int user_id, String date, int nb_pas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String condition = PAS$USER_ID + "=? AND " + PAS_JOURNALIERS$DATE + "=?";
        values.put(PAS$NB_PAS_EFFECTUES, nb_pas);
        db.update(PAS_JOURNALIERS, values, condition, new String[]{Integer.toString(user_id), date});
        db.close();
    }

    public void updateNombrePasHebdomadaire(int user_id, int semaine, int nb_pas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String condition = PAS$USER_ID + "=? AND " + PAS_HEBDOMADAIRE$NO_SEMAINE + "=?";
        values.put(PAS$NB_PAS_EFFECTUES, nb_pas);
        db.update(PAS_HEBDOMADAIRE, values, condition, new String[]{Integer.toString(user_id), Integer.toString(semaine)});
        db.close();
    }

    public void updateNombrePasMensuelle(int user_id, int mois, int nb_pas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String condition = PAS$USER_ID + "=? AND " + PAS_MENSUELS$NO_MOIS + "=?";
        values.put(PAS$NB_PAS_EFFECTUES, nb_pas);
        db.update(PAS_MENSUELS, values, condition, new String[]{Integer.toString(user_id), Integer.toString(mois)});
        db.close();
    }

    public void updateLigneJournalier(int user_id, String ancienne_date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String nouvelle_date=null;
        Calendar calendrier = Calendar.getInstance(Locale.FRANCE);

        int jour = calendrier.get(Calendar.DAY_OF_MONTH);
        int mois = calendrier.get(Calendar.MONTH);
        int annee = calendrier.get(Calendar.YEAR);

        if(jour<10) nouvelle_date += "0" + jour + "/";
        else nouvelle_date = jour + "/";

        if(mois<10) nouvelle_date += "0" + mois + "/";
        else nouvelle_date += mois + "/";

        nouvelle_date += annee;

        String condition = PAS$USER_ID + "=? AND " + PAS_JOURNALIERS$DATE + "=?";

        values.put(PAS$NB_PAS_EFFECTUES, 0);
        values.put(PAS_JOURNALIERS$DATE, nouvelle_date);


        db.update(PAS_JOURNALIERS, values, condition, new String[]{Integer.toString(user_id), ancienne_date});
        db.close();
    }

    public void updateLigneMensuelle(int user_id, int mois){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PAS$NB_PAS_EFFECTUES, 0);

        String condition = PAS$USER_ID + "=? AND " + PAS_MENSUELS$NO_MOIS + "=?";

        db.update(PAS_MENSUELS, values, condition, new String[]{Integer.toString(user_id), Integer.toString(mois)});

        db.close();
    }

    public void updateLigneHebdomadaire(int user_id, int semaine){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PAS$NB_PAS_EFFECTUES, 0);

        String condition = PAS$USER_ID + "=? AND " + PAS_HEBDOMADAIRE$NO_SEMAINE + "=?";

        db.update(PAS_HEBDOMADAIRE, values, condition, new String[]{Integer.toString(user_id), Integer.toString(semaine)});

        db.close();
    }
}
