package eu.tlsgi.tlsausleihe.logic.book;

import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.classtype.ClassType;
import eu.tlsgi.tlsausleihe.logic.exemplar.Exemplar;
import eu.tlsgi.tlsausleihe.logic.publisher.Publisher;
import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BookHandler {

    private ArrayList<Book> bookArrayList;

    private Book currentBook;

    public BookHandler() {
        this.bookArrayList = new ArrayList<>();

        try {
            JSONArray bookArray = new JSONArray(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Collections.singletonList(new BasicNameValuePair("action", "get_buecher")))));
            JSONArray exemplarArray = new JSONArray(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Collections.singletonList(new BasicNameValuePair("action", "get_exemplar")))));

            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject jsonObject = bookArray.getJSONObject(i);

                Book book = new Book(jsonObject.getString("titel"), jsonObject.getString("isbn"), TLSAusleihe.instance.getClassTypeHandler().getClassTypeByID(jsonObject.getInt("fachbereichID")), TLSAusleihe.instance.getPublisherHandler().getPublisherByID(jsonObject.getInt("verlagID")));

                this.bookArrayList.add(book);
            }

            for (int i = 0; i < exemplarArray.length(); i++) {
                JSONObject jsonObject = exemplarArray.getJSONObject(i);

                Book book = getBookByISBN(jsonObject.getString("isbn"));

                if (book != null) {
                    book.addExemplar(new Exemplar(jsonObject.getString("signatur"), book));
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public Exemplar getExemplarByBookAndID(Book book, String signatur) {
        Exemplar e = null;
        for (Exemplar exemplar : book.getExemplarArrayList())
            if (exemplar.getSignatur().equalsIgnoreCase(signatur))
                e = exemplar;
        return e;
    }

    public Book getBookByISBN(String isbn) {
        Book b = null;
        for (Book book : this.bookArrayList)
            if (book.getIsbn().equals(isbn))
                b = book;
        return b;
    }

    public ArrayList<Book> getBooksByPublisher(Publisher publisher) {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        for (Book book : this.bookArrayList)
            if (book.getPublisher() == publisher)
                bookArrayList.add(book);
        return bookArrayList;
    }

    public Exemplar getExemplarByID(String id){
        Exemplar exemplar = null;
        for(Book book : this.bookArrayList){
            for(Exemplar e : book.getExemplarArrayList()){
                if(e.getSignatur().equalsIgnoreCase(id)){
                    exemplar = e;
                }
            }
        }

        return exemplar;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public ArrayList<Book> getBookArrayList() {
        return bookArrayList;
    }
}
