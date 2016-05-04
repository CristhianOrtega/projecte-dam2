package com.proyecto.dam2.librosvidal.Utils;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


/**
 * Created by Mobile on 15/03/2016.
 */
public class Google {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


     // Check the device to make sure it has the Google Play Services APK. If
     // it doesn't, display a dialog that allows users to download the APK from
     // the Google Play Store or enable it in the device's system settings.


    public static boolean checkPlayServices(Activity context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, context,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("-- COC --", "This device is not supported.");

            }
            return false;
        }
        return true;
    }
}
