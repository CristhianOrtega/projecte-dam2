package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Communications.ServerAPI;
import com.proyecto.dam2.librosvidal.Preferences.PreferencesUser;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.DatosNavigation;

public class DetalleProducto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Product producte;
    private NavigationView navigationView;
    private View headerView;
    private SharedPreferences prefs;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        String rol = prefs.getString("ROL", "usuari");
        context = this;
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        //MOSTRAR EL HEADER Y MENU CORRESPONDIENTE SEGUN LOGIN DEL NAVIGATION
        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_all, null);
        navigationView.addHeaderView(headerView);
        navigationView.getMenu().clear();

        //Cargar preferencias del usuario en el NavHeader i opciones adicionales de admin
        prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        DatosNavigation.cargaPreferenciasUser(prefs, navigationView, headerView, context);


        //LISTENER DEL NAV HEADER
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs.getBoolean("login", false)) {
                    Intent i = new Intent(context, VerPerfil.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });

        //RECOLLIR PROD SELECCIONAT
        producte = (Product)getIntent().getSerializableExtra("Producte");


        //Asignar valores al layout

        TextView tituloDet = (TextView) findViewById(R.id.titulodet);
        tituloDet.setText(producte.getTitol());

        TextView precioDet = (TextView) findViewById(R.id.PreuDet);
        precioDet.setText(producte.getPreu()+"€");

        TextView descripcionDet = (TextView) findViewById(R.id.DescripcioDet);
        descripcionDet.setText(producte.getDescripcio());

        ImageView imageDet = (ImageView) findViewById(R.id.imageDet);

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

        AQuery aq = new AQuery(this);
        aq.id(findViewById(R.id.imageDet)).image(producte.getFoto(),true,true);
        System.out.println(producte.getFoto());



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

        if (id == R.id.buscarProdMenu) {
            Intent i = new Intent(this, Buscar.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.PerfilMenu) {
            Intent i = new Intent(this, VerPerfil.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else if (id == R.id.ChatMenu) {
            Intent i = new Intent(this, Chat.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else if (id == R.id.inicio) {
            Intent i = new Intent(this, PantallaPrincipal.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.loginMenu) {
            Intent i = new Intent(this, LoginActivity.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        else if (id == R.id.logoutMenu) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("login", false);
            editor.remove("ID");
            editor.remove("NOM");
            editor.remove("COGNOMS");
            editor.remove("EMAIL");
            editor.remove("IMAGEPERFIL");
            editor.remove("PERFIL");
            editor.remove("ROL");
            editor.remove("STRINGIMAGE");
            editor.remove("REGID");
            editor.commit();
            finish();
            startActivity(getIntent());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickContacta(View view){
        SharedPreferences prefs = context.getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);

        if(prefs.getBoolean("login", false)) {
            Intent i = new Intent(context, ConversaActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("Producte", producte);
            startActivity(i);
        }else{
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

    }

    public void eliminarProd(View view){
        ServerAPI.eliminarProd(""+producte.getId());
    }
}
