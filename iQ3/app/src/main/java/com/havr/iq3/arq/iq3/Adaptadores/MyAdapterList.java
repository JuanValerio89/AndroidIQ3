package com.havr.iq3.arq.iq3.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.havr.iq3.arq.iq3.R;

import java.util.List;

public class MyAdapterList extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> names;

    public MyAdapterList(Context context, int layout, List<String> names){
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View v = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.lista_uno,null);

        String currentName = names.get(position);

        TextView texto = (TextView)v.findViewById(R.id.texto_list_item);
        texto.setText(currentName);

        ImageView imagen = (ImageView) v.findViewById(R.id.imagen_list_view);

        switch (position){
            case 0:
                imagen.setImageResource(R.drawable.perfil_li);
                break;
            case 1:
                imagen.setImageResource(R.drawable.perfil_2li);
                break;
            case 2:
                imagen.setImageResource(R.drawable.perfil_ce);
                break;
            case 3:
                imagen.setImageResource(R.drawable.perfil_i);
                break;
            case 4:
                imagen.setImageResource(R.drawable.perfil_i);
                break;
            case 5:
                imagen.setImageResource(R.drawable.perfil_tr);
                break;
            case 6:
                imagen.setImageResource(R.drawable.perfil_or);
                break;
            case 7:
                imagen.setImageResource(R.drawable.perfil_oc);
                break;
            case 8:
                imagen.setImageResource(R.drawable.perfil_or);
                break;
        }

        return v;
    }

}
