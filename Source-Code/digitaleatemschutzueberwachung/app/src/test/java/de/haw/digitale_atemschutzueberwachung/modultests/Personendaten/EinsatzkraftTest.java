package de.haw.digitale_atemschutzueberwachung.modultests.Personendaten;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

public class EinsatzkraftTest {
    @Test
    public void testGetName() {
        Einsatzkraft e = new Einsatzkraft("Test", null, null);
        assertEquals("Test", e.getName());
    }

    @Test
    public void testGetSetName() {
        Einsatzkraft e = new Einsatzkraft("Test", null, null);
        e.setName("Test2");
        assertEquals("Test2", e.getName());
    }

    @Test
    public void testGetFunktion() {
        Einsatzkraft e = new Einsatzkraft(null, Funktion.TRUPPFUEHRER, null);
        assertEquals(Funktion.TRUPPFUEHRER, e.getFunktion());
    }

    @Test
    public void testeSetzeNeuenPAundPrüfeAufKorrektesSetzen() throws Exception {
        Einsatzkraft e = new Einsatzkraft(null, null, null);
        PAGeraet pag = new PAGeraet(200);
        e.setPag(pag);
        assertEquals(pag, e.getPag());
    }

    @Test
    public void testeFunktionSetzen() {
        Einsatzkraft e = new Einsatzkraft("Test", null, null);
        e.setFunktion(Funktion.TRUPPFUEHRER);
        assertEquals(Funktion.TRUPPFUEHRER, e.getFunktion());
    }

    @Test
    public void testeGibRueckzugSollWert() throws Exception {
        PAGeraet p = new PAGeraet(300);
        p.setzeDruck(Status.START, 300);
        p.setzeDruck(Status.ZIEL, 200);
        Einsatzkraft einsatzkraft = new Einsatzkraft("Test", null, p);
        assertEquals(200, einsatzkraft.rueckzugsollwert());
    }
}
