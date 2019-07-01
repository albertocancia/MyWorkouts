package com.example.bodybuilding;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class EserciziActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private Chronometer chronometer;
    private long pauseOffset = 0;
    private boolean running = false;
    private Button button_start, button_reset, button_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esercizi);
        String txt = getIntent().getExtras().getString("val_textView");

        toolbar = (Toolbar) findViewById(R.id.tool_bar_esercizi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(txt);
        //EserciziActivity.this.setTitle(txt);

        button_start = findViewById(R.id.button_start);
        button_pause = findViewById(R.id.button_pause);
        button_reset = findViewById(R.id.button_reset);
        chronometer = findViewById(R.id.chronometer);

        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        button_start.setOnClickListener(this);
        button_pause.setOnClickListener(this);
        button_reset.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v == findViewById(R.id.button_start)){
            if(!running){
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running = true;
            }
        }else if(v == findViewById(R.id.button_pause)){
            if(running){
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                running = false;
            }
        }else if(v == findViewById(R.id.button_reset)){
            long startTime = SystemClock.elapsedRealtime();
            chronometer.setBase(startTime);
            pauseOffset = 0;
        }
    }
}
