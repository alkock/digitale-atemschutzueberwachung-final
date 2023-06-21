package de.haw.digitale_atemschutzueberwachung.Layout;

import de.haw.digitale_atemschutzueberwachung.InjectorManager;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;

/**
 * Dieser Adapter wurde zum Testen gebaut und extrahiert die Klassen von dem Layout
 */
public class UeberwachungstafelAdapter {
    private Ueberwachungstafel ueberwachungstafel;

    public UeberwachungstafelAdapter() {
        if (InjectorManager.IM != null) {
            this.ueberwachungstafel = InjectorManager.IM.gibUeberwachungstafel();
        }
    }

    public void onTick(int zeit, Trupp trupp) {
        if (ueberwachungstafel != null) ueberwachungstafel.onTick(zeit, trupp);
    }

    public void onZweiDrittelTick(int zeit, Trupp trupp) {
        if (ueberwachungstafel != null) ueberwachungstafel.onZweiDrittelTick(zeit, trupp);
    }

    public void onEinDrittelTick(int zeit, Trupp trupp) {
        if (ueberwachungstafel != null) ueberwachungstafel.onEinDrittelTick(zeit, trupp);
    }
}