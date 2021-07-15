package eu.tlsgi.tlsausleihe.gui.controller.student;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentSelectController implements Initializable {

    public TextField searchField;

    public ListView<Student> studentList;

    public Button selectButton;
    public Button backButton;

    private ObservableList<Student> studentObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentObservableList = FXCollections.observableList(TLSAusleihe.instance.getStudentHandler().getStudentArrayList());

        studentList.setItems(studentObservableList);

        searchField.setPromptText("Vorname, Nachname oder SchülerID");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<Student> studentArrayList = new ArrayList<>(TLSAusleihe.instance.getStudentHandler().getStudentArrayList());

            String searchFieldText = searchField.getText().toLowerCase().replace(" ", "");
            studentArrayList.removeIf(student -> !((student.getVorname().toLowerCase().contains(searchFieldText)) || (student.getNachname().toLowerCase().contains(searchFieldText)) || (student.getStudentID().toLowerCase().equalsIgnoreCase(searchFieldText))));

            studentObservableList = FXCollections.observableList(studentArrayList);
            studentList.setItems(studentObservableList);
            studentList.refresh();
        });

        studentList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    if (studentList.getSelectionModel().getSelectedItem() != null) {
                        TLSAusleihe.instance.getStudentHandler().setSelectedStudent(studentList.getSelectionModel().getSelectedItem());
                    }
                }
            }
        });

        selectButton.setOnAction(new EventHandler<ActionEvent>() {
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