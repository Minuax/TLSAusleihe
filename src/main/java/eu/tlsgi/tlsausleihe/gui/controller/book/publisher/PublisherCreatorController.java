package eu.tlsgi.tlsausleihe.gui.controller.book.publisher;

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
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PublisherCreatorController implements Initializable {


    public TextField nameField;


    public Button saveChangesButton;
    public Button revertButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        revertButton.setOnAction(event -> {
            nameField.setText(TLSAusleihe.instance.getPublisherHandler().getCurrentPublisher().getPublisherName());
        });

        saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sind Sie sich sicher, dass Sie diesen Verlag anlegen wollen?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Verlag anlegen");
                alert.setHeaderText("Verlag anlegen?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    if(TLSAusleihe.instance.getPublisherHandler().getPublisherByName(nameField.getText()) == null){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", nameField.getText());

                        int publisherID = -1;
                        try {
                            publisherID = Integer.parseInt(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "verlag_insert"), new BasicNameValuePair("data", jsonObject.toString())))));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        if(publisherID != -1) {
                            Publisher newPublisher = new Publisher(publisherID, nameField.getText());
                            TLSAusleihe.instance.getPublisherHandler().getPublisherArrayList().add(newPublisher);
                            TLSAusleihe.instance.getPublisherHandler().setCurrentPublisher(newPublisher);

                            Alert a = new Alert(Alert.AlertType.INFORMATION, "Der Verlag wurde erfolgreich hinzugefügt. Zur Verlagliste zurückkehren?", ButtonType.YES, ButtonType.NO);
                            a.setTitle("Zu Verlagliste zurückkehren?");
                            a.setHeaderText("Zurück zur Verlagliste");
                            a.showAndWait();

                            if(a.getResult() == ButtonType.YES){
                                TLSAusleihe.instance.getFrameHandler().openFrame("PublisherList");
                            }else{
                                a.close();
                            }
                        }else{
                            Alert a = new Alert(Alert.AlertType.ERROR, "Fehler beim hinzufügen des Verlages. Bitte überprüfen Sie Ihre Internetverbindung!", ButtonType.OK);
                            a.showAndWait();
                        }

                    }
                } else {
                    alert.close();
                }
            }
        });

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("PublisherList"));
    }
}
