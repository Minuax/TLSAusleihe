package eu.tlsgi.tlsausleihe.gui.controller.book.exemplar;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.exemplar.Exemplar;
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

public class ExemplarListController implements Initializable {

    public ListView<Exemplar> exemplarList;

    public Label exemplarLabel;
    public Label signaturLabel;
    public Label lendLabel;

    public Button deleteExemplarButton;
    public Button newExemplarButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TLSAusleihe.instance.isAdmin()) {
            deleteExemplarButton.setDisable(false);
            newExemplarButton.setDisable(false);
        }

        exemplarLabel.setText(TLSAusleihe.instance.getBookHandler().getCurrentBook().getTitle() + " (" + TLSAusleihe.instance.getBookHandler().getCurrentBook().getExemplarArrayList().size() + ")");

        exemplarList.setItems(FXCollections.observableArrayList(TLSAusleihe.instance.getBookHandler().getCurrentBook().getExemplarArrayList()));
        exemplarList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exemplar>() {

            @Override
            public void changed(ObservableValue<? extends Exemplar> observable, Exemplar oldValue, Exemplar newValue) {
                if (newValue != null) {
                    signaturLabel.setText("Signatur: " + newValue.getSignatur());

                    if (TLSAusleihe.instance.getLendHandler().getLenderByExemplar(newValue) == null) {
                        lendLabel.setText("Nicht verliehen");
                    } else {
                        lendLabel.setText("Verliehen an: " + TLSAusleihe.instance.getLendHandler().getLenderByExemplar(newValue).getVorname().charAt(0) + ". " + TLSAusleihe.instance.getLendHandler().getLenderByExemplar(newValue).getNachname());
                    }

                }
            }
        });

        deleteExemplarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (exemplarList.getSelectionModel().getSelectedItem() != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie das Exemplar mit der Signatur '" + exemplarList.getSelectionModel().getSelectedItem().getSignatur() + "' wirklich löschen?", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Exemplar löschen");
                    alert.setHeaderText("Exemplar wirklich löschen?");
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("signatur", exemplarList.getSelectionModel().getSelectedItem().getSignatur());

                        try {
                            WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "exemplar_delete"), new BasicNameValuePair("data", jsonObject.toString())));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        TLSAusleihe.instance.getBookHandler().getCurrentBook().getExemplarArrayList().remove(exemplarList.getSelectionModel().getSelectedItem());
                        exemplarList.getItems().remove(exemplarList.getSelectionModel().getSelectedItem());

                        if (!exemplarList.getItems().isEmpty())
                            exemplarList.getSelectionModel().select(0);
                        exemplarList.refresh();
                    } else {
                        alert.close();
                    }
                }
            }
        });

        newExemplarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("ExemplarCreator");
            }
        });

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("BookList"));

        if (!exemplarList.getItems().isEmpty()) {
            exemplarList.getSelectionModel().select(exemplarList.getItems().get(0));
        }
    }

}
