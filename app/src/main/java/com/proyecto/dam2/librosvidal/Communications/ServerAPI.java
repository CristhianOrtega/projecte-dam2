package com.proyecto.dam2.librosvidal.Communications;

import android.util.Log;

import com.proyecto.dam2.librosvidal.Utils.Google;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
                Thread.sleep(50);
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


    public static void postToGCM(String regid,String message){

        String content = "{\"to\" : "+regid+",\"data\" : {\"message\": \""+message+"\"},}";

        try{

            // 1. URL
            URL url = new URL("https://android.googleapis.com/gcm/send");

            // 2. Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 3. Specify POST method
            conn.setRequestMethod("POST");

            // 4. Set the headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key="+ Google.ClauServer);

            conn.setDoOutput(true);

            // 5. Add JSON data into POST request body

            // 5.2 Get connection output stream
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

            // 5.3 Copy Content "JSON" into
            wr.write(content.getBytes());
            System.out.println(content);

            // 5.4 Send the request
            wr.flush();

            // 5.5 close
            wr.close();

            // 6. Get the response
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 7. Print result
            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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


    public static boolean eliminarProd(String idProd){

        // --- Save regid --------------------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("action","delete_product");
        postParams.put("id_product",idProd);
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
