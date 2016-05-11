package com.proyecto.dam2.librosvidal.Communications;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.proyecto.dam2.librosvidal.Preferences.PreferencesUser;
import com.proyecto.dam2.librosvidal.Utils.Google;




import java.io.IOException;


/**
 * Created by cortega on 15/03/2016.
 */

public class RegisterGCM {

    public static final String EXTRA_MESSAGE = "EXTRA message";
    public static final String PROPERTY_REG_ID = "a83ek10dn17s";
    private static final String PROPERTY_APP_VERSION = "1";

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    static String SENDER_ID = "370733945559";


    private static GoogleCloudMessaging gcm;
    private static String regid;

    public static void register(Activity context) {

        Log.i("GCM "," Register GCM START!|  ");

        if (Google.checkPlayServices(context)) {
            gcm = GoogleCloudMessaging.getInstance(context);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground(context);
            }

        } else {
            Log.i("GCM", "No valid Google Play Services APK found.");
        }


    }

    /**
     * Gets the current registration ID for application on RegisterGCM service.
     *
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public static String getRegistrationId(Context context) {

        String registrationId = PreferencesUser.getPreference("REGID", context);

        if (registrationId == "" ) {
            Log.i("GCM", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.

        if (PreferencesUser.getPreference("appVersion",context).equals("")){
            PreferencesUser.setPreference("appVersion","0",context);
        }

        int registeredVersion = Integer.valueOf(PreferencesUser.getPreference("appVersion",context));

        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i("GCM", "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen

            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Registers the application with RegisterGCM servers asynchronously.
     *
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private static void registerInBackground(final Context context) {

        new Thread() {
            public void run() {

                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over
                    // HTTP,
                    // so it can use RegisterGCM/HTTP or CCS to send messages to
                    // your app.
                    // The request to your server should be authenticated if
                    // your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the
                    // device
                    // will send upstream messages to a server that echo back
                    // the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.

                    storeRegistrationId(context, regid);

                    Log.i("GCM", msg);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.e("GCM", msg);

                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }

            }
        }.start();

    }



    /**
     * Sends the registration ID to your server over HTTP, so it can use
     * RegisterGCM/HTTP or CCS to send messages to your app. Not needed for this
     * demo since the device sends upstream messages to a server that echoes
     * back the message using the 'from' address in the message.
     */
    private static void sendRegistrationIdToBackend() {

    }

    public static void sendConfigToBackend(final Context context) {

    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context
     *            application's context.
     * @param regId
     *            registration ID
     */
    private static void storeRegistrationId(Context context, String regId) {

        int appVersion = getAppVersion(context);
        Log.i("GCM", "Saving regId on app version " + appVersion);


        PreferencesUser.setPreference("REGID", regId, context);
        PreferencesUser.setPreference("appVersion", appVersion + "",context);

        String email = PreferencesUser.getPreference("email",context);

        ServerAPI.saveRegId(email,regId);

        SharedPreferences prefs = context.getSharedPreferences("PreferenciasUser", Context.MODE_PRIVATE);
        int id = prefs.getInt("ID", 0);

        ServerAPI.modifyRegIdProducts(id,regId);

    }

}
