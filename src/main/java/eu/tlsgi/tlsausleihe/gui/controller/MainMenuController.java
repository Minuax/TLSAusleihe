package eu.tlsgi.tlsausleihe.gui.controller;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public Button studentMenuButton;
    public Button bookMenuButton;
    public Button teacherMenuButton;
    public Button adminMenuButton;
    public Button logoutButton;
    public Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("StudentMenu");
            }
        });

        bookMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("BookMenu");
            }
        });

        teacherMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("TeacherMenu");
            }
        });

        adminMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("AdminMenu");
            }
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: Handle logout
                System.out.println("Handle logout..");
            }
        });

        closeButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie das Programm wirklich schließen?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Programm schließen");
            alert.setHeaderText("Programm schließen?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                System.exit(1337);
            } else {
                alert.close();
            }
        });
    }
}
