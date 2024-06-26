package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenhelper extends SQLiteAssetHelper {

    private static final String IDENTITE="identite";

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
        super(context, DATABASE_NAME, null, 1);
    }

    public void addLigneIdentite(int user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            Genre genre = Genre.HOMME;
            ContentValues ligne = new ContentValues();

            ligne.put(IDENTITE$USER_ID, user_id);
            ligne.put(IDENTITE$PRENOM, "Dieunel");
            ligne.put(IDENTITE$NOM, "MARCELIN");
            ligne.put(IDENTITE$AGE, 19);
            ligne.put(IDENTITE$POIDS, 68);
            ligne.put(IDENTITE$TAILLE, 185);
            ligne.put(IDENTITE$TYPE_DE_PERSONNE, 1);
            ligne.put(IDENTITE$GENRE,genre.getGenre());

            db.insert(IDENTITE, null, ligne);
        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLigneSommeil");
        }
    }

    public void deleteIdentite(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(IDENTITE, null, null);
        db.close();
    }
}
