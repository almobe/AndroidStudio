package com.example.usuario.imagen_splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    //Splash delay configurado a seis segundos
    private long SPLASH_DELAY = 5000; //5 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //En el onCreat() Metemos un timertask con un intent para que pase de este activity al main
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();

        //Al objeto timer se le pasa el timerTask y el splash delay
        timer.schedule(task, SPLASH_DELAY );
    }
}
