package com.example.bodybuilding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class SchedeAdapter extends FirestoreRecyclerAdapter<Scheda, SchedeAdapter.SchedeHolder> {

    public SchedeAdapter(FirestoreRecyclerOptions<Scheda> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SchedeHolder holder, int position, @NonNull Scheda model) {
        holder.textViewGiorni.setText(model.getGiorno());
        List<String> l = model.getEsercizi();
        holder.textViewEsercizi.setText("");
        for(int i = 0; i < l.size(); i++) {
            holder.textViewEsercizi.append(l.get(i) + "\n");
        }
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
        TextView textViewEsercizi;

        public SchedeHolder(@NonNull View itemView) {
            super(itemView);
            textViewGiorni = itemView.findViewById(R.id.text_view_giorni);
            textViewEsercizi = itemView.findViewById(R.id.text_view_esercizi);
        }
    }
}
