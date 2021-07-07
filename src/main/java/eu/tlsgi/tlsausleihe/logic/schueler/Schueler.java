package eu.tlsgi.tlsausleihe.logic.schueler;

import java.util.Date;

public class Schueler {

    private int schuelerID;
    private String vorname, nachname;
    private Date geburtsDatum;

    public Schueler(int schuelerID, String vorname, String nachname, Date geburtsDatum) {
        this.schuelerID = schuelerID;

        this.vorname = vorname;
        this.nachname = nachname;
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

    public Date getGeburtsDatum() { return geburtsDatum; }
}
