package com.example.usuario.app_horarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by USUARIO on 17/11/2017.
 */

public class Login extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        login_button = (Button) findViewById(R.id.button_iniciar_sesion_login);


        //TO DO: PONER UN CONDICIONAL DE QUE SI NOS DEVUELVE LA BBDD UN TRUE PASAR A LA PANTALLA PRINCIPAL
        //Y SI NOS DEVUELVE UN FALSE SACAR UN TOAST O UN MENSAJE (TODAVÍA NO LO HE DECIDIDO) DICIENDO
        //EMAIL O PASSWORD ERRONEOS
/*
        button_login.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent new_intent = new Intent(MainActivity.this, Register.class);
                startActivity(new_intent);
            }
        });*/


    }
}
