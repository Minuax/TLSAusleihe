package eu.tlsgi.tlsausleihe.gui.controller;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentMenuController implements Initializable {

    public Button studentListButton;
    public Button searchStudentButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("StudentList");
            }
        });

        searchStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("StudentSelect");
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("MainMenu");
            }
        });
    }
}
