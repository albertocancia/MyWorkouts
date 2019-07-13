package com.example.bodybuilding;

import android.app.Dialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EserciziActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private Chronometer chronometer;
    private long pauseOffset = 0;
    private boolean running = false;
    private Button button_start, button_reset, button_pause, button_aggiorna;
    private String giornoSettimana, completo, txt, rep, peso, serie;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private HashMap<String, List<String>> listHash;
    String identify;
    List<String> es = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esercizi);
        TextView txtSerie = (TextView)findViewById(R.id.txt_serie);
        TextView txtRep = (TextView)findViewById(R.id.txt_ripetizioni);
        TextView txtPeso = (TextView)findViewById(R.id.txt_peso);
        Intent intent = getIntent();
        txt = intent.getStringExtra("ESERCIZIO");
        serie = intent.getStringExtra("SERIE");
        rep = intent.getStringExtra("RIPETIZIONI");
        peso = intent.getStringExtra("PESO");
        giornoSettimana = intent.getStringExtra("GIORNO");
        completo = intent.getStringExtra("NOMECOMPLETO");

        txtSerie.setText(serie);
        txtRep.setText(rep);
        txtPeso.setText(peso);

        toolbar = findViewById(R.id.tool_bar_esercizi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(txt);

        button_start = findViewById(R.id.button_start);
        button_pause = findViewById(R.id.button_pause);
        button_reset = findViewById(R.id.button_reset);
        chronometer = findViewById(R.id.chronometer);
        button_aggiorna = findViewById(R.id.button_aggiorna);

        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        button_start.setOnClickListener(this);
        button_pause.setOnClickListener(this);
        button_reset.setOnClickListener(this);
        button_aggiorna.setOnClickListener(this);
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
            chronometer.stop();
            chronometer.setBase(startTime);
            pauseOffset = 0;
        }else if(v == findViewById(R.id.button_aggiorna)){
            final Dialog d = new Dialog(this);
            d.setTitle("NumberPicker");
            d.setContentView(R.layout.dialog_aggiorna);
            final Button buttonAdd = d.findViewById(R.id.buttonAdd);
            final Button buttonAnnulla = d.findViewById(R.id.buttonAnnulla);
            final NumberPicker np3 = (NumberPicker) d.findViewById(R.id.numberPicker3);
            np3.setMaxValue(300);
            np3.setMinValue(0);
            np3.setWrapSelectorWheel(false);
            d.show();
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int valore = np3.getValue();
                    readData(new EserciziActivity.FirestoreCallback() {
                        @Override
                        public void onCallback (List < String > list, String id){
                            //controllo per vedere se il giorno scelto è gia presente nel DB

                            //se non è presente aggiungi i corrispondenti esercizi
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            CollectionReference schedeRef = db.collection("Schede");
                            Iterator<String> iterator = list.iterator();
                            String temp;
                            List<String> nuova = new ArrayList<>();
                            while (iterator.hasNext()) {
                                temp = iterator.next();
                                if (!(temp.equals(completo))) {
                                    nuova.add(temp);
                                }else
                                    nuova.add(txt + "_" + serie + "_" + rep + "_" +valore);
                            }
                            schedeRef = db.collection("Schede");
                            schedeRef.document(identify)
                                    .update(
                                            "esercizi", nuova
                                    );
                            ;
                            finish();
                            Intent newIntent = new Intent(EserciziActivity.this, MainActivity.class);
                            startActivity(newIntent);
                        }

                    });
                    d.dismiss();
                }
            });

            buttonAnnulla.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    d.dismiss();
                }
            });






        }
    }
    public void setList(List<String> temp){
        es = temp;
    }
    public void setIdentify(String id){
        identify = id;
    }

    private interface FirestoreCallback{
        void onCallback(List<String> list, String id);
    }
    private void readData(final EserciziActivity.FirestoreCallback firestoreCallback){
        db.collection("Schede").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String uid = (String) document.get("uid");
                        String gg = (String) document.get("giorno");
                        if (user.getUid().equals(uid) && giornoSettimana.equals(gg)) {
                            es = (List<String>) document.get("esercizi");
                            identify = document.getId();
                            break;
                        }
                    }
                    firestoreCallback.onCallback(es, identify);
                }
            }
        });
    }
}
