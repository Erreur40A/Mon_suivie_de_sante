package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenhelper extends SQLiteAssetHelper {

    private static final String DATABSE_NAME = "db_monSuiviDeSante.db";

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
}
