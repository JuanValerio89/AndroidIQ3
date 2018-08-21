package com.havr.iq3.arq.iq3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.havr.iq3.arq.iq3.Actividades.Seleccion;

public class MainActivity extends AppCompatActivity {

    public static int PrecioUsar = 0;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_privacidad:
                return true;
            case R.id.mayoreo:
                PrecioUsar = 0;
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Toast.makeText(this, "Mayoreo",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menudeo:
                PrecioUsar = 1;
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Toast.makeText(this, "Menudeo",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
