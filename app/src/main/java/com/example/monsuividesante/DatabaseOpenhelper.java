package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

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

    /*Syntaxe des constantes des colonnes : nom de la table suivit de $ puis du nom de la colonne*/
    /*Sa va nous permettre d'éviter les fautes de frappes*/
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

    private static final String DATABASE_NAME = "mon_suivi_de_sante_db.db";

    public DatabaseOpenhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void addLigneActiviteCalorie(String user_id){
        try(SQLiteDatabase db = getWritableDatabase()){

            Date aujourdhui = new Date();

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String dateAujourdhui = format.format(aujourdhui);

            ContentValues ligne = new ContentValues();
            ligne.put(APPORT_EN_ENERGIE$USER_ID, user_id);
            ligne.put(APPORT_EN_ENERGIE$DATE, dateAujourdhui);
            ligne.put(APPORT_EN_ENERGIE$CALORIE_CONSOMME, 0);
            ligne.put(APPORT_EN_ENERGIE$CALORIE_DEPENSE, 0);
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

    public void updateCalorieConsomme(float nb_consomme, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(APPORT_EN_ENERGIE$CALORIE_CONSOMME, nb_consomme);

        String condition = APPORT_EN_ENERGIE$DATE + "=?";


        db.update(APPORT_EN_ENERGIE, maj, condition, new String[]{date});
    }

    public void updateCalorieDepense(float nb_depense, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues maj = new ContentValues();

        maj.put(APPORT_EN_ENERGIE$CALORIE_DEPENSE, nb_depense);

        String condition = APPORT_EN_ENERGIE$DATE + "=?";


        db.update(APPORT_EN_ENERGIE, maj, condition, new String[]{date});
    }
}
