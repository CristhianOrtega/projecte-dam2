package com.proyecto.dam2.librosvidal.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.proyecto.dam2.librosvidal.R;

public class NuevoProducto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int SELECT_IMAGE = 6452;
    private int TAKE_PICTURE = 4352;

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

                    imgPhoto.setImageURI(selectedImage);
                }
            if(requestCode == TAKE_PICTURE)
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = data.getData();

                    imgPhoto.setImageURI(selectedImage);
                }
        } catch(Exception e){}
    }

    private String getPath(Uri uri) {
        String[] projection = { android.provider.MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
}
