package com.proyecto.dam2.librosvidal;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cortega on 08/04/16.
 */
public class HttpConnection {

    private String stringResponse;
    private AtomicBoolean received;

    public HttpConnection(final String url, final HashMap<String, String> postDataParams, final String from) {

        received = new AtomicBoolean(false);

        new Thread() {
            public void run() {
                performPostCall(url, postDataParams, from);
            }
        }.start();
    }


    public String performPostCall(String requestURL, HashMap<String, String> postDataParams, String from) {
        //Log.e("llença post a ",from + " "+ requestURL+" ");


        String response = "";


        URL url;

        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    os, "UTF-8"));
            if (postDataParams != null)
                writer.write(getPostDataString(postDataParams));
            else
                writer.write("");

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                stringResponse = response;

            } else {

                //throw new HttpException(requestURL + " HTTP ERROR: " + responseCode + "");
            }
        } catch (UnknownHostException net) {

            stringResponse = "NONET";

        } catch (Exception e) {

            stringResponse = null;

        } finally {

            received.set(true);
        }

        Log.i("--- POST RESPONSE ---", response);
        return response;
    }


    private String getPostDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public boolean isReceived() {
        return received.get();
    }

    public String getResponse() {
        return stringResponse;
    }

}
