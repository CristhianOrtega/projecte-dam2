package com.proyecto.dam2.librosvidal.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.proyecto.dam2.librosvidal.EventHandlers.GcmBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GcmService extends IntentService {

    static final public String COPA_RESULT = "com.proyecto.dam2.librosvidal.Services.GcmService.REQUEST_PROCESSED";
    static final public String COPA_MESSAGE = "com.proyecto.dam2.librosvidal.Services.GcmService.COPA_MSG";
    private static LocalBroadcastManager broadcaster;
    private static boolean running = false;
    Context context;

    public GcmService() {
        super("GcmService");
    }


    public static void sendResult(String message) {
        Intent intent = new Intent(COPA_RESULT);
        if (message != null)
            intent.putExtra(COPA_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        running = true;
        context = this;
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    public static boolean isRunning() {
        return running;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("GCM", "onHandleIntent()");

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                // sendNotification("Send error: " + extras.toString());
                //ServerAPI.getQueueDown(this);

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                // sendNotification("Deleted messages on server: " + extras.toString());
                //ServerAPI.getQueueDown(this);


            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                // Post notification of received message.
                // sendNotification("Received: " + extras.toString());

                Log.i("GCM", " Received GCM: " + extras.toString());

                try {

                    JSONObject json =  new JSONObject(extras.getString("message"));
                    String action = json.get("action").toString();

                    if (action.equals("communicator")){



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
