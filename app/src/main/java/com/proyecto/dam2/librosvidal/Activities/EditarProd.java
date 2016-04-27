package com.proyecto.dam2.librosvidal.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.proyecto.dam2.librosvidal.R;

public class EditarProd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_prod);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView viewVenta = (TextView) findViewById(R.id.ventaEdit);
        TextView viewIntercanvi = (TextView) findViewById(R.id.intercanviEdit);
        TextView viewPeticio = (TextView) findViewById(R.id.peticioEdit);
        viewVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Venta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewIntercanvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Intercanvi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewPeticio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Peticio", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
