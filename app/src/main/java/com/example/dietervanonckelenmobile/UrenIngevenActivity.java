package com.example.dietervanonckelenmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UrenIngevenActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText naam;
    private EditText les;
    private EditText uren;
    private EditText datum;
    private Button indienen;
    private String parsedNaam;
    private String parsedLes;
    private String parsedUren;
    private String parsedDatum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ureningeven);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        naam = findViewById(R.id.naam);
        les = findViewById(R.id.les);
        uren = findViewById(R.id.uren);
        datum = findViewById(R.id.datum);
        indienen = findViewById(R.id.indienen);
        parsedDatum = datum.toString();
        parsedLes = les.toString();
        parsedNaam = naam.toString();
        parsedUren = uren.toString();



        indienen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == indienen) {
                    urenIngeven(parsedNaam, parsedLes, parsedUren, parsedDatum);
                }
            }
        });
    }

    public void urenIngeven(String naam, String les, String uren, String datum) {
        UurObject nieuwUur = new UurObject(naam, les, uren, datum);
        mDatabase.child("uren").setValue(nieuwUur);
    }
}



