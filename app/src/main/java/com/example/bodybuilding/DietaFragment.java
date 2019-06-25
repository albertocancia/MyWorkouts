package com.example.bodybuilding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DietaFragment extends Fragment {
    View view;
    static TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dieta, container, false);

        textView = view.findViewById(R.id.textView);

        return view;
    }

    public static void addNewItem(String result){
        textView.setText(result);
    }
}
