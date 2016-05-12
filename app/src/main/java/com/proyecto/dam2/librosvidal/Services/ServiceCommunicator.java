package com.proyecto.dam2.librosvidal.Services;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.proyecto.dam2.librosvidal.Clases.Message;
import com.proyecto.dam2.librosvidal.Clases.QueueMessages;
import com.proyecto.dam2.librosvidal.Communications.ServerAPI;


/**
 * Created by Mobile on 05/05/2016.
 */


public class ServiceCommunicator extends IntentService {
    Context context;
    private static boolean running = false;

    public ServiceCommunicator() {
        super("Communicator");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("COC", "*********************************************************************");
        Log.i("COC", "   STARTING SERVICE....");

        while (true) {


            Message message =  new Message();
            try {

                if(!QueueMessages.MessageQueue.isEmpty()){
                    Log.i("COC", "    ->  Missatge en cua ");
                    message = QueueMessages.MessageQueue.pop();
                    ServerAPI.postToGCM(message.getRegIDFor(),message.getMessage(),message.getFromName(),context);
                }

                Thread.sleep(500);

            } catch (Exception e) {
                QueueMessages.MessageQueue.push(message);
                e.printStackTrace();
                Log.i("COC", "******************************************** Error service message");
            }

        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        context = this;
        return START_STICKY;
    }

    public static boolean isRunning() {
        return running;
    }
}
