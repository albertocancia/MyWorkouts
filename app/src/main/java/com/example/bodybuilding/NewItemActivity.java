package com.example.bodybuilding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NewItemActivity extends AppCompatActivity {
    final String TAG = "NewItemActivity";
    ArrayAdapter<Esercizio> adapter;           //creazione adapter
    ArrayList<String> eserciziList;  //arraylist di esercizi
    List<Esercizio> list = new ArrayList<Esercizio>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String giorno = intent.getStringExtra("GIORNO");
        setContentView(R.layout.activity_new_item);// to read the todo string
        ListView listView = findViewById(R.id.list);

        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(giorno);

        // fineeeeeeeeeeeeeeeeeeee


        eserciziList = new ArrayList<String>();
        adapter = new EserciziAdapter(this,list);

        db.collection("Esercizi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();
                    DocumentSnapshot ds;
                    while (iterator.hasNext()) {
                        ds = iterator.next();
                        list.add(new Esercizio((String) ds.get("Nome")));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        listView.setAdapter(adapter);

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference cr = db.collection("Schede");

        /*String es[] = {"Prova","prova2","prova3"};
        List<String> esercizi = Arrays.asList(es);
        Scheda scheda = new Scheda(user.getUid(),"Lunedì",esercizi);
        cr.add(scheda);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // goto back activity from here
                startActivity(new Intent(this.getApplicationContext(), GiorniActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private List<Esercizio> getEsercizio() {
        list.add(new Esercizio("Linux"));
        list.add(new Esercizio("Windows7"));
        list.add(new Esercizio("Suse"));

        return list;
    }
}
