package com.example.bodybuilding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import static android.support.constraint.Constraints.TAG;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference schedeRef = db.collection("Schede");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }


    public int getChildrenCount(int i) {
        return this.listHashMap.get(this.listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = Group Item , i1 = ChildItem
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.parent_layout,null);
        }
        final TextView lblListHeader = (TextView)view.findViewById(R.id.parent_txt);
        ImageView imgView = (ImageView) view.findViewById(R.id.img_delete);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String giorno = lblListHeader.getText().toString();

                Query query = schedeRef.whereEqualTo("giorno", giorno).whereEqualTo("uid", user.getUid());
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                schedeRef.document(document.getId()).delete();
                            }
                        } else {
                            Log.d("ExListAdapter", "Error getting documents: ", task.getException());
                        }
                    }
                });
                context.startActivity(new Intent(new Intent(context, MainActivity.class)));
                Toast.makeText(context, "Scheda eliminata per il giorno: "+giorno,
                        Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        final String childText = (String)getChild(i,i1);

        LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.child_layout,null);
        // ++++++++++++++++ NUOVO ++++++++++++++++++++++++++++++++
        StringTokenizer stToken = new StringTokenizer(childText,"_");
        String nomeEs ="", numSerie ="", numRep = "";
        if(stToken.hasMoreTokens())
            nomeEs = stToken.nextToken();
        if(stToken.hasMoreTokens())
            numSerie = stToken.nextToken();
        if(stToken.hasMoreTokens())
            numRep = stToken.nextToken();

        holder = new ViewHolder();
        holder.txtEsercizio = view.findViewById(R.id.child_txt);
        holder.txtSerie = view.findViewById(R.id.serie_txt1);
        holder.txtRipetizioni = view.findViewById(R.id.rep_txt);
        holder.txtEsercizio.setText(nomeEs);
        holder.txtSerie.setText(numSerie);
        holder.txtRipetizioni.setText(numRep);
        // +++++++++++++++++ FINE NUOVO ++++++++++++++++++++++++++
        TextView txt = view.findViewById(R.id.child_txt);
        holder.txtEsercizio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String esercizio = holder.txtEsercizio.getText().toString();
                String serie = holder.txtSerie.getText().toString();
                String ripetizioni = holder.txtRipetizioni.getText().toString();
                if(!esercizio.equals("")) {
                    Intent intent = new Intent(context, EserciziActivity.class);
                    intent.putExtra("ESERCIZIO", esercizio);
                    intent.putExtra("SERIE", serie);
                    intent.putExtra("RIPETIZIONI", ripetizioni);
                    context.startActivity(intent);
                }
            }
        });
        return view;
    }
    static class ViewHolder {
        TextView txtEsercizio, txtRipetizioni, txtSerie;

    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
