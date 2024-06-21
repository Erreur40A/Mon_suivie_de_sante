package com.example.monsuividesante;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenhelper extends SQLiteAssetHelper {

    private static final String DATABSE_NAME = "mon_suivi_de_sante_db.db";

    public DatabaseOpenhelper(Context context) {
        super(context, DATABSE_NAME, null, 1);
    }
}
