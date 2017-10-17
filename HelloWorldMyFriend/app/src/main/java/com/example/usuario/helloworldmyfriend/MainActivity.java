package com.example.usuario.helloworldmyfriend;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.URI;

import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="ACTIVITYLIFE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"Cargada mi app");

        //Generar el Listener del primer botón
        final Button button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Vamos a programar nuestra llamada a la segunda ventana
                Intent llamada = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(llamada);
            }
        });

        //Generamos el listener del segundo botón
        final Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Vamos a programar nuestra llamada a maps
                Uri direccion= Uri.parse("geo:0,0?q=Aldaya");
                Intent llamada = new Intent(Intent.ACTION_VIEW, direccion);
                startActivity(llamada);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        Log.i(TAG, "Iniciamos nuestra app");
    }

    protected void onResume(){
        super.onResume();
        Log.i(TAG,"Volvemos a nuestra app");

    }

    protected void onPause(){
        super.onPause();
        Log.i(TAG,"Nuestra app passa a segundo plano");

    }

    protected void onStop(){
        super.onStop();
        Log.i(TAG, "Paramos nuestra app");
    }

    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, "Reiniciamos la app");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "Destruimos la app");
    }
}
