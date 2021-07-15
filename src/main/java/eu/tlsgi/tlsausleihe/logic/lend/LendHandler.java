package eu.tlsgi.tlsausleihe.logic.lend;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.book.Book;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
import eu.tlsgi.tlsausleihe.logic.exemplar.Exemplar;
import eu.tlsgi.tlsausleihe.logic.student.Student;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class LendHandler {

    private ArrayList<Lend> lendArrayList;

    private Student selectedStudent;
    private String sourceFrame;

    public LendHandler() {
        this.lendArrayList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Collections.singletonList(new BasicNameValuePair("action", "get_ausleihe")))));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Lend lend = new Lend(jsonObject.getInt("ausleiheID"), TLSAusleihe.instance.getBookHandler().getExemplarByID(jsonObject.getString("signatur")), TLSAusleihe.instance.getStudentHandler().getStudentByID(jsonObject.getInt("schuelerID") + ""),  new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("verleihDatum")));

                this.lendArrayList.add(lend);
            }
        } catch (IOException | ParseException ioException) {
            ioException.printStackTrace();
        }
    }

    public Lend getLendByID(int lendID) {
        Lend l = null;
        for (Lend lend : this.lendArrayList)
            if (lend.getLendID() == lendID)
                l = lend;
        return l;
    }

    public Lend getLendByExemplar(Exemplar exemplar) {
        Lend l = null;
        for (Lend lend : this.lendArrayList) {
            if (lend.getExemplar() == exemplar) {
                l = lend;
            }
        }
        return l;
    }

    public ArrayList<Lend> getLendsByStudent(Student student) {
        ArrayList<Lend> lends = new ArrayList<>();
        for (Lend lend : this.lendArrayList)
            if (lend.getStudent() == student)
                lends.add(lend);
        return lends;
    }

    public ArrayList<Lend> getLendsByStudent(String studentID) {
        ArrayList<Lend> lends = new ArrayList<>();
        for (Lend lend : this.lendArrayList)
            if (lend.getStudent().getStudentID().equalsIgnoreCase(studentID))
                lends.add(lend);
        return lends;
    }

    public ArrayList<Lend> getLendsByBook(Book book) {
        ArrayList<Lend> lends = new ArrayList<>();

        for (Lend lend : lendArrayList)
            if (lend.getExemplar().getBook() == book)
                lends.add(lend);
        return lends;
    }

    public Student getLenderByExemplar(Exemplar exemplar) {
        Student student = null;
        for (Lend lend : this.lendArrayList)
            if (lend.getExemplar() == exemplar)
                student = lend.getStudent();
        return student;
    }

    public boolean isLend(Exemplar exemplar){
        boolean l = false;
        for(Lend lend : lendArrayList){
            if (lend.getExemplar() == exemplar) {
                l = true;
                break;
            }
        }
        return l;
    }

    public Student getSelectedStudent() { return selectedStudent; }

    public void setSelectedStudent(Student selectedStudent) { this.selectedStudent = selectedStudent; }

    public String getSourceFrame() { return sourceFrame; }

    public void setSourceFrame(String sourceFrame) { this.sourceFrame = sourceFrame; }

    public ArrayList<Lend> getLendArrayList() {
        return lendArrayList;
    }
}
