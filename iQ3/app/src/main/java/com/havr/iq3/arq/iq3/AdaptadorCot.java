package com.havr.iq3.arq.iq3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class AdaptadorCot extends BaseAdapter{

    private Context context;
    private int layout;
    private List<String> names;

    public AdaptadorCot(Context context, int layout, List<String> names){
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
        v = layoutInflater.inflate(R.layout.list_item_cot,null);
        float Long = 0;

        EditText Longitud = (EditText) v.findViewById(R.id.edit_tx_1) ;
        String TxLong = Longitud.getText().toString();
        Log.d(TAG,"TxLong"+TxLong.isEmpty());
        if(!TxLong.isEmpty())
            Long = Float.parseFloat(TxLong);

        String currentName = names.get(position);
        String[] PorPerfil = currentName.split("@");
        TextView texto = (TextView)v.findViewById(R.id.tx_adap_1);
        texto.setText(PorPerfil[1]+","+PorPerfil[2]);

        TextView texto1 = (TextView)v.findViewById(R.id.tx_adap_2);
        float Valor = Float.parseFloat(PorPerfil[0]);
        float Precio = (float) (Valor * Long);
        texto1.setText("Precio: $ "+Precio);

        return v;
    }
}
