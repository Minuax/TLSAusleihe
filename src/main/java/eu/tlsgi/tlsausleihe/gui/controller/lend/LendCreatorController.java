package eu.tlsgi.tlsausleihe.gui.controller.lend;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.lend.Lend;
import eu.tlsgi.tlsausleihe.utils.date.DateUtil;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LendCreatorController implements Initializable {


    public TextField isbnField;
    public TextField signatureField;

    public DatePicker datePicker;

    public Button selectStudentButton;
    public Button saveChangesButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TLSAusleihe.instance.getLendHandler().getSelectedStudent() != null) {
            selectStudentButton.setText(TLSAusleihe.instance.getLendHandler().getSelectedStudent().getVorname().charAt(0) + ". " + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getNachname() + " (Ändern)");
        }

        selectStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getFrameHandler().openFrame("StudentMethodSelect");
                TLSAusleihe.instance.getLendHandler().setSourceFrame("LendCreator");
            }
        });

        saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TLSAusleihe.instance.getLendHandler().getSelectedStudent() != null) {
                    if (!(isbnField.getText().isEmpty()) && !(signatureField.getText().isEmpty())) {
                        if (TLSAusleihe.instance.getBookHandler().getBookByISBN(isbnField.getText()) != null) {
                            if (TLSAusleihe.instance.getBookHandler().getExemplarByBookAndID(TLSAusleihe.instance.getBookHandler().getBookByISBN(isbnField.getText()), signatureField.getText()) != null) {
                                if (!datePicker.getEditor().getText().isEmpty()) {
                                    if (!TLSAusleihe.instance.getLendHandler().isLend(TLSAusleihe.instance.getBookHandler().getExemplarByID(signatureField.getText()))) {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie das Exemplar '" + signatureField.getText() + "' des Buches '" + TLSAusleihe.instance.getBookHandler().getBookByISBN(isbnField.getText()).getTitle() + "' wirklich an '" + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getVorname().charAt(0) + ". " + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getNachname() + " (" + TLSAusleihe.instance.getLendHandler().getSelectedStudent().getKlasse() + ")", ButtonType.YES, ButtonType.CANCEL);
                                        alert.setHeaderText("Exemplar verleihen?");
                                        alert.setContentText("Sind Sie sich sicher?");

                                        alert.showAndWait();

                                        if (alert.getResult() == ButtonType.YES) {
                                            Lend lend = null;
                                            try {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("verleihDatum", datePicker.getValue().toString());
                                                jsonObject.put("signatur", signatureField.getText());
                                                jsonObject.put("schuelerID", Integer.parseInt(TLSAusleihe.instance.getLendHandler().getSelectedStudent().getStudentID()));

                                                int lendID = Integer.parseInt(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "ausleihe_insert"), new BasicNameValuePair("data", jsonObject.toString())))));
                                                lend = new Lend(lendID, TLSAusleihe.instance.getBookHandler().getExemplarByID(signatureField.getText()), TLSAusleihe.instance.getLendHandler().getSelectedStudent(), DateUtil.convertToDateViaSqlDate(datePicker.getValue()));

                                                TLSAusleihe.instance.getLendHandler().getLendArrayList().add(lend);
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                        }
                                    }else{
                                        Alert a = new Alert(Alert.AlertType.ERROR, "Dieses Buch ist bereits verliehen. Bitte überprüfen Sie Ihre Angaben.", ButtonType.OK);
                                        a.showAndWait();
                                    }
                                } else {
                                    Alert a = new Alert(Alert.AlertType.ERROR, "Sie haben kein Ausleihdatum angegeben. Bitte überprüfen Sie Ihre Angaben.", ButtonType.OK);
                                    a.showAndWait();
                                }
                            } else {
                                Alert a = new Alert(Alert.AlertType.ERROR, "Dieses Exemplar konnte nicht gefunden werden. Bitte überprüfen Sie Ihre Angaben.", ButtonType.OK);
                                a.showAndWait();
                            }
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Dieses Buch konnte nicht gefunden werden. Bitte überprüfen Sie Ihre Angaben.", ButtonType.OK);
                            a.showAndWait();
                        }
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Es sind nicht alle benötigten Felder ausgefüllt. Bitte überprüfen Sie Ihre Angaben.", ButtonType.OK);
                        a.showAndWait();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Sie haben keinen Schüler ausgewählt!. Bitte überprüfen Sie Ihre Angaben.", ButtonType.OK);
                    a.showAndWait();
                }
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TLSAusleihe.instance.getLendHandler().setSelectedStudent(null);
                TLSAusleihe.instance.getLendHandler().setSourceFrame("");
                TLSAusleihe.instance.getFrameHandler().openFrame("LendMenu");
            }
        });
    }
}
