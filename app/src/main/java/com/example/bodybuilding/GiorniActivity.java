package com.example.bodybuilding;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiorniActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;            //creazione adapter
    List<String> giorniList;
    String[] giorniArray = {"Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato","Domenica"};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giorni);
        listView = findViewById(R.id.list);

        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Giorno di allenamento");

        giorniList = Arrays.asList(giorniArray);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, giorniList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Intent intent = new Intent(v.getContext(), NewItemActivityProva.class);
                String text = giorniArray[position];
                //based on item add info to intent
                intent.putExtra("GIORNO",text);
                startActivity(intent);
            }
        });
    }

    //Per tornare indietro a MainActivity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // goto back activity from here
                Intent intent = new Intent();
                //intent.putExtra("HOME", 1);
                setResult(RESULT_OK, intent);
                finish();
                return true;
                // goto back activity from here
                //startActivity(new Intent(this.getApplicationContext(), MainActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
