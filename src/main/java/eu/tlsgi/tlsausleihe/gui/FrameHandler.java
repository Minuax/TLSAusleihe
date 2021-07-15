package eu.tlsgi.tlsausleihe.gui;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class FrameHandler {

    public void openFrame(String frameName) {
        try {
            Scene scene = null;
            if(frameName.contains("Book")){
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/book/" + frameName + ".fxml"))));
            }else if(frameName.contains("Exemplar")){
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/book/exemplar/" + frameName + ".fxml"))));
            }else if(frameName.contains("Publisher")) {
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/book/publisher/" + frameName + ".fxml"))));
            }else if(frameName.contains("Student")){
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/student/" + frameName + ".fxml"))));
            }else if(frameName.contains("Lend")){
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/lend/" + frameName + ".fxml"))));
            }else{
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/" + frameName + ".fxml"))));
            }

            scene.getStylesheets().add("/css/style.css");

            TLSAusleihe.instance.getStage().setScene(scene);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
