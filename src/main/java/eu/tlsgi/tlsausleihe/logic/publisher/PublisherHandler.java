package eu.tlsgi.tlsausleihe.logic.publisher;

import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PublisherHandler {

    private ArrayList<Publisher> publisherArrayList;

    private Publisher currentPublisher;

    public PublisherHandler() {
        this.publisherArrayList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Collections.singletonList(new BasicNameValuePair("action", "get_verlaege")))));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Publisher publisher = new Publisher(jsonObject.getInt("verlagID"), jsonObject.getString("name"));

                this.publisherArrayList.add(publisher);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setCurrentPublisher(Publisher currentPublisher) { this.currentPublisher = currentPublisher; }

    public Publisher getCurrentPublisher() { return currentPublisher; }

    public Publisher getPublisherByID(int publisherID) {
        Publisher p = null;
        for (Publisher publisher : this.publisherArrayList) {
            if (publisher.getPublisherID() == publisherID) {
                p = publisher;
            }
        }

        return p;
    }

    public Publisher getPublisherByName(String name) {
        Publisher p = null;
        for (Publisher publisher : this.publisherArrayList) {
            if (publisher.getPublisherName().equalsIgnoreCase(name)) {
                p = publisher;
            }
        }

        return p;
    }

    public ArrayList<Publisher> getPublisherArrayList() {
        return publisherArrayList;
    }
}
