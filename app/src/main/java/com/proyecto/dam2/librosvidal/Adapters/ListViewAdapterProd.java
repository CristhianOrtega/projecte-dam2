package com.proyecto.dam2.librosvidal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.proyecto.dam2.librosvidal.Clases.Product;
import com.proyecto.dam2.librosvidal.R;

public class ListViewAdapterProd extends BaseAdapter {

    static ArrayList<Product> myList = new ArrayList<Product>();
    LayoutInflater inflater;
    Context context;


    public ListViewAdapterProd(Context context, ArrayList<Product> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Product getItem(int position) {
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
            convertView = inflater.inflate(R.layout.producte, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Product currentListProd = getItem(position);
        mViewHolder.titolProd.setText(currentListProd.getTitol());
        return convertView;


    }


    private class MyViewHolder {
        TextView titolProd;
        TextView preuProd;
        //ImageView imageProd;


        public MyViewHolder(View item) {
            titolProd = (TextView) item.findViewById(R.id.titol);
            preuProd = (TextView) item.findViewById(R.id.Preu);
            //imageProd = (ImageView) item.findViewById(R.id.fotoPrin);
        }
    }
}
