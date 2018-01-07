package com.example.usuario.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Clase que utilizaremos para formalizar la autenticación de entrada como primer registro
public class Register2 extends AppCompatActivity {

    //Declaración objeto necesario para la autenticación
    private FirebaseAuth mAuth;

    //Declaración de objetos que manejaremos en la interfaz
    private EditText edit_mail;
    private EditText edit_password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Inicialización de los mismos
        edit_mail=(EditText) findViewById(R.id.editMailLogin);
        edit_password=(EditText) findViewById(R.id.editPasswordLogin);
        login = (Button) findViewById(R.id.buttonLoginLogin);

        //Si presionamos el botón
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //El contenido de los EditText pasa a ser dos variables String
                String emailL = edit_mail.getText().toString();
                String passwordL = edit_password.getText().toString();

                //Si no están vacios
                if (!TextUtils.isEmpty(emailL)) {
                    if (!TextUtils.isEmpty(passwordL)) {
                        registrar (emailL,passwordL);//...llamamos al método registrar pasándole los dos parámetros
                    } else {
                        Toast.makeText(Register2.this, "Debes de introducir la contraseña", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Register2.this, "Debes de introducir el email", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //Método registrar al que le pasamos por parámetro tanto el email como la contraseña
    private void registrar (String email, String password){

        //Iniacilizamos el objeto FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Sobre el objeto de la autenticación llamamos al método createUserWithEmailAndPassword pasándole el email y el password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register2.this, "Authentication succesful."+user.getUid(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (Register2.this, Register.class);//Intent al activity de entrada de usuario a la bbdd
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
