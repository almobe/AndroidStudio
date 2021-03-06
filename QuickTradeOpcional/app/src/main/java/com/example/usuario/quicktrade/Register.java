package com.example.usuario.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.quicktrade.model.usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.password;

//Clase que utilizaremos como entrada a la bbdd de un nuevo usuario
public class Register extends AppCompatActivity {

    //Partes del layout que utilizaremos en el Activity
    private EditText edit_nombre;
    private EditText edit_usuario;
    private EditText edit_apellidos;
    private EditText edit_direccion;
    private Button register;

    //Objeto para la autenticación
    private FirebaseAuth mAuth;

    //Contador que nons ayudará a saber si ya hay un nombre igual en la bbdd
    private int contador;

    //Declaramos objeto DatabaseReference
    private DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //Objetos del layout
        edit_nombre = (EditText) findViewById(R.id.editNombreRegister);
        edit_apellidos = (EditText) findViewById(R.id.editApellidosRegister);
        edit_direccion = (EditText) findViewById(R.id.editDireccionRegister);
        edit_usuario = (EditText) findViewById(R.id.editUsuarioRegister);
        register = (Button) findViewById(R.id.buttonRegisterRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicialización de los objetos FirebaseDatabase y FirebaseAuth
                bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));
                mAuth = FirebaseAuth.getInstance();

                //Strings necesarios para crear un nuevo usuario
                final String nombre_usuario = edit_usuario.getText().toString();
                final String nombre = edit_nombre.getText().toString();
                final String apellidos = edit_apellidos.getText().toString();
                final String correo = mAuth.getCurrentUser().getEmail();
                final String direccion = edit_direccion.getText().toString();

                //Condicionales sobre si alguno de los elementos está vacio
                if (!TextUtils.isEmpty(nombre_usuario)) {
                    if (!TextUtils.isEmpty(nombre)) {
                        if (!TextUtils.isEmpty(apellidos)) {
                            if (!TextUtils.isEmpty(correo)) {
                                if (!TextUtils.isEmpty(direccion)) {
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
                                         //Si no existe ese nombre de usuario, lo creamos y pasamos al Activity Principal
                                           if (contador == 0) {
                                             usuarios u = new usuarios(nombre_usuario, nombre, apellidos, correo, direccion);
                                                String clave = bbdd.push().getKey();
                                                 bbdd.child(clave).setValue(u);
                                             Toast.makeText(Register.this, "Usuario " + u.getNombre_usuario() + " añadido", Toast.LENGTH_LONG).show();
                                             Intent intent = new Intent (Register.this, Principal.class);
                                               startActivity(intent);
                                        } else {
                                             Toast.makeText(Register.this, "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
                                            }
                                          }

                                         @Override
                                          public void onCancelled(DatabaseError databaseError) {

                                          }
                                          });

                                          } else {
                                        Toast.makeText(Register.this, "Debes de introducir la dirección de correo", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(Register.this, "Debes de introducir el correo electrónico", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Register.this, "Debes de introducir los apellidos", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "Debes de introducir el nombre", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Debes de introducir el nombre de usuario", Toast.LENGTH_LONG).show();
                    }
                }

                 });

    }

}
