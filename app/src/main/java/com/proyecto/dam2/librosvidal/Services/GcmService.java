package com.proyecto.dam2.librosvidal.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.proyecto.dam2.librosvidal.Activities.ConversaActivity;
import com.proyecto.dam2.librosvidal.Activities.PantallaPrincipal;
import com.proyecto.dam2.librosvidal.Database.LogChatSQLite;
import com.proyecto.dam2.librosvidal.EventHandlers.GcmBroadcastReceiver;
import com.proyecto.dam2.librosvidal.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GcmService extends IntentService {


    // https://iowme.com/beta/lib/gcm.php?test=10&message=hola&id_gcm=APA91bEbbgAD0ezj8a26C0psgOizTJakqrQ3rGGU1Awj_t33t38qicvOedkohAriTO5hp_av4yPMF52ix3Irso2STDmG1_91FlwTdMT-WsPHRRwqcQZYMgX9VxrCHy2AQnkL-NQv_01E&api_key=AIzaSyDOdHZFZfhX_YCUrahJQhvLrPNcGx901ho
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

                    JSONObject json = new JSONObject(extras.getString("message"));
                    String action = json.get("action").toString();

                    if (action.equals("communicator")) {


                        String regId = json.getString("regId");
                        String missatge = json.getString("text");
                        long fecha = System.currentTimeMillis();
                        String nomUser = "";

                        // mostrar notificació
                        showNotification(regId,nomUser);

                        // inserir missatge a la bd
                        LogChatSQLite BD = new LogChatSQLite(context);
                        BD.guardarMensaje(regId,missatge,fecha,nomUser);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    public void showNotification(String regID,String nom) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        //.setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("LibrosVidal")
                        .setContentText("Nuevo mensaje de "+nom);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ConversaActivity.class);
        resultIntent.putExtra("regId",regID);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(PantallaPrincipal.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int mId = 0;
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
