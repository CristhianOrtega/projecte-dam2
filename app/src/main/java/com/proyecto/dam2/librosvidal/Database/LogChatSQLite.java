package com.proyecto.dam2.librosvidal.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proyecto.dam2.librosvidal.Clases.Contacte;
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

        db.execSQL("CREATE TABLE conversa ("+
                "_idConversa INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "regID TEXT, nomUser TEXT)");

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

    public void guardarConversa(String regID,String nomUser) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO conversa VALUES ( null, '"+
                regID+"', '"+nomUser+"')");
        db.close();

    }


    public ArrayList listaMensajes(String regID, Context context) {

        ArrayList<Message> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT regID, missatge, fecha, nomUser, isSelf  FROM " +
                "logChat WHERE regID = '"+regID+"'",null);

        while (cursor.moveToNext()){

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

    public ArrayList listaChat(Context context) {

        ArrayList<Contacte> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT regID, nomUser  FROM " +
                "conversa",null);

        while (cursor.moveToNext()){
            Contacte c =  new Contacte(cursor.getString(1),cursor.getString(0));
            result.add(c);
        }

        cursor.close();
        db.close();
        return result;

    }

    public String getLastMessage(String regID) {

        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT missatge FROM " +
                "logChat WHERE regID = '"+regID+"' ORDER BY fecha ASC",null);

        while (cursor.moveToNext()){
            result = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return result;

    }


}
