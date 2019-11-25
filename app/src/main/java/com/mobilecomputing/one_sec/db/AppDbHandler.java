package com.mobilecomputing.one_sec.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import com.mobilecomputing.one_sec.model.LoginInfo;

public class AppDbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "one-sec.db";
    public static final String TABLE_LOGIN = "login";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_MOB_NUM = "mob_num";
    public static final String COLUMN_EMAIL = "email";

    //We need to pass database information along to superclass
    public AppDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LOGIN + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT ," +
                COLUMN_PASSWORD + " TEXT ," +
                COLUMN_DOB + " TEXT ," +
                COLUMN_EMAIL + " TEXT ," +
                COLUMN_MOB_NUM + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }

    public void addUser(LoginInfo product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, product.getUsername());
        values.put(COLUMN_PASSWORD, product.getPassword());
        values.put(COLUMN_MOB_NUM, product.getPassword());
        values.put(COLUMN_EMAIL, product.getPassword());
        values.put(COLUMN_DOB, product.getPassword());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

    public LoginInfo getUser(String username, String password) {
        LoginInfo loginInfo = new LoginInfo();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_USERNAME + " = '" + username + "' AND " + COLUMN_PASSWORD + " = '" + password + "'";// why not leave out the WHERE  clause?
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if (recordSet.getCount() == 1) {
            loginInfo.setUsername(recordSet.getString(recordSet.getColumnIndex("username")));
            loginInfo.setMobNum(recordSet.getString(recordSet.getColumnIndex("mob_num")));
            loginInfo.setDob(recordSet.getString(recordSet.getColumnIndex("dob")));
            loginInfo.setEmail(recordSet.getString(recordSet.getColumnIndex("email")));
        }
        db.close();
        return loginInfo;
    }

    public void addDummyUser() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "upendra");
        values.put(COLUMN_PASSWORD, "pass");
        values.put(COLUMN_EMAIL, "uanthwal@gmail.com");
        values.put(COLUMN_MOB_NUM, "9024031170");
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

}
