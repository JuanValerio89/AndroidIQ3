package com.havr.iq3.arq.iq3.Adaptadores;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.havr.iq3.arq.iq3.MainActivity;
import com.havr.iq3.arq.iq3.R;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.havr.iq3.arq.iq3.MainActivity.PrecioUsar;

public class AdaptadorCot extends BaseAdapter{

    private Context context;
    private int layout;
    private List<String> names;
    private String valoresEdit;
    private TextView texto1;
    private String Precio;
    private Float[] ValoresPesos = new Float[50];
    private Float[] ValoresPrecios = new Float[50];
    private float Cotizacion = 0;

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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        View v = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        v = layoutInflater.inflate(R.layout.list_item_cot,null);
        float Long = 0;

        EditText Longitud = (EditText) v.findViewById(R.id.edit_tx_1) ;
        String TxLong = Longitud.getText().toString();
        Log.d(TAG,"Precio a usar:" + PrecioUsar);
        if(!TxLong.isEmpty())
            Long = Float.parseFloat(TxLong);

        String currentName = names.get(position);
        String[] PorPerfil = currentName.split("@");

        TextView texto = (TextView)v.findViewById(R.id.tx_adap_1);




        texto.setText(PorPerfil[1]+","+PorPerfil[4]);
        texto1 = (TextView)v.findViewById(R.id.tx_adap_2);


        if(PrecioUsar == 0){
            Precio = PorPerfil[2];
            texto1.setText("Precio: "+PorPerfil[2]);
        }else{
            Precio = PorPerfil[3];
            texto1.setText("Precio: "+PorPerfil[3]);
        }
        String PrecioFinal = Precio.replace("$","");
        ValoresPesos[position] =  Float.parseFloat(PorPerfil[0]) *  Float.parseFloat(PrecioFinal);
        Log.d(TAG,"Multiplicacion A:"+ValoresPesos[position]);

        final EditText TxEdit = (EditText) v.findViewById(R.id.edit_tx_1);
        TxEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Log.d(TAG,"FOCUS ON");
                }else {
                    Log.d(TAG,"FOCUS OFF:");
                }
            }
        });

        // Regresar el texto de la longitud, en donde se detecta un cambio
        TxEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                String ValoresEdit = TxEdit.getText().toString();
                Log.d(TAG,"Texto antes:"+ValoresEdit);
            }
            @Override
            public void afterTextChanged(Editable s) {
                valoresEdit = TxEdit.getText().toString();
                Log.d(TAG,"Texto despues:"+valoresEdit+position);
                if(!valoresEdit.isEmpty()) {
                    ValoresPrecios[position] = ValoresPesos[position] * Float.parseFloat(valoresEdit);
                    Cotizacion = 0;
                    for(int i = 0; i <= position; i++)
                    {
                        Cotizacion = ValoresPrecios[i] + Cotizacion;
                    }
                }

                Log.d(TAG,"Multiplicacion A:"+ValoresPrecios[position]);
                texto1.setText("Precio: $$ "+Cotizacion);
            }
        });



        return v;
    }
}
