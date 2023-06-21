package de.haw.digitale_atemschutzueberwachung.modultests.Personendaten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

public class StatusTest {
    @Test
    public void gibInverseSetAlle() {
        Set<Status> alleStatus = new HashSet<>(Arrays.asList(Status.values()));
        Set<Status> erwartet = Collections.emptySet();

        Set<Status> inverse = Status.gibInverseSet(alleStatus);
        assertEquals(erwartet, inverse);
    }

    @Test
    public void gibInverseSetLeer() {
        Set<Status> keinStatus = Collections.emptySet();
        Set<Status> erwartet = new HashSet<>(Arrays.asList(Status.values()));

        Set<Status> inverse = Status.gibInverseSet(keinStatus);
        assertEquals(erwartet, inverse);
    }

    @Test
    public void gibInverseSetEinzelnerStatus() {
        Set<Status> einStatus = new HashSet<>(Arrays.asList(Status.START));
        Set<Status> erwartet = new HashSet<>(Arrays.asList(Status.values()));
        erwartet.remove(Status.START);

        Set<Status> inverse = Status.gibInverseSet(einStatus);
        assertEquals(erwartet, inverse);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeAufNull() {
        Set<Status> erwartet = new HashSet<>(Arrays.asList(Status.values()));
        Set<Status> inverse = Status.gibInverseSet(null);
        assertEquals(erwartet, inverse);
    }

    @Test
    public void testGibStringVonStatus() {
        assertEquals("Status.START", Status.gibStringVonStatus(Status.START));
        assertEquals("Status.ZIEL", Status.gibStringVonStatus(Status.ZIEL));
        assertEquals("Status.RUECKZUGSOLL", Status.gibStringVonStatus(Status.RUECKZUGSOLL));
        assertEquals("Status.RUECKZUGIST", Status.gibStringVonStatus(Status.RUECKZUGIST));
        assertEquals("Status.KONTROLLE1o3", Status.gibStringVonStatus(Status.KONTROLLE1o3));
        assertEquals("Status.KONTROLLE2o3", Status.gibStringVonStatus(Status.KONTROLLE2o3));
        assertEquals("Status.ENDE", Status.gibStringVonStatus(Status.ENDE));
        assertEquals("Status.NULL", Status.gibStringVonStatus(Status.NULL));
    }

    @Test
    public void testGibStatusVonString() {
        assertEquals(Status.START, Status.gibStatusVonString("Status.START"));
        assertEquals(Status.ZIEL, Status.gibStatusVonString("Status.ZIEL"));
        assertEquals(Status.RUECKZUGSOLL, Status.gibStatusVonString("Status.RUECKZUGSOLL"));
        assertEquals(Status.RUECKZUGIST, Status.gibStatusVonString("Status.RUECKZUGIST"));
        assertEquals(Status.KONTROLLE1o3, Status.gibStatusVonString("Status.KONTROLLE1o3"));
        assertEquals(Status.KONTROLLE2o3, Status.gibStatusVonString("Status.KONTROLLE2o3"));
        assertEquals(Status.ENDE, Status.gibStatusVonString("Status.ENDE"));
        assertEquals(Status.NULL, Status.gibStatusVonString(""));
        assertEquals(Status.NULL, Status.gibStatusVonString("Invalid status"));
    }

    @Test
    public void testStatusOhneNull() {
        Set<Status> erwarteteStatusOhneNull = new HashSet<>(Arrays.asList(Status.START, Status.ZIEL, Status.RUECKZUGSOLL, Status.RUECKZUGIST, Status.KONTROLLE1o3, Status.KONTROLLE2o3, Status.ENDE));
        Set<Status> tatsächlicheStatusOhneNull = Status.statusOhneNull();
        assertEquals(erwarteteStatusOhneNull, tatsächlicheStatusOhneNull);
    }

    @Test
    public void testAnzahlStatusOhneNull() {
        int erwarteteAnzahlStatusOhneNull = Status.values().length - 1;
        int tatsächlicheAnzahlStatusOhneNull = Status.statusOhneNull().size();
        assertEquals(erwarteteAnzahlStatusOhneNull, tatsächlicheAnzahlStatusOhneNull);
    }

    @Test
    public void testNullNichtEnthalten() {
        Set<Status> tatsächlicheStatusOhneNull = Status.statusOhneNull();
        assertFalse(tatsächlicheStatusOhneNull.contains(Status.NULL));
    }

    @Test
    public void testNormalisierterString() {
        // Teste normale Statuswerte
        assertEquals("Start", Status.normalisierterString(Status.START));
        assertEquals("Ziel", Status.normalisierterString(Status.ZIEL));
        assertEquals("Rueckzug Soll", Status.normalisierterString(Status.RUECKZUGSOLL));
        assertEquals("Rückzug", Status.normalisierterString(Status.RUECKZUGIST));
        assertEquals("Eindrittel", Status.normalisierterString(Status.KONTROLLE1o3));
        assertEquals("Zweidrittel", Status.normalisierterString(Status.KONTROLLE2o3));
        assertEquals("Ende", Status.normalisierterString(Status.ENDE));
        assertEquals("Null", Status.normalisierterString(Status.NULL));

        // Teste null-Input
        assertNull(Status.normalisierterString(null));
    }
}




