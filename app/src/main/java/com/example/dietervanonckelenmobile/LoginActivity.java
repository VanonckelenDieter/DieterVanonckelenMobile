package com.example.dietervanonckelenmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    CheckBox rememberLogin;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button button;
    SharedPreferences preferences;
    private static final String TAG = "Logging loginActivity";
    SharedPreferences.Editor editor;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email_input);
        password = findViewById(R.id.login_password_input);
        rememberLogin = findViewById(R.id.RememberLogin);
        reset = findViewById(R.id.resetPw);
        rememberLogin.setChecked(true);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        button = findViewById(R.id.login);
        Log.d(TAG, "onCreate: Login activity rendered");

        preferences = getSharedPreferences("myApp", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String prefEmail = preferences.getString(getString(R.string.preferencesEmail), "");
        String prefPassword = preferences.getString(getString(R.string.preferencesPassword), "");
        email.setText(prefEmail);
        password.setText(prefPassword);

        reset.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         FirebaseAuth auth = FirebaseAuth.getInstance();

                                         auth.sendPasswordResetEmail(prefEmail)
                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task) {
                                                         if (task.isSuccessful()) {
                                                             Log.d(TAG, "Email sent.");
                                                             Toast.makeText(LoginActivity.this, "Rest link is send to your e-mail, also check SPAM folder.",
                                                                     Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 });

                                     }
                                 }

        );
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
                                if (rememberLogin.isChecked()) {
                                    editor.putString(getString(R.string.preferencesEmail), Email);
                                    editor.commit();
                                    editor.putString(getString(R.string.preferencesPassword), Password);
                                    editor.commit();
                                } else {
                                    editor.putString(getString(R.string.preferencesEmail), "");
                                    editor.commit();
                                    editor.putString(getString(R.string.preferencesPassword), "");
                                    editor.commit();
                                }

                                Log.i(TAG, "Shared Preferences saved");

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