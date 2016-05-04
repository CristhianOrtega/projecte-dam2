package com.proyecto.dam2.librosvidal.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

/**
 * Created by cortega on 04/05/2016.
 */
public class LogChatSQLite extends SQLiteOpenHelper {


    //Métodos de SQLiteOpenHelper
    public LogChatSQLite(Context context) {
        super(context, "logChat", null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE logChat ("+
                "_idMessage INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "regID TEXT, missatge TEXT, fecha LONG, nomUser TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En caso de una nueva versión habría que actualizar las tablas
    }


    public void guardarMensaje(String regID, String missatge, long fecha, String nomUser) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO logChat VALUES ( null, "+
                regID+", '"+missatge+"', "+fecha+"', "+nomUser+")");
        db.close();

    }


    public Vector listaMensajes(int regID) {
        Vector result = new Vector();
        SQLiteDatabase db = getReadableDatabase();
       /* Cursor cursor = db.rawQuery("SELECT puntos, nombre FROM " +
                "puntuaciones ORDER BY puntos DESC LIMIT " +regID, null);*/
        Cursor cursor = db.rawQuery("SELECT regID, missatge, fecha, nomUser  FROM " +
                "logChat WHERE regID = '"+regID+"'",null);
        while (cursor.moveToNext()){

            //           get regID                 get Missatge            get Fecha              get NomUser
            result.add(cursor.getString(0)+" " +cursor.getString(1)+" "+cursor.getLong(2)+" "+cursor.getString(3));

        }
        cursor.close();
        db.close();
        return result;

    }


}