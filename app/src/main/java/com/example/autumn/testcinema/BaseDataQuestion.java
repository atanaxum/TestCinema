package com.example.autumn.testcinema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

/**
 * Created by Autumn on 14/03/2018.
 */

public class BaseDataQuestion {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "question.db";

    private static final String TABLE = "questions";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_REP = "REPONSE_JUSTE";
    private static final int NUM_COL_REP = 1;
    private static final String COL_REP_USER = "REPONSE_USER";
    private static final int NUM_COL_REP_USER = 2;

    private SQLiteDatabase bdd;

    private BaseData bd;

    public BaseDataQuestion(Context context){
        //On crée la BDD et sa table
        bd = new BaseData(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = bd.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertQuestion(int id, int rep){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID, id);
        values.put(COL_REP, rep);
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE, null, values);
    }

    public long insertResponse(int id, int rep){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID, id);
        values.put(COL_REP_USER, rep);
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE, null, values);
    }

    public int getReponseJuste(int id){
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_REP}, COL_REP + " LIKE \"" + id +"\"", null, null, null, null);
        return c.getInt(NUM_COL_REP);
    }

    public int getReponseUtilisateur(int id){
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_REP_USER}, COL_REP_USER + " LIKE \"" + id +"\"", null, null, null, null);
        return c.getInt(NUM_COL_REP_USER);
    }
}
