package de.haw.digitale_atemschutzueberwachung.Layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import de.haw.digitale_atemschutzueberwachung.Datenhaltung.DatenverwaltungImpl;
import de.haw.digitale_atemschutzueberwachung.InjectorManager;
import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.R;

public class MainActivity extends AppCompatActivity {

    public static Einsatzinformationen einsatzinformationen;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onPause() {
        super.onPause();
        try {
            InjectorManager.IM.gibDatenverwaltung().speichereEinsatz(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            InjectorManager.IM.gibDatenverwaltung().speichereEinsatz(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (InjectorManager.IM != null && InjectorManager.IM.gibNeuerEinsatzIntent()) {
            super.onCreate(savedInstanceState);
            einsatzinformationen = neuerEinsatz();
        } else {
            super.onCreate(savedInstanceState);
            InjectorManager im = new InjectorManager();
            InjectorManager.IM.setzeDatenverwaltung(new DatenverwaltungImpl(this));
            try {
                einsatzinformationen = InjectorManager.IM.gibDatenverwaltung().einsatzLaden();
            } catch (Exception e) {
                einsatzinformationen = neuerEinsatz();
            }
        }

        InjectorManager.IM.setzeEinsatzinformation(einsatzinformationen);
        Intent intent = new Intent(MainActivity.this, Ueberwachungstafel.class);
        InjectorManager.IM.setzeNeuerEinsatzIntent(false);
        startActivity(intent);
    }

    public static Einsatzinformationen neuerEinsatz() {
        Date heute = Calendar.getInstance().getTime();
        einsatzinformationen = new Einsatzinformationen("Adresse", "", "Einsatznummer", "Rufgruppe", "Einheitsführer", heute, new HashMap<Trupp, Einsatzauftrag>(), new LinkedList<Trupp>() {
        });
        try {
            erstelleBasisTrupps();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return einsatzinformationen;
    }

    public static void erstelleBasisTrupps() throws Exception {
        for (int i = 0; i < 3; i++) {
            PAGeraet paGeraet1 = new PAGeraet(0);
            PAGeraet paGeraet2 = new PAGeraet(0);
            PAGeraet paGeraet3 = new PAGeraet(0);

            Einsatzkraft e1 = new Einsatzkraft("Truppführer", Funktion.TRUPPFUEHRER, paGeraet1);
            Einsatzkraft e2 = new Einsatzkraft("Truppmann1", Funktion.TRUPPMANN, paGeraet2);
            Einsatzkraft e3 = new Einsatzkraft("Truppmann2", Funktion.TRUPPMANN2, paGeraet3);
            int truppnummer = i + 1;
            Trupp trupp = new Trupp("Funkrufname " + "Trupp " + truppnummer, e1, e2, e3);

            Einsatzauftrag einsatzauftrag = new Einsatzauftrag("Einsatz-auftrag und Verortung", 30, "Bemerkungen");

            einsatzinformationen.fuegeTruppHinzu(trupp, einsatzauftrag);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public static Einsatzinformationen getEinsatzinformationen() {
        return einsatzinformationen;
    }


}