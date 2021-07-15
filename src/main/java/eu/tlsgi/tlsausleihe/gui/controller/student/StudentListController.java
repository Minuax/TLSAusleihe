package eu.tlsgi.tlsausleihe.gui.controller.student;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.form.Form;
import eu.tlsgi.tlsausleihe.logic.student.Student;
import eu.tlsgi.tlsausleihe.utils.date.DateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentListController implements Initializable {

    public ListView<Student> studentList;
    public ComboBox<Form> classComboBox;

    public ImageView studentPicture;

    public Button selectStudentButton;
    public Button backButton;

    public Label nameLabel;
    public Label classLabel;
    public Label birthdayLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Form allClasses = new Form(-1, "Alle anzeigen");

        classComboBox.getItems().add(allClasses);
        classComboBox.setValue(allClasses);

        classComboBox.valueProperty().addListener(new ChangeListener<Form>() {
            @Override
            public void changed(ObservableValue<? extends Form> observable, Form oldValue, Form newValue) {
                if (newValue == allClasses) {
                    studentList.getItems().clear();
                    for (Student student : TLSAusleihe.instance.getStudentHandler().getStudentArrayList()) {
                        studentList.getItems().add(student);
                    }
                    studentList.getSelectionModel().select(studentList.getItems().get(0));
                } else {
                    studentList.getItems().clear();
                    for (Student student : TLSAusleihe.instance.getStudentHandler().getStudentArrayList()) {
                        if (student.getKlasse() == newValue) {
                            studentList.getItems().add(student);
                        }
                    }
                    studentList.getSelectionModel().select(studentList.getItems().get(0));
                }
            }
        });


        for (Student student : TLSAusleihe.instance.getStudentHandler().getStudentArrayList()) {
            studentList.getItems().add(student);
        }

        for (Form form : TLSAusleihe.instance.getFormHandler().getKlasseArrayList()) {
            classComboBox.getItems().add(form);
        }

        studentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue != null) {
                    nameLabel.setText(newValue.getVorname() + " " + newValue.getNachname());
                    birthdayLabel.setText(DateUtil.convertDate(newValue.getGeburtsDatum()));
                    classLabel.setText(newValue.getKlasse().getBezeichnung());
                }
            }
        });

        studentList.getSelectionModel().select(studentList.getItems().get(0));

        selectStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (studentList.getSelectionModel().getSelectedItem() != null) {
                    TLSAusleihe.instance.getLendHandler().setSelectedStudent(studentList.getSelectionModel().getSelectedItem());
                    TLSAusleihe.instance.getFrameHandler().openFrame(TLSAusleihe.instance.getLendHandler().getSourceFrame().equalsIgnoreCase("LendMenu") ? "LendListByS" : TLSAusleihe.instance.getLendHandler().getSourceFrame());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Sie haben keinen Schüler ausgewählt!", ButtonType.OK);
                    alert.setTitle("Fehler: kein Schüler ausgewählt!");
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        alert.close();
                    }
                }
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("StudentMethodSelect");
            }
        });
    }

}
