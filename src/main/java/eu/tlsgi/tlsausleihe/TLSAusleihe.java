package eu.tlsgi.tlsausleihe;

import eu.tlsgi.tlsausleihe.gui.FrameHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TLSAusleihe extends Application {

    public static TLSAusleihe instance;

    private Stage stage;

    private FrameHandler frameHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.stage = primaryStage;

        this.frameHandler = new FrameHandler();


        this.frameHandler.openFrame("MainMenu");

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public FrameHandler getFrameHandler() {
        return frameHandler;
    }
}
