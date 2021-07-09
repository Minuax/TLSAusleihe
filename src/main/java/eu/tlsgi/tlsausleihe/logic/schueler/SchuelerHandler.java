package eu.tlsgi.tlsausleihe.logic.schueler;

import com.github.javafaker.Faker;
import eu.tlsgi.tlsausleihe.TLSAusleihe;
import eu.tlsgi.tlsausleihe.logic.klasse.Klasse;

import java.util.*;

public class SchuelerHandler {

    private ArrayList<Schueler> schuelerArrayList;

    public SchuelerHandler() {
        this.schuelerArrayList = new ArrayList<>();

        Faker faker = new Faker();

        for (int i = 0; i < 2500; i++) {
            this.schuelerArrayList.add(new Schueler(i, faker.name().firstName(), faker.name().lastName(), TLSAusleihe.instance.getKlassenHandler().getKlasseArrayList().get(new Random().nextInt(TLSAusleihe.instance.getKlassenHandler().getKlasseArrayList().size())), new Date(new Random().nextInt(2030), new Random().nextInt(12), new Random().nextInt(28))));
        }

        Collections.sort(this.schuelerArrayList, (s1, s2) -> (s1.getVorname() + " " + s1.getNachname()).compareToIgnoreCase((s2.getVorname() + " " + s2.getNachname())));

        for (Schueler schueler : this.schuelerArrayList) {
            System.out.println(schueler.getVorname() + " " + schueler.getNachname());
        }
    }

    public ArrayList<Schueler> getSchuelerArrayList() {
        return schuelerArrayList;
    }
}
