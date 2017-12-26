package com.example.usuario.quicktrade2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button registro;
    private Button logueo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registro = (Button) findViewById(R.id.buttonRegisterStart);
        logueo = (Button) findViewById(R.id.buttonLoginStart);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        logueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent2 = new Intent (MainActivity.this, Login.class);
                //startActivity(intent2);
            }
        });
    }
}
