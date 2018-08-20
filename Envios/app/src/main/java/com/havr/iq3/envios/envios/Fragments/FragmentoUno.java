package com.havr.iq3.envios.envios.Fragments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.havr.iq3.envios.envios.Adapters.AdapterList;
import com.havr.iq3.envios.envios.MainActivity;
import com.havr.iq3.envios.envios.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoUno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoUno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoUno extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "FragmentoUno";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences settings;
    List<String> list;

    private TextView TxPrueba;

    public FragmentoUno() {
        // Required empty public constructor
    }

    LocationManager locationManager;
    public double longitudeGPS, latitudeGPS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_framento_uno, container, false);
        TxPrueba = (TextView) view.findViewById(R.id.tx_uno_prueba);
        final ListView ListaMateriales = (ListView) view.findViewById(R.id.lista_fragmento_uno);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TODO;
        }
        String locationProviderA = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProviderA);
        longitudeGPS = lastKnownLocation.getLongitude();
        latitudeGPS = lastKnownLocation.getLatitude();
        Toast.makeText(getContext(), "lat,lon:"+longitudeGPS+","+latitudeGPS, Toast.LENGTH_SHORT).show();


        settings = getApplicationContext().getSharedPreferences("IQ", 0);
        String DatosCot = settings.getString(MainActivity.KEY_STRING_PERFILES,"");
        String[] StringPerfiles = DatosCot.split("@");

        Log.d(TAG, "Lista:"+DatosCot);

        List<String> list = new ArrayList<String>();

        for(int i=0; i < StringPerfiles.length; i++){
            list.add(StringPerfiles[i]);
        }

        AdapterList myAdapter = new AdapterList(getContext(), R.layout.lista_fragmento_uno, list);
        ListaMateriales.setAdapter(myAdapter);

        ListaMateriales.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)ListaMateriales.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), " - Valor: "+itemval, Toast.LENGTH_LONG).show();

            }

        });

        return view;


    }

    private Context getApplicationContext() {
        Context Contexto = getActivity();
        return Contexto;
    }

}
