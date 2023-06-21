package de.haw.digitale_atemschutzueberwachung.Datenhaltung;

import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;

public interface Datenverwaltung {

    /**
     * Diese Methode speichert die Trupps inklusive ihres Einsatzauftrages in der Datenbank ab.
     * Die Informationen werden dabei aus dem InjectionManager gezogen.
     * Beim speichern darf Null übergeben werden, dann wird die Einsatzinformatione
     * aus dem InjectionManager genommen.
     *
     * @param einsatzinformationen die Einsatzinformationen,
     *                             die gespeichert werden sollen. [Für das Testen der Datenbank.]
     */
    void speichereEinsatz(Einsatzinformationen einsatzinformationen);

    /**
     * Diese Methode lädt die Trupps inklusive ihres Einsatzauftrages aus der Datenbank.
     *
     * @return den Gesamteinsatz, der geladen werden soll.
     */
    Einsatzinformationen einsatzLaden();

}
