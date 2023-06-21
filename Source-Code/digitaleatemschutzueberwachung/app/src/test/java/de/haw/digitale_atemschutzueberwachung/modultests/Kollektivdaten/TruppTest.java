package de.haw.digitale_atemschutzueberwachung.modultests.Kollektivdaten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

//@RunWith(RobolectricTestRunner.class)
public class TruppTest {

    private Trupp trupp;

    @Before
    public void setUp() throws Exception {
        // Arrange
        Einsatzkraft truppfuehrer = new Einsatzkraft("Hans", Funktion.TRUPPFUEHRER, new PAGeraet(300));
        Einsatzkraft truppmann1 = new Einsatzkraft("Peter", Funktion.TRUPPMANN, new PAGeraet(300));
        Einsatzkraft truppmann2 = new Einsatzkraft("Paul", Funktion.TRUPPMANN2, new PAGeraet(300));

        trupp = new Trupp("Angriffstrupp", truppfuehrer, truppmann1, truppmann2);
    }


    @Test
    public void erstelleTrupp() {
        Trupp trupp = new Trupp("Trupp 1");
        assertTrue(trupp != null);
    }

    @Test
    public void testTruppConstructor() throws Exception {
        // Arrange
        Einsatzkraft truppfuehrer = new Einsatzkraft("Hans", Funktion.TRUPPFUEHRER, new PAGeraet(300));
        Einsatzkraft truppmann1 = new Einsatzkraft("Peter", Funktion.TRUPPMANN, new PAGeraet(300));
        Einsatzkraft truppmann2 = new Einsatzkraft("Paul", Funktion.TRUPPMANN2, new PAGeraet(300));

        // Act

        Trupp trupp = new Trupp("Angriffstrupp", truppfuehrer, truppmann1, truppmann2);

        // Assert
        assertEquals("Angriffstrupp", trupp.get_name());
        assertEquals(truppfuehrer, trupp.getTrupp()[Funktion.gibIndex(Funktion.TRUPPFUEHRER)]);
        assertEquals(truppmann1, trupp.getTrupp()[Funktion.gibIndex(Funktion.TRUPPMANN)]);
        assertEquals(truppmann2, trupp.getTrupp()[Funktion.gibIndex(Funktion.TRUPPMANN2)]);
        assertEquals(Status.NULL, trupp.get_einsatzstatus());

    }


    @Test
    public void testGetTruppName() {
        // Act
        String truppName = trupp.get_name();

        // Assert
        assertEquals("Angriffstrupp", truppName);
    }

    @Test
    public void testGetTruppMembers() {
        // Act
        Einsatzkraft[] truppMembers = trupp.getTrupp();

        // Assert
        assertEquals(3, truppMembers.length);
        assertEquals("Hans", truppMembers[0].getName());
        assertEquals("Peter", truppMembers[1].getName());
        assertEquals("Paul", truppMembers[2].getName());
    }

    @Test
    public void testSetEinsatzstatusValid() {
        // Arrange
        Status expectedStatus = Status.START;

        // Act
        trupp.set_einsatzstatus(expectedStatus);
        Status actualStatus = trupp.get_einsatzstatus();

        // Assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEinsatzstatusInvalid() {
        // Arrange
        Status invalidStatus = Status.KONTROLLE1o3;

        // Act
        trupp.set_einsatzstatus(invalidStatus);

        // Assert (Exception)
    }






    @Test
    public void testSetEinsatzstatusgültig() {

        // Arrange
        Status expectedStatus = Status.START;
        Trupp trupp = new Trupp("Trupp 1");

        // Act
        trupp.set_einsatzstatus(expectedStatus);
        Status actualStatus = trupp.get_einsatzstatus();

        // Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEinsatzstatusungültig() {
        // Arrange
        Trupp trupp = new Trupp("Trupp 1");
        Status invalidStatus = Status.KONTROLLE1o3;

        // Act

        trupp.set_einsatzstatus(invalidStatus);

        // Assert (Exception)
    }
}
