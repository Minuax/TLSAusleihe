package eu.tlsgi.tlsausleihe.gui.controller.lend;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
import eu.tlsgi.tlsausleihe.logic.lend.Lend;
import eu.tlsgi.tlsausleihe.utils.date.DateUtil;
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

public class LendListBySController implements Initializable {

    public Label lendListLabel;
    public Label nameLabel;
    public Label signaturLabel;
    public Label lendToLabel;
    public Label lendSinceLabel;

    public ListView<Lend> lendList;

    public ComboBox<ClassType> classTypeCombobox;

    public Button returnLendButton;
    public Button backButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lendListLabel.setText("Verliehene Bücher von " + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getVorname().charAt(0) + ". " + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getNachname() + " (" + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getKlasse().getBezeichnung() + ") (" + TLSAusleihe.instance.getBookHandler().getBookArrayList().size() + ")");

        lendList.getItems().addAll(TLSAusleihe.instance.getLendHandler().getLendsByStudent(TLSAusleihe.instance.getLendHandler().getSelectedStudent()));

        ClassType allClasses = new ClassType(-1, "Alle anzeigen", "0");
        classTypeCombobox.setItems(FXCollections.observableArrayList(TLSAusleihe.instance.getClassTypeHandler().getClassTypeArrayList()));
        classTypeCombobox.getItems().add(allClasses);
        classTypeCombobox.setValue(allClasses);
        classTypeCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == allClasses) {
                lendList.getItems().clear();

                for (Lend lend : TLSAusleihe.instance.getLendHandler().getLendArrayList()) {
                    lendList.getItems().add(lend);
                }

                if (!lendList.getItems().isEmpty())
                    lendList.getSelectionModel().select(lendList.getItems().get(0));
                lendList.refresh();
            } else {
                lendList.getItems().clear();

                for (Lend lend : TLSAusleihe.instance.getLendHandler().getLendArrayList()) {
                    if (lend.getExemplar().getBook().getClassType() == newValue) {
                        lendList.getItems().add(lend);
                    }
                }

                if (!lendList.getItems().isEmpty())
                    lendList.getSelectionModel().select(lendList.getItems().get(0));
                lendList.refresh();
            }
        });

        lendList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameLabel.setText("Titel: " + newValue.getExemplar().getBook().getTitle());
                signaturLabel.setText("Signatur: " + newValue.getExemplar().getSignatur());
                lendToLabel.setText("Verliehen an: " + newValue.getStudent().getVorname().charAt(0) + ". " + newValue.getStudent().getNachname());
                lendSinceLabel.setText("Verliehen seit: " + DateUtil.convertDate(newValue.getLendDate()));
            }
        });

        returnLendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (lendList.getSelectionModel().getSelectedItem() != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie dieses Buch wirklich als zurückgegeben markieren?", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Buch zurückgeben?");
                    alert.setHeaderText("Buch zurückgeben");

                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ausleiheID", lendList.getSelectionModel().getSelectedItem().getLendID());

                        try {
                            WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "ausleihe_delete"), new BasicNameValuePair("data", jsonObject.toString())));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        TLSAusleihe.instance.getLendHandler().getLendArrayList().remove(lendList.getSelectionModel().getSelectedItem());
                        lendList.getItems().remove(lendList.getSelectionModel().getSelectedItem());

                        lendListLabel.setText("Verliehene Bücher von " + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getVorname().charAt(0) + ". " + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getNachname() + " (" + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getKlasse().getBezeichnung() + ") (" + TLSAusleihe.instance.getBookHandler().getBookArrayList().size() + ")");

                        if (!lendList.getItems().isEmpty())
                            lendList.getSelectionModel().select(lendList.getItems().get(0));
                        lendList.refresh();
                    } else {
                        alert.close();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Sie haben kein Buch ausgewählt!", ButtonType.OK);
                    alert.setTitle("Kein Buch ausgewählt");
                    alert.setHeaderText("Kein Buch ausgewählt.");

                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        alert.close();
                    }
                }
            }
        });

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("LendMenu"));

        if (!lendList.getItems().isEmpty()) {
            lendList.getSelectionModel().select(lendList.getItems().get(0));
        }
    }
}
