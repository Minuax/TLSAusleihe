package eu.tlsgi.tlsausleihe.logic.buch;

import eu.tlsgi.tlsausleihe.logic.verlag.Verlag;

public class Buch {

    private String titel, isbn;
    private Verlag verlag;

    public Buch(String titel, String isbn, Verlag verlag) {
        this.titel = titel;
        this.isbn = isbn;
        this.verlag = verlag;
    }

    public String getTitel() {
        return titel;
    }

    public String getIsbn() {
        return isbn;
    }

    public Verlag getVerlag() {
        return verlag;
    }
}
