package eu.tlsgi.tlsausleihe;

import eu.tlsgi.tlsausleihe.gui.FrameHandler;
import eu.tlsgi.tlsausleihe.logic.buch.BuchHandler;
import eu.tlsgi.tlsausleihe.logic.klasse.KlassenHandler;
import eu.tlsgi.tlsausleihe.logic.schueler.SchuelerHandler;
import eu.tlsgi.tlsausleihe.logic.verlag.VerlagHandler;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TLSAusleihe extends Application {

    public static TLSAusleihe instance;

    private Stage stage;

    private KlassenHandler klassenHandler;
    private VerlagHandler verlagHandler;
    private SchuelerHandler schuelerHandler;
    private BuchHandler buchHandler;

    private FrameHandler frameHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.stage = primaryStage;

        this.klassenHandler = new KlassenHandler();
        this.verlagHandler = new VerlagHandler();
        this.schuelerHandler = new SchuelerHandler();
        this.buchHandler = new BuchHandler();

        this.frameHandler = new FrameHandler();

        this.frameHandler.openFrame("MainMenu");

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("img/logo.png"));
        primaryStage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public KlassenHandler getKlassenHandler() {
        return klassenHandler;
    }

    public VerlagHandler getVerlagHandler() {
        return verlagHandler;
    }

    public SchuelerHandler getSchuelerHandler() {
        return schuelerHandler;
    }

    public BuchHandler getBuchHandler() {
        return buchHandler;
    }

    public FrameHandler getFrameHandler() {
        return frameHandler;
    }
}
