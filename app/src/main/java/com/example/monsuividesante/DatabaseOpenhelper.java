
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

    private static final String SOMMEIL$USER_ID = "user_id";
    private static final String SOMMEIL$HEURE_REVEIL_REEL = "heure_de_reveil_reelle";
    private static final String SOMMEIL$HEURE_REVEIL_PREVUE = "heure_de_reveil_prevue";
    private static final String SOMMEIL$HEURE_COUCHER_REEL = "heure_de_coucher_reelle";
    private static final String SOMMEIL$HEURE_COUCHER_PREVUE = "heure_de_coucher_prevue";
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

    private static final String IDENTITE$USER_ID="user_id";
    private static final String IDENTITE$NOM="nom";
    private static final String IDENTITE$PRENOM="prenom";
    private static final String IDENTITE$AGE="age";
    private static final String IDENTITE$POIDS="poids";
    private static final String IDENTITE$TAILLE="taille";
    private static final String IDENTITE$GENRE="genre";
    private static final String IDENTITE$TYPE_DE_PERSONNE="type_de_pers";

    private static final String DATABASE_NAME = "mon_suivi_de_sante_db.db";



    public DatabaseOpenhelper(Context context) {
        super(context, DATABSE_NAME, null, 1);
    }

    public void updatePrenom(String prenom, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("prenom", prenom);
        db.update("identite", values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void updateNom(String nom, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        db.update("identite", values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void updateAge(int age, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("age", age);
        db.update("identite", values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void updateTaille(int taille, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taille", taille);
        db.update("identite", values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void updatePoids(int poids, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("poids", poids);
        db.update("identite", values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void updateGenre(String genre, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("genre", genre);
        db.update("identite", values, "id = ?", new String[]{String.valueOf(id)});
    }
    public void updateTypeDePersonne(int type, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type_de_personne", type);
        db.update("identite", values, "id = ?", new String[]{String.valueOf(id)});
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
            ligne.put(SOMMEIL$HEURE_COUCHER_REEL, "22:00");
            ligne.put(SOMMEIL$HEURE_COUCHER_PREVUE, "20:00");
            ligne.put(SOMMEIL$HEURE_REVEIL_REEL, "10:00");
            ligne.put(SOMMEIL$HEURE_REVEIL_PREVUE, "08:00");

            db.insert(SOMMEIL, null, ligne);
        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLigneSommeil");
        }
    }





    public void deleteSommeil(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SOMMEIL, null, null);
        db.close();
    }

    public void addLigneIdentite(int user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            Genre genre = Genre.HOMME;
            ContentValues ligne = new ContentValues();

            ligne.put(IDENTITE$USER_ID, user_id);
            ligne.put(IDENTITE$PRENOM, "Dieunel");
            ligne.put(IDENTITE$NOM, "MARCELIN");
            ligne.put(IDENTITE$AGE, 23);
            ligne.put(IDENTITE$POIDS, 68);
            ligne.put(IDENTITE$TAILLE, 185);
            ligne.put(IDENTITE$TYPE_DE_PERSONNE, 1);
            ligne.put(IDENTITE$GENRE,genre.getGenre());

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
