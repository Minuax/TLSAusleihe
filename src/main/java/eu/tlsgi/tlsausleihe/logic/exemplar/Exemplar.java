package eu.tlsgi.tlsausleihe.logic.exemplar;

import eu.tlsgi.tlsausleihe.logic.book.Book;

public class Exemplar {

    private final String signatur;
    private final Book book;

    public Exemplar(String signatur, Book book){
        this.signatur = signatur;
        this.book = book;
    }

    public String getSignatur() {
        return signatur;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return book.getTitle() + "(" + signatur + ")";
    }
}
