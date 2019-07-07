package com.example.bodybuilding;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.*;

/**
 * Created by Latitude on 02/10/2016.
 */

public class DietaAdapter extends ArrayAdapter<Pasto> {

    Context mContext;

    LayoutInflater inflater;
    Map<String, Object> pastiMap;
    List<Pasto> listPastiMap;
    List<Pasto> listPasti;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private List<Esercizio> nuovaLista = new ArrayList<Esercizio>();


    public DietaAdapter(Context context, List<Pasto> listPasti) {

        super(context, R.layout.row_spuntino, listPasti);

        this.mContext = context;
        this.listPasti = listPasti;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listPastiMap = listPasti;

    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_spuntino, null);
            holder = new ViewHolder();
            holder.txtPasto = convertView.findViewById(R.id.label);
            holder.cbShowName = convertView.findViewById(R.id.check);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        pastiMap = returnMap(listPastiMap);
        Pasto pasto = listPasti.get(position);
        holder.cbShowName.setChecked(pasto.getActive());
        if(pasto.getActive()){
            holder.txtPasto.setPaintFlags(holder.txtPasto.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtPasto.setTextColor(Color.GRAY);
        }
        holder.cbShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbShowName.isChecked()) {
                    String nomePasto = holder.txtPasto.getText().toString();
                    holder.txtPasto.setPaintFlags(holder.txtPasto.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.txtPasto.setTextColor(Color.GRAY);
                    Iterator<Pasto> it = listPastiMap.iterator();
                    Pasto temp;
                    String tempS;
                    List<Pasto> tempList = new ArrayList<>();
                    while(it.hasNext()){
                        temp = it.next();
                        tempS = temp.getName();
                        if(tempS.equals(nomePasto)){
                            tempList.add(new Pasto(nomePasto,true));
                        }else
                            tempList.add(temp);
                    }
                    listPastiMap = tempList;
                    pastiMap.replace(nomePasto,true);
                    db.collection("Dieta").document(user.getUid())
                            .update(pastiMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                    Toast.makeText(mContext,nomePasto+" completato",Toast.LENGTH_SHORT).show();

                } else {
                    String nomePasto = holder.txtPasto.getText().toString();
                    holder.txtPasto.setPaintFlags(0);
                    holder.txtPasto.setTextColor(Color.BLACK);
                    Iterator<Pasto> it = listPastiMap.iterator();
                    Pasto temp;
                    String tempS;
                    List<Pasto> tempList = new ArrayList<>();
                    while(it.hasNext()){
                        temp = it.next();
                        tempS = temp.getName();
                        if(tempS.equals(nomePasto)){
                            tempList.add(new Pasto(nomePasto,false));
                        }else
                            tempList.add(temp);
                    }
                    listPastiMap = tempList;
                    pastiMap.replace(nomePasto,false);
                    db.collection("Dieta").document(user.getUid())
                            .update(pastiMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }
            }
        });

        holder.txtPasto.setText(pasto.getName());

        return convertView;

    }

    @Override

    public int getCount() {

        return listPasti.size();

    }

    static class ViewHolder {
        TextView txtPasto;
        CheckBox cbShowName;
    }

    public List<Esercizio> nuovaScheda(){
        return nuovaLista;
    }

    public Map<String,Object> returnMap(List<Pasto> listTemp){
        Map<String,Object> mapRet = new HashMap<>();
        Iterator<Pasto> crunchifyIterator = listTemp.iterator();
        Pasto temp;
        while(crunchifyIterator.hasNext()) {
            temp = crunchifyIterator.next();
            mapRet.put(temp.getName(), temp.getActive());
            Log.w("Prova pasti", "Pasto: " + temp.getName());
        }
        return mapRet;
    }

}
