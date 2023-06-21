package de.haw.digitale_atemschutzueberwachung.Datenhaltung;

import java.util.TimerTask;

import de.haw.digitale_atemschutzueberwachung.InjectorManager;

/**
 * Diese Klasse wird alle fünf Minuten von der Ueberwachungstafel aufgerufen
 * und speichert automatisch den aktuellen Einsatz in der Datenbank.
 */

public class CronjobSpeichern extends TimerTask {
    @Override
    public void run() {
        Datenverwaltung datenverwaltung = InjectorManager.IM.gibDatenverwaltung();
        datenverwaltung.speichereEinsatz(null);
    }
}
