package com.example.bodybuilding;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditActivity extends AppCompatActivity {
    final String TAG = "EditActivity";
    private EditText edit_peso;
    private EditText edit_altezza;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference utentiRef = db.collection("Utenti");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int peso, altezza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edit_peso = findViewById(R.id.edit_peso);
        edit_altezza = findViewById(R.id.edit_altezza);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_edit, menu);
        return true;
    }

    //Per tornare indietro a MainActivity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // goto back activity from here
                Intent intent = new Intent();
                //intent.putExtra("HOME", 1);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            // goto back activity from here
            //startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
            case R.id.action_confirm:

                if( !(edit_peso.getText().toString().equals("")) && !(edit_altezza.getText().toString().equals("")) ) {
                    peso = Integer.parseInt(edit_peso.getText().toString());
                    altezza = Integer.parseInt(edit_altezza.getText().toString());
                    utentiRef.document(user.getUid())
                            .update(
                                    "peso", peso,
                                    "altezza", altezza
                            );
                    Intent newIntent = new Intent(this, MainActivity.class);
                    // Start as sub-activity for result
                    startActivity(newIntent);
                    finish();
                }else if( !(edit_peso.getText().toString().equals("")) ) {
                    peso = Integer.parseInt(edit_peso.getText().toString());
                    utentiRef.document(user.getUid())
                            .update(
                                    "peso", peso
                            );
                    Intent newIntent = new Intent(this, MainActivity.class);
                    // Start as sub-activity for result
                    startActivity(newIntent);
                    finish();
                }else if( !(edit_altezza.getText().toString().equals("")) ) {
                    altezza = Integer.parseInt(edit_altezza.getText().toString());
                    utentiRef.document(user.getUid())
                            .update(
                                    "altezza", altezza
                            );
                    Intent newIntent = new Intent(this, MainActivity.class);
                    // Start as sub-activity for result
                    startActivity(newIntent);
                    finish();
                }else{
                    Toast.makeText(this, "Inserire valore per almeno un campo", Toast.LENGTH_SHORT).show();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
