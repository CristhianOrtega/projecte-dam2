package com.proyecto.dam2.librosvidal.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proyecto.dam2.librosvidal.Clases.Message;
import com.proyecto.dam2.librosvidal.Preferences.PreferencesUser;

import java.util.ArrayList;
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
                "regID TEXT, missatge TEXT, fecha LONG, nomUser TEXT, isSelf TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En caso de una nueva versión habría que actualizar las tablas
    }


    public void guardarMensaje(String regID, String missatge, long fecha, String nomUser, String isSelf) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO logChat VALUES ( null, '"+
                regID+"', '"+missatge+"', '"+fecha+"', '"+nomUser+"','"+isSelf+"')");
        db.close();

    }


    public ArrayList listaMensajes(String regID, Context context) {
        ArrayList<Message> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
       /* Cursor cursor = db.rawQuery("SELECT puntos, nombre FROM " +
                "puntuaciones ORDER BY puntos DESC LIMIT " +regID, null);*/


        Cursor cursor = db.rawQuery("SELECT regID, missatge, fecha, nomUser, isSelf  FROM " +
                "logChat WHERE regID = '"+regID+"'",null);


        while (cursor.moveToNext()){

            //           get regID                 get Missatge            get Fecha              get NomUser
            // result.add(cursor.getString(0)+" " +cursor.getString(1)+" "+cursor.getLong(2)+" "+cursor.getString(3));


            String nomBD = cursor.getString(3);
            String nomPR = PreferencesUser.getPreference("NOM", context);
            boolean isSelf = false;
            if (nomBD.equals(nomPR)){ isSelf = true;}
            Message m =  new Message(cursor.getString(3),cursor.getString(1),cursor.getString(0),isSelf);
            result.add(m);

        }
        cursor.close();
        db.close();
        return result;

    }


}
