package com.havr.iq3.arq.iq3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Seleccion extends AppCompatActivity {

    Button BtEnviarManual;
    Button BtCotizacion;

    RadioButton BtIMCA;
    RadioButton BtAISC;



    RadioGroup RadioGrupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);



        BtEnviarManual = (Button) findViewById(R.id.bt_enviar_manual);
        BtCotizacion = (Button) findViewById(R.id.bt_cotizaciones);
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

        BtCotizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("IQ", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Piezas", 5);
                editor.commit();

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
