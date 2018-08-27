package com.havr.iq3.arq.iq3.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.havr.iq3.arq.iq3.R;

public class Seleccion extends AppCompatActivity {

    Button BtEnviarManual;
    Button BtCotizacionMM;
    Button BtCotizacionIN;

    RadioButton BtIMCA;
    RadioButton BtAISC;

    public static int DatoMM_IN;

    RadioGroup RadioGrupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);



        BtEnviarManual = (Button) findViewById(R.id.bt_enviar_manual);
        BtCotizacionMM = (Button) findViewById(R.id.bt_cotizaciones_mm);
        BtCotizacionIN = (Button) findViewById(R.id.bt_cotizaciones_in);

        BtIMCA = (RadioButton) findViewById(R.id.radio_imca);
        BtAISC =  (RadioButton) findViewById(R.id.radio_aisc);
        RadioGrupo = (RadioGroup) findViewById(R.id.radio_manual);


        BtEnviarManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BtIMCA.isChecked() == true){
                    Intent intentoA = new Intent(Seleccion.this, Manual.class);
                    intentoA.putExtra("Manual","IMCA");
                    startActivity(intentoA);
                    Toast.makeText(Seleccion.this, "IMCA", Toast.LENGTH_LONG).show();
                }
                else if(BtAISC.isChecked() == true){
                    Intent intentoA = new Intent(Seleccion.this, Manual.class);
                    intentoA.putExtra("Manual","AISC");
                    startActivity(intentoA);
                    Toast.makeText(Seleccion.this, "AISC", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(Seleccion.this, "No has hecho la seleccion", Toast.LENGTH_LONG).show();
                }


            }
        });

        BtCotizacionMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("IQ", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Piezas", 5);
                editor.commit();
                DatoMM_IN = 1;
                Intent Inte = new Intent(Seleccion.this, Cotizaciones.class);
                startActivity(Inte);
            }
        });

        BtCotizacionIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("IQ", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Piezas", 1);
                editor.commit();
                DatoMM_IN = 2;
                Intent Inte = new Intent(Seleccion.this, Cotizaciones.class);
                startActivity(Inte);
            }
        });



    }

    public void comprobarModoPago(View view) {
        if (RadioGrupo.getCheckedRadioButtonId() == R.id.radio_aisc) {
            final String text = "Comprobar ubicación del usuario AISC";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        if (RadioGrupo.getCheckedRadioButtonId() == R.id.radio_imca) {
            final String text = "Comprobar ubicación del usuario IMCA";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
}
