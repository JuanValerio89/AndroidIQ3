package com.havr.iq3.envios.envios.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.havr.iq3.envios.envios.R;

import java.util.List;

public class AdapterList extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> names;

    public AdapterList(Context context, int layout, List<String> names){
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
        v = layoutInflater.inflate(R.layout.lista_fragmento_uno,null);

        String currentName = names.get(position);
        String[] DatosA = currentName.split(",");
        TextView textoA = (TextView)v.findViewById(R.id.tx_title);
        textoA.setText(DatosA[0] + "," + DatosA[1]);

        TextView textoB = (TextView)v.findViewById(R.id.tx_subtitle);
        textoB.setText(DatosA[2] + "," + DatosA[3]);

        return v;
    }

}
