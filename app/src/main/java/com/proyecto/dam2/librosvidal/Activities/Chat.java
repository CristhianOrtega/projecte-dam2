package com.proyecto.dam2.librosvidal.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.proyecto.dam2.librosvidal.Adapters.ListViewAdapterChats;
import com.proyecto.dam2.librosvidal.Adapters.ListViewAdapterProd;
import com.proyecto.dam2.librosvidal.Clases.Contacte;
import com.proyecto.dam2.librosvidal.Clases.Message;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.Database.LogChatSQLite;
import com.proyecto.dam2.librosvidal.R;

import java.util.ArrayList;

public class Chat extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ListView ListViewDetail;
    public static ArrayList<Contacte> listaChats;
    private ListViewAdapterChats adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupActionBar();

        context = this;

        ListViewDetail = (ListView) findViewById(R.id.listChats);

        LogChatSQLite logChatSQLite = new LogChatSQLite(context);
        listaChats =  logChatSQLite.listaChat(context);

        adapter = new ListViewAdapterChats(this, listaChats);
        ListViewDetail.setAdapter(adapter);

        //Listener onClick del ListView
        ListViewDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, ConversaActivity.class);
                i.putExtra("regID",listaChats.get(position).getREGIDFOR());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        //Showing Swipe Refresh animation on activity create
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_chat);
        swipeRefreshLayout.setOnRefreshListener(this);

    }


    @Override
    public void onRefresh() {

        ListViewDetail = (ListView) findViewById(R.id.listChats);

        LogChatSQLite logChatSQLite = new LogChatSQLite(context);
        listaChats =  logChatSQLite.listaChat(context);

        adapter = new ListViewAdapterChats(this, listaChats);
        ListViewDetail.setAdapter(adapter);

        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);

    }


    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
