package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.Image;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class EditarPerfil extends AppCompatActivity {

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


    public void editaInfo(){

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


        editaImatge();



    }


    public void editaImatge(){

        // --- modify_user_image -------------------------------------------------------------------

        ImageView img = (ImageView) findViewById(R.id.editFotoPerfil);
        img.buildDrawingCache();
        Bitmap bmap = img.getDrawingCache();
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




    }
}
