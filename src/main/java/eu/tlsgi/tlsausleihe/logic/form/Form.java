package eu.tlsgi.tlsausleihe.logic.form;

public class Form {

    private int id;
    private String bezeichnung;

    public Form(int id, String bezeichnung) {
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
