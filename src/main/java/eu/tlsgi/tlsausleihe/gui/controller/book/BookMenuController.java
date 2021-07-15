package eu.tlsgi.tlsausleihe.gui.controller.book;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class BookMenuController implements Initializable {

    public Button handleBooksButton;
    public Button handlePublishersButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleBooksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("BookList");
            }
        });

        handlePublishersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("PublisherList");
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
