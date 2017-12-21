package com.example.usuario.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.usuario.quicktrade.model.usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Declaramos todos los elementos que intervendrán con la interfaz
    private EditText texto_usuario;
    private EditText texto_nombre;
    private EditText texto_apellidos;
    private EditText texto_correo;
    private EditText texto_direccion;
    private Button boton_agregar;
    private Button boton_modificar;
    private Spinner spinner_usuarios;

    //Declaramos elementos que intervendrán con la salida de usuarios por el Spinner
    private ArrayList<String> arrary_usuarios;
    private ArrayAdapter<String> adapter;

    private int contador;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto_usuario = (EditText) findViewById(R.id.editText);
        texto_nombre = (EditText) findViewById(R.id.editText2);
        texto_apellidos = (EditText) findViewById(R.id.editText3);
        texto_correo = (EditText) findViewById(R.id.editText4);
        texto_direccion = (EditText) findViewById(R.id.editText5);
        boton_agregar = (Button) findViewById(R.id.button_agregar);
        boton_modificar = (Button) findViewById(R.id.button_modificar);
        spinner_usuarios = (Spinner) findViewById(R.id.spinner_usuarios);

        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
       // bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //Iniciamos objetos necesarios para mostrar por el spinner los usuarios que tenemos
        arrary_usuarios = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrary_usuarios);
        spinner_usuarios.setAdapter(adapter);

        //Listener sobre el botón de agregar usuario
        boton_agregar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //Strings sobre lo que ponemos en los EditText
                final String nombre_usuario = texto_usuario.getText().toString();
                final String nombre = texto_nombre.getText().toString();
                final String apellidos = texto_apellidos.getText().toString();
                final String correo = texto_correo.getText().toString();
                final String direccion = texto_direccion.getText().toString();

                //Condicionales sobre si alguno de los elementos está vacio
                if(!TextUtils.isEmpty(nombre_usuario)){
                    if(!TextUtils.isEmpty(nombre)) {
                        if(!TextUtils.isEmpty(apellidos)) {
                            if(!TextUtils.isEmpty(correo)) if (!TextUtils.isEmpty(direccion)) {
                                //Búsqueda Query sobre el nombre de usuario
                                Query q = bbdd.orderByChild(getString(R.string.campo_nombre_usuario)).equalTo(nombre_usuario);
                                q.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        contador = 0;
                                        //Por cada nombre de usuario que encuentre igual el contador subiraá
                                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                            contador++;
                                        }
                                        //Si no existe ese nombre de usuario, lo creamos
                                        if (contador == 0) {
                                            usuarios u = new usuarios(nombre_usuario, nombre, apellidos, correo, direccion);
                                            String clave = bbdd.push().getKey();
                                            bbdd.child(clave).setValue(u);
                                            Toast.makeText(MainActivity.this, "Usuario " + u.getNombre_usuario() + " añadido", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            } else {
                                Toast.makeText(MainActivity.this, "Debes de introducir la dirección de correo", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Debes de introducir el correo electrónico", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Debes de introducir los apellidos", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Debes de introducir el nombre", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Debes de introducir el nombre de usuario", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Método para sacar los nombres de usuarios por el spinner
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Ante cualquier modificación, se limpia el array
                arrary_usuarios.clear();

                //Con un bucle for por cada nombre de usuario que encuentre lo añade al array
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    usuarios usuario = datasnapshot.getValue(usuarios.class);
                    String nombre_usuario = usuario.getNombre_usuario();
                    arrary_usuarios.add(nombre_usuario);
                }

                //Sobre el spinner se hace un setAdapter del adaptador anteriormente iniciado
                spinner_usuarios.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Listener sobre el botón de modificar usuario
        boton_modificar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //Se selecciona el usaurio con el que queremos trabajar en el spinner
                final String usuario = spinner_usuarios.getItemAtPosition(spinner_usuarios.getSelectedItemPosition()).toString();

                    //Búsqueda Query del usuario del que hablamos en base a el elegido en el spinner
                    Query q=bbdd.orderByChild(getString(R.string.campo_nombre_usuario)).equalTo(usuario);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Con el objeto datansnapshop cogemos la clave del objeto usuario
                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();

                                //Si todos los campos que podemos modificar están vacíos
                                if(texto_nombre.getText().toString().isEmpty()&&texto_apellidos.getText().toString().isEmpty()
                                        &&texto_correo.getText().toString().isEmpty()&&texto_direccion.getText().toString().isEmpty()){
                                    Toast.makeText(MainActivity.this, "Rellena alguno de los campos para modificar", Toast.LENGTH_LONG).show();

                                }
                                //Si lo que queremos modificar no está vacio lo modificamos
                                if (!texto_nombre.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.campo_nombre)).setValue(texto_nombre.getText().toString());
                                }
                                if (!texto_apellidos.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.campo_apellidos)).setValue(texto_apellidos.getText().toString());
                                }
                                if (!texto_correo.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.campo_correo)).setValue(texto_correo.getText().toString());
                                }
                                if (!texto_direccion.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.campo_direccion)).setValue(texto_direccion.getText().toString());
                                }
                                Toast.makeText(MainActivity.this, "Se ha modificado el usuario "+usuario, Toast.LENGTH_LONG).show();

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
