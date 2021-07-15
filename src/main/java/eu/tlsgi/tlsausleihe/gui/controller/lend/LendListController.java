package eu.tlsgi.tlsausleihe.gui.controller.lend;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.book.Book;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
import eu.tlsgi.tlsausleihe.logic.exemplar.Exemplar;
import eu.tlsgi.tlsausleihe.logic.lend.Lend;
import eu.tlsgi.tlsausleihe.utils.date.DateUtil;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class LendListController implements Initializable {

    public Label lendListLabel;
    public Label nameLabel;
    public Label signaturLabel;
    public Label lendToLabel;
    public Label lendSinceLabel;

    public ListView<Lend> lendList;

    public ComboBox<ClassType> classTypeCombobox;

    public Button backButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lendListLabel.setText("Verliehene Bücher (" + TLSAusleihe.instance.getBookHandler().getBookArrayList().size() + ")");

        lendList.getItems().addAll(TLSAusleihe.instance.getLendHandler().getLendArrayList());

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

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("LendMenu"));

        if (!lendList.getItems().isEmpty()) {
            lendList.getSelectionModel().select(lendList.getItems().get(0));
        }
    }
}
