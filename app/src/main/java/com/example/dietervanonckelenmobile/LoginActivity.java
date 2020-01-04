package com.example.dietervanonckelenmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends MainActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button button;
    private static final String TAG = "Logging loginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email_input);
        password = findViewById(R.id.login_password_input);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        button = findViewById(R.id.login);
        Log.d(TAG, "onCreate: Login activity rendered");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button) {
                    LoginUser();
                }
            }
        });
    }

    public void LoginUser() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                currentUser = mAuth.getCurrentUser();
                                finish();
                                startActivity(new Intent(getApplicationContext(),
                                        ProfileActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "couldn't login",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Received an exception after trying to login:  " + e.getMessage());
                        }

                    }
                });
    }
}