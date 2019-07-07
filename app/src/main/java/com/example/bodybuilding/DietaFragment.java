package com.example.bodybuilding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DietaFragment extends Fragment {
    View view;
    static TextView textView;
    DietaAdapter adapter;           //creazione adapter
    List<Pasto> list = new ArrayList<Pasto>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dieta, container, false);

        ListView listView = view.findViewById(R.id.list);
        adapter = new DietaAdapter(this.getContext(),list);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("Dieta").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot ds = task.getResult();
                    list.add(new Pasto("Cena",(boolean)ds.get("Cena")));
                    list.add(new Pasto("Colazione",(boolean)ds.get("Colazione")));
                    list.add(new Pasto("Pranzo",(boolean)ds.get("Pranzo")));
                    list.add(new Pasto("Spuntino",(boolean)ds.get("Spuntino")));
                    list.add(new Pasto("Spuntino2",(boolean)ds.get("Spuntino2")));
                    list.add(new Pasto("Spuntino3",(boolean)ds.get("Spuntino3")));
                    adapter.notifyDataSetChanged();

                }
            }
        });
        listView.setAdapter(adapter);
        return view;
    }

    public static void addNewItem(String result){
        textView.setText(result);
    }
}
