package com.example.bodybuilding;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableEserciziAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference schedeRef = db.collection("Schede");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private List<Esercizio> listCost = new ArrayList<Esercizio>();


    public ExpandableEserciziAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
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
            view = inflater.inflate(R.layout.parent_esercizi,null);
        }
        final TextView lblListHeader = (TextView)view.findViewById(R.id.parent_txt);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ExpandableListAdapter.ViewHolder holder;
        final String childText = (String)getChild(i,i1);

        LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.child_layout,null);


        holder = new ExpandableListAdapter.ViewHolder();
        holder.txtEsercizio = view.findViewById(R.id.child_txt);
        holder.txtEsercizio.setText(childText);
        TextView txt = view.findViewById(R.id.child_txt);
        holder.txtEsercizio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String nomeEsercizio = holder.txtEsercizio.getText().toString();
                Toast.makeText(context, "Esercizio: "+nomeEsercizio, Toast.LENGTH_SHORT).show();
                final Dialog d = new Dialog(context);
                d.setTitle("NumberPicker");
                d.setContentView(R.layout.dialog);
                final Button buttonAdd = d.findViewById(R.id.buttonAdd);
                final Button buttonAnnulla = d.findViewById(R.id.buttonAnnulla);
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
                np.setMaxValue(100);
                np.setMinValue(0);
                np.setWrapSelectorWheel(false);
                final NumberPicker np2 = (NumberPicker) d.findViewById(R.id.numberPicker2);
                np2.setMaxValue(100);
                np2.setMinValue(0);
                np2.setWrapSelectorWheel(false);
                final NumberPicker np3 = (NumberPicker) d.findViewById(R.id.numberPicker3);
                np3.setMaxValue(300);
                np3.setMinValue(0);
                np3.setWrapSelectorWheel(false);
                d.show();
                buttonAdd.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int serie = np.getValue();
                        int rep = np2.getValue();
                        int peso = np3.getValue();
                        Esercizio nuovo = new Esercizio(nomeEsercizio,serie,rep,peso);
                        listCost.add(nuovo);

                        d.dismiss();
                    }
                });
                buttonAnnulla.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        d.dismiss();
                    }
                });
            }
        });
        return view;
    }
    static class ViewHolder {
        TextView txtEsercizio;

    }

    public List<Esercizio> getLista(){
        return listCost;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
