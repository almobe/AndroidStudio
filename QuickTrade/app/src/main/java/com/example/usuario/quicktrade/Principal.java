package com.example.usuario.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//Clase de la pantalla principal de la app, con acceso a los Activitys
public class Principal extends AppCompatActivity {

    //Cuatro botones para cada uno de los Activity
    private Button misProductos;
    private Button productosUsuario;
    private Button productosCategoria;
    private Button configuracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        //Inicialización de los botones
        misProductos = (Button) findViewById(R.id.buttonmisproductos);
        productosUsuario = (Button) findViewById(R.id.buttonproductosusuario);
        productosCategoria = (Button) findViewById(R.id.buttonproductoscategoria);
        configuracion = (Button) findViewById(R.id.buttonconfiguracion);

        //Cada uno de los botones le pasa una clase diferente al método acceso_activity(class clase)
        misProductos.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                acceso_activity(MisProductos.class);
            }
        });

        productosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               acceso_activity(PorUsuario.class);
            }
        });

        productosCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceso_activity(PorCategoria.class);
            }
        });

        configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceso_activity(Modificar.class);
            }
        });

    }

    //Este método recibe una clase y da acceso a un activity u otro
    private void acceso_activity(Class clase){
        Intent intent = new Intent (Principal.this, clase);
        startActivity(intent);
    }
}
