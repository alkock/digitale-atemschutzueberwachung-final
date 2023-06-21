package de.haw.digitale_atemschutzueberwachung.modultests.Datenhaltung;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.haw.digitale_atemschutzueberwachung.Datenhaltung.DatenverwaltungImpl;
import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

@RunWith(RobolectricTestRunner.class)
public class DatenverwaltungImplRoboeletricTest {
    private SQLiteDatabase db;
    private DatenverwaltungImpl dvi;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dvi = new DatenverwaltungImpl(context);
        db = dvi.getWritableDatabase();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testeSpeichereEinsatz() throws Exception {
        Einsatzinformationen einsatzinformationen = erstelleVollständigeEinsatzinformationen();
        dvi.speichereEinsatz(einsatzinformationen);
        Cursor cursor = db.rawQuery("SELECT COUNT(Einsatzstrasse) FROM Einsatzinformationen", null);
        cursor.moveToFirst();
        int wert = cursor.getInt(0);
        cursor.close();
        assertEquals(1, wert);

        Cursor cursor2 = db.rawQuery("SELECT * FROM Einsatzinformationen", null);
        cursor2.moveToFirst();
        String einsatzstrasse = cursor2.getString(1);
        String einsatzort = cursor2.getString(2);
        String rufgruppe = cursor2.getString(3);
        assertEquals("Straße + Nr.", einsatzstrasse);
        assertEquals("Einsatznummer", einsatzort);
        assertEquals("Rufgruppe", rufgruppe);
        cursor2.close();

    }

    @Test
    public void testeLadeEinsatz() throws Exception {
        testeSpeichereEinsatz();
        Einsatzinformationen zuVergleichende = erstelleVollständigeEinsatzinformationen();
        Einsatzinformationen einsatzinformationen = dvi.einsatzLaden();
        assertEquals(zuVergleichende.getEinsatzhausnummer(), einsatzinformationen.getEinsatzhausnummer());
        assertEquals(zuVergleichende.getEinsatzstrasse(), einsatzinformationen.getEinsatzstrasse());
        assertEquals(zuVergleichende.getRufgruppe(), einsatzinformationen.getRufgruppe());
        assertEquals(zuVergleichende.getEinheitsfuehrer(), einsatzinformationen.getEinheitsfuehrer());
        assertEquals(zuVergleichende.getEinsatznummer(), einsatzinformationen.getEinsatznummer());

        assertEquals(zuVergleichende.getTrupps().size(), einsatzinformationen.getTrupps().size());
        List<Trupp> zuVergleichendeListOfTrupps = zuVergleichende.getListOfTrupps();
        List<Trupp> einsatzkraftList = einsatzinformationen.getListOfTrupps();

        for (int i = 0; i < zuVergleichendeListOfTrupps.size(); i++) {
            Trupp zuVergleichenderTrupp = zuVergleichendeListOfTrupps.get(i);
            Trupp trupp = einsatzkraftList.get(i);
            assertEquals(zuVergleichenderTrupp.get_zeitZweiDrittel(), trupp.get_zeitZweiDrittel());
            assertEquals(zuVergleichenderTrupp.get_zeitEinDrittel(), trupp.get_zeitEinDrittel());
            assertEquals(zuVergleichenderTrupp.get_einsatzstatus(), trupp.get_einsatzstatus());
            assertEquals(zuVergleichenderTrupp.get_zeitEnde(), trupp.get_zeitEnde());
            assertEquals(zuVergleichenderTrupp.get_zeitEinDrittelTimer(), trupp.get_zeitEinDrittelTimer());
            assertEquals(zuVergleichenderTrupp.get_zeitZweiDrittelTimer(), trupp.get_zeitZweiDrittelTimer());
            assertEquals(zuVergleichenderTrupp.get_zeitEndeTimer(), trupp.get_zeitEndeTimer());
            for(int y = 0; y < zuVergleichenderTrupp.getTrupp().length; y++)
            {
                Einsatzkraft zuVergleichendeEinsatzkraft = zuVergleichenderTrupp.getTrupp()[y];
                Einsatzkraft einsatzkraft = trupp.getTrupp()[y];
                assertEquals(zuVergleichendeEinsatzkraft.getFunktion(), einsatzkraft.getFunktion());
                assertEquals(zuVergleichendeEinsatzkraft.getName(), einsatzkraft.getName());

                PAGeraet zuVergleichendesPaGeraet = zuVergleichendeEinsatzkraft.getPag();
                PAGeraet paGeraet = einsatzkraft.getPag();

                for(Status s : Status.values())
                {
                    assertEquals(zuVergleichendesPaGeraet.getDruck(s), paGeraet.getDruck(s));
                }
            }
        }


    }

    public Einsatzinformationen erstelleVollständigeEinsatzinformationen() throws Exception {
        Date heute = Calendar.getInstance().getTime();
        Einsatzinformationen einsatzinformationen = new Einsatzinformationen("Straße + Nr.", "", "Einsatznummer", "Rufgruppe", "Einheitsführer", heute, new HashMap<Trupp, Einsatzauftrag>(), new LinkedList<Trupp>() {
        });
        for (int i = 0; i < 3; i++) {
            PAGeraet paGeraet1 = new PAGeraet(0);
            PAGeraet paGeraet2 = new PAGeraet(0);
            PAGeraet paGeraet3 = new PAGeraet(0);

            Einsatzkraft e1 = new Einsatzkraft("Truppführer", Funktion.TRUPPFUEHRER, paGeraet1);
            Einsatzkraft e2 = new Einsatzkraft("Truppmann1", Funktion.TRUPPMANN, paGeraet2);
            Einsatzkraft e3 = new Einsatzkraft("Truppmann2", Funktion.TRUPPMANN2, paGeraet3);
            int truppnummer = i + 1;
            Trupp trupp = new Trupp("Funkrufname " + "Trupp " + truppnummer, e1, e2, e3);

            Einsatzauftrag einsatzauftrag = new Einsatzauftrag("Einsatz-auftrag", 30, "Bemerkungen");

            einsatzinformationen.fuegeTruppHinzu(trupp, einsatzauftrag);
        }
        return einsatzinformationen;
    }
}
