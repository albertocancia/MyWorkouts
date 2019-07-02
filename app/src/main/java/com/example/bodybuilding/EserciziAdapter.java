package com.example.bodybuilding;


import java.util.List;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class EserciziAdapter extends ArrayAdapter<Esercizio> implements TextWatcher {

    private final List<Esercizio> list;
    private final Activity context;
    int listPosititon;

    public EserciziAdapter(Activity context, List<Esercizio> list) {
        super(context, R.layout.row, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
        protected EditText address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listPosititon = position;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.row, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.label);
            viewHolder.checkbox = (CheckBox) convertView
                    .findViewById(R.id.check);
            viewHolder.address = (EditText) convertView
                    .findViewById(R.id.txtAddress);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            int getPosition = (Integer) buttonView.getTag();
//Here we get the position that we have  set for the checkbox using setTag.
                            list.get(getPosition).setSelected(buttonView.isChecked());
// Set the value of checkbox to maintain its state.
                        }
                    });
            viewHolder.address.addTextChangedListener(this);

            convertView.setTag(viewHolder);
            convertView.setTag(R.id.label, viewHolder.text);
            convertView.setTag(R.id.check, viewHolder.checkbox);
            convertView.setTag(R.id.txtAddress, viewHolder.address);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.text.setText(list.get(position).getName());
        viewHolder.checkbox.setChecked(list.get(position).isSelected());
        /*if (list.get(position).getAddress() != null) {
            viewHolder.address.setText(list.get(position).getAddress() + "");
        } else {
            viewHolder.address.setText("");
        }*/

        return convertView;
    }

    @Override
    public void afterTextChanged(Editable s) {
        //list.get(listPosititon).setAddress(s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }
}
