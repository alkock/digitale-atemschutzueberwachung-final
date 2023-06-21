package de.haw.digitale_atemschutzueberwachung.Personendaten;

import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.RUECKZUGSOLL;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.START;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.ZIEL;

import java.util.HashMap;
import java.util.Map;

public class PAGeraet {


    Map<Status, Integer> druecke;

    public void setDruecke(Map<Status, Integer> druecke) {
        this.druecke = druecke;
    }

    public Map<Status, Integer> getDruecke() {
        return druecke;
    }

    public int getDruck(Status status) {
        if (!druecke.containsKey(status)) return 0;
        return druecke.get(status);
    }


    //OCL8 Bedingt durch die Abfrage in Zeile 32 muss der Druck positiv sein.
    public PAGeraet(int druck) throws Exception {
        druecke = new HashMap<Status, Integer>();
        if (druck < 0) throw new IllegalArgumentException("Druck darf nicht negativ sein!");
        druecke.put(Status.START, druck);
    }


    public void setzeDruck(Status status, int druck) {
        druecke.put(status, druck);
        errechneRueckzugsdruck();
    }


    /**
     * Diese Methode errechnet den Soll-Rueckzugsdruck, wenn Start und Ziel bekannt sind.
     */
    private void errechneRueckzugsdruck() {
        if (druecke.containsKey(Status.START) && druecke.containsKey(Status.ZIEL) && druecke.get(Status.ZIEL) != 0 && getDruck(START) != 0) {
            int druckstart = getDruck(START);
            int druckziel = getDruck(ZIEL);
            int rueckzugsdruck = (druckstart - druckziel) * 2;
            druecke.put(Status.RUECKZUGSOLL, rueckzugsdruck);
        }
    }

    //200 99
    public boolean rueckzugMoeglich() {
        if (getDruck(START) == 0 || getDruck(ZIEL) == 0) return true;
        if (!druecke.containsKey(Status.RUECKZUGSOLL)) return true;
        return getDruck(ZIEL) >= getDruck(RUECKZUGSOLL);

    }


}




