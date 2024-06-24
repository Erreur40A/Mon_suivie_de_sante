package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import android.database.Cursor;

public class DatabaseOpenhelper extends SQLiteAssetHelper {

    private static final String DATABSE_NAME = "mon_suivi_de_sante_db.db";

    private static final String SOMMEIL = "sommeil";

    private static final String SOMMEIL$USER_ID = "user_id";
    private static final String SOMMEIL$HEURE_REVEIL_REEL = "heure_de_reveil_reelle";
    private static final String SOMMEIL$HEURE_REVEIL_PREVUE = "heure_de_reveil_prevue";
    private static final String SOMMEIL$HEURE_COUCHER_REEL = "heure_de_coucher_reelle";
    private static final String SOMMEIL$HEURE_COUCHER_PREVUE = "heure_de_coucher_prevue";


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
            ligne.put("difference", 0F);
            ligne.put("nb_heures_de_sommeil_effectuees", 0F);
            ligne.put("nb_heures_de_sommeil_prevues", 0);

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
}
