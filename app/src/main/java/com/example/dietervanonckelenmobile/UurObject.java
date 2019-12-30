package com.example.dietervanonckelenmobile;


public class UurObject {
    private String naam;
    private String les;
    private String uren;
    private String datum;
    private int positie;


    // Plain Old Java Object

    public UurObject() {

    }

    public UurObject(int positie) {
        this.positie = positie;
    }

    public UurObject(String naam, String les, String uren, String datum) {
        if (naam.trim().equals("")) {
            naam = "Geen naam";
        }
        this.naam = naam;
        this.les = les;
        this.uren = uren;
        this.datum = datum;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getLes() {
        return les;
    }

    public void setLes(String les) {
        this.les = les;
    }

    public String getUren() {
        return uren;
    }

    public void setUren(String uren) {
        this.uren = uren;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
