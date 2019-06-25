package com.example.bodybuilding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class TimerFragment extends Fragment implements View.OnClickListener{

    private Chronometer chronometer;
    private long pauseOffset = 0;
    private boolean running = false;
    private Button button_start, button_reset, button_pause;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, false);

        button_start = view.findViewById(R.id.button_start);
        button_pause = view.findViewById(R.id.button_pause);
        button_reset = view.findViewById(R.id.button_reset);
        chronometer = view.findViewById(R.id.chronometer);

        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        button_start.setOnClickListener(this);
        button_pause.setOnClickListener(this);
        button_reset.setOnClickListener(this);

        return view;
    }

    public void onClick(View v){
        if(v == view.findViewById(R.id.button_start)){
            if(!running){
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running = true;
            }
        }else if(v == view.findViewById(R.id.button_pause)){
            if(running){
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                running = false;
            }
        }else if(v == view.findViewById(R.id.button_reset)){
            long startTime = SystemClock.elapsedRealtime();
            chronometer.setBase(startTime);
            pauseOffset = 0;
        }
    }
}
