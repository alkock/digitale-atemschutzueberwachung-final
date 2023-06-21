package de.haw.digitale_atemschutzueberwachung.Kollektivdaten;

public class Einsatzauftrag {
    String auftrag;
    int maximaleEinsatzzeit;
    String bemerkungen;

    //OCL6: Der Auftrag muss bedingt durch den Konstruktor zu initialiserung bereits einen Wert haben.
    public Einsatzauftrag(String auftrag, int maximaleEinsatzzeit, String bemerkungen) {
        if (maximaleEinsatzzeit < 0)
            throw new IllegalArgumentException("Die maximale Einsatzzeit darf nicht negativ sein.");
        if (auftrag == null)
            throw new IllegalArgumentException("Der Auftrag darf nicht null sein.");
        this.auftrag = auftrag;
        this.maximaleEinsatzzeit = maximaleEinsatzzeit;
        this.bemerkungen = bemerkungen;
    }

    public String getAuftrag() {
        return auftrag;
    }

    public void setEinsatzauftrag(String auftrag) {
        this.auftrag = auftrag;
    }

    public int getEinsatzzeit() {
        return maximaleEinsatzzeit;
    }

    public String getBemerkungen() {
        return bemerkungen;
    }


}


