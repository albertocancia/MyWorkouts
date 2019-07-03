package com.example.bodybuilding;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bodybuilding.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Latitude on 02/10/2016.
 */

public class ProvaAdapter extends ArrayAdapter<Esercizio> {

    Activity mContext;

    LayoutInflater inflater;

    List<Esercizio> listEsercizi;

    private List<Esercizio> nuovaLista = new ArrayList<Esercizio>();


    public ProvaAdapter(Activity context, List<Esercizio> listEsercizi) {

        super(context, R.layout.row, listEsercizi);

        this.mContext = context;
        this.listEsercizi = listEsercizi;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.txtEsercizio = (TextView) convertView.findViewById(R.id.label);
            holder.edSerie = (EditText) convertView.findViewById(R.id.txtSerie);
            holder.edRep = (EditText) convertView.findViewById(R.id.txtRep);
            holder.cbShowName = (CheckBox) convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Esercizio esercizio = listEsercizi.get(position);
        holder.cbShowName.setChecked(false);
        holder.edSerie.setVisibility(View.VISIBLE);
        holder.edRep.setVisibility(View.VISIBLE);
        holder.cbShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbShowName.isChecked()) {
                    String nomeEs = holder.txtEsercizio.getText().toString();
                    int serie = Integer.parseInt(holder.edSerie.getText().toString());
                    int rep = Integer.parseInt(holder.edRep.getText().toString());
                    nuovaLista.add(new Esercizio(nomeEs,serie,rep));

                    Toast.makeText(mContext,"Serie: "+serie+" Rep: "+rep,Toast.LENGTH_SHORT).show();

                } else {
                    //arrayPeople.get(position).setShowName(false);
                    //holder.edSerie.setVisibility(View.INVISIBLE);
                    //holder.edRep.setVisibility(View.INVISIBLE);
                    //arrayPeople.get(position).setName("");

                }
            }
        });

//Fill EditText with the value you have in data source
        holder.txtEsercizio.setText(esercizio.getName());
        holder.edSerie.setId(position);
        //holder.edRep.setId(position);

//we need to update adapter once we finish with editing
        holder.edSerie.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    /*final int position = v.getId();
                    final EditText caption = (EditText) v;
                    listEsercizi.get(position).setRep(Integer.parseInt(caption.getText().toString()));
                    Toast.makeText(mContext,""+listEsercizi.get(position).getRep(),Toast.LENGTH_SHORT).show();*/
                }

            }

        });
        holder.edRep.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    /*final int position = v.getId();
                    final EditText caption = (EditText) v;
                    listEsercizi.get(position).setRep(Integer.parseInt(caption.getText().toString()));
                    Toast.makeText(mContext,""+listEsercizi.get(position).getRep(),Toast.LENGTH_SHORT).show();*/
                }

            }

        });

        return convertView;

    }

    @Override

    public int getCount() {

        return listEsercizi.size();

    }

    static class ViewHolder {
        TextView txtEsercizio;
        EditText edSerie, edRep;
        CheckBox cbShowName;
    }

    public List<Esercizio> nuovaScheda(){
        return nuovaLista;
    }

}