package de.haw.digitale_atemschutzueberwachung.modultests.Datenhaltung;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import org.junit.Test;

import de.haw.digitale_atemschutzueberwachung.Datenhaltung.DatenverwaltungImpl;


public class DatenverwaltungImplModulTest {


    @Test
    public void testeAufKorrektenString() {
        DatenverwaltungImpl dvi = new DatenverwaltungImpl(null);
        String actual = dvi.checkForMaliciousInput("Test");
        assertEquals("Test", actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeAufNull() {
        DatenverwaltungImpl dvi = new DatenverwaltungImpl(null);
        String actual = dvi.checkForMaliciousInput(null);
    }

    @Test
    public void testeAufFehlerhaftenString() {
        DatenverwaltungImpl dvi = new DatenverwaltungImpl(null);
        String actual = dvi.checkForMaliciousInput("'Test ");
        assertEquals("''Test ", actual);
    }

    @Test
    public void testeAufSQLInjection() {
        DatenverwaltungImpl dvi = new DatenverwaltungImpl(null);
        String actual = dvi.checkForMaliciousInput("' DROP TABLE  ");
        assertEquals("'' DROP TABLE  ", actual);
    }

    @Test
    public void testeAufSQLInjection2() {
        DatenverwaltungImpl dvi = new DatenverwaltungImpl(null);
        String actual = dvi.checkForMaliciousInput("' OR 1=1; DROP TABLE  ");
        assertEquals("'' OR 1=1; DROP TABLE  ", actual);
    }

}
