package de.haw.digitale_atemschutzueberwachung.modultests.Kollektivdaten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;

@RunWith(RobolectricTestRunner.class)
public class TruppRoboeletricTest {
    @Test
    public void setzeTimerUndBeendeEinsatz() throws InterruptedException {
        Trupp trupp = new Trupp("");
        trupp.setzeTimerUndAktivere(30);

        assertTrue(trupp.get_zeitEndeTimer() != null);
        assertTrue(trupp.get_zeitEinDrittelTimer() != null);
        assertTrue(trupp.get_zeitZweiDrittelTimer() != null);

        trupp.beendeEinsatz();


        long ende = trupp.get_zeitEnde();
        long einDrittel = trupp.get_zeitEinDrittel();
        long zweiDrittel = trupp.get_zeitZweiDrittel();
        Thread.sleep(5000);
        assertEquals(trupp.get_zeitEnde(), ende);
        assertEquals(trupp.get_zeitEinDrittel(), einDrittel);
        assertEquals(trupp.get_zeitZweiDrittel(), zweiDrittel);

    }

    @Test
    public void testeDasAlleTimerVorhandenenSind() {
        Trupp trupp = new Trupp("TestTrupp");
        trupp.setzeTimerUndAktivere(30);
        assertTrue(trupp.get_zeitEndeTimer() != null);
        assertTrue(trupp.get_zeitEinDrittelTimer() != null);
        assertTrue(trupp.get_zeitZweiDrittelTimer() != null);
    }

    @Test
    public void erstelleEinsatzzeit() {
        Trupp trupp = new Trupp("Trupp 1");
        trupp.setzeTimerUndAktivere(30);
        trupp.get_zeitEnde();
        assertTrue(trupp.get_zeitEnde() == 30);
    }

    @Test
    public void erstelleEinsatzzeitEinDrittel() {
        Trupp trupp = new Trupp("TestTrupp");
        trupp.setzeTimerUndAktivere(30);
        assertTrue(trupp.get_zeitEinDrittel() == 10);
    }

}
