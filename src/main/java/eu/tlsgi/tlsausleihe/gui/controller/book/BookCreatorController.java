package eu.tlsgi.tlsausleihe.gui.controller.book;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.book.Book;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
import eu.tlsgi.tlsausleihe.logic.publisher.Publisher;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BookCreatorController implements Initializable {


    public TextField nameField;
    public TextField isbnField;

    public ComboBox<ClassType> classTypeCombobox;
    public ComboBox<Publisher> publisherCombobox;

    public Button saveChangesButton;
    public Button revertButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classTypeCombobox.setItems(FXCollections.observableArrayList(TLSAusleihe.instance.getClassTypeHandler().getClassTypeArrayList()));
        ClassType classType;
        classTypeCombobox.setValue(classType = new ClassType(-1, "Fachbereich auswählen.", "0"));

        publisherCombobox.setItems(FXCollections.observableArrayList(TLSAusleihe.instance.getPublisherHandler().getPublisherArrayList()));
        publisherCombobox.setValue(new Publisher(-1, "Verlag auswählen"));

        revertButton.setOnAction(event -> {
            nameField.setText("");
            isbnField.setText("");

            classTypeCombobox.setValue(classType);
        });

        saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sind Sie sich sicher, dass Sie dieses Buch anlegen wollen?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Buch anlegen");
                alert.setHeaderText("Buch anlegen?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    if(TLSAusleihe.instance.getBookHandler().getBookByISBN(isbnField.getText()) == null){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("isbn", isbnField.getText());
                        jsonObject.put("titel", nameField.getText());
                        jsonObject.put("fachbereichID", classTypeCombobox.getValue().getClassTypeID());
                        jsonObject.put("verlagID", publisherCombobox.getValue().getPublisherID());

                        try {
                            WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "buch_insert"), new BasicNameValuePair("data", jsonObject.toString())));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        Book newBook = new Book(nameField.getText(), isbnField.getText(), classTypeCombobox.getValue(), publisherCombobox.getValue());
                        TLSAusleihe.instance.getBookHandler().getBookArrayList().add(newBook);
                        TLSAusleihe.instance.getBookHandler().setCurrentBook(newBook);

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Das Buch wurde erfolgreich hinzugefügt. Zur Bücherliste zurückkehren?", ButtonType.YES, ButtonType.NO);
                        a.setTitle("Zu Bücherliste zurückkehren?");
                        a.setHeaderText("Zurück zur Bücherliste");
                        a.showAndWait();

                        if(a.getResult() == ButtonType.YES){
                            TLSAusleihe.instance.getFrameHandler().openFrame("BookList");
                        }else{
                            a.close();
                        }
                    }
                } else {
                    alert.close();
                }
            }
        });

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("BookList"));
    }
}
