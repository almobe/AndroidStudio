package com.example.usuario.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by USUARIO on 21/12/2017.
 */

public class Principal extends AppCompatActivity {

    private Button misProductos;
    private Button productosUsuario;
    private Button productosCategoria;
    private Button configuracion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        misProductos = (Button) findViewById(R.id.buttonmisproductos);
        productosUsuario = (Button) findViewById(R.id.buttonproductosusuario);
        productosCategoria = (Button) findViewById(R.id.buttonproductoscategoria);
        configuracion = (Button) findViewById(R.id.buttonconfiguracion);

        misProductos.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                acceso_activity(MisProductos.class);

            }
        });

        misProductos.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {


            }
        });

        misProductos.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {


            }
        });

        misProductos.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void acceso_activity(Class clase){
        Intent intent = new Intent (Principal.this, clase);
        startActivity(intent);
    }
}
