package com.mobilecomputing.one_sec.activities;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "testing4DB.db";
    public static final String TABLE_LOGIN = "signup";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";

    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LOGIN + "(" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                COLUMN_PASSWORD + " TEXT ," +
                COLUMN_EMAIL + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }


    public void addUserInfo(Login_Class login){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, login.get_username());
        values.put(COLUMN_PASSWORD, login.get_password());
        values.put(COLUMN_EMAIL, login.get_email());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }


    public void deleteUser(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LOGIN + " WHERE " + COLUMN_USERNAME + "=\"" + productName + "\";");
    }


    public String printUserInfo(){
        String dbString = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LOGIN + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("username")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("username"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }


    public int checkUserInfo(String username){
        String dbString = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LOGIN + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("username")) != null) {
                if(recordSet.getString(recordSet.getColumnIndex("username")).equals(username)){
                    return 1;
                }

            }
            recordSet.moveToNext();
        }
        db.close();
        return 0;
    }

}
