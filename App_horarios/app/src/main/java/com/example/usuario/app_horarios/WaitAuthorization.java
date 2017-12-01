package com.example.usuario.app_horarios;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import model.Empleado;

/**
 * Created by USUARIO on 24/11/2017.
 */

public class WaitAuthorization extends AppCompatActivity {

    TextView textoNombre;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_authorization);
        textoNombre = (TextView) findViewById(R.id.textView4);


        Bundle extras = getIntent().getExtras();

        Empleado empleado = extras.getParcelable("Empleado");

        textoNombre.setText("Solicitud enviada "+empleado.getNombre()+".");

    }



    }

