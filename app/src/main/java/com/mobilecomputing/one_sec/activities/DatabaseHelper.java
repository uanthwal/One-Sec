package com.mobilecomputing.one_sec.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "onesec.db";
    public static final String TABLE_NAME = "credentials";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM1";
    public static final String COL3 = "ITEM2";
    public static final String COL4 = "ITEM3";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        System.out.println("onCreate test");
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " ITEM1 TEXT, ITEM2 TEXT, ITEM3 TEXT)";
        System.out.println("query is " + createTable);
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item1, String item2, String item3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);


//        Cursor data = db.rawQuery("PRAGMA TABLE_INFO(credentials) ", null);
//        System.out.println("data is "+ data );

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public Cursor getListValue(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ITEM1=\""+name+ "\"";
        System.out.println(sqlQuery);
        Cursor data = db.rawQuery(sqlQuery, null);
        return data;
    }
}
