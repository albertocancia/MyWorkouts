package com.example.bodybuilding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class HomeFragment extends Fragment {
    private TextView txtEmail;
    private TextView txtNome;
    private TextView txtPeso;
    private TextView txtAltezza;
    private Button btn_add_workout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference utentiRef = db.collection("Utenti");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        txtNome = v.findViewById(R.id.txtNome);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtPeso = v.findViewById(R.id.txtPeso);
        txtAltezza = v.findViewById(R.id.txtAltezza);
        btn_add_workout= v.findViewById(R.id.btn_add_workout);

        utentiRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();
                    DocumentSnapshot ds;
                    while (iterator.hasNext()) {
                        ds = iterator.next();
                        String uid = (String) ds.getId();
                        if(user.getUid().equals(uid)) {
                            long peso = (long) ds.get("peso");
                            long altezza = (long) ds.get("altezza");
                            txtNome.setText("Nome: " + (String) ds.get(("nome")));
                            txtEmail.setText("Email: " + (String) user.getEmail());
                            txtPeso.setText("Peso: " + Long.toString(peso));
                            txtAltezza.setText("Altezza: " + Long.toString(altezza));
                        }
                    }
                }
            }
        });

        btn_add_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendarEvent = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", calendarEvent.getTimeInMillis());
                intent.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
                intent.putExtra("title", "Allenamento");
                intent.putExtra("allDay", false);
                intent.putExtra("rule", "FREQ=YEARLY");
                startActivity(intent);

            }
        });

        return v;
    }

    public String getEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email="";
        if (user != null) {
            // Name, email address, and profile photo Url
            email = user.getEmail();
        }
        return email;
    }

    public String getNome(){
        /*FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("Utenti").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        name = (String)document.get("Nome");
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/

        return "";
    }
}
