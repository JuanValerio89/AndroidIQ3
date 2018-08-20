package com.havr.iq3.arq.iq3;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cotizaciones extends AppCompatActivity {
    private static final String TAG = "Cotizaciones";


    Button BtBorrar;
    SharedPreferences settings;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizaciones);

        final ListView ListaCot = (ListView)findViewById(R.id.listaview_cot);

        BtBorrar = (Button) findViewById(R.id.bt_cot_borrar);


        // Get from the SharedPreferences
        settings = getApplicationContext().getSharedPreferences("IQ", 0);
        String DatosCot = settings.getString(Perfiles.KEY_STRING_PERFILES,"");
        String[] StringPerfiles = DatosCot.split(",");

        Log.d(TAG, "List vacio:"+StringPerfiles[0].length()+",");
        list = new ArrayList<String>();
        if(StringPerfiles[0].length() != 0) {

            for (int i = 0; i < StringPerfiles.length; i++) {

                list.add(StringPerfiles[i]);
                Log.d("Cotizaciones", "Perfil: " + StringPerfiles[i]);
            }


            AdaptadorCot myAdapter = new AdaptadorCot(this, R.layout.list_item_cot, list);
            ListaCot.setAdapter(myAdapter);

            ListaCot.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int item = position;
                    String itemval = (String) ListaCot.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(), " - Valor: " + itemval, Toast.LENGTH_LONG).show();
                }

            });
        }
        else
            Toast.makeText(this, "No hay materiales por mostrar",Toast.LENGTH_SHORT).show();

        BtBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                Log.d(TAG,"Lista:"+list.isEmpty());
                if(!list.isEmpty()) {
                    list.clear();
                    MyAdapterList myAdapter = new MyAdapterList(Cotizaciones.this, R.layout.list_item_cot, list);
                    ListaCot.setAdapter(myAdapter);
                }
            }
        });
    }
}
