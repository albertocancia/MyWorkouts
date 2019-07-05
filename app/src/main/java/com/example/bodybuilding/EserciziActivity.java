package com.example.bodybuilding;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.StringTokenizer;

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
        TextView txtSerie = (TextView)findViewById(R.id.txt_serie);
        TextView txtRep = (TextView)findViewById(R.id.txt_ripetizioni);
        TextView txtPeso = (TextView)findViewById(R.id.txt_peso);
        Intent intent = getIntent();
        String txt = intent.getStringExtra("ESERCIZIO");
        String serie = intent.getStringExtra("SERIE");
        String rep = intent.getStringExtra("RIPETIZIONI");
        String peso = intent.getStringExtra("PESO");

        txtSerie.setText(serie);
        txtRep.setText(rep);
        txtPeso.setText(peso);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_esercizi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(txt);

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

    protected void onResume(){
        super.onResume();
    }

    //Per tornare indietro a SchedeFragment()
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // goto back activity from here
                Intent intent = new Intent();
                //intent.putExtra("HOME", 1);
                setResult(RESULT_OK, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
