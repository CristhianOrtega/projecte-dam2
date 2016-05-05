package com.proyecto.dam2.librosvidal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.proyecto.dam2.librosvidal.Clases.Contacte;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.R;

import java.util.ArrayList;

public class ListViewAdapterChats extends BaseAdapter {
    static ArrayList<Contacte> myList = new ArrayList<Contacte>();
    LayoutInflater inflater;
    Context context;


    public ListViewAdapterChats(Context context, ArrayList<Contacte> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Contacte getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contacte, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Contacte currentListProd = getItem(position);
        mViewHolder.nomcontact.setText(currentListProd.getNom());
        mViewHolder.lastMsg.setText("" + currentListProd.getLastMsg());
        mViewHolder.aq.id(convertView.findViewById(R.id.fotoCont)).image(currentListProd.getImagePerfilCont(), true, true);
        System.out.println(currentListProd.getImagePerfilCont());

        return convertView;


    }


    private class MyViewHolder {
        TextView nomcontact;
        TextView lastMsg;
        ImageView imageContact;
        AQuery aq;


        public MyViewHolder(View item) {
            nomcontact = (TextView) item.findViewById(R.id.nomContact);
            lastMsg = (TextView) item.findViewById(R.id.lastMsg);
            imageContact = (ImageView) item.findViewById(R.id.fotoCont);
            aq = new AQuery(context);
        }
    }
}
