package de.haw.digitale_atemschutzueberwachung.modultests.Layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowApplication;

import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;
import de.haw.digitale_atemschutzueberwachung.Layout.MainActivity;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    MainActivity activity;

    @Before
    public void setUp() {
        ShadowApplication.getInstance().setSystemService(Context.INPUT_METHOD_SERVICE, mock(InputMethodManager.class));
        activity = Robolectric.buildActivity(MainActivity.class).create().start().visible().get();
    }



    @Test
    public void testNeuerEinsatz() {
        Einsatzinformationen einsatz = MainActivity.neuerEinsatz();
        assertEquals("Einsatznummer", einsatz.getEinsatznummer());
        assertEquals("Rufgruppe", einsatz.getRufgruppe());
    }

    @Test
    public void testErstelleBasisTrupps() throws Exception {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = activityController.get();
        MainActivity.erstelleBasisTrupps();
        Einsatzinformationen einsatzinformationen = MainActivity.einsatzinformationen;

        assertNotNull(einsatzinformationen);

        for (Trupp trupp : einsatzinformationen.getTrupps().keySet()) {
            assertNotNull(trupp);
            assertNotNull(trupp.get_name());
            assertNotNull(trupp.getTrupp()[0]);
            assertNotNull(trupp.getTrupp()[1]);
            assertNotNull(trupp.getTrupp()[2]);
            assertEquals(trupp.get_einsatzstatus(), Status.NULL);
        }
    }
}
