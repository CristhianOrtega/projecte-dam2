package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidquery.AQuery;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;

import java.util.HashMap;

public class VerPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        // CARREGAR DADES DES DE SHARED PREFERENCES
        SharedPreferences prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        String nom = prefs.getString("NOM", "");
        String cognoms = prefs.getString("COGNOMS", "");
        String email = prefs.getString("EMAIL", "");
        String perfil = prefs.getString("PERFIL","");
        String ruta = prefs.getString("IMAGEPERFIL","http://librosvidal.esy.es/images/fotoperfil.png");



        AQuery aq=new AQuery(this); // intsialze aquery
        aq.id(findViewById(R.id.nombrePerfil)).text(nom);
        aq.id(findViewById(R.id.apellidosPerfil)).text(cognoms);
        aq.id(findViewById(R.id.emailPerfil)).text(email);
        aq.id(findViewById(R.id.descripcionPerfil)).text(perfil);
        aq.id(findViewById(R.id.fotoPerfil)).image(ruta, true, true);



    }

}
