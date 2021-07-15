package eu.tlsgi.tlsausleihe.gui.controller.book.publisher;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.book.Book;
import eu.tlsgi.tlsausleihe.logic.publisher.Publisher;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class PublisherListController implements Initializable {

    public Label nameLabel;

    public ListView<Publisher> publisherList;

    public Button editPublisherButton;
    public Button deletePublisherButton;
    public Button newPublisherButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        publisherList.setItems(FXCollections.observableArrayList(TLSAusleihe.instance.getPublisherHandler().getPublisherArrayList()));
        publisherList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Publisher>() {

            @Override
            public void changed(ObservableValue<? extends Publisher> observable, Publisher oldValue, Publisher newValue) {
                if (newValue != null) {
                    nameLabel.setText("Name: " + newValue.getPublisherName());
                }
            }
        });

        editPublisherButton.setOnAction(event -> {
            if (publisherList.getSelectionModel().getSelectedItem() != null) {
                TLSAusleihe.instance.getPublisherHandler().setCurrentPublisher(publisherList.getSelectionModel().getSelectedItem());
                TLSAusleihe.instance.getFrameHandler().openFrame("PublisherEditor");
            }
        });

        deletePublisherButton.setOnAction(event -> {
            if (publisherList.getSelectionModel().getSelectedItem() != null) {
                if (TLSAusleihe.instance.getBookHandler().getBooksByPublisher(publisherList.getSelectionModel().getSelectedItem()).isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie den Verlag '" + publisherList.getSelectionModel().getSelectedItem().getPublisherName() + "' wirklich löschen?", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Verlag löschen");
                    alert.setHeaderText("Verlag wirklich löschen?");
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("verlagID", publisherList.getSelectionModel().getSelectedItem().getPublisherID());

                        try {
                            WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "verlag_delete"), new BasicNameValuePair("data", jsonObject.toString())));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        TLSAusleihe.instance.getPublisherHandler().getPublisherArrayList().remove(publisherList.getSelectionModel().getSelectedItem());
                        publisherList.getItems().remove(publisherList.getSelectionModel().getSelectedItem());

                        if (!publisherList.getItems().isEmpty())
                            publisherList.getSelectionModel().select(0);
                        publisherList.refresh();
                    }else{
                        alert.close();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Der Verlag '" + publisherList.getSelectionModel().getSelectedItem().getPublisherName() + "' kann nicht gelöscht werden, da noch Bücher auf ihn registriert sind.", ButtonType.OK);
                    alert.setTitle("Fehler beim löschen des Verlages");
                    alert.setHeaderText("Verlag kann nicht gelöscht werden!");
                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK){
                        alert.close();
                    }
                }
            }
        });

        newPublisherButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("PublisherCreator"));

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("BookMenu"));

        if (!TLSAusleihe.instance.getPublisherHandler().getPublisherArrayList().isEmpty()) {
            publisherList.getSelectionModel().select(TLSAusleihe.instance.getPublisherHandler().getPublisherArrayList().get(0));
        }
    }

}
