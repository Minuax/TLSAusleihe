package eu.tlsgi.tlsausleihe.gui.controller.book;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.book.Book;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
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
import java.util.Collections;
import java.util.ResourceBundle;

public class BookListController implements Initializable {

    public Label bookListLabel;
    public Label nameLabel;
    public Label isbnLabel;
    public Label amountLabel;

    public ListView<Book> bookList;

    public ComboBox<ClassType> classTypeCombobox;

    public Button editBookButton;
    public Button showExemplarsButton;
    public Button deleteBookButton;
    public Button newBookButton;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TLSAusleihe.instance.isAdmin()) {
            editBookButton.setDisable(false);
            newBookButton.setDisable(false);
            deleteBookButton.setDisable(false);
        }

        bookListLabel.setText("Bücherliste (" + TLSAusleihe.instance.getBookHandler().getBookArrayList().size() + ")");

        bookList.getItems().addAll(TLSAusleihe.instance.getBookHandler().getBookArrayList());

        ClassType allClasses = new ClassType(-1, "Alle anzeigen", "0");
        classTypeCombobox.setItems(FXCollections.observableArrayList(TLSAusleihe.instance.getClassTypeHandler().getClassTypeArrayList()));
        classTypeCombobox.getItems().add(allClasses);
        classTypeCombobox.setValue(allClasses);
        classTypeCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == allClasses) {
                bookList.getItems().clear();

                for (Book book : TLSAusleihe.instance.getBookHandler().getBookArrayList()) {
                    bookList.getItems().add(book);
                }

                if (!bookList.getItems().isEmpty())
                    bookList.getSelectionModel().select(bookList.getItems().get(0));
                bookList.refresh();
            } else {
                bookList.getItems().clear();

                for (Book book : TLSAusleihe.instance.getBookHandler().getBookArrayList()) {
                    if (book.getClassType() == newValue) {
                        bookList.getItems().add(book);
                    }
                }

                if (!bookList.getItems().isEmpty())
                    bookList.getSelectionModel().select(bookList.getItems().get(0));
                bookList.refresh();
            }
        });

        bookList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameLabel.setText("Title: " + newValue.getTitle());
                isbnLabel.setText("ISBN: " + newValue.getIsbn());
                amountLabel.setText("Exemplare: " + newValue.getExemplarArrayList().size() + "");
            }
        });

        editBookButton.setOnAction(event -> {
            if (bookList.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sie haben kein Buch ausgewählt!", ButtonType.OK);
                alert.setTitle("Kein Buch ausgewählt!");
                alert.setHeaderText("Bitte wählen Sie ein Buch aus.");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            } else {
                TLSAusleihe.instance.getBookHandler().setCurrentBook(bookList.getSelectionModel().getSelectedItem());

                TLSAusleihe.instance.getFrameHandler().openFrame("BookEditor");
            }
        });

        showExemplarsButton.setOnAction(event -> {
            if (bookList.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sie haben kein Buch ausgewählt!", ButtonType.OK);
                alert.setTitle("Kein Buch ausgewählt!");
                alert.setHeaderText("Bitte wählen Sie ein Buch aus.");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            } else {
                TLSAusleihe.instance.getBookHandler().setCurrentBook(bookList.getSelectionModel().getSelectedItem());
                TLSAusleihe.instance.getFrameHandler().openFrame("ExemplarList");
            }
        });

        deleteBookButton.setOnAction(event -> {
            if (bookList.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sie haben kein Buch ausgewählt!", ButtonType.OK);
                alert.setTitle("Kein Buch ausgewählt!");
                alert.setHeaderText("Bitte wählen Sie ein Buch aus.");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie dieses Buch wirklich löschen? Dadurch gehen auch ALLE Exemplare verloren!", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Buch löschen");
                alert.setHeaderText("Sind Sie sich sicher?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    // TODO: Handle delete
                    if (TLSAusleihe.instance.getLendHandler().getLendsByBook(bookList.getSelectionModel().getSelectedItem()).size() == 0) {
                        handleDelete();
                    } else {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR, "Es sind noch nicht alle Exemplare dieses Buches zurückgegeben worden. Wollen Sie es trotzdem löschen?", ButtonType.YES, ButtonType.NO);
                        alert1.setTitle("Buch löschen");
                        alert1.setHeaderText("Sind Sie sich sicher?");
                        alert1.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            handleDelete();
                        } else {
                            alert1.close();
                        }
                    }
                } else {
                    alert.close();
                }
            }
        });

        newBookButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("BookCreator"));

        backButton.setOnAction(event -> TLSAusleihe.instance.getFrameHandler().openFrame("BookMenu"));

        if (!bookList.getItems().isEmpty()) {
            bookList.getSelectionModel().select(bookList.getItems().get(0));
        }
    }

    public void handleDelete() {
        try {
            for (Exemplar exemplar : bookList.getSelectionModel().getSelectedItem().getExemplarArrayList()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("signatur", exemplar.getSignatur());

                WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "exemplar_delete"), new BasicNameValuePair("data", jsonObject.toString())));
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isbn", bookList.getSelectionModel().getSelectedItem().getIsbn());

            WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Arrays.asList(new BasicNameValuePair("action", "buch_delete"), new BasicNameValuePair("data", jsonObject.toString())));
            TLSAusleihe.instance.getBookHandler().getBookArrayList().remove(bookList.getSelectionModel().getSelectedItem());
            bookList.getItems().remove(bookList.getSelectionModel().getSelectedItem());

            bookListLabel.setText("Bücherliste (" + bookList.getItems().size() + ")");

            if (!bookList.getItems().isEmpty()) {
                bookList.getSelectionModel().select(0);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
