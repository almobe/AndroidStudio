package com.example.usuario.mynavigationdrawer.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.mynavigationdrawer.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMaps.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMaps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMaps extends Fragment {

    //Objeto de la clase GoogleMaps
    private GoogleMap mMap;

    // Required empty public constructor
    public FragmentMaps() {

    }

    //Constructor de newInstance
    public static FragmentMaps newInstance(String param1, String param2) {
        FragmentMaps fragment = new FragmentMaps();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_maps, container, false);

        //Objeto int para comprobar si los playServices están en correcto funcionamiento
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());

        //Si lo están...
        if (status == ConnectionResult.SUCCESS){

            Log.i("status", String.valueOf(status));

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map);

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {
                        //Iniciamos objeto
                        mMap = googleMap;

                        //Le decimos qué tipo de mapa queremos
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                        //Creamos uiSettings para añadirle las características que queramos
                        UiSettings uiSettings = mMap.getUiSettings();

                        //Nosotros le añadimos a modo de prueba los botones de más y menos zoom
                        uiSettings.setZoomControlsEnabled(true);

                        //Coordenadas lugar
                        LatLng oficina = new LatLng(40.719600, -74.006622);
                        //Añadimos la marca en el mapa
                        mMap.addMarker(new MarkerOptions().position(oficina).title("14 North More Street, NY"));
                        float zoomlevel=19;//Nivel de zoom (máximo 22)
                        //Método para centrar el mapa dentro de un área
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oficina,zoomlevel));
                    }}
                });
        }else{
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity)getContext(),10);
            dialog.show();
        }
        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
