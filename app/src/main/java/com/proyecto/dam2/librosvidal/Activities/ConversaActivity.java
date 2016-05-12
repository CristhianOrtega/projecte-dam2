package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.dam2.librosvidal.Adapters.ListViewAdapterMessages;
import com.proyecto.dam2.librosvidal.Clases.Message;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Clases.QueueMessages;
import com.proyecto.dam2.librosvidal.Communications.ServerAPI;
import com.proyecto.dam2.librosvidal.Database.LogChatSQLite;
import com.proyecto.dam2.librosvidal.Preferences.PreferencesUser;
import com.proyecto.dam2.librosvidal.R;
import com.proyecto.dam2.librosvidal.Services.GcmService;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Vector;

public class ConversaActivity extends AppCompatActivity {

    ListView ListViewDetail;
    public static ArrayList<Message> listaMessages;
    private ListViewAdapterMessages adapter;
    Product producte;
    Context context;
    String regID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        context = this;

        // Controlar si es el primer cop que obri xat amb aquesta persona
        //PreferencesUser.setPreference("chat_"+regID,"",context);

        // Carregar dades del producte per si ve de clicar el boto de contacta
        if (getIntent().getSerializableExtra("Producte") != null) {
            producte = (Product) getIntent().getSerializableExtra("Producte");
            System.out.println("PROVA CHAT   -    ENTRA PER PER MENU DE PRODCUTE");
            regID = producte.getRegId().replace("\"","");
            LogChatSQLite BD = new LogChatSQLite(context);
            if (BD.listaMensajes(regID,context) != null){
                listaMessages =BD.listaMensajes(regID,context) ;
            }else{
                listaMessages = new ArrayList<>();
            }
            System.out.println("    REG_ID PARA: "+regID);
            System.out.println("    REG_ID TUYO: "+PreferencesUser.getPreference("REGID",context));

        }

        // Carregar dades si ve per la notificació.
        if (getIntent().getStringExtra("regID") != null){
            regID = getIntent().getStringExtra("regID").toString();
            LogChatSQLite BD = new LogChatSQLite(context);
            listaMessages = BD.listaMensajes(regID,context);
            System.out.println("PROVA CHAT   -    ENTRA PER NOTIFICACIÓ");
            System.out.println("    REG_ID PARA: "+regID);
            System.out.println("    REG_ID TUYO: "+PreferencesUser.getPreference("REGID",context));

            // Controlar si es el primer cop que obri xat amb aquesta persona
            if (PreferencesUser.getPreference("chat_"+regID,context).equals("")){
                PreferencesUser.setPreference("chat_"+regID,"true",context);
                BD.guardarConversa(regID, listaMessages.get(0).getFromName());
            }

            //treure notificació
            GcmService.cancelNotification(context);

        }


        // CARGAR ELEMENTOS EN EL LIST VIEW

        updateList();

    }

    public void onClickSend(View view){
        //obtenir el missatge
        TextView textView = (TextView) findViewById(R.id.inputMsg);
        String text = textView.getText().toString();

        //netejar la caixa
        textView.setText("");

        //Comprovar que no estigui buit
        if (text!=""){

            // crear objecte missatge
            String usuari = PreferencesUser.getPreference("NOM",context);


            System.out.println(" ** ENVIAMENT DE "+usuari+" ** \n MISSATGE: "+text+" \n REGID: "+regID);

            Message message = new Message(usuari,text,regID,true);

            // guardar el missatge a la BD
            LogChatSQLite bd = new LogChatSQLite(context);
            bd.guardarMensaje(regID,text,System.currentTimeMillis(),usuari,"true");


            /* TODO OBTENIR EL NOM DEL USUARI.
            // Controlar si es el primer cop que obri xat amb aquesta persona i guarda conversa
            if (PreferencesUser.getPreference("chat_"+regID,context).equals("")){
                listaMessages = bd.listaMensajes(regID,context);
                PreferencesUser.setPreference("chat_"+regID,"true",context);
                bd.guardarConversa(regID, ServerAPI.getUserName(regID));
            }
            */

            //afegir-lo a la llista.
            listaMessages.add(message);

            // refrescar la listview
            updateList();

            // Afegir a la cua de sortida
            QueueMessages.MessageQueue.push(message);

        }

    }

    public void updateList(){
        ListViewDetail = (ListView) findViewById(R.id.list_view_messages);
        adapter = new ListViewAdapterMessages(this, listaMessages);
        ListViewDetail.setAdapter(adapter);
    }

}
