package de.haw.digitale_atemschutzueberwachung.modultests.Personendaten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

public class PAGeraetTest {

    @Test
    public void testPAGeraet() throws Exception {
        // Äquivalenzklasse 1: Druck ist positiv oder null
        int positiverDruck = 100;
        PAGeraet paGeraet1 = new PAGeraet(positiverDruck);
        assertEquals(positiverDruck, paGeraet1.getDruck(Status.START));

        int NullDruck = 0;
        PAGeraet paGeraet2 = new PAGeraet(NullDruck);
        assertEquals(NullDruck, paGeraet2.getDruck(Status.START));

        // Äquivalenzklasse 2: Druck ist negativ
        int negativerDruck = -100;
        assertThrows(IllegalArgumentException.class, () -> {
            new PAGeraet(negativerDruck);
        });
    }
    @Test
    public void normaleFlasche200bar() {
        PAGeraet pag = null;
        try {
            pag = new PAGeraet(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(200, pag.getDruck(Status.START));
    }

    @Test
    public void normaleFlasche300bar() {
        PAGeraet pag = null;
        try {
            pag = new PAGeraet(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(300, pag.getDruck(Status.START));
    }

    @Test
    public void frageDruckAbObwohlNichtGesetzt() {
        PAGeraet pag = null;
        try {
            pag = new PAGeraet(300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int wert = pag.getDruck(Status.ZIEL);
        assertEquals(0, wert);
    }

    @Test
    public void setzeDruckUndFrageAb() {
        PAGeraet pag = null;
        try {
            pag = new PAGeraet(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pag.setzeDruck(Status.ZIEL, 200);
        int tatsächlich = pag.getDruck(Status.ZIEL);
        assertEquals(200, tatsächlich);
    }

    @Test
    public void testeMapAufKorrektheit() {
        PAGeraet pag = null;
        try {
            pag = new PAGeraet(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pag.setzeDruck(Status.ZIEL, 200);
        Map<Status, Integer> map = pag.getDruecke();
        assertEquals(200, map.get(Status.ZIEL).intValue());
    }


    @Test
    public void testePositiv300RueckzugsDruecke() throws Exception {
        int start = 300;
        for(int i = 300; i >= 200; i--)
        {
            testRueckzugMoeglich(start, i);
        }
    }

    @Test
    public void testePositiv200RueckzugsDruecke() throws Exception {
        int start = 200;
        for(int i = 200; i >= 100; i--)
        {
            testRueckzugMoeglich(start, i);
        }
    }

   @Test
    public void testeNegativ300RueckzugsDruecke() throws Exception {
        int start = 300;
        for (int i = 199; i >= 1; i--)
        {
            testeRueckzugNichtMoeglich(start, i);
        }
    }

    @Test
    public void testeNegativ200RueckzugsDruecke() throws Exception {
        int start = 200;
        for (int i = 99; i >= 1; i--)
        {
            testeRueckzugNichtMoeglich(start, i);
        }
    }

    public void testeRueckzugNichtMoeglich(int start, int ziel) throws Exception {
        PAGeraet p = new PAGeraet(start);
        p.setzeDruck(Status.START, start);
        p.setzeDruck(Status.ZIEL, ziel);
        assertFalse(p.rueckzugMoeglich());
    }

    public void testRueckzugMoeglich(int start, int ziel) throws Exception {
        PAGeraet p = new PAGeraet(start);
        Map<Status, Integer> druecke = new HashMap<>();
        druecke.put(Status.START, start);
        druecke.put(Status.ZIEL, ziel);
        p.setDruecke(druecke);
        assertTrue(p.rueckzugMoeglich());
    }

}
