package com.example.usuario.mynavigationdrawer;

/**
 * Created by USUARIO on 29/01/2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity{

    //Splash delay configurado a seis segundos
    private long SPLASH_DELAY = 1000; //1 segundo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //En el onCreat() Metemos un timertask con un intent para que pase de este activity al main
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();

        //Al objeto timer se le pasa el timerTask y el splash delay
        timer.schedule(task, SPLASH_DELAY );
    }
}
