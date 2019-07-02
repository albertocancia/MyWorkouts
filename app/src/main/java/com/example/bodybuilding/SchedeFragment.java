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
    private String[] gg_settimana= {"lunedi", "martedi", "mercoledi", "giovedi", "venerdi", "sabato", "domenica"};
    private List<String> es = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schede, container, false);

        listView = (ExpandableListView) view.findViewById(R.id.exp_list);
        listHash = new HashMap<>();
        listDataHeader = new ArrayList<>();

        listDataHeader.add("Lunedi");
        listDataHeader.add("Martedi");
        listDataHeader.add("Mercoledi");
        listDataHeader.add("Giovedi");
        listDataHeader.add("Venerdi");
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

    private void initData() {

        listDataHeader.add("Lunedi");
        listDataHeader.add("Martedi");
        listDataHeader.add("Mercoledi");
        listDataHeader.add("Giovedi");
        listDataHeader.add("Venerdi");
        listDataHeader.add("Sabato");
        listDataHeader.add("Domenica");

        /*schedeRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Scheda scheda = documentSnapshot.toObject(Scheda.class);
                            list.add(scheda);
                        }
                    }
                });*/
        db.collection("Schede").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        listHash.put(gg, es);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        listView.setAdapter(listAdapter);

        /*ArrayList<String> e = new ArrayList<>();
        e.add("panca");
        e.add("croci");
        list.add(new Scheda("qweer", "lunedi", e));
        list.add(new Scheda("qwertyu", "martedi", e));
        list.add(new Scheda("qr", "venerdi", e));
        list.add(new Scheda("qwe", "sabato", e));*/
        /*Iterator<Scheda> iterator = list.iterator();
        Scheda s;
        while(iterator.hasNext()){
            s = iterator.next();
            String giorno = s.getGiorno();
            List<String> esercizi = s.getEsercizi();

            List<String> lunedi = new ArrayList<>();
            List<String> martedi = new ArrayList<>();
            List<String> mercoledi = new ArrayList<>();
            List<String> giovedi = new ArrayList<>();
            List<String> venerdi = new ArrayList<>();
            List<String> sabato = new ArrayList<>();
            List<String> domenica = new ArrayList<>();

            switch(giorno.toLowerCase()){
                case "lunedi":
                    lunedi.add("ciao");
                    break;
                case "martedi":
                    martedi.add("mart");
                    break;
                case "mercoledi":
                    mercoledi.add("mer");
                    break;
                case "giovedi":
                    giovedi.add("gio");
                    break;
                case "venerdi":
                    venerdi.add("ven");
                    break;
                case "sabato":
                    sabato.add("sa");
                    break;
                case "domenica":
                    domenica.add("d");
                    break;
            }

        }
        /*List<String> lunedi = new ArrayList<>();

        List<String> martedi = new ArrayList<>();
        martedi.add("Expandable ListView");
        martedi.add("Google Map");
        martedi.add("Chat Application");
        martedi.add("Firebase ");

        List<String> mercoledi = new ArrayList<>();
        mercoledi.add("Xamarin Expandable ListView");
        mercoledi.add("Xamarin Google Map");
        mercoledi.add("Xamarin Chat Application");
        mercoledi.add("Xamarin Firebase ");

        List<String> giovedi = new ArrayList<>();
        giovedi.add("UWP Expandable ListView");
        giovedi.add("UWP Google Map");
        giovedi.add("UWP Chat Application");
        giovedi.add("UWP Firebase ");

        List<String> venerdi = new ArrayList<>();
        venerdi.add("Venerdi");

        List<String> sabato = new ArrayList<>();
        sabato.add("Sabato");

        List<String> domenica = new ArrayList<>();
        domenica.add("Domenica");*/

        /*listHash.put(listDataHeader.get(0), lunedi);
        listHash.put(listDataHeader.get(1), martedi);
        listHash.put(listDataHeader.get(2), mercoledi);
        listHash.put(listDataHeader.get(3), giovedi);
        listHash.put(listDataHeader.get(4), venerdi);
        listHash.put(listDataHeader.get(5), sabato);
        listHash.put(listDataHeader.get(6), domenica);*/
    }
}
