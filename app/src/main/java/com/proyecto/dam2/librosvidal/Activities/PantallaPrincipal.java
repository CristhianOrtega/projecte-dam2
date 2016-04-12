package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.proyecto.dam2.librosvidal.Adapters.ListViewAdapterProd;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PantallaPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    ListView list;
    ListView ListViewDetail;
    ArrayList<Product> listaProd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Declaracion del floating button de añadir producto + funcion de dirigir a la activity cuando se pulsa
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NuevoProducto.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //MOSTRAR EL HEADER DEL NAVIGATION
        View headerView= navigationView.inflateHeaderView(R.layout.nav_header_all);

        //LISTENER DEL NAV HEADER
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VerPerfil.class );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        // CARGAR ELEMENTOS EN EL LIST VIEW
        listaProd = new ArrayList<>();
        list=(ListView)findViewById(R.id.list);
        ListViewDetail = (ListView) findViewById(R.id.list);
        //Cargar elementos en array de productos
        obtenirElements();
        ListViewDetail.setAdapter(new ListViewAdapterProd(context, listaProd));
    }

    private void obtenirElements() {
        // --- request all products ---------------------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("action","return_all_products");
        String url = "http://librosvidal.esy.es/api.php";

        HttpConnection request = new HttpConnection(url, postParams,
                "login");

        while (!request.isReceived()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {

            }
        }

        response = request.getResponse();

        Log.i("COC", "Login->" + response);

        try{
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i<jsonArray.length();i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                int id = Integer.valueOf(jsonObject.get("ID").toString());
                String titol = jsonObject.get("TITOL").toString();
                String descripcio = jsonObject.get("DESCRIPCIO").toString();
                double preu = Double.valueOf(jsonObject.get("PREU").toString());
                boolean peticio;
                if (jsonObject.get("PETICIO").toString().equals("1")){
                    peticio = true;
                } else {
                    peticio = false;
                }
                boolean venta;
                if (jsonObject.get("VENTA").toString().equals("1")){
                    venta = true;
                } else {
                    venta = false;
                }
                boolean intercanvi;
                if (jsonObject.get("INTERCANVI").toString().equals("1")){
                    intercanvi = true;
                } else {
                    intercanvi = false;
                }
                // Crear producte i afegir a la llsita
                Product producte = new Product(id,titol,descripcio,preu,peticio,venta,intercanvi);
                listaProd.add(producte);

            }




        } catch (Exception e){
            System.out.println("Error al pasar a JSON" + e);
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

        } else if (id == R.id.ChatMenu) {

        } else if (id == R.id.menuConfig) {
            Intent i = new Intent(this, Configuracion.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.loginMenu) {
            Intent i = new Intent(this, LoginActivity.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}