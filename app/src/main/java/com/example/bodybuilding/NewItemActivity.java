package com.example.bodybuilding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    ProvaAdapter adapter;           //creazione adapter
    ArrayList<String> eserciziList;  //arraylist di esercizi
    List<Esercizio> list = new ArrayList<Esercizio>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProvaAdapter adapternuovo;
    String giorno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        giorno = intent.getStringExtra("GIORNO");
        setContentView(R.layout.activity_new_item);// to read the todo string
        ListView listView = findViewById(R.id.list);

        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(giorno);

        // fineeeeeeeeeeeeeeeeeeee


        eserciziList = new ArrayList<String>();
        adapter = new ProvaAdapter(this,list);

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



    }

    //Per tornare indietro a GiorniActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean var;
        switch (item.getItemId()) {
            case android.R.id.home:
                // goto back activity from here
                Intent intent = new Intent();
                //intent.putExtra("HOME", 1);
                setResult(RESULT_OK, intent);
                finish();
                var = true;
                break;
            case R.id.action_confirm:
                List<Esercizio> nuovaLista = adapter.nuovaScheda(); //prendo la lista creata dall'utente
                List<String> eserciziString = new ArrayList<String>();  //creo lista per le stringhe
                Iterator<Esercizio> crunchifyIterator = nuovaLista.iterator();

                while (crunchifyIterator.hasNext()) {
                    eserciziString.add(crunchifyIterator.next().toString()); //aggiungo alla lista di string la stringa completa
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                CollectionReference cr = db.collection("Schede");


                Scheda scheda = new Scheda(user.getUid(),giorno,eserciziString);
                cr.add(scheda);
                Intent newIntent = new Intent(this.getApplicationContext(), MainActivity.class);
                // Start as sub-activity for result
                startActivity(newIntent);
                var = true;
                break;
            default:
                var = super.onOptionsItemSelected(item);
        }
        return var;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.esercizi_menu, menu);
        return true;
    }
}
