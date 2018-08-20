package com.havr.iq3.arq.iq3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button BtGoogle;
    private Button BtFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtGoogle = (Button) findViewById(R.id.bt_google);
        BtFacebook = (Button) findViewById(R.id.bt_facebook);

        BtGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentoA = new Intent(MainActivity.this, Seleccion.class);
                startActivity(IntentoA);
            }
        });

        BtFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentoB = new Intent(MainActivity.this, Seleccion.class);
                startActivity(IntentoB);
            }
        });
    }
}
