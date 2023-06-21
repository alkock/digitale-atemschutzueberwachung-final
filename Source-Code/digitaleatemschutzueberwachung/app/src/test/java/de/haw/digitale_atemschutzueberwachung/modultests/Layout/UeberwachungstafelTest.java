package de.haw.digitale_atemschutzueberwachung.modultests.Layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.RUECKZUGSOLL;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowApplication;
import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import de.haw.digitale_atemschutzueberwachung.InjectorManager;
import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;
import de.haw.digitale_atemschutzueberwachung.Layout.Ueberwachungstafel;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;
import de.haw.digitale_atemschutzueberwachung.R;

@RunWith(RobolectricTestRunner.class)
public class UeberwachungstafelTest {
    Ueberwachungstafel activity;
    InjectorManager im;

    @Before
    public void setUp() throws Exception {
        im = new InjectorManager();
        Einsatzinformationen ei = erstelleVollständigeEinsatzinformationen();
        im.setzeNeuerEinsatzIntent(false);
        im.setzeEinsatzinformation(ei);
        ShadowApplication.getInstance().setSystemService(Context.INPUT_METHOD_SERVICE, mock(InputMethodManager.class));
        activity = Robolectric.buildActivity(Ueberwachungstafel.class).create().start().visible().get();
    }

    @Test
    public void testOnCreate() {
        ActivityController<Ueberwachungstafel> activityController = Robolectric.buildActivity(Ueberwachungstafel.class);
        Ueberwachungstafel activity = activityController.get();

        activity.onCreate(null);
    }



    @Test
    public void setzeUhrzeitTest() throws Exception {
        Method setzeUhrzeit = Ueberwachungstafel.class.getDeclaredMethod("setzeUhrzeit", Status.class);
        setzeUhrzeit.setAccessible(true);
        TextView label_start_time = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_start_time);
        assertEquals(label_start_time.getText(), "--:-- Uhr");
        setzeUhrzeit.invoke(activity, Status.START);
        assertNotEquals(label_start_time.getText(), "--:-- Uhr");
    }


    @Test
    public void testeTick() throws Exception
    {
        Field einsatzinformationen = Ueberwachungstafel.class.getDeclaredField("einsatzinformationen");
        einsatzinformationen.setAccessible(true);
        Einsatzinformationen ei = (Einsatzinformationen) einsatzinformationen.get(activity);
        Trupp aktuellerTrupp = ei.getListOfTrupps().get(0);
        aktuellerTrupp.set_maximaleEinsatzzeit(1800000);
        int zeit = 312000;

        Method onTick = Ueberwachungstafel.class.getDeclaredMethod("onTick", int.class, Trupp.class);
        onTick.setAccessible(true);
        Method onZweiDrittelTick = Ueberwachungstafel.class.getDeclaredMethod("onZweiDrittelTick", int.class, Trupp.class);
        onZweiDrittelTick.setAccessible(true);
        Method onEinDrittelTick = Ueberwachungstafel.class.getDeclaredMethod("onEinDrittelTick", int.class, Trupp.class);
        onEinDrittelTick.setAccessible(true);
        onTick.invoke(activity, zeit, aktuellerTrupp);
        onZweiDrittelTick.invoke(activity, zeit, aktuellerTrupp);
        onEinDrittelTick.invoke(activity, zeit, aktuellerTrupp);
        TextView label_start = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_start);
        TextView label_ende = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_ende);
        TextView label_eindrittel = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_eindrittel);
        TextView label_zweidrittel = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_zweidrittel);
        assertTrue(((String)(label_start.getText())).contains("24:48"));
        assertTrue(((String)(label_ende.getText())).contains("05:12"));
        assertTrue(((String)(label_eindrittel.getText())).contains("05:12"));
        assertTrue(((String)(label_zweidrittel.getText())).contains("05:12"));
    }

    @Test(expected=  InvocationTargetException.class) //bedingt durch IllegalArgument in Converter
    public void testeOhneMaxEinsatzzeitparam() throws Exception
    {
        Field einsatzinformationen = Ueberwachungstafel.class.getDeclaredField("einsatzinformationen");
        einsatzinformationen.setAccessible(true);
        Einsatzinformationen ei = (Einsatzinformationen) einsatzinformationen.get(activity);
        Trupp aktuellerTrupp = ei.getListOfTrupps().get(0);
        int zeit = 312000;

        Method onTick = Ueberwachungstafel.class.getDeclaredMethod("onTick", int.class, Trupp.class);
        onTick.setAccessible(true);
        Method onZweiDrittelTick = Ueberwachungstafel.class.getDeclaredMethod("onZweiDrittelTick", int.class, Trupp.class);
        onZweiDrittelTick.setAccessible(true);
        Method onEinDrittelTick = Ueberwachungstafel.class.getDeclaredMethod("onEinDrittelTick", int.class, Trupp.class);
        onEinDrittelTick.setAccessible(true);
        onTick.invoke(activity, zeit, aktuellerTrupp);
        onZweiDrittelTick.invoke(activity, zeit, aktuellerTrupp);
        onEinDrittelTick.invoke(activity, zeit, aktuellerTrupp);
        TextView label_start = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_start);
        TextView label_ende = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_ende);
        TextView label_eindrittel = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_eindrittel);
        TextView label_zweidrittel = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.label_zweidrittel);

    }

    @Test
    public void testeTruppWechseln() throws Exception
    {
        Method aktualisereFelder = Ueberwachungstafel.class.getDeclaredMethod("aktualisiereFelder", int.class);
        aktualisereFelder.setAccessible(true);
        aktualisereFelder.invoke(activity, 2);

        TextView truppname = activity.findViewById(de.haw.digitale_atemschutzueberwachung.R.id.funkrufname);
        assertEquals(truppname.getText(), "Funkrufname Trupp 2");

        aktualisereFelder.invoke(activity, 1);
        assertEquals(truppname.getText(), "Funkrufname Trupp 1");
    }



    @Test
    public void findeHöchstenDruckPositivTest2() throws Exception
    {
        Field einsatzinformationen = Ueberwachungstafel.class.getDeclaredField("einsatzinformationen");
        einsatzinformationen.setAccessible(true);
        Einsatzinformationen ei = (Einsatzinformationen) einsatzinformationen.get(activity);
        ei.getListOfTrupps().get(0).getTrupp()[Funktion.gibIndex(Funktion.TRUPPFUEHRER)].getPag().setzeDruck(Status.START, 300);
        ei.getListOfTrupps().get(0).getTrupp()[Funktion.gibIndex(Funktion.TRUPPFUEHRER)].getPag().setzeDruck(Status.ZIEL, 200);
        ei.getListOfTrupps().get(0).getTrupp()[Funktion.gibIndex(Funktion.TRUPPMANN)].getPag().setzeDruck(Status.START, 300);
        ei.getListOfTrupps().get(0).getTrupp()[Funktion.gibIndex(Funktion.TRUPPMANN)].getPag().setzeDruck(Status.ZIEL, 210);
    }



    @Test
    public void pruefeAufZuWenigRueckzugsdruckNegativTest() throws Exception
    {
        Method pruefeAufZuWenigRueckzugsdruck = Ueberwachungstafel.class.getDeclaredMethod("pruefeAufZuWenigRueckzugsdruck");
        pruefeAufZuWenigRueckzugsdruck.setAccessible(true);
        Field alarmHandler = Ueberwachungstafel.class.getDeclaredField("alarmHandler");
        alarmHandler.setAccessible(true);
        Handler handler = (Handler) alarmHandler.get(activity);
        assertEquals(null, handler);
    }

    @Test
    public void pruefeAufZuWenigRueckzugsdruckPositivTest() throws Exception
    {
        Field einsatzinformationen = Ueberwachungstafel.class.getDeclaredField("einsatzinformationen");
        einsatzinformationen.setAccessible(true);
        Einsatzinformationen ei = (Einsatzinformationen) einsatzinformationen.get(activity);
        ei.getListOfTrupps().get(0).getTrupp()[0].getPag().setzeDruck(Status.START, 300);
        ei.getListOfTrupps().get(0).getTrupp()[0].getPag().setzeDruck(Status.ZIEL, 90);

        Method pruefeAufZuWenigRueckzugsdruck = Ueberwachungstafel.class.getDeclaredMethod("pruefeAufZuWenigRueckzugsdruck");
        pruefeAufZuWenigRueckzugsdruck.setAccessible(true);
        pruefeAufZuWenigRueckzugsdruck.invoke(activity);
        Field alarmHandler = Ueberwachungstafel.class.getDeclaredField("alarmHandler");
        alarmHandler.setAccessible(true);
        Handler handler = (Handler) alarmHandler.get(activity);
        assertNotEquals(null, handler);
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
