package eu.tlsgi.tlsausleihe.gui;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class FrameHandler {

    public void openFrame(String frameName) {
        try {
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/" + frameName + ".fxml"))));

            scene.getStylesheets().add("/css/style.css");

            TLSAusleihe.instance.getStage().setScene(scene);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
