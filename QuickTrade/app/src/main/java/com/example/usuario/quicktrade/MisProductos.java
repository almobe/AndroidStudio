package com.example.usuario.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by USUARIO on 21/12/2017.
 */

public class MisProductos extends AppCompatActivity {

    //Elelementos que hay en el layout
    private EditText edit_nombre_producto;
    private EditText edit_descripicion_producto;
    private Spinner spinner_categoria;
    private EditText edit_precio_producto;
    private Button button_agregar_producto;

    //Objeto de la autenticación necesario para saber cual es el usuario que está ahora mismo utilizando la app
    private FirebaseAuth mAuth;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;
    DatabaseReference bbdd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_productos);

        edit_nombre_producto = (EditText) findViewById(R.id.editText_nombre_producto);
        edit_descripicion_producto = (EditText) findViewById(R.id.editText_descripcion_producto);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        edit_precio_producto = (EditText) findViewById(R.id.editText_precio_producto);
        button_agregar_producto = (Button) findViewById(R.id.button_nuevo_producto);

        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //Iniciamos objeto autenticathor
        mAuth = FirebaseAuth.getInstance();


        button_agregar_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Datos del producto en varibales
                final String nombre_producto = edit_nombre_producto.getText().toString();
                final String descripicion_producto = edit_descripicion_producto.getText().toString();
                final String categoria = spinner_categoria.getItemAtPosition(spinner_categoria.getSelectedItemPosition()).toString();
                final double precio = Double.parseDouble(edit_precio_producto.getText().toString());

                //El nombre de usuario del producto será el email del usuario actual
                final String nombre_usuario = mAuth.getCurrentUser().getEmail();

                    //Condicionales para saber si nos dejamos algún campo por rellenar
                    if (!TextUtils.isEmpty(nombre_producto)) {
                        if (!TextUtils.isEmpty(descripicion_producto)) {
                            if (!TextUtils.isEmpty(String.valueOf(precio))) {

                                //Se crea el producto
                                Producto p = new Producto(nombre_producto,descripicion_producto,categoria,precio,nombre_usuario);

                                //Se inicializa el objeto de la bbdd en el nodo productos
                                bbdd2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

                                //Creamos objeto clave
                                String clave = bbdd2.push().getKey();

                                //Hacemos un setValue sobre el nodo de la bbdd
                                bbdd2.child(clave).setValue(p);

                                Toast.makeText(MisProductos.this, "Producto introducido correctamente", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(MisProductos.this, "Debes de introducir el precio del producto", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MisProductos.this, "Debes de introducir la descripción del producto", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MisProductos.this, "Debes de introducir el nombre del producto", Toast.LENGTH_LONG).show();
                    }

            }
        });

}

}
