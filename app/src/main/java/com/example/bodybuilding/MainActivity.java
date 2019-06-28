package com.example.bodybuilding;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
/*import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;*/


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    static final int SUBACTIVITY_NEW_ITEM = 1;
    static final int SUBACTIVITY_NEW_DIET = 2;
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
            startActivityForResult(intent, SUBACTIVITY_NEW_ITEM);
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

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() -> " + data);
        switch (requestCode) {
            case SUBACTIVITY_NEW_ITEM:
                switch (resultCode) {
                    case Activity.RESULT_OK: // add the new todoItem
                        String returnValue = data.getStringExtra("ITEM_TASK");
                        Log.d(TAG, "onActivityResult() -> " + returnValue);
                        SchedeFragment.addNewItem(returnValue);
                        return;
                    case Activity.RESULT_CANCELED: // nothing to do
                        return;
                    default:
                        throw new RuntimeException("Case not implemented");
                }
            case SUBACTIVITY_NEW_DIET:
                switch (resultCode) {
                    case Activity.RESULT_OK: // add the new todoItem
                        String returnValue = data.getStringExtra("DIET_TASK");
                        Log.d(TAG, "onActivityResult() -> " + returnValue);
                        DietaFragment.addNewItem(returnValue);
                        return;
                    case Activity.RESULT_CANCELED: // nothing to do
                        return;
                    default:
                        throw new RuntimeException("Case not implemented");
                }
            default:
                throw new RuntimeException();
        }
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
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                            //MIO
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
