package com.example.bodybuilding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Iterator;
import java.util.List;

public class HomeFragment extends Fragment {
    String name="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView tv1 = v.findViewById(R.id.txtEmail);
        tv1.setText(getEmail());
        TextView tv2 = v.findViewById(R.id.txtNome);
        tv2.setText(getNome());
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (user != null) {
            db.collection("Utenti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                        Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();
                        DocumentSnapshot ds;
                        ds = iterator.next();
                        //name = (String)ds.get("Nome");
                        name = "prova";
                    }
                }
            });
        }
        name = "provafuori";
        return name;
    }
}
