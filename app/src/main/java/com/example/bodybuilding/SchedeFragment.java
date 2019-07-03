package com.example.bodybuilding;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SchedeFragment extends Fragment {
    View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference schedeRef = db.collection("Schede");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private List<String> es = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schede, container, false);

        listView = (ExpandableListView) view.findViewById(R.id.exp_list);
        listHash = new HashMap<>();
        listDataHeader = new ArrayList<>();

        listDataHeader.add("Lunedì");
        listDataHeader.add("Martedì");
        listDataHeader.add("Mercoledì");
        listDataHeader.add("Giovedì");
        listDataHeader.add("Venerdì");
        listDataHeader.add("Sabato");
        listDataHeader.add("Domenica");

        //INIZIALIZZAZIONE HASHMAP
        es.add("");
        listHash.put(listDataHeader.get(0), es);
        listHash.put(listDataHeader.get(1), es);
        listHash.put(listDataHeader.get(2), es);
        listHash.put(listDataHeader.get(3), es);
        listHash.put(listDataHeader.get(4), es);
        listHash.put(listDataHeader.get(5), es);
        listHash.put(listDataHeader.get(6), es);

        schedeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    Iterator<DocumentSnapshot> iterator = myListOfDocuments.iterator();
                    DocumentSnapshot ds;
                    while (iterator.hasNext()) {
                        ds = iterator.next();
                        String uid = (String) ds.get("uid");
                        String gg =(String) ds.get("giorno");
                        List<String> es = (List<String>) ds.get("esercizi");
                        if(user.getUid().equals(uid)) {
                            listHash.put(gg, es);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        listAdapter = new ExpandableListAdapter(this.getContext(), listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        return view;
    }

}
