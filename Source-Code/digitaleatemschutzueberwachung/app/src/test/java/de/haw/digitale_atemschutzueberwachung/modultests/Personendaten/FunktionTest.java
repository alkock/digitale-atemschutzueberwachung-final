package de.haw.digitale_atemschutzueberwachung.modultests.Personendaten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPFUEHRER;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPMANN;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPMANN2;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.gibIndex;

import org.junit.Test;

import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;

public class FunktionTest {
    @Test
    public void getFromStringMitTruppfuehrer() {
        Funktion erwartet = TRUPPFUEHRER;
        Funktion ergebnis = Funktion.getFromString("TRUPPFUEHRER");
        assertEquals(erwartet, ergebnis);
    }

    @Test
    public void getFromStringMitTruppmann() {
        Funktion erwartet = TRUPPMANN;
        Funktion ergebnis = Funktion.getFromString("TRUPPMANN");
        assertEquals(erwartet, ergebnis);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFromStringMitFalschemString() {
        Funktion.getFromString("Falscher String");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFromStringMitNull() {
        Funktion.getFromString(null);
    }

    @Test
    public void testGetNormalStringFromFunktionWithNullFunktion() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Funktion.getNormalStringFromFunktion(null));
        assertEquals("Funktion darf nicht null sein", exception.getMessage());
    }

    @Test
    public void testGetNormalStringFromFunktionWithTruppfuehrerFunktion() {
        assertEquals("Truppführer", Funktion.getNormalStringFromFunktion(TRUPPFUEHRER));
    }

    @Test
    public void testGetNormalStringFromFunktionWithTruppmannFunktion() {
        assertEquals("Truppmann", Funktion.getNormalStringFromFunktion(TRUPPMANN));
    }

    @Test
    public void testGetNormalStringFromFunktionWithTruppmann2Funktion() {
        assertEquals("Truppmann 2", Funktion.getNormalStringFromFunktion(TRUPPMANN2));
    }

    @Test
    public void testGibIndex() {
        assertEquals(0, gibIndex(TRUPPFUEHRER));
        assertEquals(1, gibIndex(TRUPPMANN));
        assertEquals(2, gibIndex(TRUPPMANN2));
        assertEquals(-1, gibIndex(null));
    }

}