package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatabaseOpenhelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "mon_suivi_de_sante_db.db";
    private static final int DATABASE_VERSION = 1;

    private static final String PAS_HEBDOMADAIRE = "pas_hebdo";
    private static final String PAS_JOURNALIERS = "pas_journaliers";
    private static final String PAS_MENSUELS = "pas_mensuels";

    private static final String PAS$USER_ID = "user_id";
    private static final String PAS$OBJECTIFS = "objectifs";
    private static final String PAS$NB_PAS_EFFECTUES = "nb_pas_effectues";
    private static final String PAS$DIFF_PAS = "diff_pas";

    private static final String PAS_HEBDOMADAIRE$NO_SEMAINE = "no_semaine";
    private static final String PAS_MENSUELS$NO_MOIS = "no_mois";
    private static final String PAS_JOURNALIERS$DATE = "date";

    public DatabaseOpenhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
