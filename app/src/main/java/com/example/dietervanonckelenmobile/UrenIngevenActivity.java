package com.example.dietervanonckelenmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class UrenIngevenActivity extends AppCompatActivity {
    private EditText naam;
    private EditText les;
    private EditText uren;
    private EditText datum;
    private Button indienen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ureningeven);
        naam = findViewById(R.id.naam);
        les = findViewById(R.id.les);
        uren = findViewById(R.id.uren);
        datum = findViewById(R.id.datum);
        indienen = findViewById(R.id.indienen);



        indienen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == indienen) {
                    urenIngeven(naam,les,uren,datum);
                }
            }
        });
    }

    public void urenIngeven(EditText naam, EditText les, EditText uren, EditText datum) {
        UurObject nieuwUur = new UurObject(naam, les, uren, datum);
    }
}



