package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import com.proyecto.dam2.librosvidal.Adapters.ListViewAdapterProd;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.Communications.ServerAPI;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.DatosNavigation;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class PantallaPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    Context context = this;
    NavigationView navigationView;
    ListView ListViewDetail;
    ArrayList<Product> listaProd;
    View headerView;
    SharedPreferences prefs;
    private ListViewAdapterProd adapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().clear();
        System.out.println("Entra desde onResume");
        //cargaPreferenciasUser();
        DatosNavigation.cargaPreferenciasUser(prefs,navigationView,headerView,context);

    }

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        //MOSTRAR EL HEADER Y MENU CORRESPONDIENTE SEGUN LOGIN DEL NAVIGATION
        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_all, null);
        navigationView.addHeaderView(headerView);
        navigationView.getMenu().clear();

        //Cargar preferencias del usuario en el NavHeader i opciones adicionales de admin
        prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        DatosNavigation.cargaPreferenciasUser(prefs,navigationView,headerView,context);


        //LISTENER DEL NAV HEADER
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getBoolean("login", false)) {
                    Intent i = new Intent(context, VerPerfil.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });

        // CARGAR ELEMENTOS EN EL LIST VIEW
        //listaProd = (ArrayList<Product> )getIntent().getSerializableExtra("lista");
        listaProd = SplashScreen.listaProd;
        System.out.println("En la lista hay: " + listaProd.size());
        ListViewDetail = (ListView) findViewById(R.id.list);

        adapter = new ListViewAdapterProd(this, listaProd);
        ListViewDetail.setAdapter(adapter);

        //Listener onClick del ListView
        ListViewDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idProdClick = listaProd.get(position).getId();
                Intent i = new Intent(context, DetalleProducto.class );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("Producte",listaProd.get(position));
                startActivity(i);
            }
        });

        //Showing Swipe Refresh animation on activity create
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        obtenirProductes();
    }

    private void obtenirProductes() {

        // --- request all products ---------------------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        listaProd = new ArrayList<>();
        postParams.put("action","return_all_products");
        String url = "http://programacion.cocinassobreruedas.com/api.php";

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

        //RECOLLIR DADES DELS PRODUCTES I AFEGIR-LOS AL ARRAY DE PRODUCTES
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

                boolean venut;
                if (jsonObject.get("VENUT").toString().equals("1")){
                    venut = true;
                } else {
                    venut = false;
                }

                //String regId = jsonObject.get("regid").toString();

                String image = ServerAPI.getProductImage(""+id);
                image = "" + image;
                System.out.println(image);


                // Crear producte i afegir a la llsita
                if (!venut){
                    Product producte = new Product(id,titol,descripcio,preu,peticio,venta,intercanvi,image,null);
                    listaProd.add(producte);
                }

            }

            //Actualizar lista
            adapter = new ListViewAdapterProd(this, listaProd);
            ListViewDetail.setAdapter(adapter);

            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);

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
            editor.commit();
            finish();
            startActivity(getIntent());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
