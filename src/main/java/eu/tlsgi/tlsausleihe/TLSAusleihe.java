package eu.tlsgi.tlsausleihe;

import eu.tlsgi.tlsausleihe.gui.FrameHandler;
import eu.tlsgi.tlsausleihe.logic.book.BookHandler;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassTypeHandler;
import eu.tlsgi.tlsausleihe.logic.form.FormHandler;
import eu.tlsgi.tlsausleihe.logic.lend.LendHandler;
import eu.tlsgi.tlsausleihe.logic.student.StudentHandler;
import eu.tlsgi.tlsausleihe.logic.publisher.PublisherHandler;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TLSAusleihe extends Application {

    public static TLSAusleihe instance;

    private Stage stage;

    private FormHandler formHandler;
    private ClassTypeHandler classTypeHandler;
    private PublisherHandler publisherHandler;
    private StudentHandler studentHandler;
    private BookHandler bookHandler;
    private LendHandler lendHandler;

    private FrameHandler frameHandler;

    private boolean admin;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.stage = primaryStage;

        this.formHandler = new FormHandler();
        this.classTypeHandler = new ClassTypeHandler();
        this.publisherHandler = new PublisherHandler();
        this.studentHandler = new StudentHandler();
        this.bookHandler = new BookHandler();
        this.lendHandler = new LendHandler();

        this.frameHandler = new FrameHandler();

        this.frameHandler.openFrame("MainMenu");

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("img/logo.png"));
        primaryStage.show();

        this.admin = true;
    }

    public Stage getStage() {
        return stage;
    }

    public FormHandler getFormHandler() {
        return formHandler;
    }

    public ClassTypeHandler getClassTypeHandler() { return classTypeHandler; }

    public PublisherHandler getPublisherHandler() {
        return publisherHandler;
    }

    public StudentHandler getStudentHandler() {
        return studentHandler;
    }

    public BookHandler getBookHandler() {
        return bookHandler;
    }

    public LendHandler getLendHandler() { return lendHandler; }

    public FrameHandler getFrameHandler() {
        return frameHandler;
    }

    public boolean isAdmin() { return admin; }
}
