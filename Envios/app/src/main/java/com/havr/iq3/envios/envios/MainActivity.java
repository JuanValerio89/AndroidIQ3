package com.havr.iq3.envios.envios;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.havr.iq3.envios.envios.Activities.AgregarMaterial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int PERMS_REQUEST_CODE = 1;
    public static String KEY_STRING_PERFILES;

    public static final String MIME_TEXT_PLAIN = "text/plain";
    private TextView DatosNFC;
    private NfcAdapter mNfcAdapter;
    private Button BtNFCiniciar;
    private PendingIntent pendingIntent;
    private IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;

    private TextView TxPrueba;
    private TextView TxNFC;
    private TextView TxComponente;

    private Button BtSiguiente;

    private String NFCdat = "s";
    private String HexNFC;
    private String SDNFC;

    SharedPreferences settings;
    String DatosCot = "s";

    private final static  String TAG = "Principal";
    LocationManager locationManager;
    public double longitudeGPS, latitudeGPS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!DatosCot.equals("s")) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(KEY_STRING_PERFILES, DatosCot);
                    editor.putInt("Piezas", 5);
                    editor.commit();
                    Snackbar.make(view, "Se ha guardado la lista", Snackbar.LENGTH_LONG)
                            .setAction("HAVR", null).show();
                    Log.d(TAG,"DatosCot:"+DatosCot);
                }else{
                    SuperActivityToast.create(MainActivity.this, new Style(), Style.TYPE_BUTTON)
                            .setButtonText("No hay datos por almacenar")
                            .setButtonIconResource(R.drawable.error)
                            .setOnButtonClickListener("good_tag_name", null, null)
                            .setProgressBarColor(Color.WHITE)
                            .setText("Error")
                            .setDuration(Style.DURATION_LONG)
                            .setFrame(Style.FRAME_STANDARD)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED))
                            .setAnimations(Style.DURATION_SHORT).show();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TxPrueba = (TextView) findViewById(R.id.tx_main_prueba);
        TxNFC = (TextView) findViewById(R.id.tx_nfc);
        TxComponente = (TextView) findViewById(R.id.tx_main_material);

        BtSiguiente = (Button) findViewById(R.id.bt_siguiente);

        TxPrueba.setText("Hola mundo!");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    0);
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};

        if (mNfcAdapter == null) {
            SuperActivityToast.create(this, new Style(), Style.TYPE_BUTTON)
                    .setButtonText("Dispositivo sin NFC")
                    .setButtonIconResource(R.drawable.ic_launcher_background)
                    .setOnButtonClickListener("good_tag_name", null, null)
                    .setProgressBarColor(Color.WHITE)
                    .setText("HAVR")
                    .setDuration(Style.DURATION_LONG)
                    .setFrame(Style.FRAME_STANDARD)
                    .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_CYAN))
                    .setAnimations(Style.DURATION_SHORT).show();
            finish();
            return;
        }else{
            SuperActivityToast.create(this, new Style(), Style.TYPE_BUTTON)
                    .setButtonText("Listo para usar")
                    .setButtonIconResource(R.drawable.ic_menu_send)
                    .setOnButtonClickListener("good_tag_name", null, null)
                    .setProgressBarColor(Color.WHITE)
                    .setText("HAVR")
                    .setDuration(Style.DURATION_LONG)
                    .setFrame(Style.FRAME_STANDARD)
                    .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_CYAN))
                    .setAnimations(Style.DURATION_SHORT).show();
        }

        BtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxComponente.setText("Componente:");;
                TxNFC.setText("Esperando lectura");
                TxNFC.setBackgroundResource(R.color.colorAccent);

                settings = getApplicationContext().getSharedPreferences("IQ", 0);
                DatosCot = settings.getString(KEY_STRING_PERFILES,"");
                DatosCot =SDNFC + "@" + DatosCot;


            }
        });

    }

    //Método createRecord será el que nos codifique el mensaje para crear un NdefRecord
    @SuppressLint("NewApi") private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "us";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payLoad = new byte[1 + langLength + textLength];

        payLoad[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payLoad, 1, langLength);
        System.arraycopy(textBytes, 0, payLoad, 1+langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payLoad);

        return recordNFC;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            setupForegroundDispatch(this, mNfcAdapter);
            //mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        //locationManager.removeUpdates((LocationListener) NFCdetection.this);
        Log.d(TAG,"Se ha parado la aplicación");
        stopForegroundDispatch(this, mNfcAdapter);
        super.onPause();
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        Log.d(TAG,"Lectura NFC: inicio za");
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
        Log.d(TAG,"Lectura NFC: inicio zb");
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
            Log.d(TAG,"Lectura NFC: inicio");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        Log.d(TAG,"stopForegroundDispatch");
        adapter.disableForegroundDispatch(activity);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        /*
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        String action = intent.getAction();
        String valor = intent.toString();
        Log.d(TAG,"Action NFC"+action);
        Log.d(TAG,"To string:"+valor);
        if (intent.hasExtra((NfcAdapter.EXTRA_TAG))) {
            //
            // Toast.makeText(this, "NFC!!", Toast.LENGTH_LONG).show();
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            //String datoNFC1 =  intent.getParcelableExtra(NfcAdapter.FLAG_READER_NFC_BARCODE);
            Log.d(TAG, "Datos del NFC:");

        }
        else
            Log.d(TAG, "NFc vacia");
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        Log.d(TAG,"Lectura NFC: inicio A");
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            String dat = String.valueOf(intent.getData());
            //String uri = intent.getDataString();
            NdefMessage[] msgs;
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            Log.d(TAG,"Tipo MYME:"+type);
            Log.d(TAG,"dat:"+dat);
            if(dat.length() > 5)
                NFCdat = dat;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
                Log.d(TAG, "Leer NFC" + type);
            }else if(dat.length() > 5){
                Log.d(TAG,"No MYME, si dat");
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
            }
            else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];
            Log.d(TAG, "TAG:" + "NdefReaderTask");
            StringBuilder sb = new StringBuilder();
            byte[] id = tag.getId();
            sb.append("ID HEX :").append(toHex(id));
            HexNFC = sb.toString();
            Log.d(TAG, "TAG:" + HexNFC);
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                Log.e(TAG, "NFC not supported");
                return null;
            }

            if(NFCdat.length() > 5 ) {
                String regresar = NFCdat;
                NFCdat = "s";
                Log.d(TAG,"Se enviaran los datos aaa ");
                return regresar;
            }else {

                NdefMessage ndefMessage = ndef.getCachedNdefMessage();

                NdefRecord[] records = ndefMessage.getRecords();
                for (NdefRecord ndefRecord : records) {
                    if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                        Log.d(TAG, "Lectura NFC:" + ndefMessage);

                        try {
                            Log.d(TAG, "DATOS:" + ndefMessage);
                            return readText(ndefRecord);
                        } catch (UnsupportedEncodingException e) {
                            Log.e(TAG, "Unsupported Encoding", e);
                        }
                    }
                }
            }
            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            /*
             * See NFC forum specification for "Text Record Type Definition" at 3.2.1
             *
             * http://www.nfc-forum.org/specs/
             *
             * bit_7 defines encoding
             * bit_6 reserved for future use, must be 0
             * bit_5..0 length of IANA language code
             */
            Log.d(TAG,"Lectura NFC: inicio B");
            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                String[] resultadoFolio = result.split("#");
                SDNFC = result;
                result = "Componente:" + result;
                TxComponente.setText(result);
                TxNFC.setText("Revisa componente");
                TxNFC.setBackgroundResource(R.color.colorPrimary);
                Log.d(TAG,"Lectura:"+result);
            }
        }
    }
    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_confimar) {
            // Handle the camera action
        } else if (id == R.id.nav_historial) {

        } else if (id == R.id.nav_agregar) {
            Intent intento = new Intent(MainActivity.this, AgregarMaterial.class);
            startActivity(intento);
        } else if (id == R.id.nav_config) {

        } else if (id == R.id.nav_compartir) {

        } else if (id == R.id.nav_enviar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void tareaLarga() {
        try {
            setContentView(R.layout.activity_main);
            Thread.sleep(2000);
        } catch (InterruptedException var2) {
            ;
        }

    }

}
