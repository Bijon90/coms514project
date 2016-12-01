package com.example.preeti.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Preeti on 10/31/16.
 */

public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread myThread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent StartMainScreen=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(StartMainScreen);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
