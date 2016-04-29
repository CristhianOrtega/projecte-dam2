package com.proyecto.dam2.librosvidal.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.Image;

import java.io.IOException;
import java.util.HashMap;

public class NuevoProducto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int SELECT_IMAGE = 6452;
    private int TAKE_PICTURE = 4352;
    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    public void dialogPhoto (View view){
        dialogPhoto();
    }

    private void dialogPhoto(){
        try{
            final CharSequence[] items = {"Seleccionar de la galería", "Hacer una foto"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seleccionar una foto");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch(item){
                        case 0:
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent, SELECT_IMAGE);
                            break;
                        case 1:
                            startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE);
                            break;
                    }

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch(Exception e){}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageButton imgPhoto = (ImageButton) findViewById(R.id.imageButton);
        try{
            if (requestCode == SELECT_IMAGE)
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    uriImage = selectedImage;
                    imgPhoto.setImageURI(selectedImage);
                }
            if(requestCode == TAKE_PICTURE)
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = data.getData();
                    uriImage = selectedImage;
                    imgPhoto.setImageURI(selectedImage);
                }
        } catch(Exception e){}
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void enviar (View view){
        Context context =  this;

        SharedPreferences prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        int id = prefs.getInt("ID",0);

        EditText inputDescripcio = (EditText) findViewById(R.id.editTextDescripcion);
        EditText inputTitol = (EditText) findViewById(R.id.editTextNombreProd);
        EditText inputPreu = (EditText) findViewById(R.id.editTextPrecio);

        CheckBox inputPeticio = (CheckBox) findViewById(R.id.checkPeticion);
        CheckBox inputVenta = (CheckBox) findViewById(R.id.checkVenta);
        CheckBox inputIntercanvi = (CheckBox) findViewById(R.id.checkIntercambio);

        ImageButton imgPhoto = (ImageButton) findViewById(R.id.imageButton);

        Bitmap bitmap =  null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmap = Image.createThumbnail(bitmap);
        String imatge = Image.imageToString(bitmap);
        // --- Register new product ----------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("action","new_product");
        postParams.put("id_user",id+"");
        postParams.put("titol",inputTitol.getText().toString());
        postParams.put("descripcio",inputDescripcio.getText().toString());
        postParams.put("preu",inputPreu.getText().toString());
        postParams.put("peticio",inputPeticio.isChecked()+"");
        postParams.put("venta",inputVenta.isChecked()+"");
        postParams.put("intercanvi",inputIntercanvi.isChecked()+"");
        postParams.put("imatge",imatge);

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

        Log.i("COC", "New product added ->" + response);

        if (response.equals("true")){
            Toast.makeText(context, "Producto añadido", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, Registro_Usuario.class );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else{
            Toast.makeText(context,"Error al añadir.",Toast.LENGTH_SHORT).show();
        }

    }
}
