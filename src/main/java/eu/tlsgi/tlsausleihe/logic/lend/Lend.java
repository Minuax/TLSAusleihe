package eu.tlsgi.tlsausleihe.logic.lend;

import eu.tlsgi.tlsausleihe.logic.exemplar.Exemplar;
import eu.tlsgi.tlsausleihe.logic.student.Student;

import java.util.Date;

public class Lend {

    private int lendID;

    private Exemplar exemplar;
    private Student student;
    private Date lendDate;

    public Lend(int lendID, Exemplar exemplar, Student student, Date lendDate) {
        this.lendID = lendID;
        this.exemplar = exemplar;
        this.student = student;
        this.lendDate = lendDate;
    }

    public int getLendID() { return lendID; }

    public Exemplar getExemplar() { return exemplar; }

    public Student getStudent() { return student; }

    public Date getLendDate() { return lendDate; }

    @Override
    public String toString() {
        return exemplar.getBook().getTitle() + ", " + student.getVorname().charAt(0) + ". " + student.getNachname() + " (" + student.getKlasse() + ")";
    }
}
