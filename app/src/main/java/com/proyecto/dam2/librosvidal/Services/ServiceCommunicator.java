package com.proyecto.dam2.librosvidal.Services;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.proyecto.dam2.librosvidal.Clases.Message;
import com.proyecto.dam2.librosvidal.Clases.QueueMessages;
import com.proyecto.dam2.librosvidal.Communications.ServerAPI;


/**
 * Created by Mobile on 05/05/2016.
 */
public class ServiceCommunicator extends IntentService {

    public ServiceCommunicator() {
        super("Communicator");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        while (true) {

            Log.i("COC", "--> processQueue()");
            Message message =  new Message();
            try {

                if(!QueueMessages.MessageQueue.isEmpty()){
                    message = QueueMessages.MessageQueue.pop();
                    ServerAPI.sendMessageByGCM(message.getRegIDFor(),message.getMessage());
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
        return START_STICKY;
    }
}
