package com.havr.iq3.arq.iq3.Actividades;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.havr.iq3.arq.iq3.Adaptadores.MyAdapter;
import com.havr.iq3.arq.iq3.R;

import java.util.ArrayList;
import java.util.List;

public class Manual extends AppCompatActivity {

    private static final String TAG = "Manual";
    private String TipoManual;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapatador;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_manual);
        setContentView(R.layout.lista_manual); // RecyclerView

        Intent intent = getIntent();
        String Manual = intent.getStringExtra("Manual");

        final ListView ListaMateriales = (ListView)findViewById(R.id.listview_materiales);
        Resources res = getResources();

        String[] StringPerfiles = res.getStringArray(R.array.conjunto_perfiles_imca);
        if(Manual.equals("IMCA")){
            StringPerfiles = res.getStringArray(R.array.conjunto_perfiles_imca);
            TipoManual = "IMCA";
        }
        if(Manual.equals("AISC")){
            StringPerfiles = res.getStringArray(R.array.conjunto_perfiles_aisc);
            TipoManual = "AISC";
        }
        List<String> list = new ArrayList<String>();
        for(int i=0; i < StringPerfiles.length; i++){
            list.add(StringPerfiles[i]);
        }
        Log.d(TAG,"Tamaño lista:"+list.size());

        mRecyclerView =  (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapatador = new MyAdapter(list, R.layout.recycler_manual_perfilex, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(Manual.this, name + "=" + position, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), " - Valor: "+itemval, Toast.LENGTH_LONG).show();

                Intent moverse = new Intent(Manual.this,Perfiles.class);
                moverse.putExtra("Manual",TipoManual);
                moverse.putExtra("Perfil",position);
                moverse.putExtra("SPerfil",name);
                startActivity(moverse);
            }
        }, 0);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapatador);
/*
        MyAdapterList myAdapter = new MyAdapterList(this, R.layout.lista_uno, list);
        ListaMateriales.setAdapter(myAdapter);

        ListaMateriales.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)ListaMateriales.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), " - Valor: "+itemval, Toast.LENGTH_LONG).show();

                Intent moverse = new Intent(Manual.this,Perfiles.class);
                moverse.putExtra("Manual",TipoManual);
                moverse.putExtra("Perfil",item);
                moverse.putExtra("SPerfil",itemval);
                startActivity(moverse);
            }

        });
        */

    }

}
