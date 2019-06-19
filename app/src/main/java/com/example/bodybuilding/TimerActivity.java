package com.example.bodybuilding;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

public class TimerActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        chronometer = findViewById(R.id.chronometer);
    }

    public void startChronometer(View v){
        if(!running){
            chronometer.start();
            running = true;
        }

    }

    public void pauseChronometer(View v){
        if(running){
            chronometer.stop();
            running = false;
        }

    }

    public void resetChronometer(View v){

    }
}
