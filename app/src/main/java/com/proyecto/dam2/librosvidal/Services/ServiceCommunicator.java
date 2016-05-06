package com.proyecto.dam2.librosvidal.Services;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.proyecto.dam2.librosvidal.Clases.QueueMessages;


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

            try {

                if(!QueueMessages.MessageQueue.isEmpty()){

                }

            } catch (Exception e) {

                e.printStackTrace();
                Log.i("COC", "******************************************** Error service message");

            }
        }
    }


}
