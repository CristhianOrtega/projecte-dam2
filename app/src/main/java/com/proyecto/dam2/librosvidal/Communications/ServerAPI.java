package com.proyecto.dam2.librosvidal.Communications;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cortega on 29/04/16.
 */
public class ServerAPI {

    public static String getProductImage(String idProduct){
        String image = "";
        // --- request iamge products ---------------------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("action","return_image_product");
        postParams.put("id_product",idProduct);
        String url = "http://librosvidal.esy.es/api.php";

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

        return image;
    }
}