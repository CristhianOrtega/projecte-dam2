package com.proyecto.dam2.librosvidal.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.R;

public class EditarProd extends AppCompatActivity {

    Product producte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_prod);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        producte = (Product)getIntent().getSerializableExtra("Producte");

        AQuery aQuery = new AQuery(this);
        aQuery.id(findViewById(R.id.tituloEdit)).text(producte.getTitol());
        aQuery.id(findViewById(R.id.DescripcioEdit)).text(producte.getDescripcio());
        aQuery.id(findViewById(R.id.PreuEdit)).text(producte.getPreu() + "€");

        aQuery.id(findViewById(R.id.imageEdit)).image(producte.getFoto()); //ARREGLAR ESTA LINEA

        TextView viewVenta = (TextView) findViewById(R.id.ventaEdit);
        if (producte.isVenta()){
            viewVenta.setTextSize(20);
            viewVenta.setTextColor(getResources().getColor(R.color.greenActivado));
        } else {
            viewVenta.setTextSize(10);
            viewVenta.setTextColor(getResources().getColor(R.color.redNoActivado));
        }

        TextView viewIntercanvi = (TextView) findViewById(R.id.intercanviEdit);
        if (producte.isIntercanvi()){
            viewIntercanvi.setTextSize(20);
            viewIntercanvi.setTextColor(getResources().getColor(R.color.greenActivado));
        } else {
            viewIntercanvi.setTextSize(10);
            viewIntercanvi.setTextColor(getResources().getColor(R.color.redNoActivado));
        }

        TextView viewPeticio = (TextView) findViewById(R.id.peticioEdit);
        if (producte.isPeticio()){
            viewPeticio.setTextSize(20);
            viewPeticio.setTextColor(getResources().getColor(R.color.greenActivado));
        } else {
            viewPeticio.setTextSize(10);
            viewPeticio.setTextColor(getResources().getColor(R.color.redNoActivado));
        }


        viewVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView viewVenta = (TextView) findViewById(R.id.ventaEdit);
                if (producte.isVenta()){
                    producte.setVenta(false);
                    viewVenta.setTextSize(10);
                    viewVenta.setTextColor(getResources().getColor(R.color.redNoActivado));
                } else {
                    producte.setVenta(true);
                    viewVenta.setTextSize(20);
                    viewVenta.setTextColor(getResources().getColor(R.color.greenActivado));
                }
            }
        });

        viewIntercanvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView viewIntercanvi = (TextView) findViewById(R.id.intercanviEdit);
                if (producte.isVenta()){
                    producte.setVenta(false);
                    viewIntercanvi.setTextSize(10);
                    viewIntercanvi.setTextColor(getResources().getColor(R.color.redNoActivado));
                } else {
                    producte.setVenta(true);
                    viewIntercanvi.setTextSize(20);
                    viewIntercanvi.setTextColor(getResources().getColor(R.color.greenActivado));
                }
            }
        });

        viewPeticio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView viewPeticio = (TextView) findViewById(R.id.peticioEdit);
                if (producte.isVenta()){
                    producte.setVenta(false);
                    viewPeticio.setTextSize(10);
                    viewPeticio.setTextColor(getResources().getColor(R.color.redNoActivado));
                } else {
                    producte.setVenta(true);
                    viewPeticio.setTextSize(20);
                    viewPeticio.setTextColor(getResources().getColor(R.color.greenActivado));
                }
            }
        });
    }

}
