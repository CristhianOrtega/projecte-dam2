package com.proyecto.dam2.librosvidal.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.Image;

import java.io.IOException;
import java.util.HashMap;

public class EditarProd extends AppCompatActivity {
    private int SELECT_IMAGE = 6452;
    private int TAKE_PICTURE = 4352;
    Product producte;
    private Uri uriImage;
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


    public String editaImatge(){

        // --- modify_user_image -------------------------------------------------------------------

        ImageButton img = (ImageButton) findViewById(R.id.editFotoPerfil);

        Bitmap bmap =  null;
        try {
            bmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bmap = Image.createThumbnail(bmap);
        String stringImatge = Image.imageToString(bmap);

        SharedPreferences prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        int idUser = prefs.getInt("ID", 0);

        String response = "";
        HashMap<String, String> postParams = new HashMap<>();
        postParams.put("action", "modify_user_image");
        postParams.put("imatge", stringImatge);
        postParams.put("id_user", String.valueOf(idUser));

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

        Log.i("COC", "EditImage -> " + response);

        if (response.equals("true")){

            Intent intent = new Intent(this, VerPerfil.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

        return stringImatge;

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
        ImageButton imgPhoto = (ImageButton) findViewById(R.id.imageEdit);
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

}
