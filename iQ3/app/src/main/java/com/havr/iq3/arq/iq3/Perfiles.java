package com.havr.iq3.arq.iq3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Perfiles extends AppCompatActivity {

    private static final String TAG = "PERFILES";
    public static String KEY_STRING_PERFILES;
    public String[] row;
    public String[] Columnas = new String[ 400 ];
    public String[] SpinString = new String[ 400 ];
    ImageView ImagenPerfiles;
    TextView TipoAngulo;
    String[] PerfilConjunto;
    private List scoreList;
    private Button BtAgregar;
    private String TipoPerfil = "";
    private String WL = "";
    private int NumeroGuardar = 0;

    //public String[][] Matriz = new String[ 200 ][200];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_perfiles);
        ImagenPerfiles = (ImageView) findViewById(R.id.image_perfiles);
        TipoAngulo = (TextView) findViewById(R.id.tx_tipo_angulo);
        BtAgregar = (Button) findViewById(R.id.bt_agregar);

        final ListView ListaPerfiles = (ListView) findViewById(R.id.list_perfiles);



        Intent intent = getIntent();
        int ValorItem = intent.getIntExtra("Perfil",0);
        String datoPerfil = intent.getStringExtra("SPerfil");
        TipoPerfil = datoPerfil;
        String TipoManual = intent.getStringExtra("Manual");
        TipoAngulo.setText(datoPerfil);

        InputStream inputStream;
        CSVFile csvFile;
        String listString;
        Resources res = getResources();
        PerfilConjunto = res.getStringArray(R.array.conjunto_ld);
        switch (ValorItem){
            case 0:
                ImagenPerfiles.setImageResource(R.drawable.perfil_li);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_li);
                else
                    inputStream = getResources().openRawResource(R.raw.in_li);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_li);
                break;
            case 1:
                ImagenPerfiles.setImageResource(R.drawable.perfil_li);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_ld);
                else
                    inputStream = getResources().openRawResource(R.raw.in_ld);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_ld);
                break;
            case 2:
                ImagenPerfiles.setImageResource(R.drawable.perfil_ce);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_ce);
                else
                    inputStream = getResources().openRawResource(R.raw.in_ce);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_ce);
                break;
            case 3:
                ImagenPerfiles.setImageResource(R.drawable.perfil_ce);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_ie);
                else
                    inputStream = getResources().openRawResource(R.raw.in_ie);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_ie);
                break;
            case 4:
                ImagenPerfiles.setImageResource(R.drawable.perfil_i);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_ir);
                else
                    inputStream = getResources().openRawResource(R.raw.in_ir);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_ir);
                break;
            case 5:
                ImagenPerfiles.setImageResource(R.drawable.perfil_tr);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_tr);
                else
                    inputStream = getResources().openRawResource(R.raw.in_tr);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_tr);
                break;
            case 6:
                ImagenPerfiles.setImageResource(R.drawable.perfil_2li);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_2li);
                else
                    inputStream = getResources().openRawResource(R.raw.in_2li);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_2li);
                break;
            case 9:
                ImagenPerfiles.setImageResource(R.drawable.perfil_oc);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_oc);
                else
                    inputStream = getResources().openRawResource(R.raw.in_oc);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_oc);
                break;
            case 10:
                ImagenPerfiles.setImageResource(R.drawable.perfil_or);
                Log.d("Perfiles","Info:"+ValorItem);
                if(TipoManual.equals("IMCA"))
                    inputStream = getResources().openRawResource(R.raw.mm_or);
                else
                    inputStream = getResources().openRawResource(R.raw.in_or);
                csvFile = new CSVFile(inputStream);
                scoreList = csvFile.read();
                Log.d("Perfiles","Info: B"+ scoreList.size());
                listString = "";
                PerfilConjunto = res.getStringArray(R.array.conjunto_or);
                break;
        }

        // Listado superior de las medidas
        final String[] SpinOtro = new String[ scoreList.size() ];
        for(int ii = 0; ii < scoreList.size();ii++) {
            if(TipoManual.equals("IMCA"))
                SpinOtro[ii] = SpinString[ii] + " mm";
            else
                SpinOtro[ii] = SpinString[ii] + " in";
        }
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinOtro));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // Se selecciona por medida, la informamcion es procesado al corta por ","
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                boolean BOWL = false;
                NumeroGuardar = pos;
                String[] StringInfoDatos = Columnas[pos].split(",");
                String[] StringListaValores = new String[StringInfoDatos.length-1];
                Log.d(TAG,"Tamaño String:"+StringInfoDatos.length);
                for(int xx = 0; xx < PerfilConjunto.length; xx++){
                    StringListaValores[xx] = PerfilConjunto[xx] + StringInfoDatos[xx+1]; // Guardar lista de valores por medida
                    String[] PerfilWL = PerfilConjunto[xx].split(" ");  // Buscar WL para almacenar al momento de agregar
                    if(PerfilWL[0].equals("w/I"))
                    {
                        WL = StringInfoDatos[xx+1];
                        BOWL = true;
                    }
                }
                if(!BOWL)
                    WL = "NO";

                ArrayAdapter<String> adaptadorPerfiles = new ArrayAdapter<String>(Perfiles.this,
                        android.R.layout.simple_list_item_activated_1, StringListaValores);
                ListaPerfiles.setAdapter(adaptadorPerfiles);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });



        Log.d("Perfiles","Informacion:" + ValorItem + ",val in:"+ SpinString[ValorItem] +",Otra info:"
                +Columnas[ValorItem]);

        // Guardamos la información
        BtAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("IQ", 0);
                String DatosCot = settings.getString(KEY_STRING_PERFILES,"");
                DatosCot = WL+"@"+TipoPerfil + "@" + SpinOtro[NumeroGuardar] + "," + DatosCot;
                Log.d(TAG,"Valor WL:"+WL);
                if(!WL.equals("NO")){
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(KEY_STRING_PERFILES,DatosCot);
                    editor.putInt("Piezas", 5);
                    editor.commit();
                    Toast.makeText(Perfiles.this, "Se agrego a la cotización",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(Perfiles.this, "No se encontro W/I",Toast.LENGTH_LONG).show();
            }
        });
    }

    public class CSVFile {
        InputStream inputStream;

        public CSVFile(InputStream inputStream){
            this.inputStream = inputStream;
        }

        public List read(){
            List resultList = new ArrayList();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String csvLine;
                int i = 0;
                while ((csvLine = reader.readLine()) != null) {
                    row = csvLine.split(",");
                    SpinString[i] = row[0];
                    //Log.d("Perfiles","Haciendo listas:"+csvLine);
                    Columnas[i] = csvLine;
                    i += 1;
                    resultList.add(row);
                }

            }
            catch (IOException ex) {
                throw new RuntimeException("Error in reading CSV file: "+ex);
            }
            finally {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    throw new RuntimeException("Error while closing input stream: "+e);
                }
            }
            return resultList;
        }
    }
}
