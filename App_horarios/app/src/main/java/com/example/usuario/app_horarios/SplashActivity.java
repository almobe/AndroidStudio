package com.example.usuario.app_horarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by USUARIO on 16/11/2017.
 */

public class SplashActivity extends AppCompatActivity {

    //Splash delay configurado a seis segundos
    private long SPLASH_DELAY = 5; //1 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

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
