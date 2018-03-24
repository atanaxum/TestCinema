package com.example.autumn.testcinema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Autumn on 14/03/2018.
 */

public class BaseData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "questionnaire.db";          //Nom du fichier contenant la bd sur le smartphone
    public static final String TABLE_NAME = "questions";                    //Nom de la table BD
    public static final String COL_ID = "ID";                               //Nom de la colonne contenant les ids des questions
    public static final String COL_REP = "REP";                             //Nom de la colonne contenant les numeros des reponses justes
    public static final String COL_USER = "USR";                            //Nom de la colonne contenant les numeros des reponses données par l'utilisateur

    public static final String TABLE_NAME_2 = "USER";                       //Nom de la table BD contenant les infos utilisateur
    public static final String COL_ID_2 = "ID";
    public static final String COL_NOM_2 = "NOM";

    private static final String TAG = "BaseData" ;

    public BaseData(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d( TAG,"BD Constructor Finish" );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d( TAG,"BD CREATE Start" );

        String s = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_REP + " INTEGER NOT NULL, "
                + COL_USER + " INTEGER);";
        db.execSQL(s);

        String s1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_2 + "("
                + COL_ID_2 + " INTEGER, "
                + COL_NOM_2 + "  TEXT )";

        db.execSQL(s1);

        Log.d( TAG,"BD CREATE Finish" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d( TAG,"BD UPGRADE Start" );
        String s = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(s);

        s = "DROP TABLE IF EXISTS " + TABLE_NAME_2 + ";";
        db.execSQL(s);
        Log.d( TAG,"BD DELETE Finish" );

        onCreate(db);
        Log.d( TAG,"BD UPGRADE Finish" );
    }

    /**
     * Inserer une nouvelle valeur dans la BD
     * @param id est l'id de la question à inserer
     * @param rep est la reponse juste à la question
     * @param usr est la reponse donnée par l'utilisateur
     * @return VRAI si l'Insertion est un succes
     */
    public boolean insertData(int id, int rep,int usr) {
        Log.d( TAG,"BD INSERT Start" );
        SQLiteDatabase db = this.getWritableDatabase();                     //Ecrire dans la BD
        ContentValues contentValues = new ContentValues();                  //Variables à inserer dans la teble
        contentValues.put(COL_ID,id);
        contentValues.put(COL_REP,rep);
        contentValues.put(COL_USER,usr);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        db.close();                                                         //Toujours fermer la BD apres modification
        if(result == -1){
            Log.d( TAG,"BD INSERT Fail" );
            return false;

        }else{
            Log.d( TAG,"BD INSERT Succes" );
            return true;
        }
    }

    /**
     * Mettre à Jour la BD
     * @param id est l'id de la question à mettre à jour
     * @param rep est la reponse juste à la question
     * @param usr est la reponse donnée par l'utilisateur
     * @return VRAI si la Mise à Jour est un succes
     */
    public boolean updateData(String id,int rep,int usr) {
        Log.d(TAG,"BD UPDATE "+id+" Start avec usr = "+usr);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,id);
        contentValues.put(COL_REP,rep);
        contentValues.put(COL_USER,usr);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        db.close();
        Log.d(TAG,"BD UPDATE Finish");
        return true;
    }

    /**
     * Renvoie la reponse juste à une question donnée
     * @param id est l'ID de la question
     * @return Numero correspondant à la bonne reponse
     */
    public int getReponse(int id) {
        Log.d(TAG, "GET REPONSE Start");
        SQLiteDatabase db = this.getWritableDatabase();

        //Requete recuperant la reponse juste à une question
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_ID,COL_REP},
                COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        Log.d(TAG, "GET REPONSE Cursor créé avec succes");

        if (cursor != null) cursor.moveToFirst();

        int reponse = Integer.parseInt(cursor.getString(1));
        cursor.close();

        return reponse;
    }

    /**
     * Renvoie la reponse de l'utilisateur à une question donnée
     * @param id est l'ID de la question
     * @return Numero correspondant à la reponse donnée par l'usager
     */
    public int getReponseUtilisateur(int id) {
        Log.d(TAG, "GET REPONSE USER Start");
        SQLiteDatabase db = this.getWritableDatabase();

        //Requete recuperant la reponse juste à une question
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_ID,COL_USER},
                COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        Log.d(TAG, "GET REPONSE USER Cursor créé avec succes");

        if (cursor != null) cursor.moveToFirst();

        int reponse = Integer.parseInt(cursor.getString(1));
        cursor.close();

        return reponse;
    }

    /**
     * Renvoie le nombre de questions enregistrés dans la BD
     * @return ID maximum enregistré
     */
    public int getMax() {
        Log.d(TAG,"MAX Start");
        SQLiteDatabase db = this.getWritableDatabase();

        String s = "SELECT count("+COL_ID+") as nb FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(s,null);
        cursor.moveToFirst();

        Log.d(TAG,"MAX count(ID) = "+cursor.getString( cursor.getColumnIndex( "nb" )));

        //Verifie que la table n'est pas vide pour renvoyer un ID maximum (ce qui correspond au nombre maximum de questions
        if(0 != Integer.valueOf( cursor.getString( cursor.getColumnIndex( "nb" )) )) {
            String z = "SELECT max("+COL_ID+") as Max FROM "+TABLE_NAME;
            Cursor zcursor = db.rawQuery(z,null);
            zcursor.moveToFirst();
            Log.d(TAG,"MAX max(ID) = "+cursor.getString( zcursor.getColumnIndex( "Max" )));
            return Integer.valueOf( cursor.getString( zcursor.getColumnIndex( "Max" )) ) ;
        }
        return 0;
    }

    /**
     * Renvoie le nombre de reponses d'utilisateur enregistrés dans la BD
     * @return COL_USER maximum enregistré
     */
    public int getNbReponse() {
        Log.d(TAG,"NB REPONSE Start");
        SQLiteDatabase db = this.getWritableDatabase();

        String s = "SELECT count("+COL_USER+") as nb FROM "+TABLE_NAME + " WHERE "+COL_USER+" <> 9";
        Cursor cursor = db.rawQuery(s,null);
        cursor.moveToFirst();

        Log.d(TAG,"NB REPONSE count(COL_USR) = "+cursor.getString( cursor.getColumnIndex( "nb" )));
        return Integer.valueOf( cursor.getString( cursor.getColumnIndex( "nb" ))) ;
    }



    public boolean insertName(int id,String nom){
        Log.d(TAG,"BD INSERT Start" );
        Log.d( TAG, "insertnom: "+ nom );
        SQLiteDatabase db = this.getWritableDatabase();                     //Ecrire dans la BD
        ContentValues contentValues = new ContentValues();                  //Variables à inserer dans la teble
        contentValues.put(COL_ID_2,id);
        contentValues.put(COL_NOM_2,nom);
        long result = db.insert(TABLE_NAME_2,null ,contentValues);
        db.close();                                                         //Toujours fermer la BD apres modification
        if(result == -1){
            Log.d( TAG,"BD INSERT Fail" );
            return false;

        }else{
            Log.d( TAG,"BD INSERT Succes" );
            return true;
        }
    }

    public boolean updateName(int id,String nom) {
        Log.d(TAG,"BD UPDATE Name "+id+" Start avec name = "+nom);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID_2,id);
        contentValues.put(COL_NOM_2,nom);
        db.update(TABLE_NAME_2, contentValues, "ID = ?",new String[] {   ""+id });
        db.close();
        Log.d(TAG,"BD UPDATE Name Finish");
        return true;
    }


    public String getNom(int id){
        Log.d(TAG, "GET REPONSE Start");
        SQLiteDatabase db = this.getWritableDatabase();

        String s = "SELECT "+COL_NOM_2+" FROM "+TABLE_NAME_2 + " WHERE "+COL_ID_2+" == "+id;
        Cursor cursor = db.rawQuery(s,null);
        cursor.moveToFirst();

        Log.d(TAG,"GET NOM = "+cursor.getString( cursor.getColumnIndex( COL_NOM_2 )));
        return cursor.getString(cursor.getColumnIndex( COL_NOM_2 ));
    }

    public int getNB() {
        Log.d(TAG, "NB Start");
        SQLiteDatabase db = this.getWritableDatabase( );

        String s = "SELECT count(" + COL_ID_2 + ") as nb FROM " + TABLE_NAME_2;
        Cursor cursor = db.rawQuery(s, null);
        cursor.moveToFirst( );

        Log.d(TAG, "NB count(ID) = " + cursor.getString(cursor.getColumnIndex("nb")));
        return Integer.valueOf(cursor.getColumnIndex("nb"));
    }
}
