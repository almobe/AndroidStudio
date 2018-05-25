package com.example.usuario.app_horarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManagerActivity extends AppCompatActivity {

    private TextView textoPrueba;

    private Button button_empleado,  button_ascender ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        button_empleado = (Button) findViewById(R.id.button_por_empleado);
        button_ascender = (Button) findViewById(R.id.button_pasar_gerencia);

        //Botón empleado para pasar al activity donde veremos todas las peticiones por empleado
        button_empleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityPorEmpleado.class);
                startActivity(intent);
            }
        });

        //Botón ascender que nos pasará al activity donde podremos ascender a los empleados al rol de manager
        button_ascender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), ActivityAscender.class);
                startActivity(intent2);
            }
        });






    }
}
