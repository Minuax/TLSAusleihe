package eu.tlsgi.tlsausleihe.logic.schueler;

import eu.tlsgi.tlsausleihe.logic.klasse.Klasse;

import java.util.Date;

public class Schueler {

    private int schuelerID;
    private String vorname, nachname;
    private Klasse klasse;
    private Date geburtsDatum;

    public Schueler(int schuelerID, String vorname, String nachname, Klasse klasse, Date geburtsDatum) {
        this.schuelerID = schuelerID;

        this.vorname = vorname;
        this.nachname = nachname;
        this.klasse = klasse;
        this.geburtsDatum = geburtsDatum;
    }

    public int getSchuelerID() {
        return schuelerID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public Klasse getKlasse() { return klasse; }

    public Date getGeburtsDatum() { return geburtsDatum; }

    @Override
    public String toString() {
        return vorname + " " + nachname + " (" + klasse.getBezeichnung() + ")";
    }
}
