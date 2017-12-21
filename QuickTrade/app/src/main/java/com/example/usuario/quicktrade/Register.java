package com.example.usuario.quicktrade;

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

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edit_mail;
    private EditText edit_password;
    private EditText edit_nombre;
    private EditText edit_usuario;
    private EditText edit_apellidos;
    private EditText edit_direccion;
    private Button register;

    private int contador;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edit_mail = (EditText) findViewById(R.id.editMailRegister);
        edit_password = (EditText) findViewById(R.id.editPasswordRegister);
        edit_nombre = (EditText) findViewById(R.id.editNombreRegister);
        edit_apellidos = (EditText) findViewById(R.id.editApellidosRegister);
        edit_direccion = (EditText) findViewById(R.id.editDireccionRegister);
        edit_usuario = (EditText) findViewById(R.id.editUsuarioRegister);
        register = (Button) findViewById(R.id.buttonRegisterRegister);



        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Strings sobre lo que ponemos en los EditText
                final String nombre_usuario = edit_usuario.getText().toString();
                final String nombre = edit_nombre.getText().toString();
                final String apellidos = edit_apellidos.getText().toString();
                final String correo = edit_mail.getText().toString();
                final String direccion = edit_direccion.getText().toString();
                final String password = edit_password.getText().toString();

                //Condicionales sobre si alguno de los elementos está vacio
                if (!TextUtils.isEmpty(nombre_usuario)) {
                    if (!TextUtils.isEmpty(nombre)) {
                        if (!TextUtils.isEmpty(apellidos)) {
                            if (!TextUtils.isEmpty(correo)) {
                                if (!TextUtils.isEmpty(direccion)) {
                                    //Búsqueda Query sobre el nombre de usuario
                                    //   Query q = bbdd.orderByChild(getString(R.string.campo_nombre_usuario)).equalTo(nombre_usuario);
                                    //    q.addListenerForSingleValueEvent(new ValueEventListener() {
                                    //   @Override
                                    //    public void onDataChange(DataSnapshot dataSnapshot) {
                                    contador = 0;
                                    //Por cada nombre de usuario que encuentre igual el contador subiraá
                                    //      for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                    //         contador++;
                                    //   }
                                    //Si no existe ese nombre de usuario, lo creamos
                                    if (contador == 0) {
                                        usuarios u = new usuarios(nombre_usuario, nombre, apellidos, correo, direccion);
                                        //   String clave = bbdd.push().getKey();
                                        //    bbdd.child(clave).setValue(u);
                                        registrar(correo, password);
                                        Toast.makeText(Register.this, "Usuario " + u.getNombre_usuario() + " añadido", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Register.this, "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
                                        //  }
                                        //  }

                                        // @Override
                                        //  public void onCancelled(DatabaseError databaseError) {

                                        //  }
                                        //  });

                                        //  } else {
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

                // });
            }

        });
    }

    private void registrar (String email, String password){

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Authentication succesful."+user.getUid(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
