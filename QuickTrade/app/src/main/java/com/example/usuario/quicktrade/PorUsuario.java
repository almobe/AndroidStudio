package com.example.usuario.quicktrade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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

public class PorUsuario extends AppCompatActivity {

    //Elelementos que hay en el layout
    private Spinner spinner_usuarios_por_usuario;

    //Declaramos elementos que intervendrán con la salida de usuarios por el Spinner
    private ArrayList<String> arrary_usuarios_por_usuarios;
    private ArrayAdapter<String> adapter2;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;
    DatabaseReference bbdd2;

    //Definimos el objeto ListView
    private ListView milista;
    private ArrayList<String> productos_de_usuario;
    private ArrayAdapter<String> adaptador;

    usuarios usuario, usuario_Actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.por_usuario);

        spinner_usuarios_por_usuario = (Spinner) findViewById(R.id.spinner_por_usuario);
        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //Iniciamos objetos necesarios para mostrar por el spinner los usuarios que tenemos
        arrary_usuarios_por_usuarios = new ArrayList<String>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrary_usuarios_por_usuarios);
        spinner_usuarios_por_usuario.setAdapter(adapter2);

        //Método para sacar los nombres de usuarios por el spinner
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Ante cualquier modificación, se limpia el array
                arrary_usuarios_por_usuarios.clear();

                //Con un bucle for por cada nombre de usuario que encuentre lo añade al array
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    usuario = datasnapshot.getValue(usuarios.class);
                    String nombre_usuario = usuario.getNombre_usuario();
                    arrary_usuarios_por_usuarios.add(nombre_usuario);
                }

                //Sobre el spinner se hace un setAdapter del adaptador anteriormente iniciado
                spinner_usuarios_por_usuario.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        //Inicializamos el ListView
        milista =  (ListView)findViewById(R.id.list_items_por_usuario);

        //Creamos array con los datos que queremos que aparecan en el ListView
        productos_de_usuario = new ArrayList<String>();

        //Creamos un ArrayAdapter de Strings pasándole el array datos
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, productos_de_usuario);

        //A milista le añadimos el ArrayAdapter
        milista.setAdapter(adaptador);

        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

        //Al modificar la selección del spinner se modifica lo que sacamos por el ListView
        spinner_usuarios_por_usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Primero volvemos a ver el usuario que seleccionamos en el spinner para de esta forma poder sacar su email
                //para después compararlo con el email introducido en los productos
                bbdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Con un bucle for por cada nombre de usuario que encuentre lo compare con
                        //el seleccionado en el spinner, si hay coincidencia sera el usuario usuario_Actual
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                             usuario = datasnapshot.getValue(usuarios.class);
                            if (usuario.getNombre_usuario().equals(spinner_usuarios_por_usuario.getSelectedItem().toString())){
                                usuario_Actual = usuario;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

                //Ahora vamos con la selección de artículos para cada usuario
                bbdd2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        productos_de_usuario.clear();
                        //Recorremos cada uno de los productos,
                        for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                            Producto producto = dataSnapshot2.getValue(Producto.class);
                            String nombre_producto = producto.getNombre();
                            //email del usuario seleccionado en el spinner
                            String email = usuario_Actual.getCorreo();
                            //Si coincide el usuario del producto con el usuario seleccionado
                            if(email.equals(producto.getUsuario())){
                                Log.i("producto", nombre_producto);
                                productos_de_usuario.add(nombre_producto);
                            }
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
