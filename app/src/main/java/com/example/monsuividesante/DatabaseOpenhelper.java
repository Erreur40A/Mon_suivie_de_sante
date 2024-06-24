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
    private static final String PAS$ANNEE = "annee";
    private static final String PAS$OBJECTIFS = "objectifs";
    private static final String PAS$NB_PAS_EFFECTUES = "nb_pas_effectues";
    private static final String PAS$DIFF_PAS = "diff_pas";
    private static final String PAS_HEBDOMADAIRE$MOIS = "mois";

    private static final String PAS_HEBDOMADAIRE$NO_SEMAINE = "no_semaine";
    private static final String PAS_MENSUELS$NO_MOIS = "no_mois";
    private static final String PAS_JOURNALIERS$DATE = "date";

    public DatabaseOpenhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addLignePasJournaliers(int user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            Date aujourdhui = new Date();

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String dateAujourdhui = format.format(aujourdhui);

            ContentValues ligne = new ContentValues();
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS$NB_PAS_EFFECTUES, 0);
            ligne.put(PAS$OBJECTIFS, 0);
            ligne.put(PAS$DIFF_PAS, 0);
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS_JOURNALIERS$DATE, dateAujourdhui);

            db.insert(PAS_JOURNALIERS, null, ligne);

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLignePasJournalier");
        }
    }

    public void addLignePasHebdomadaire(int user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            Calendar calendrier = Calendar.getInstance(Locale.FRANCE);

            ContentValues ligne = new ContentValues();
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS$NB_PAS_EFFECTUES, 0);
            ligne.put(PAS$OBJECTIFS, 0);
            ligne.put(PAS$DIFF_PAS, 0);
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS_HEBDOMADAIRE$MOIS, calendrier.get(Calendar.MONTH));
            ligne.put(PAS_HEBDOMADAIRE$NO_SEMAINE, calendrier.get(Calendar.WEEK_OF_MONTH));

            db.insert(PAS_HEBDOMADAIRE, null, ligne);

        }catch (SQLiteException e){
            Log.e("DatabaseOpenhelper", "addLignePasHebdomadaire");
        }
    }

    public void addLignePasMensuelle(int user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            Calendar calendrier = Calendar.getInstance(Locale.FRANCE);

            ContentValues ligne = new ContentValues();
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS$NB_PAS_EFFECTUES, 0);
            ligne.put(PAS$OBJECTIFS, 0);
            ligne.put(PAS$DIFF_PAS, 0);
            ligne.put(PAS$USER_ID, user_id);
            ligne.put(PAS$ANNEE, calendrier.get(Calendar.YEAR));
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

    // Méthode pour mettre à jour le nombre de pas pour un objectif spécifique
    public void updateNombreDePas(String table, int nombreDePas, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PAS$NB_PAS_EFFECTUES, nombreDePas);

        String whereClause =  PAS$USER_ID + "= ?";
        String[] whereArgs = {Integer.toString(user_id)};

        // Exécuter la mise à jour
        int rowsAffected = db.update(table, values, whereClause, whereArgs);

        // Vérifier si la mise à jour a réussi
        if (rowsAffected > 0) {
            // Succès : afficher un message ou effectuer d'autres actions si nécessaire
            System.out.println("Nombre de pas mis à jour pour " + table + ": " + nombreDePas);
        } else {
            // Échec : gérer les erreurs si nécessaire
            System.out.println("Échec de la mise à jour pour " + table);
        }

        // Fermer la base de données
        db.close();
    }
}
