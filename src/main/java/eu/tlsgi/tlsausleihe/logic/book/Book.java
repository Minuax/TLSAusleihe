package eu.tlsgi.tlsausleihe.logic.book;

import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
import eu.tlsgi.tlsausleihe.logic.exemplar.Exemplar;
import eu.tlsgi.tlsausleihe.logic.publisher.Publisher;

import java.util.ArrayList;

public class Book {

    private String title;
    private final String isbn;
    private final Publisher publisher;
    private ClassType classType;

    private final ArrayList<Exemplar> exemplarArrayList;

    public Book(String title, String isbn, ClassType classType, Publisher publisher) {
        this.title = title;
        this.isbn = isbn;
        this.classType = classType;
        this.publisher = publisher;

        this.exemplarArrayList = new ArrayList<>();
    }

    public void addExemplar(Exemplar exemplar){
        //TODO: Handle Database (addBook)
        this.exemplarArrayList.add(exemplar);
    }

    public void removeExemplar(Exemplar exemplar){
        //TODO: Handle Database (removeBook)
        this.exemplarArrayList.remove(exemplar);
    }
    
    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public ClassType getClassType() { return classType; }

    public Publisher getPublisher() {
        return publisher;
    }
    
    public void setTitle(String title){ this.title = title; }

    public void setClassType(ClassType classType) { this.classType = classType; }

    public ArrayList<Exemplar> getExemplarArrayList() { return exemplarArrayList; }

    @Override
    public String
    toString() {
        return title + " (" + isbn + ")";
    }
}
