package com.proyecto.dam2.librosvidal.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mobile on 04/05/2016.
 */
public class PreferencesUser {

    public static void setPreference(String clau,String valor,Context context){
        SharedPreferences prefs = context.getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(clau, valor);
        editor.commit();
    }


    public static String getPreference(String clau,Context context){
        SharedPreferences prefs = context.getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        return prefs.getString(clau,"");

    }

    public static void deletePreferencesUser(Context context){
        SharedPreferences prefs = context.getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
