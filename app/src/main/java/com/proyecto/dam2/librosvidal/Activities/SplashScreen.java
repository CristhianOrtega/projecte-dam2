package com.proyecto.dam2.librosvidal.Activities;

/**
 * Created by david on 25/4/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashScreen extends Activity {

    ArrayList<Product> listaProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /**
         * Showing splashscreen while making network calls to download necessary
         * data before launching the app Will use AsyncTask to make http call
         */
        new PrefetchData().execute();

    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            /*
             * Will make http call here This call will download required data
             * before launching the app
             * example:
             * 1. Downloading and storing in SQLite
             * 2. Downloading images
             * 3. Fetching and parsing the xml / json
             * 4. Sending device information to server
             * 5. etc.,
             */



            // --- request all products ---------------------------------------------------------------------------
            String response = "";
            HashMap<String,String> postParams = new HashMap<>();
            listaProd = new ArrayList<>();
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

                    // Crear producte i afegir a la llsita
                    if (!venut){
                        Product producte = new Product(id,titol,descripcio,preu,peticio,venta,intercanvi);
                        listaProd.add(producte);
                    }



                }

            } catch (Exception e){
                System.out.println("Error al pasar a JSON" + e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity
            Intent i = new Intent(SplashScreen.this, PantallaPrincipal.class);
            i.putExtra("lista", listaProd);
            startActivity(i);

            // close this activity
            finish();
        }

    }

}