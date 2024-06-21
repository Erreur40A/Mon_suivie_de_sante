package com.example.monsuividesante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAccess {

    private DatabaseOpenhelper openhelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context) {
        this.openhelper = new DatabaseOpenhelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openhelper.getWritableDatabase();
    }

    public void close() {
        if(db != null) {
            this.db.close();
        }
    }

    public String gettkt(String id) {
        c = db.rawQuery("SELECT * FROM message where id = ?", new String[]{id});
        c.moveToFirst();
        return c.getString(1);
    }
}