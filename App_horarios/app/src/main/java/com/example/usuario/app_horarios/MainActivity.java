package com.example.usuario.app_horarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button button_login;
    private Button button_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);

        button_login.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent new_intent = new Intent(MainActivity.this, Login.class);
                startActivity(new_intent);
            }
        });

        button_register.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent new_intent2 = new Intent(MainActivity.this, Register.class);
                startActivity(new_intent2);
            }
        });


       /* Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(null);
        user_button= (Button) findViewById(R.id.user_toolbar);
        user_button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent new_intent = new Intent(MainActivity.this, Register.class);
                startActivity(new_intent);
            }

        });*/
    }
}
