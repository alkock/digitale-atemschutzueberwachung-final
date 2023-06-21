package de.haw.digitale_atemschutzueberwachung.Personendaten;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Status {
    START, ZIEL, RUECKZUGSOLL, RUECKZUGIST, KONTROLLE1o3, KONTROLLE2o3, ENDE, NULL;

    public static Set<Status> statusOhneNull() {
        Set<Status> statusOhneNull = new HashSet<>(Arrays.asList(Status.values()));
        statusOhneNull.remove(Status.NULL);
        return statusOhneNull;
    }

    public static Set<Status> gibInverseSet(Set<Status> gegebeneStatus) {
        if (gegebeneStatus == null)
            throw new IllegalArgumentException("Das Set darf nicht null sein!");

        Set<Status> alleStatus = new HashSet<>(Arrays.asList(Status.values()));
        for (Status status : gegebeneStatus) {
            alleStatus.remove(status);
        }
        return alleStatus;
    }

    public static String gibStringVonStatus(Status status) {
        if (status == null) return "Status.NULL";
        switch (status) {
            case START:
                return "Status.START";
            case ZIEL:
                return "Status.ZIEL";
            case RUECKZUGSOLL:
                return "Status.RUECKZUGSOLL";
            case RUECKZUGIST:
                return "Status.RUECKZUGIST";
            case KONTROLLE1o3:
                return "Status.KONTROLLE1o3";
            case KONTROLLE2o3:
                return "Status.KONTROLLE2o3";
            case ENDE:
                return "Status.ENDE";
            default:
                return "Status.NULL";

        }
    }

    public static Status gibStatusVonString(String status) {
        if (status == null) return null;
        switch (status) {
            case "Status.START":
                return Status.START;
            case "Status.ZIEL":
                return Status.ZIEL;
            case "Status.RUECKZUGSOLL":
                return Status.RUECKZUGSOLL;
            case "Status.RUECKZUGIST":
                return Status.RUECKZUGIST;
            case "Status.KONTROLLE1o3":
                return Status.KONTROLLE1o3;
            case "Status.KONTROLLE2o3":
                return Status.KONTROLLE2o3;
            case "Status.ENDE":
                return Status.ENDE;
            default:
                return Status.NULL;

        }
    }

    public static String normalisierterString(Status status) {
        if (status == null) return null;
        switch (status) {
            case START:
                return "Start";
            case ZIEL:
                return "Ziel";
            case RUECKZUGSOLL:
                return "Rueckzug Soll";
            case RUECKZUGIST:
                return "Rückzug";
            case KONTROLLE1o3:
                return "Eindrittel";
            case KONTROLLE2o3:
                return "Zweidrittel";
            case ENDE:
                return "Ende";
            default:
                return "Null";
        }


    }
}
