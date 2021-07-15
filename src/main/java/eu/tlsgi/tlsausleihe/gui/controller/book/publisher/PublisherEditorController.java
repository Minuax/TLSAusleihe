package eu.tlsgi.tlsausleihe.gui.controller.book.publisher;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.book.Book;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
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

public class PublisherEditorController implements Initializable {


    public TextField nameField;
    public TextField isbnField;

    public ComboBox<ClassType> classTypeCombobox;

    public Button saveChangesButton;
    public Button revertButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameField.setText(TLSAusleihe.instance.getBookHandler().getCurrentBook().getTitle());
        isbnField.setText(TLSAusleihe.instance.getBookHandler().getCurrentBook().getIsbn());

        classTypeCombobox.setItems(FXCollections.observableArrayList(TLSAusleihe.instance.getClassTypeHandler().getClassTypeArrayList()));
        classTypeCombobox.setValue(TLSAusleihe.instance.getBookHandler().getCurrentBook().getClassType());

        revertButton.setOnAction(event -> {
            nameField.setText(TLSAusleihe.instance.getBookHandler().getCurrentBook().getTitle());
            isbnField.setText(TLSAusleihe.instance.getBookHandler().getCurrentBook().getIsbn());

            classTypeCombobox.setValue(TLSAusleihe.instance.getBookHandler().getCurrentBook().getClassType());
        });

        saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sind Sie sich sicher, dass Sie dieses Buch bearbeiten wollen? Dies wirkt sich auf ALLE Exemplare aus!", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Buch bearbeiten");
                alert.setHeaderText("Buch bearbeiten?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("isbn", TLSAusleihe.instance.getBookHandler().getCurrentBook().getIsbn());
                    jsonObject.put("titel", nameField.getText());
                    jsonObject.put("verlagID", TLSAusleihe.instance.getBookHandler().getCurrentBook().getPublisher().getPublisherID());

                    try {
                        WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "buch_update"), new BasicNameValuePair("data", jsonObject.toString())));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    Book newBook = new Book(nameField.getText(), TLSAusleihe.instance.getBookHandler().getCurrentBook().getIsbn(), classTypeCombobox.getValue(), TLSAusleihe.instance.getBookHandler().getCurrentBook().getPublisher());
                    TLSAusleihe.instance.getBookHandler().getBookArrayList().remove(TLSAusleihe.instance.getBookHandler().getCurrentBook());
                    TLSAusleihe.instance.getBookHandler().getBookArrayList().add(newBook);
                    TLSAusleihe.instance.getBookHandler().setCurrentBook(newBook);

                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Die Daten wurden erfolgreich geändert. Zur Bücherliste zurückkehren?", ButtonType.YES, ButtonType.NO);
                    a.setTitle("Zu Bücherliste zurückkehren?");
                    a.setHeaderText("Zurück zur Bücherliste");
                    a.showAndWait();

                    if(a.getResult() == ButtonType.YES){
                        TLSAusleihe.instance.getBookHandler().setCurrentBook(null);
                        TLSAusleihe.instance.getFrameHandler().openFrame("PublisherList");
                    }else{
                        a.close();
                    }
                } else {
                    alert.close();
                }
            }
        });

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("PublisherList"));
    }
}
