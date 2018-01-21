package com.example.usuario.quicktrade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.usuario.quicktrade.model.Producto;
import com.example.usuario.quicktrade.model.usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by USUARIO on 04/01/2018.
 */

public class PorCategoria extends AppCompatActivity {

    //Elelementos que hay en el layout
    private Spinner spinner_categoria;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;

    //Definimos el objeto ListView
    private ListView milista;
    private ArrayList<String> productos_de_categoria;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.por_categoria);

        spinner_categoria = (Spinner) findViewById(R.id.spinner_por_categoria);
        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));


        //Inicializamos el ListView
        milista =  (ListView)findViewById(R.id.list_items_por_categoria);

        //Creamos array con los datos que queremos que aparecan en el ListView
        productos_de_categoria = new ArrayList<String>();

        //Creamos un ArrayAdapter de Strings pasándole el array datos
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, productos_de_categoria);

        //A milista le añadimos el ArrayAdapter
        milista.setAdapter(adaptador);

        //Al hacer una modificación en el spinner de la categoría
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bbdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Borramos array
                        productos_de_categoria.clear();

                        //Recorremos todos los productos
                        for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                            Producto producto = dataSnapshot2.getValue(Producto.class);
                            String nombre_producto = producto.getNombre();

                            //Si coincide la categoría del spinner con  la categoría del producto
                            if(spinner_categoria.getSelectedItem().toString().equals(producto.getCategoria())){
                                //añadimos producto al array
                                productos_de_categoria.add(nombre_producto);
                            }
                            //y hacemos setAdapter para que lo acople al ListView
                            milista.setAdapter(adaptador);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



}
