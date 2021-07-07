package eu.tlsgi.tlsausleihe.gui;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class FrameHandler {

    public void openFrame(String frameName) throws IOException {
        TLSAusleihe.instance.getStage().setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/" + frameName + ".fxml")))));
    }

}
