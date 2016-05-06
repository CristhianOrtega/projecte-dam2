package com.proyecto.dam2.librosvidal.Activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.dam2.librosvidal.Communications.HttpConnection;
import com.proyecto.dam2.librosvidal.Communications.RegisterGCM;
import com.proyecto.dam2.librosvidal.Preferences.PreferencesUser;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Utils.Others;

import java.util.HashMap;

public class Registro_Usuario extends AppCompatActivity {

    Context context;
    Activity contextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__usuario);
        context = this;
        contextActivity = this;
    }


    public void onRegister(View v){

        EditText inputName = (EditText) findViewById(R.id.inputName);
        EditText inputApellidos = (EditText) findViewById(R.id.inputApellidos);
        EditText inputEmail = (EditText) findViewById(R.id.inputEmail);
        EditText inputPassword = (EditText) findViewById(R.id.inputPassword);
        EditText inputPasswordRepeat = (EditText) findViewById(R.id.inputPasswordRepeat);

        if (inputPassword.getText().toString().equals(inputPasswordRepeat.getText().toString())){

            // insert
            boolean success = register(inputPassword.getText().toString(), inputEmail.getText().toString(), inputName.getText().toString(), inputApellidos.getText().toString());

            if (success){
                inputName.setText("");
                inputApellidos.setText("");
                inputEmail.setText("");
                inputPassword.setText("");
                inputPasswordRepeat.setText("");
                Toast.makeText(context,"Registro correcto",Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(context,"Error al insertar",Toast.LENGTH_SHORT).show();

            }
        }else{

            Toast.makeText(context,"No coindice el password",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean register(String password, String email, String nom, String cognoms){

        // --- Register new user -------------------------------------------------------------------
        String response = "";
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("action","register");
        postParams.put("email",email);
        postParams.put("password",password);
        postParams.put("nom",password);
        postParams.put("cognoms",password);
        String url = "http://librosvidal.esy.es/api.php";

        HttpConnection request = new HttpConnection(url, postParams,
                "login");

        while (!request.isReceived()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {

            }
        }

        response = request.getResponse();

        Log.i("COC", "Register->" + response);

        if (response.equals("true")){

            int appVersion = Others.getAppVersion(context);

            PreferencesUser.setPreference("appVersion",appVersion+"",context);
            PreferencesUser.setPreference("email",email,context);

            RegisterGCM.register(contextActivity);

            return true;

        }else{
            return false;
        }


    }
}
