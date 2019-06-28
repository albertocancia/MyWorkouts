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


        // set the Home button
        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //+++++++++++++++ INIZIO CODICE UGUALE ++++++++++++++++++
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++


        ListView listView = findViewById(R.id.listViewDemo);   //oggetto listView collegato a listViewDemo di activity_new_item.xml
        eserciziList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.textViewList, eserciziList);  //assegno all'adapter l'arraylist eserciziList

        FirebaseFirestore db = FirebaseFirestore.getInstance(); //collegamento a firestore

        db.collection("Esercizi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                eserciziList.add("Primo");  //per vedere fin dove funziona
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();   //prendo tutti i documenti della raccolta esercizi
                    Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();   //uso Iterator per scorrerli uno ad uno
                    DocumentSnapshot ds;
                    eserciziList.add("Secondo");  //per vedere fin dove funziona
                    while (iterator.hasNext()) {    //finchè ci sono documentSnapshot
                        ds = iterator.next();
                        eserciziList.add((String)ds.get("Nome"));  //scrivi il valore del campo nome nell'arraylist
                    }
                    eserciziList.add("Terzo");   //per vedere fin dove funziona
                }else
                    eserciziList.add("Quarto");  //per vedere fin dove funziona
            }
        });
        eserciziList.add("Fuori funzione");

        listView.setAdapter(adapter);

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++ FINE CODICE UGUALE ++++++++++++++++
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
