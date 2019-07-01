package com.example.bodybuilding;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    private Toolbar toolbar;
    private BottomNavigationView bottomNav;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DATABASE
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    public void perform_action(View v) {
        TextView textViewEsercizio1, textViewEsercizio2, textViewEsercizio3 ,textViewEsercizio4,
                textViewEsercizio5, textViewEsercizio6, textViewEsercizio7, textViewEsercizio8, textViewEsercizio9;
        Intent intent;

        switch(v.getId()){
            case R.id.text_view_esercizio1:
                textViewEsercizio1 = findViewById(R.id.text_view_esercizio1);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio1.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio2:
                textViewEsercizio2 = findViewById(R.id.text_view_esercizio2);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio2.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio3:
                textViewEsercizio3 = findViewById(R.id.text_view_esercizio3);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio3.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio4:
                textViewEsercizio4 = findViewById(R.id.text_view_esercizio4);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio4.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio5:
                textViewEsercizio5 = findViewById(R.id.text_view_esercizio5);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio5.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio6:
                textViewEsercizio6 = findViewById(R.id.text_view_esercizio6);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio6.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio7:
                textViewEsercizio7 = findViewById(R.id.text_view_esercizio7);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio7.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio8:
                textViewEsercizio8 = findViewById(R.id.text_view_esercizio8);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio8.getText().toString());
                startActivity(intent);
                break;
            case R.id.text_view_esercizio9:
                textViewEsercizio9 = findViewById(R.id.text_view_esercizio9);
                intent = new Intent(this.getApplicationContext(), EserciziActivity.class);
                intent.putExtra("val_textView", textViewEsercizio9.getText().toString());
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_item){
            Log.d(TAG,"action ADD item has clicked");
            // Explicit intent creation
            Intent intent = new Intent(this.getApplicationContext(), NewItemActivity.class);
            // Start as sub-activity for result
            startActivity(intent);
            return true;
        }else if (id == R.id.action_new_diet) {
            Log.d(TAG,"action ADD diet has clicked");
            // Explicit intent creation
            Intent intent = new Intent(this.getApplicationContext(), NewDietActivity.class);
            // Start as sub-activity for result
            startActivity(intent);
            return true;
        } else if (id == R.id.action_signout) {
            Log.d(TAG,"action SignOut clicked");
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch(menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_scheda:
                            selectedFragment = new SchedeFragment();
                            break;
                        case R.id.nav_dieta:
                            selectedFragment = new DietaFragment();
                            break;
                        case R.id.nav_timer:
                            selectedFragment = new TimerFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            loadLoginView();
        }
        //updateUI(currentUser);
    }


    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {

                        }

                        // ...
                    }
                });
    }

}
