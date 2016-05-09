package com.proyecto.dam2.librosvidal.Utils;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Communications.HttpConnection;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david on 9/5/16.
 */
public class CargarProds {
    public static void obtenirProds(ArrayList<Product> listaProd){
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        //listaProd = new ArrayList<>();
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

                String jsonString = jsonArray.get(i).toString();
                System.out.println(jsonString);
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(jsonString);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                //System.out.println(jsonObject.getAsInt("ID"));
                int id = jsonObject.get("ID").getAsInt();

                String titol = jsonObject.get("TITOL").getAsString();
                String descripcio = jsonObject.get("DESCRIPCIO").getAsString();
                String rutaImage = jsonObject.get("ID_IMAGE").getAsString();
                String image = rutaImage.replace("\",", "");

                double preu = jsonObject.get("PREU").getAsDouble();
                boolean peticio;
                if (jsonObject.get("PETICIO").getAsString().equals("1")){
                    peticio = true;
                } else {
                    peticio = false;
                }
                boolean venta;
                if (jsonObject.get("VENTA").getAsString().equals("1")){
                    venta = true;
                } else {
                    venta = false;
                }
                boolean intercanvi;
                if (jsonObject.get("INTERCANVI").getAsString().equals("1")){
                    intercanvi = true;
                } else {
                    intercanvi = false;
                }

                boolean venut;
                if (jsonObject.get("VENUT").getAsString().equals("1")){
                    venut = true;
                } else {
                    venut = false;
                }

                // Crear producte i afegir a la llsita

                if (!venut){
                    //ARREGLAR REGID!!!
                    Product producte = new Product(id,titol,descripcio,preu,peticio,venta,intercanvi, image,null);
                    listaProd.add(producte);
                }


            }




        } catch (Exception e){
            System.out.println("Error al pasar a JSON" + e);
        }

    }
}
