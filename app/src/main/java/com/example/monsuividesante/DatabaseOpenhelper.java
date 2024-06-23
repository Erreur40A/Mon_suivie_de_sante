package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

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

    public void addLigneSommeil(String user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            ContentValues ligne = new ContentValues();
            ligne.put(SOMMEIL$USER_ID, user_id);
            ligne.put(SOMMEIL$HEURE_COUCHER_REEL, "22:00");
            ligne.put(SOMMEIL$HEURE_COUCHER_PREVUE, "20:00");
            ligne.put(SOMMEIL$HEURE_REVEIL_REEL, "10:00");
            ligne.put(SOMMEIL$HEURE_REVEIL_PREVUE, "08:00");
            ligne.put("difference", 0);
            ligne.put("nb_heures_de_sommeil_effectuees",0);
            ligne.put("nb_heures_de_sommeil_prevues",0);

            db.insert(SOMMEIL, null, ligne);
            Log.i("DatabaseOpenhelper", "addLigneSommeil reusie");

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLigneSommeil");
        }
    }

    public void deleteSommeil(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SOMMEIL, null, null);
        db.close();
    }

    public void updateHeureCoucherReel(String user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_COUCHER_REEL, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{user_id});
    }

    public void updateHeureCoucherPrevue(String user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_COUCHER_PREVUE, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{user_id});
    }

    public void updateHeureReveilReel(String user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_REVEIL_REEL, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{user_id});
    }

    public void updateHeureReveilPrevue(String user_id, String heure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(SOMMEIL$HEURE_REVEIL_PREVUE, heure);

        String condition=SOMMEIL$USER_ID + "=?";

        db.update(SOMMEIL, maj, condition, new String[]{user_id});
    }
}
