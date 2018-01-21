package com.example.usuario.quicktrade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.usuario.quicktrade.model.Producto;
import com.example.usuario.quicktrade.model.usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

//Clase que nos permitirá borrary modificar los productos de un usuario
public class Modificar extends AppCompatActivity{

    //Elementos del layout
    private Spinner spinner_producto;
    private Spinner categoria;
    private EditText nombre;
    private EditText descripcion;
    private EditText precio;
    private Button modificar;
    private Button borrar;

    private int borrado;

    //Declaramos elementos que intervendrán con la salida de productos por el Spinner
    private ArrayList<String> arrary_productos;
    private ArrayAdapter<String> adapter;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar);

        spinner_producto = (Spinner) findViewById(R.id.spinner_producto);
        categoria = (Spinner) findViewById(R.id.spinner_categoria);
        nombre = (EditText) findViewById(R.id.editText_modificar_nombre);
        descripcion = (EditText) findViewById(R.id.editText_modificar_descripcion);
        precio = (EditText) findViewById(R.id.editText_modificar_precio);
        modificar = (Button) findViewById(R.id.button_modificar);
        borrar = (Button) findViewById(R.id.button_borrar);

        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

        //Inicializamos objeto de autenticator
        mAuth = FirebaseAuth.getInstance();

        //Iniciamos objetos necesarios para mostrar por el spinner los productos que tenemos
        arrary_productos = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrary_productos);
        spinner_producto.setAdapter(adapter);

        //Método para sacar los objetos del usuario por el spinner
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Ante cualquier modificación, se limpia el array
                arrary_productos.clear();

                //Llamada al email del usuario actual para después igualarlo al usuario del producto
                String usurio_actual = mAuth.getCurrentUser().getEmail();

                //Con un bucle for por cada nombre de producto que encuentre lo añade al array si es del usuario_actual
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Producto producto1 = datasnapshot.getValue(Producto.class);
                    String usuario_producto = producto1.getUsuario();
                    String nombre_producto = producto1.getNombre() ;
                    if(usuario_producto.equals(usurio_actual)){
                        arrary_productos.add(nombre_producto);
                    }
                }
                //Sobre el spinner se hace un setAdapter del adaptador anteriormente iniciado
                spinner_producto.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Listener sobre el botón borrar
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //El integer borrado hará de semáforo para que solamente borre los objetos que señalamos
                borrado =0;
                        bbdd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Recorrer todos los productos
                                for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                                    Producto producto = dataSnapshot2.getValue(Producto.class);
                                    String nombre_producto = producto.getNombre();
                                    String clave =dataSnapshot2.getKey();

                                    //Si el que hay en el spinner coincide lo borramos con el método removeValue()
                                    if(spinner_producto.getSelectedItem().toString().equals(nombre_producto) && borrado==0){
                                        if(producto.getUsuario().equals(mAuth.getCurrentUser().getEmail())){
                                            bbdd.child(clave).removeValue();
                                            Toast.makeText(Modificar.this, "Producto"+ nombre_producto + "borrado correctamente", Toast.LENGTH_LONG).show();
                                            borrado = 1;//Pasamos borrado a 1 para que deje de borrar
                                        }else{
                                            Toast.makeText(Modificar.this, "Producto de otro usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

            }
        });

        //Listener sobre el botón modificar
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbdd.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Recorrer todos los productos
                        for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                            Producto producto = dataSnapshot2.getValue(Producto.class);
                            String nombre_producto = producto.getNombre();
                            String clave =dataSnapshot2.getKey();

                            //Si el seleccionado en el spinner es igual que el que estamos recorriendo en este momento
                            if(spinner_producto.getSelectedItem().toString().equals(nombre_producto)){
                                //Si ha variado una de las condiciones se actualiza el producto y...
                                if (!nombre.getText().toString().isEmpty()) {
                                    producto.setNombre(nombre.getText().toString());
                                }
                                if (!descripcion.getText().toString().isEmpty()){
                                    producto.setDescripcion(descripcion.getText().toString());
                                }
                                if (!precio.getText().toString().isEmpty()){
                                    producto.setPrecio(Double.parseDouble(precio.getText().toString()));
                                }
                                    ///...se hace un setValue en la bbdd
                                    bbdd.child(clave).setValue(producto);
                                    Toast.makeText(Modificar.this, "Producto"+ nombre_producto + "modificado correctamente", Toast.LENGTH_LONG).show();

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
