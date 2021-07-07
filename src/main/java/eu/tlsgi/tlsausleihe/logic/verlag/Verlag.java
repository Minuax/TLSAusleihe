package eu.tlsgi.tlsausleihe.logic.verlag;

public class Verlag {

    private int verlagID;
    private String verlagName;

    public Verlag(int verlagID, String verlagName) {
        this.verlagID = verlagID;
        this.verlagName = verlagName;
    }

    public int getVerlagID() {
        return verlagID;
    }

    public String getVerlagName() {
        return verlagName;
    }
}