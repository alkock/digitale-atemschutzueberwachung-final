package de.haw.digitale_atemschutzueberwachung;

import de.haw.digitale_atemschutzueberwachung.Datenhaltung.Datenverwaltung;
import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Layout.Ueberwachungstafel;
import de.haw.digitale_atemschutzueberwachung.Layout.UeberwachungstafelAdapter;

public class InjectorManager {
    public static InjectorManager IM;
    public Einsatzinformationen einsatzinformationen;
    public Ueberwachungstafel ueberwachungstafel;
    public Datenverwaltung datenverwaltung;
    public UeberwachungstafelAdapter ueberwachungstafelAdapter;
    boolean neuerEinsatzIntent = false;


    public InjectorManager() {
        IM = this;
    }

    public void setzeEinsatzinformation(Einsatzinformationen einsatzinformationen) {
        this.einsatzinformationen = einsatzinformationen;
    }

    public void setzeUeberwachungstafel(Ueberwachungstafel ueberwachungstafel) {
        this.ueberwachungstafel = ueberwachungstafel;
    }

    public Ueberwachungstafel gibUeberwachungstafel() {
        return ueberwachungstafel;
    }

    public Datenverwaltung gibDatenverwaltung() {
        return datenverwaltung;
    }

    public void setzeDatenverwaltung(Datenverwaltung datenverwaltung) {
        this.datenverwaltung = datenverwaltung;
    }

    public void setzeNeuerEinsatzIntent(boolean neuerEinsatzIntent) {
        this.neuerEinsatzIntent = neuerEinsatzIntent;
    }

    public UeberwachungstafelAdapter gibUeberwachungstafelAdapter() {
        if (ueberwachungstafelAdapter == null)
            ueberwachungstafelAdapter = new UeberwachungstafelAdapter();
        return ueberwachungstafelAdapter;
    }

    public boolean gibNeuerEinsatzIntent() {
        return neuerEinsatzIntent;
    }

    public Einsatzinformationen gibEinsatzinformation() {
        return einsatzinformationen;
    }
}
