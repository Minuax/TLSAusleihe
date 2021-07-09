package eu.tlsgi.tlsausleihe.logic.klasse;

public class Klasse {

    private int id;
    private String bezeichnung;

    public Klasse(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    public int getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public String toString() {
        return bezeichnung;
    }
}
