package com.example.bodybuilding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected EditText pesoEditText;
    protected EditText nomeEditText;
    protected EditText altezzaEditText;
    protected Button signUpButton;
    private FirebaseAuth mFirebaseAuth;
    private String nome;
    private int peso;
    private int altezza;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Initialize FirebaseAut
        mFirebaseAuth = FirebaseAuth.getInstance();
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        emailEditText = (EditText) findViewById(R.id.emailField);
        pesoEditText = (EditText) findViewById(R.id.txtPeso);
        altezzaEditText = (EditText) findViewById(R.id.txtAltezza);
        nomeEditText = (EditText) findViewById(R.id.txtNome);
        signUpButton = (Button) findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                nome = nomeEditText.getText().toString();
                peso = Integer.parseInt(pesoEditText.getText().toString().trim());
                altezza = Integer.parseInt(altezzaEditText.getText().toString().trim());
                password = password.trim();
                email = email.trim();


                if (password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(R.string.signup_error_message).setTitle(R.string.signup_error_title).setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                CollectionReference cr = db.collection("Utenti");
                                User utente = new User(nome,peso,altezza);
                                cr.document(user.getUid()).set(utente);
                                cr = db.collection("Dieta");
                                Map<String, Object> pasto = new HashMap<>();
                                pasto.put("Colazione", false);
                                pasto.put("Spuntino", false);
                                pasto.put("Pranzo", false);
                                pasto.put("Spuntino2", false);
                                pasto.put("Cena", false);
                                pasto.put("Spuntino3", false);

                                cr.document(user.getUid()).set(pasto);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                builder.setMessage(task.getException().getMessage()).setTitle(R.string.login_error_title).setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });

                }
            }
        });
    }
}
