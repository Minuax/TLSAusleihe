package eu.tlsgi.tlsausleihe.logic.student;

import eu.tlsgi.tlsausleihe.logic.form.Form;

import java.util.Date;

public class Student {

    private String studentID, vorname, nachname;
    private Form form;
    private Date geburtsDatum;

    public Student(String studentID, String vorname, String nachname, Form form, Date geburtsDatum) {
        this.studentID = studentID;

        this.vorname = vorname;
        this.nachname = nachname;
        this.form = form;
        this.geburtsDatum = geburtsDatum;
    }

    public String getStudentID() { return studentID; }

    public String getVorname() { return vorname; }

    public String getNachname() { return nachname; }

    public Form getKlasse() { return form; }

    public Date getGeburtsDatum() { return geburtsDatum; }

    @Override
    public String toString() {
        return vorname + " " + nachname + " (" + form.getBezeichnung() + ")";
    }
}
