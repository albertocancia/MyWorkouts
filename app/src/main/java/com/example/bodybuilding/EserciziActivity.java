package com.example.bodybuilding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class EserciziActivity extends AppCompatActivity {
    private Toolbar toolbar;

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
    }
}
