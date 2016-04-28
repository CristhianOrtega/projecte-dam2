package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetalleProducto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences prefs;
    Product producte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        String rol = prefs.getString("ROL", "usuari");
        System.out.println("ROL: " + rol);
        if (rol.equals("admin")){
            setContentView(R.layout.activity_detalle_producto_admin);
        } else {
            setContentView(R.layout.activity_detalle_producto);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //RECOLLIR PROD SELECCIONAT
        producte = (Product)getIntent().getSerializableExtra("Producte");


        //Asignar valores al layout

        TextView tituloDet = (TextView) findViewById(R.id.titulodet);
        tituloDet.setText(producte.getTitol());

        TextView precioDet = (TextView) findViewById(R.id.PreuDet);
        precioDet.setText(producte.getPreu()+"€");

        TextView descripcionDet = (TextView) findViewById(R.id.DescripcioDet);
        descripcionDet.setText(producte.getDescripcio());

        TextView ventaDet = (TextView) findViewById(R.id.ventaDet);
        if (producte.isVenta()){
            ventaDet.setTextSize(20);
            ventaDet.setTextColor(getResources().getColor(R.color.greenActivado));
        } else {
            ventaDet.setTextSize(10);
            ventaDet.setTextColor(getResources().getColor(R.color.redNoActivado));
        }

        TextView intercanviDet = (TextView) findViewById(R.id.intercanviDet);
        if (producte.isIntercanvi()){
            intercanviDet.setTextSize(20);
            intercanviDet.setTextColor(getResources().getColor(R.color.greenActivado));
        } else {
            intercanviDet.setTextSize(10);
            intercanviDet.setTextColor(getResources().getColor(R.color.redNoActivado));
        }

        TextView peticioDet = (TextView) findViewById(R.id.peticioDet);
        if (producte.isPeticio()){
            peticioDet.setTextSize(20);
            peticioDet.setTextColor(getResources().getColor(R.color.greenActivado));
        } else {
            peticioDet.setTextSize(10);
            peticioDet.setTextColor(getResources().getColor(R.color.redNoActivado));
        }


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalle_producto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editarProd(View view){
        Intent i = new Intent(this, EditarProd.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Producte",producte);
        startActivity(i);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
