package com.proyecto.dam2.librosvidal.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.Image;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class EditarPerfil extends AppCompatActivity {

    private int SELECT_IMAGE = 6452;
    private int TAKE_PICTURE = 4352;
    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        carregarDadesPerfil();
    }

    public void carregarDadesPerfil(){
        // CARREGAR DADES DES DE SHARED PREFERENCES
        SharedPreferences prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        String nom = prefs.getString("NOM", "");
        String cognoms = prefs.getString("COGNOMS", "");
        String email = prefs.getString("EMAIL", "");
        String perfil = prefs.getString("PERFIL","");
        String ruta = prefs.getString("IMAGEPERFIL","http://librosvidal.esy.es/images/fotoperfil.png");



        AQuery aq=new AQuery(this); // intsialze aquery
        aq.id(findViewById(R.id.editNombrePerfil)).text(nom);
        aq.id(findViewById(R.id.editApellidosPerfil)).text(cognoms);
        aq.id(findViewById(R.id.editEmailPerfil)).text(email);
        aq.id(findViewById(R.id.editDescripcionPerfil)).text(perfil);
        aq.id(findViewById(R.id.editFotoPerfil)).image(ruta, true, true);
    }


    public void editaInfo(View v){

        EditText editNom = (EditText) findViewById(R.id.editNombrePerfil);
        EditText editApellidos = (EditText) findViewById(R.id.editApellidosPerfil);
        EditText editDescripcion = (EditText) findViewById(R.id.editDescripcionPerfil);
        EditText editEmail = (EditText) findViewById(R.id.editEmailPerfil);

        SharedPreferences prefs = getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        int idUser = prefs.getInt("ID", 0);

        // --- modify_user_perfil ------------------------------------------------------------------
        String response = "";
        HashMap<String, String> postParams = new HashMap<>();
        postParams.put("action", "modify_user_perfil");
        postParams.put("email", editEmail.getText().toString());
        postParams.put("cognoms", editApellidos.getText().toString());
        postParams.put("nom", editNom.getText().toString());
        postParams.put("perfil", editDescripcion.getText().toString());
        postParams.put("id_user",String.valueOf(idUser));


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


        Log.i("COC", "editPerfil -> " + response);

        if (response.equals("true")){
            // CARREGAR DADES A SHARED PREFERENCES
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("NOM", editNom.getText().toString());
            editor.putString("COGNOMS", editApellidos.getText().toString());
            editor.putString("EMAIL", editEmail.getText().toString());
            editor.putString("PERFIL", editDescripcion.getText().toString());
            String strignimage = editaImatge();
            editor.putString("STRINGIMAGE", strignimage);
            editor.commit();
        }



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
        ImageButton imgPhoto = (ImageButton) findViewById(R.id.editFotoPerfil);
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
