package com.example.bodybuilding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView tv1 = v.findViewById(R.id.txtNome);
        tv1.setText(getName());
        return v;
    }

    public String getName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name="";
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getEmail();
        }
        return name;
    }
}
