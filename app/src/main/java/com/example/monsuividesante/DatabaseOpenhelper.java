package com.example.monsuividesante;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenhelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "mon_suivi_de_sante_db.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Méthode pour mettre à jour le nombre de pas pour un objectif spécifique
    public void updateNombreDePas(String table, int nombreDePas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pas", nombreDePas);

        String whereClause = "type = ?";
        String[] whereArgs = {table};

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
