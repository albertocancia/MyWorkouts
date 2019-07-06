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
    SchedaAdapter adapter;           //creazione adapter
    ArrayList<String> eserciziList;  //arraylist di esercizi
    List<Esercizio> list = new ArrayList<Esercizio>();
    List<Esercizio> tempList = new ArrayList<Esercizio>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String giorno;
    List<String> giorni;    //lista usata per sapere quali giorni hanno gia un allenamento

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
        adapter = new SchedaAdapter(this,list);

        db.collection("Esercizi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();
                    DocumentSnapshot ds;
                    while (iterator.hasNext()) {
                        ds = iterator.next();
                        tempList.add(new Esercizio((String) ds.get("Nome"),(String)ds.get("Categoria")));

                        adapter.notifyDataSetChanged();
                    }
                    Iterator<Esercizio> iteratorLista = tempList.iterator();
                    List<Esercizio> tempPetto = new ArrayList<Esercizio>();
                    List<Esercizio> tempGambe = new ArrayList<Esercizio>();
                    List<Esercizio> tempDorsali = new ArrayList<Esercizio>();
                    List<Esercizio> tempTricipiti = new ArrayList<Esercizio>();
                    List<Esercizio> tempBicipiti = new ArrayList<Esercizio>();
                    List<Esercizio> tempAddome = new ArrayList<Esercizio>();
                    Esercizio tempEsercizio;
                    while(iteratorLista.hasNext()){
                        tempEsercizio = iteratorLista.next();
                        if(tempEsercizio.getCategoria().equals("Petto")){
                            tempPetto.add(tempEsercizio);
                        }else if(tempEsercizio.getCategoria().equals("Gambe")){
                            tempGambe.add(tempEsercizio);
                        }else if(tempEsercizio.getCategoria().equals("Dorsali")){
                            tempDorsali.add(tempEsercizio);
                        }else if(tempEsercizio.getCategoria().equals("Tricipiti")){
                            tempTricipiti.add(tempEsercizio);
                        }else if(tempEsercizio.getCategoria().equals("Bicipiti")){
                            tempBicipiti.add(tempEsercizio);
                        }else if(tempEsercizio.getCategoria().equals("Addominali")){
                            tempAddome.add(tempEsercizio);
                        }
                    }
                    list.addAll(tempPetto);
                    list.addAll(tempGambe);
                    list.addAll(tempDorsali);
                    list.addAll(tempTricipiti);
                    list.addAll(tempBicipiti);
                    list.addAll(tempAddome);
                    adapter.notifyDataSetChanged();
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
                giorni = new ArrayList<>();
                //recupero giorni con allenamento dal DB

                readData(new FirestoreCallback() {
                    @Override
                    public void onCallback(List<String> list) {
                        Log.d(TAG, list.toString());
                        //controllo per vedere se il giorno scelto è gia presente nel DB
                        boolean controllo = false;
                        if(!giorni.isEmpty() && giorni.contains(giorno))
                            controllo = true;

                        //se non è presente aggiungi i corrispondenti esercizi
                        if(!controllo) {
                            List<Esercizio> nuovaLista = adapter.nuovaScheda(); //prendo la lista creata dall'utente
                            List<String> eserciziString = new ArrayList<String>();  //creo lista per le stringhe
                            Iterator<Esercizio> crunchifyIterator = nuovaLista.iterator();

                            while (crunchifyIterator.hasNext()) {
                                eserciziString.add(crunchifyIterator.next().toString()); //aggiungo alla lista di string la stringa completa
                            }
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            CollectionReference cr = db.collection("Schede");

                            Scheda scheda = new Scheda(user.getUid(), giorno, eserciziString);
                            cr.add(scheda);
                            //Intent intent = new Intent();
                            //setResult(RESULT_OK, intent);
                            finish();
                            Intent newIntent = new Intent(NewItemActivity.this, MainActivity.class);
                            // Start as sub-activity for result
                            startActivity(newIntent);
                        }else{ //altrimenti messaggio di errore
                            Toast.makeText(NewItemActivity.this, "Scheda già presente per "+giorno,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                var = true;
                break;
            default:
                var = super.onOptionsItemSelected(item);
        }
        return var;
    }

    //prendo i dati dal db e richiamo firestoreCallback.onCallback(giorni)
    //onCallback aggiunge gli esercizi o mostra il messaggio di errore
    private void readData(final FirestoreCallback firestoreCallback){
        db.collection("Schede").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Scheda> list = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Scheda s = document.toObject(Scheda.class);
                        list.add(s);
                        String uid = s.getUid();
                        if(user.getUid().equals(uid)) {
                            giorni.add(s.getGiorno());
                        }
                    }
                    firestoreCallback.onCallback(giorni);
                }
            }
        });
    }

    //interfaccia utilizzata per usare i dati recuperati
    //dal db al di fuori di onComplete
    private interface FirestoreCallback{
        void onCallback(List<String> list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.esercizi_menu, menu);
        return true;
    }
}
