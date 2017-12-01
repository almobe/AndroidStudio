package com.example.usuario.imagen_splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 06/11/2017.
 */

public class MainActivity extends AppCompatActivity {

    //Declaramos sin iniciar el ReccyclerView, el Adapatador y el ArrayList
    RecyclerView rv;
    Adaptador ARVElemento;
    private List<Elemento> elementos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Iniciamos el objeto RecyclerView con el Recycler de la interfaz .xml
        rv= (RecyclerView)findViewById(R.id.RECYCLER_VIEW);

        //Creamos un objeto LinearLayoutManager, le decimos que tiene que ser vertical y se lo pasamos al RecyclerView
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lim);

        //Llamamos a los métodos data() y incializaAdapatador()
        data();
        inicializaAdaptador();

    }

    //Método que inicializa el ArrayList y en cual le pasamos los elementos que queremos que cree
    //y que pase al RecyclerView
    public void data(){
        elementos = new ArrayList();

        elementos.add(new Elemento("Ghost",R.drawable.greenghost,612345789));
        elementos.add(new Elemento("Iker Jimenez",R.drawable.iker,698745612));
        elementos.add(new Elemento("JJ Benitez",R.drawable.jj,635753951));
        elementos.add(new Elemento("Ghostbusters",R.drawable.caza,654789321));
        elementos.add(new Elemento("Police",R.drawable.policia,666999888));
        elementos.add(new Elemento("Bruja Lola",R.drawable.bruja,902902902));
        elementos.add(new Elemento("Sandro Rey",R.drawable.brujo,906906906));
        elementos.add(new Elemento("Bruce Willis",R.drawable.bruce,654164937));
        elementos.add(new Elemento("Bomberos",R.drawable.bombero,652316954));

    }

    //Método en el cual inicalizamos el adaptador
    public void inicializaAdaptador(){
        ARVElemento= new Adaptador(elementos);
        rv.setAdapter(ARVElemento);
    }


}
