package com.example.autumn.testcinema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Autumn on 14/03/2018.
 */

public class BaseData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "questions";
    public static final String COL_ID = "ID";
    public static final String COL_REP = "REP";
    public static final String COL_USER = "USR";
    private static final String TAG = "BaseData" ;

    public BaseData(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d( TAG,"BD Constructor Finish" );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        this.supprimer( db );
        Log.d( TAG,"BD CREATE Start" );
        String s = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_REP + " INTEGER NOT NULL, "
                + COL_USER + " INTEGER);";
        db.execSQL(s);
        Log.d( TAG,"BD CREATE Finish" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.supprimer( db );
        onCreate(db);
    }

    public void supprimer(SQLiteDatabase db){
        String s = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(s);
    }

    public boolean insertData(int id, int rep,int usr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,id);
        contentValues.put(COL_REP,rep);
        contentValues.put(COL_USER,usr);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public int getReponse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "Jusque la ça marche old");

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_ID,COL_REP},
                COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        Log.d(TAG, "REP ap cursor");

        if (cursor != null) cursor.moveToFirst();

        int reponse = Integer.parseInt(cursor.getString(1));
        Log.d(TAG, "REP avant return");
        cursor.close();

        return reponse;
    }

    public int getMax() {
        Log.d(TAG,"MAX");
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "SELECT count(ID) as nb FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(s,null);
        cursor.moveToFirst();
        Log.d(TAG,"MAX = "+cursor.getString( cursor.getColumnIndex( "nb" )));
        if(0 != Integer.valueOf( cursor.getString( cursor.getColumnIndex( "nb" )) )) {
            String z = "SELECT max(ID) as Max FROM "+TABLE_NAME;
            Cursor zcursor = db.rawQuery(z,null);
            zcursor.moveToFirst();
            Log.d(TAG,"MAX = "+cursor.getString( zcursor.getColumnIndex( "Max" )));
            return Integer.valueOf( cursor.getString( zcursor.getColumnIndex( "Max" )) ) ;
        }
        return 0;
    }

    public boolean updateData(String id,int rep,int usr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,id);
        contentValues.put(COL_REP,rep);
        contentValues.put(COL_USER,usr);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        db.close();
        return true;
    }
}
