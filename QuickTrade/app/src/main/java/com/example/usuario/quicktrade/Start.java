package com.example.usuario.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by USUARIO on 20/12/2017.
 */

public class Start extends AppCompatActivity {

    private Button registro;
    private Button logueo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        registro = (Button) findViewById(R.id.buttonRegisterStart);
        logueo = (Button) findViewById(R.id.buttonLoginStart);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Start.this, Register.class);
                startActivity(intent);
            }
        });

        logueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (Start.this, Login.class);
                startActivity(intent2);
            }
        });

    }
}
