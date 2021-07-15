package eu.tlsgi.tlsausleihe.logic.student;

import com.github.javafaker.Faker;
import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentHandler {

    private ArrayList<Student> studentArrayList;

    private Student selectedStudent;

    public StudentHandler() {
        this.studentArrayList = new ArrayList<>();

        try {
            JSONArray studentArray = new JSONArray(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Collections.singletonList(new BasicNameValuePair("action", "get_schueler")))));
            for (int i = 0; i < studentArray.length(); i++) {
                JSONObject jsonObject = studentArray.getJSONObject(i);
                this.studentArrayList.add(new Student(jsonObject.getInt("schuelerID") + "", jsonObject.getString("vorname"), jsonObject.getString("nachname"), TLSAusleihe.instance.getFormHandler().getFormByID(jsonObject.getInt("klassenID")), new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("gebDatum"))));
            }

        } catch (IOException | ParseException ioException) {
            ioException.printStackTrace();
        }

        this.studentArrayList.sort((s1, s2) -> (s1.getVorname() + " " + s1.getNachname()).compareToIgnoreCase((s2.getVorname() + " " + s2.getNachname())));
    }

    public Student getStudentByID(String studentID) {
        Student s = null;

        for (Student student : this.studentArrayList) {
            if (student.getStudentID().equalsIgnoreCase(studentID)) {
                s = student;
            }
        }

        return s;
    }

    public ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }
}
