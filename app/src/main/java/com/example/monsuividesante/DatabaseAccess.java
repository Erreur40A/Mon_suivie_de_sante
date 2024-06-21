package com.example.monsuividesante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

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

    public ArrayList<String> getDuree(){
        c = db.rawQuery("SELECT * FROM duree", new String[]{});
        ArrayList<String> res = new ArrayList<String>();

        while (c.moveToFirst()){
            res.add(c.getString(c.getColumnIndexOrThrow("duree")));
            Log.println(Log. ,res.get(res.size()));
        }

        return res;
    }
}