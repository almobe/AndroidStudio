package com.example.usuario.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Clase que utilizaremos como Login para el acceso de un usuario ya registrado en la app
public class Login extends AppCompatActivity {

    //Objetos del layout
    private EditText edit_mail;
    private EditText edit_password;
    private Button login;

    //Objeto necesario para manejar el acceso autorizado
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edit_mail=(EditText) findViewById(R.id.editMailLogin);
        edit_password=(EditText) findViewById(R.id.editPasswordLogin);
        login = (Button) findViewById(R.id.buttonLoginLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Strings que rescatamos de los editText
                String emailL = edit_mail.getText().toString();
                String passwordL = edit_password.getText().toString();

                //Método Login desarrollado más abajo, le pasamos por parámetro los Strings anteriores
                login (emailL,passwordL);

            }
        });
    }

    //Método login para el acceso autorizado a la app
    private void login (String email, String password){

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)//Método sobre el objeto autentication que varía del de registro
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "SignIn succesful."+user.getUid(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (Login.this, Principal.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "SignIn failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}