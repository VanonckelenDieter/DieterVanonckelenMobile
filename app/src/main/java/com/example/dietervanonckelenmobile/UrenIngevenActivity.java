package com.example.dietervanonckelenmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UrenIngevenActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText naam;
    private EditText les;
    private EditText uren;
    private EditText datum;
    private Button indienen;
    private String parsedNaam;
    private String parsedLes;
    private String parsedUren;
    private String parsedDatum;
    private String userId;
    private static final String TAG = "Logging write to db";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ureningeven);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();

        naam = findViewById(R.id.naam);
        les = findViewById(R.id.les);
        uren = findViewById(R.id.uren);
        datum = findViewById(R.id.datum);
        indienen = findViewById(R.id.indienen);
        Log.d(TAG, "onCreate: ingeven activity rendered");

        indienen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parsedDatum = datum.getText().toString();
                parsedLes = les.getText().toString();
                parsedNaam = naam.getText().toString();
                parsedUren = uren.getText().toString();
                if (v == indienen) {
                    urenIngeven(parsedNaam, parsedLes, parsedUren, parsedDatum);
                }
            }
        });
    }

    public void urenIngeven(String naam, String les, String uren, String datum) {
        try {
            UurObject nieuwUur = new UurObject(naam, les, uren, datum);
            DatabaseReference nieuwUurRef = mDatabase.child("uren").child(userId).push();
            nieuwUurRef.setValue(nieuwUur);
            Log.d(TAG, "urenIngeven: " + nieuwUur);
            Toast.makeText(this, "De uren zijn opgeslagen", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileActivity.class);
            this.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Received an exception after trying to write data to db :  " + e.getMessage());
        }
    }
}



