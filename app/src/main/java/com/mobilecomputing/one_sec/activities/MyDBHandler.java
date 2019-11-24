package com.example.one_sec;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "testing2DB.db";
    public static final String TABLE_LOGIN = "login";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LOGIN + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT ," +
                COLUMN_PASSWORD + " TEXT " +
                ");";
        db.execSQL(query);
    }
    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }

    //Add a new row to the database
    public void addUser(Login_Class product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, product.get_username());
        values.put(COLUMN_PASSWORD, product.get_password());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteUser(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LOGIN + " WHERE " + COLUMN_USERNAME + "=\"" + productName + "\";");
    }

    // this is goint in record_TextView in the Main activity.
    public String printUserInfo(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
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

}