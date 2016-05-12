package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.proyecto.dam2.librosvidal.Clases.Contacte;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.GMailSender;

import java.util.Properties;

public class Contactar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void enviarMsg(View view){

        GMailSender sender = new GMailSender("david.martinez.cvb@gmail.com", "*******");
        try {
            sender.sendMail("Asunto del mail",
                    "Cuerpo del mail",
                    "david.martinez.cvb@gmail.com",
                    "david.martinez.cvb@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }


}
