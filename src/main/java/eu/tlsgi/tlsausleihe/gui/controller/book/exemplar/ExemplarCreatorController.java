package eu.tlsgi.tlsausleihe.gui.controller.book.exemplar;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.exemplar.Exemplar;
import eu.tlsgi.tlsausleihe.logic.publisher.Publisher;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ExemplarCreatorController implements Initializable {


    public TextField signatureField;

    public Button saveChangesButton;
    public Button revertButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        revertButton.setOnAction(event -> {
            signatureField.setText("");
        });

        saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sind Sie sich sicher, dass Sie dieses Exemplar anlegen wollen?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Exemplar anlegen");
                alert.setHeaderText("Exemplar anlegen?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    if (TLSAusleihe.instance.getBookHandler().getExemplarByBookAndID(TLSAusleihe.instance.getBookHandler().getCurrentBook(), signatureField.getText()) == null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("isbn", TLSAusleihe.instance.getBookHandler().getCurrentBook().getIsbn());
                        jsonObject.put("signatur", signatureField.getText());

                        try {
                            WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "exemplar_insert"), new BasicNameValuePair("data", jsonObject.toString())));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        Exemplar newExemplar = new Exemplar(signatureField.getText(), TLSAusleihe.instance.getBookHandler().getCurrentBook());
                        TLSAusleihe.instance.getBookHandler().getCurrentBook().getExemplarArrayList().add(newExemplar);

                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Das Exemplar wurde erfolgreich hinzugefügt. Zur Exemplarliste zurückkehren?", ButtonType.YES, ButtonType.NO);
                        a.setTitle("Zu Exemplarliste zurückkehren?");
                        a.setHeaderText("Zurück zur Exemplarliste");
                        a.showAndWait();

                        if (a.getResult() == ButtonType.YES) {
                            TLSAusleihe.instance.getFrameHandler().openFrame("ExemplarList");
                        } else {
                            a.close();
                        }
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Dieses Exemplar existiert bereits. Bitte überprüfen Sie Ihre Angaben.", ButtonType.OK);
                    a.showAndWait();
                }

            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("ExemplarList");
            }
        });
    }
}
