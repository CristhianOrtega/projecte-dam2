package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.dam2.librosvidal.Adapters.ListViewAdapterMessages;
import com.proyecto.dam2.librosvidal.Clases.Message;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Clases.QueueMessages;
import com.proyecto.dam2.librosvidal.Preferences.PreferencesUser;
import com.proyecto.dam2.librosvidal.R;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    ListView ListViewDetail;
    ArrayList<Message> listaMessages;
    private ListViewAdapterMessages adapter;
    Product producte;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        context = this;

        // Carregar dades del producte
        producte = (Product)getIntent().getSerializableExtra("Producte");

        // CARGAR ELEMENTOS EN EL LIST VIEW
        listaMessages = new ArrayList<>();

        // prova
        Message m = new Message("Cristhian","Hola estoy interesado en tu libro",producte.getRegId(),true);
        listaMessages.add(m);
        Message m1 = new Message("Jorge","Hola! Cuando y donde quieres quedar?",producte.getRegId(),false);
        listaMessages.add(m1);

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
            String regID = producte.getRegId();

            Message message = new Message(usuari,text,regID,true);

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