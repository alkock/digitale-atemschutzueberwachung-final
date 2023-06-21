package de.haw.digitale_atemschutzueberwachung.Personendaten;

public enum Funktion {
    TRUPPFUEHRER, TRUPPMANN, TRUPPMANN2;

    public static Funktion getFromString(String s) throws IllegalArgumentException {
        if (s == null) throw new IllegalArgumentException("Funktion darf nicht null sein");
        if (s.equals("TRUPPFUEHRER")) return TRUPPFUEHRER;
        if (s.equals("TRUPPMANN")) return TRUPPMANN;
        if (s.equals("TRUPPMANN2")) return TRUPPMANN2;
        throw new IllegalArgumentException("Funktion" + s + " nicht gefunden");
    }

    public static String getNormalStringFromFunktion(Funktion f) {
        if (f == null) throw new IllegalArgumentException("Funktion darf nicht null sein");
        if (f.equals(TRUPPFUEHRER)) return "Truppführer";
        if (f.equals(TRUPPMANN)) return "Truppmann";
        if (f.equals(TRUPPMANN2)) return "Truppmann 2";
        throw new IllegalArgumentException("Funktion" + f + " nicht gefunden");
    }

    public static int gibIndex(Funktion f) {
        if (f == null) return -1;
        if (f.equals(TRUPPFUEHRER)) return 0;
        if (f.equals(TRUPPMANN)) return 1;
        if (f.equals(TRUPPMANN2)) return 2;
        return -1;
    }
}
