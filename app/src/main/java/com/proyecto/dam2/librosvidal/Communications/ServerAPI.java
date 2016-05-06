package com.proyecto.dam2.librosvidal.Communications;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cortega on 29/04/16.
 */
public class ServerAPI {

    public static String getProductImage(String idProduct){

        String image = "";
        // --- request image products ---------------------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("action","return_image_product");
        postParams.put("id_product",idProduct);
        String url = "http://programacion.cocinassobreruedas.com/api.php";

        HttpConnection request = new HttpConnection(url, postParams,
                "product image");

        while (!request.isReceived()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {

            }
        }

        response = request.getResponse();

        Log.i("COC", "GetProduct Image->" + response);

        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            String ruta = jsonObject.get("RUTA").toString();
            image = ruta;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static void sendMessageByGCM(String regid,String message){
        HttpConnection httppost;
        String response = "";
        try {

            HashMap<String,String> postParams =  new HashMap<>();
            postParams.put("registration_id", regid);
            postParams.put("data.mode", message);
            httppost = new HttpConnection("https://android.googleapis.com/gcm/send",postParams,"chat");

            // esperar resposta per part del server
            while (!httppost.isReceived()) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {

                }
            }

            response = httppost.getResponse();

            Log.i("COC", "Reponse del enviament del missatge ->" + response);

        }catch (Exception e) {
            Log.i("COC","Error al enviar missatge per gcm");
        }
    }


    public static boolean saveRegId(String email,String regid){

        // --- Save regid --------------------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("action","save_regid");
        postParams.put("email",email);
        postParams.put("regid",regid);
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

        Log.i("COC", "SAve regID->" + response);

        if (response.equals("true")){return true;}
        else{return false;}
    }

}
