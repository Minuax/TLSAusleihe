package eu.tlsgi.tlsausleihe.gui.controller.lend;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LendMenuController implements Initializable {

    public Button lendListButton;
    public Button newLendButton;
    public Button returnLendButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lendListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("LendList");
            }
        });

        returnLendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("StudentMethodSelect");
                TLSAusleihe.instance.getLendHandler().setSourceFrame("LendMenu");
            }
        });

        newLendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("LendCreator");
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
