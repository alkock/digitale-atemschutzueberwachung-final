package de.haw.digitale_atemschutzueberwachung.Personendaten;

public class Einsatzkraft {

    String name;
    Funktion funktion;
    PAGeraet pag;

    //OCL7 : Bedingt durch den Konstruktor muss Name und Funktion übergeben werden.
    public Einsatzkraft(String name, Funktion funktion, PAGeraet paGeraet) {
        this.name = name;
        this.funktion = funktion;
        this.pag = paGeraet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Funktion getFunktion() {
        return funktion;
    }

    public void setFunktion(Funktion funktion) {
        this.funktion = funktion;
    }

    public PAGeraet getPag() {
        return pag;
    }

    public void setPag(PAGeraet pag) {
        this.pag = pag;
    }

    public boolean istRueckzugMoeglich() {
        return pag.rueckzugMoeglich();
    }

    public int rueckzugsollwert() {
        return pag.getDruck(Status.RUECKZUGSOLL);

    }


}
