package com.example.dietervanonckelenmobile;

import android.widget.EditText;

import java.util.Date;

public class UurObject {
    public EditText naam;
    public EditText les;
    public EditText uren;
    public EditText datum;


    public UurObject(EditText naam, EditText les, EditText uren, EditText datum) {
        this.naam = naam;
        this.les = les;
        this.uren = uren;
        this.datum = datum;
    }
}
