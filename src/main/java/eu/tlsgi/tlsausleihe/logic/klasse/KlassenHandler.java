package eu.tlsgi.tlsausleihe.logic.klasse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class KlassenHandler {

    private ArrayList<Klasse> klasseArrayList;

    private final String[] klassen = {"BG", "HBFI", "RE", "HS"};

    public KlassenHandler() {
        this.klasseArrayList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            this.klasseArrayList.add(new Klasse(i, new Random().nextInt(20) + klassen[new Random().nextInt(klassen.length)] + "X" + new Random().nextInt(5)));
        }

        Collections.sort(this.klasseArrayList, (s1, s2) -> (s1.getBezeichnung()).compareToIgnoreCase((s2.getBezeichnung())));

        for (Klasse klasse : klasseArrayList) {
            System.out.println(klasse);
        }
    }

    public ArrayList<Klasse> getKlasseArrayList() {
        return klasseArrayList;
    }
}
