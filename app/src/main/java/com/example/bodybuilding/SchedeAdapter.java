package com.example.bodybuilding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Iterator;
import java.util.List;

public class SchedeAdapter extends FirestoreRecyclerAdapter<Scheda, SchedeAdapter.SchedeHolder> {

    public SchedeAdapter(FirestoreRecyclerOptions<Scheda> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SchedeHolder holder, int position, @NonNull Scheda model) {
        holder.textViewGiorni.setText(model.getGiorno());
        List<String> l = model.getEsercizi();
        Iterator<String> iterator = l.iterator();
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio1.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio2.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio3.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio4.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio5.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio6.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio7.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio8.setText(list);
        }
        if(iterator.hasNext()){
            String list = iterator.next();
            holder.textViewEsercizio9.setText(list);
        }
        /*for(int i = 0; i < l.size(); i++) {
            holder.textViewEsercizio1.append(l.get(i) + "\n");
        }*/
    }

    @NonNull
    @Override
    public SchedeHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schede_item,
                parent, false);
        return new SchedeHolder(v);
    }

    class SchedeHolder extends RecyclerView.ViewHolder{
        TextView textViewGiorni;
        TextView textViewEsercizio1, textViewEsercizio2, textViewEsercizio3 ,textViewEsercizio4,
                textViewEsercizio5, textViewEsercizio6, textViewEsercizio7, textViewEsercizio8, textViewEsercizio9;

        public SchedeHolder(@NonNull View itemView) {
            super(itemView);
            textViewGiorni = itemView.findViewById(R.id.text_view_giorni);
            textViewEsercizio1 = itemView.findViewById(R.id.text_view_esercizio1);
            textViewEsercizio2 = itemView.findViewById(R.id.text_view_esercizio2);
            textViewEsercizio3 = itemView.findViewById(R.id.text_view_esercizio3);
            textViewEsercizio4 = itemView.findViewById(R.id.text_view_esercizio4);
            textViewEsercizio5 = itemView.findViewById(R.id.text_view_esercizio5);
            textViewEsercizio6 = itemView.findViewById(R.id.text_view_esercizio6);
            textViewEsercizio7 = itemView.findViewById(R.id.text_view_esercizio7);
            textViewEsercizio8 = itemView.findViewById(R.id.text_view_esercizio8);
            textViewEsercizio9 = itemView.findViewById(R.id.text_view_esercizio9);
        }
    }
}
