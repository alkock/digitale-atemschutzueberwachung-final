package de.haw.digitale_atemschutzueberwachung.modultests.Kerndaten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;

public class EinsatzinformationenTest {
    private Einsatzinformationen einsatz;

    @Before
    public void setUp() throws Exception {
        einsatz = new Einsatzinformationen("Musterstraße", "1", "123", "A", "Hans Müller", new Date(), new HashMap<Trupp, Einsatzauftrag>(), new ArrayList<Trupp>());
    }

    @Test
    public void testSetAndGetEinsatzstrasse() {
        String einsatzstrasse = "Teststraße";
        einsatz.setEinsatzstrasse(einsatzstrasse);
        assertEquals(einsatzstrasse, einsatz.getEinsatzstrasse());
    }

    @Test
    public void testSetAndGetEinsatznummer() {
        String einsatznummer = "456";
        einsatz.setEinsatznummer(einsatznummer);
        assertEquals(einsatznummer, einsatz.getEinsatznummer());
    }

    @Test
    public void testSetAndGetRufgruppe() {
        String rufgruppe = "B";
        einsatz.setRufgruppe(rufgruppe);
        assertEquals(rufgruppe, einsatz.getRufgruppe());
    }

    @Test
    public void testSetAndGetEinheitsfuehrer() {
        String einheitsfuehrer = "Max Mustermann";
        einsatz.setEinheitsfuehrer(einheitsfuehrer);
        assertEquals(einheitsfuehrer, einsatz.getEinheitsfuehrer());
    }

    @Test
    public void testSetAndGetDatum() {
        Date datum = new Date();
        einsatz.setDatum(datum);
        assertEquals(datum, einsatz.getDatum());
    }

    @Test
    public void testSetAndGetEinsatzhausnummer() {
        String einsatzhausnummer = "2";
        einsatz.setEinsatzhausnummer(einsatzhausnummer);
        assertEquals(einsatzhausnummer, einsatz.getEinsatzhausnummer());
    }

    @Test
    public void testSetAndGetTrupps() {
        Map<Trupp, Einsatzauftrag> trupps = new HashMap<>();
        trupps.put(new Trupp(""), new Einsatzauftrag("", 1, ""));
        einsatz.setTrupps(trupps);
        assertEquals(trupps, einsatz.getTrupps());
    }

    @Test
    public void testSetAndGetListOfTrupps() {
        List<Trupp> trupps = new ArrayList<>();
        trupps.add(new Trupp(""));
        einsatz.setListOfTrupps(trupps);
        assertEquals(trupps, einsatz.getListOfTrupps());
    }

    @Test
    public void testFuegeTruppHinzu() {
        Trupp trupp = new Trupp("");
        Einsatzauftrag einsatzauftrag = new Einsatzauftrag("", 1, "");
        einsatz.fuegeTruppHinzu(trupp, einsatzauftrag);
        assertTrue(einsatz.getTrupps().containsKey(trupp));
        assertTrue(einsatz.getListOfTrupps().contains(trupp));
        assertEquals(einsatzauftrag, einsatz.getTrupps().get(trupp));
    }
}
