package de.haw.digitale_atemschutzueberwachung.Kerndaten;

import java.util.Date;
import java.util.List;
import java.util.Map;

import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;

public class Einsatzinformationen {

    String einsatzstrasse;
    String einsatzhausnummer;
    String Einsatznummer;
    String Rufgruppe;
    String Einheitsfuehrer;
    Date Datum;
    Map<Trupp, Einsatzauftrag> trupps;
    List<Trupp> listOfTrupps;

    //OCL1 : Bedingt durch den Konstruktor kann kein Exemplar der Klasse ohne die definierten Attribute erzeugt werden.
    public Einsatzinformationen(String einsatzstrasse, String einsatzhausnummer, String einsatznummer, String rufgruppe, String einheitsfuehrer, Date datum, Map<Trupp, Einsatzauftrag> trupps, List<Trupp> listOfTrupps) {
        this.einsatzstrasse = einsatzstrasse;
        this.einsatzhausnummer = einsatzhausnummer;
        Einsatznummer = einsatznummer;
        Rufgruppe = rufgruppe;
        Einheitsfuehrer = einheitsfuehrer;
        Datum = datum;
        this.trupps = trupps;
        this.listOfTrupps = listOfTrupps;
    }


    public String getEinsatzstrasse() {
        return einsatzstrasse;
    }

    public void setEinsatzstrasse(String einsatzstrasse) {
        this.einsatzstrasse = einsatzstrasse;
    }

    public String getEinsatznummer() {
        return Einsatznummer;
    }

    public void setEinsatznummer(String einsatznummer) {
        Einsatznummer = einsatznummer;
    }

    public String getRufgruppe() {
        return Rufgruppe;
    }

    public void setRufgruppe(String rufgruppe) {
        Rufgruppe = rufgruppe;
    }

    public String getEinheitsfuehrer() {
        return Einheitsfuehrer;
    }

    public void setEinheitsfuehrer(String einheitsfuehrer) {
        Einheitsfuehrer = einheitsfuehrer;
    }

    public Date getDatum() {
        return Datum;
    }

    public void setDatum(Date datum) {
        Datum = datum;
    }

    public void setEinsatzhausnummer(String einsatzhausnummer) {
        this.einsatzhausnummer = einsatzhausnummer;
    }

    public List<Trupp> getListOfTrupps() {
        return listOfTrupps;
    }

    public void setListOfTrupps(List<Trupp> listOfTrupps) {
        this.listOfTrupps = listOfTrupps;
    }

    public void setTrupps(Map<Trupp, Einsatzauftrag> trupps) {
        this.trupps = trupps;
    }


    public Map<Trupp, Einsatzauftrag> getTrupps() {
        return trupps;
    }

    public String getEinsatzhausnummer() {
        return einsatzhausnummer;
    }

    public void fuegeTruppHinzu(Trupp trupp, Einsatzauftrag einsatzauftrag) {
        trupps.put(trupp, einsatzauftrag);
        listOfTrupps.add(trupp);
    }
}
