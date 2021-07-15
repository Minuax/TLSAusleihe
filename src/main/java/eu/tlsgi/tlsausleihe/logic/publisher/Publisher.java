package eu.tlsgi.tlsausleihe.logic.publisher;

public class Publisher {

    private int publisherID;
    private String publisherName;

    public Publisher(int publisherID, String publisherName) {
        this.publisherID = publisherID;
        this.publisherName = publisherName;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public String getPublisherName() {
        return publisherName;
    }

    @Override
    public String toString() {
        return publisherName;
    }
}