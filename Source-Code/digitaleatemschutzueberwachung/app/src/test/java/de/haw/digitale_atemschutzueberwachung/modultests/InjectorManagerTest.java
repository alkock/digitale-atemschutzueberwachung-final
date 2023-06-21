package de.haw.digitale_atemschutzueberwachung.modultests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

import de.haw.digitale_atemschutzueberwachung.Datenhaltung.Datenverwaltung;
import de.haw.digitale_atemschutzueberwachung.Datenhaltung.DatenverwaltungImpl;
import de.haw.digitale_atemschutzueberwachung.InjectorManager;
import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Layout.Ueberwachungstafel;


public class InjectorManagerTest {
    private InjectorManager injectorManager;
    private Einsatzinformationen einsatzinformationen;

    @Before
    public void setUp() {
        injectorManager = new InjectorManager();
        einsatzinformationen = new Einsatzinformationen("", "", "", "", "", Calendar.getInstance().getTime(), new HashMap<>(), new LinkedList<>());
    }

    @Test
    public void testSetzeUeberwachungstafel() {
        InjectorManager injectorManager = new InjectorManager();
        Ueberwachungstafel ueberwachungstafel = mock(Ueberwachungstafel.class);
        injectorManager.setzeUeberwachungstafel(ueberwachungstafel);
        assertEquals(ueberwachungstafel, injectorManager.gibUeberwachungstafel());
    }

    @Test
    public void testSetzeEinsatzinformation() {
        injectorManager.setzeEinsatzinformation(einsatzinformationen);
        assertEquals(einsatzinformationen, injectorManager.gibEinsatzinformation());
    }
    @Test
    public void testGibUeberwachungstafelAdapter() {
        InjectorManager injectorManager = new InjectorManager();
        assertNotNull(injectorManager.gibUeberwachungstafelAdapter());
    }

    @Test
    public void testSetzeNeuerEinsatzIntent() {
        InjectorManager injectorManager = new InjectorManager();
        injectorManager.setzeNeuerEinsatzIntent(true);
        assertTrue(injectorManager.gibNeuerEinsatzIntent());
    }

    @Test
    public void testSetzeDatenverwaltung() {
        InjectorManager injectorManager = new InjectorManager();
        Datenverwaltung datenverwaltung = new DatenverwaltungImpl(null);
        injectorManager.setzeDatenverwaltung(datenverwaltung);
        assertEquals(datenverwaltung, injectorManager.gibDatenverwaltung());
    }




}



