package eu.tlsgi.tlsausleihe;

import javafx.application.Application;
import javafx.stage.Stage;

public class TLSAusleihe extends Application {

    public static TLSAusleihe instance;

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.stage = primaryStage;

    }

    public Stage getStage() { return stage; }
}
