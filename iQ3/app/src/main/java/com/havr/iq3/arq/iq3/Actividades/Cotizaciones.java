package com.havr.iq3.arq.iq3.Actividades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CancellationSignal;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.havr.iq3.arq.iq3.Adaptadores.AdaptadorCot;
import com.havr.iq3.arq.iq3.Adaptadores.MyAdapterList;
import com.havr.iq3.arq.iq3.GMailSender;
import com.havr.iq3.arq.iq3.R;

import java.util.ArrayList;
import java.util.List;

public class Cotizaciones extends AppCompatActivity {
    private static final String TAG = "Cotizaciones";


    Button BtBorrar;
    Button BtCotizar;
    Button BtEnviar;

    TextView TxCotizacion;
    PrintedPdfDocument pdf;
    SharedPreferences settings;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizaciones);

        final ListView ListaCot = (ListView)findViewById(R.id.listaview_cot);

        BtBorrar = (Button) findViewById(R.id.bt_cot_borrar);
        BtCotizar = (Button) findViewById(R.id.bt_cot);
        BtEnviar = (Button) findViewById(R.id.bt_enviar_correo);

        TxCotizacion = (TextView) findViewById(R.id.text_cotizacion);

        // Get from the SharedPreferences Mm or IN
        settings = getApplicationContext().getSharedPreferences("IQ", 0);
        int DatoMMIN = settings.getInt("Piezas",0);
        String[] StringPerfiles;
        // Recuperar MM
        if(DatoMMIN == 5){
            settings = getApplicationContext().getSharedPreferences("IQ_MM", 0);
            String DatosCot = settings.getString(Perfiles.KEY_STRING_PERFILES_MM,"");
            StringPerfiles = DatosCot.split(",");
        }
        // Recuperar IN
        else{
            settings = getApplicationContext().getSharedPreferences("IQ_IN", 0);
            String DatosCot = settings.getString(Perfiles.KEY_STRING_PERFILES_IN,"");
            StringPerfiles = DatosCot.split(",");
        }


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

        BtCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxCotizacion.setText("Total: $ "+AdaptadorCot.Cotizacion);
            }
        });

        BtEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG,"Enviar correo");
                LongOperation l=new LongOperation();
                l.execute();
                // ,heriberto.segura@carbopapel.com.mx

            }
        });
    }
    public class LongOperation extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            try {
                int dato = 0;

                GMailSender sender = new GMailSender("cotizaciones.iq3@gmail.com", "2008640354");
                String name = "Cotizacion";
                String titulo = "Cotizacion: $ "+AdaptadorCot.Cotizacion;
                try {
                    Log.d(TAG,"Enviando");
                    sender.sendMail(1,"Cotización",
                            titulo + "",
                            "valeriovaa@gmail.com",

                            "valeriovaa@gmail.com,juan.valerio@h-avr.com,mmcm.icv@gmail.com");
                } catch (Exception e) {
                    Log.e("error", e.getMessage(), e);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e("error", e.getMessage(), e);
                return "Email Not Sent";
            }
            return "Email Sent";
        }


        @Override
        protected void onPostExecute(String result) {
            Log.e("LongOperation", result + "");
        }

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }



}
