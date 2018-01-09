package com.example.usuario.imagen_splash;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 06/11/2017.
 */

public class MainActivity extends AppCompatActivity implements MyFragment.OnFragmentInteractionListener, Fragment_Dinamico.OnFragmentInteractionListener, IComunica_Fragments{

    MyFragment fragment;
    Fragment_Dinamico fragment_dinamico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        fragment =new MyFragment();
        //Generamos llamada al fragment para que se cargue en el contenedor
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_fragment,fragment).commit();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void enviarElemento(Elemento elemento) {

        fragment_dinamico = new Fragment_Dinamico();
        //Generamos el objeto Bundle para enviar información
        Bundle bundle_envio=new Bundle();
        //Al bundle envio le metemos un objeto serializable
        bundle_envio.putSerializable("objeto",elemento);

        fragment_dinamico.setArguments(bundle_envio);

        //cargamos el fragment en el activity
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_fragment,fragment_dinamico).addToBackStack(null).commit();
        //el addToBackStack optimiza el reemplazo
    }
}
