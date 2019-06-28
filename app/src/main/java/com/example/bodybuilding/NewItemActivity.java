package com.example.bodybuilding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewItemActivity extends AppCompatActivity {
    EditText editText;


    private final String TAG = "Esercizi";
    ArrayAdapter<String> adapter;            //creazione adapter
    ArrayList<String> eserciziList;  //arraylist di esercizi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        ListView listView = findViewById(R.id.listViewDemo);

        // set the Home button
        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        eserciziList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.textViewList, eserciziList);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Esercizi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                eserciziList.add("Primo");
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();
                    DocumentSnapshot ds;
                    eserciziList.add("Secondo");
                    while (iterator.hasNext()) {
                        ds = iterator.next();
                        eserciziList.add((String)ds.get("Nome"));
                    }
                    eserciziList.add("Terzo");
                }else
                    eserciziList.add("Quarto");
            }
        });
        eserciziList.add("prova1");
        eserciziList.add("prova2");

        listView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // goto back activity from here
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ITEM_TASK", editText.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
