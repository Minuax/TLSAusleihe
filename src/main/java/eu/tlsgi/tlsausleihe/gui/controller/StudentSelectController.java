package eu.tlsgi.tlsausleihe.gui.controller;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.klasse.Klasse;
import eu.tlsgi.tlsausleihe.logic.schueler.Schueler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class StudentSelectController implements Initializable {

    public TextField searchField;
    public ListView<Schueler> studentList;
    public Button continueOrBackButton;
    public ComboBox<Klasse> classComboBox;

    public ImageView studentPicture;

    public Label nameLabel;
    public Label classLabel;
    public Label birthdayLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Klasse allClasses = new Klasse(-1, "Alle anzeigen");

        classComboBox.getItems().add(allClasses);
        classComboBox.setValue(allClasses);

        classComboBox.valueProperty().addListener(new ChangeListener<Klasse>() {
            @Override
            public void changed(ObservableValue<? extends Klasse> observable, Klasse oldValue, Klasse newValue) {
                if (newValue == allClasses) {
                    studentList.getItems().clear();
                    for (Schueler schueler : TLSAusleihe.instance.getSchuelerHandler().getSchuelerArrayList()) {
                        studentList.getItems().add(schueler);
                    }
                    studentList.getSelectionModel().select(studentList.getItems().get(0));
                } else {
                    studentList.getItems().clear();
                    for (Schueler schueler : TLSAusleihe.instance.getSchuelerHandler().getSchuelerArrayList()) {
                        if (schueler.getKlasse() == newValue) {
                            studentList.getItems().add(schueler);
                        }
                    }
                    studentList.getSelectionModel().select(studentList.getItems().get(0));
                }
            }
        });

        for (Schueler schueler : TLSAusleihe.instance.getSchuelerHandler().getSchuelerArrayList()) {
            studentList.getItems().add(schueler);
        }

        for (Klasse klasse : TLSAusleihe.instance.getKlassenHandler().getKlasseArrayList()) {
            classComboBox.getItems().add(klasse);
        }

        studentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Schueler>() {

            @Override
            public void changed(ObservableValue<? extends Schueler> observable, Schueler oldValue, Schueler newValue) {
                if (newValue != null) {
                    nameLabel.setText(newValue.getVorname() + " " + newValue.getNachname());
                    birthdayLabel.setText(simpleDateFormat.format(newValue.getGeburtsDatum()));
                    classLabel.setText(newValue.getKlasse().getBezeichnung());
                }
            }
        });

        studentList.getSelectionModel().select(studentList.getItems().get(0));
    }
}
