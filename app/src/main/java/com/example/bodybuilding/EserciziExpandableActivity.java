package com.example.bodybuilding;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EserciziExpandableActivity extends AppCompatActivity {
    final String TAG = "NewItemActivity";
    private ExpandableEserciziAdapter adapter;           //creazione adapter
    ArrayList<String> eserciziList;  //arraylist di esercizi
    List<Esercizio> listCost = new ArrayList<Esercizio>();
    List<Esercizio> tempList = new ArrayList<Esercizio>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ExpandableListView listView;
    private HashMap<String, List<String>> listHash;
    String giorno;
    List<String> giorni;    //lista usata per sapere quali giorni hanno gia un allenamento
    private List<String> listDataHeader;
    private List<String> es = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        giorno = intent.getStringExtra("GIORNO");
        setContentView(R.layout.activity_esercizi_expandable);// to read the todo string
        listView = findViewById(R.id.exp_list);
        listHash = new HashMap<>();
        listDataHeader = new ArrayList<>();

        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(giorno);


        listDataHeader.add("PETTO");
        listDataHeader.add("GAMBE");
        listDataHeader.add("DORSALI");
        listDataHeader.add("SPALLE");
        listDataHeader.add("BICIPITI");
        listDataHeader.add("TRICIPITI");
        listDataHeader.add("ADDOMINALI");

        es.add("");
        listHash.put(listDataHeader.get(0), es);
        listHash.put(listDataHeader.get(1), es);
        listHash.put(listDataHeader.get(2), es);
        listHash.put(listDataHeader.get(3), es);
        listHash.put(listDataHeader.get(4), es);
        listHash.put(listDataHeader.get(5), es);
        listHash.put(listDataHeader.get(6), es);

        // fineeeeeeeeeeeeeeeeeeee
        final Context context = this;
        eserciziList = new ArrayList<String>();
        adapter = new ExpandableEserciziAdapter(this, listDataHeader, listHash);

        db.collection("Esercizi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();
                    DocumentSnapshot ds;

                    while (iterator.hasNext()) {
                        ds = iterator.next();
                        String nome = (String)ds.get("Nome");
                        String categoria = (String)ds.get("Categoria");
                        tempList.add(new Esercizio(nome,categoria));
                    }
                    Iterator<Esercizio> iteratorLista = tempList.iterator();
                    List<String> tempPetto = new ArrayList<>();
                    List<String> tempGambe = new ArrayList<>();
                    List<String> tempSpalle = new ArrayList<>();
                    List<String> tempDorsali = new ArrayList<>();
                    List<String> tempBicipiti = new ArrayList<>();
                    List<String> tempTricipiti = new ArrayList<>();
                    List<String> tempAddome = new ArrayList<>();
                    Esercizio tempEsercizio;
                    while(iteratorLista.hasNext()){
                        tempEsercizio = iteratorLista.next();
                        if(tempEsercizio.getCategoria().equals("Petto")){
                            tempPetto.add(tempEsercizio.getName());
                        }else if(tempEsercizio.getCategoria().equals("Gambe")){
                            tempGambe.add(tempEsercizio.getName());
                        }else if(tempEsercizio.getCategoria().equals("Dorsali")){
                            tempDorsali.add(tempEsercizio.getName());
                        }else if(tempEsercizio.getCategoria().equals("Tricipiti")){
                            tempTricipiti.add(tempEsercizio.getName());
                        }else if(tempEsercizio.getCategoria().equals("Bicipiti")){
                            tempBicipiti.add(tempEsercizio.getName());
                        }else if(tempEsercizio.getCategoria().equals("Addominali")){
                            tempAddome.add(tempEsercizio.getName());
                        }else if(tempEsercizio.getCategoria().equals("Spalle")){
                            tempSpalle.add(tempEsercizio.getName());
                        }
                    }
                    listHash.put("PETTO", tempPetto);
                    listHash.put("DORSALI", tempDorsali);
                    listHash.put("SPALLE", tempSpalle);
                    listHash.put("GAMBE", tempGambe);
                    listHash.put("ADDOMINALI", tempAddome);
                    listHash.put("TRICIPITI", tempTricipiti);
                    listHash.put("BICIPITI", tempBicipiti);
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

                readData(new EserciziExpandableActivity.FirestoreCallback() {
                    @Override
                    public void onCallback(List<String> list) {
                        Log.d(TAG, list.toString());
                        //controllo per vedere se il giorno scelto è gia presente nel DB
                        boolean controllo = false;
                        if(!giorni.isEmpty() && giorni.contains(giorno))
                            controllo = true;

                        //se non è presente aggiungi i corrispondenti esercizi
                        if(!controllo) {
                            List<Esercizio> nuovaLista = adapter.getLista();
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
                            Intent newIntent = new Intent(EserciziExpandableActivity.this, MainActivity.class);
                            // Start as sub-activity for result
                            startActivity(newIntent);
                        }else{ //altrimenti messaggio di errore
                            Toast.makeText(EserciziExpandableActivity.this, "Scheda già presente per "+giorno,
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
    private void readData(final EserciziExpandableActivity.FirestoreCallback firestoreCallback){
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
