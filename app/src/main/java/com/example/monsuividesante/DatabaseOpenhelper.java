
package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.database.Cursor;

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
    private final String PAS_HEBDOMADAIRE = "pas_hebdo";
    private final String PAS_JOURNALIERS = "pas_journaliers";
    private final String PAS_MENSUELS = "pas_mensuels";

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
            ligne.put(SOMMEIL$HEURE_COUCHER_PREVUE, "22:00");
            ligne.put(SOMMEIL$HEURE_REVEIL_PREVUE, "08:00");

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

            Date aujourdhui = new Date();

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String dateAujourdhui = format.format(aujourdhui);

            ContentValues ligne = new ContentValues();
            ligne.put(APPORT_EN_ENERGIE$USER_ID, user_id);
            ligne.put(APPORT_EN_ENERGIE$DATE, dateAujourdhui);
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

}
