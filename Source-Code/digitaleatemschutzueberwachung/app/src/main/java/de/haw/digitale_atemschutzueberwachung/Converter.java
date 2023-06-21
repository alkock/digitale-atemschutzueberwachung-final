package de.haw.digitale_atemschutzueberwachung;

public class Converter {

    /**
     * Diese Methode formatiert Millisekunden in das Format MinMin:SekSek
     */
    public static String formatMillisekunden(double milisekunden) {
        if (milisekunden < 0)
            throw new IllegalArgumentException("Die übergebene Zeit darf nicht negativ sein");
        int sekunden = (int) (milisekunden / 1000);
        int minuten = sekunden / 60;
        sekunden = sekunden % 60;
        return String.format("%02d:%02d", minuten, sekunden);
    }


}
