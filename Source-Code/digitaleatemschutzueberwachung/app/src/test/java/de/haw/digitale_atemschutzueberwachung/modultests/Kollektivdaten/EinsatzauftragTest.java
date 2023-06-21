package de.haw.digitale_atemschutzueberwachung.modultests.Kollektivdaten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;

public class EinsatzauftragTest {

    private Einsatzauftrag einsatzauftrag;

    @Before
    public void setUp() {
        // Arrange
        einsatzauftrag = new Einsatzauftrag("Auftrag", 30, "Bemerkungen");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setzeIllegaleEinsatzzeit() {
        int falscherWert = -1;
        Einsatzauftrag einsatzauftrag = new Einsatzauftrag("Auftrag", falscherWert, "");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testeAufKeinenAuftrag() {
        String auftrag = null;
        Einsatzauftrag einsatzauftrag = new Einsatzauftrag(auftrag, 1, "");
    }

    @Test
    public void erstelleEinsatzauftragUndTesteZeitGetter() {
        String auftrag = "2OG, rechts";
        Einsatzauftrag einsatzauftrag = new Einsatzauftrag(auftrag, 30, "");
        assertEquals(auftrag, einsatzauftrag.getAuftrag());
    }

    @Test
    public void erstelleEinsatzaufragUndTesteBemerkungenGetter() {
        String bemerkungen = "spezielles Vorgehen";
        Einsatzauftrag einsatzauftrag = new Einsatzauftrag("", 1, bemerkungen);
        assertEquals(bemerkungen, einsatzauftrag.getBemerkungen());
    }

    @Test
    public void aendereEinsatzauftragUndTesteObKorrektGesetzt() {
        Einsatzauftrag einsatzauftrag = new Einsatzauftrag("1", 30, "");
        String neuerEinsatzauftrag = "2";
        einsatzauftrag.setEinsatzauftrag(neuerEinsatzauftrag);
        assertEquals(einsatzauftrag.getAuftrag(), neuerEinsatzauftrag);
    }

    @Test
    public void testeObEinsatzzeitKorrektGesetzt() {
        int einsatzzeit = 50;
        Einsatzauftrag einsatzauftrag = new Einsatzauftrag("", einsatzzeit, "");
        assertEquals(einsatzzeit, einsatzauftrag.getEinsatzzeit());
    }



    @Test
    public void testGetAuftrag() {
        // Act
        String auftrag = einsatzauftrag.getAuftrag();

        // Assert
        assertEquals("Auftrag", auftrag);
    }

    @Test
    public void testGetEinsatzzeit() {
        // Act
        int einsatzzeit = einsatzauftrag.getEinsatzzeit();

        // Assert
        assertEquals(30, einsatzzeit);
    }

    @Test
    public void testGetBemerkungen() {
        // Act
        String bemerkungen = einsatzauftrag.getBemerkungen();

        // Assert
        assertEquals("Bemerkungen", bemerkungen);
    }

    @Test
    public void testSetEinsatzauftrag() {
        // Act
        einsatzauftrag.setEinsatzauftrag("Neuer Auftrag");

        // Assert
        assertEquals("Neuer Auftrag", einsatzauftrag.getAuftrag());
    }

    @Test
    public void testSetEinsatzauftrag_Null() {
        // Act
        einsatzauftrag.setEinsatzauftrag(null);

        // Assert
        assertNull(einsatzauftrag.getAuftrag());
    }

}
