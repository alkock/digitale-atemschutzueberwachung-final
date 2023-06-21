package de.haw.digitale_atemschutzueberwachung.Layout;

import static de.haw.digitale_atemschutzueberwachung.Converter.formatMillisekunden;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPFUEHRER;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPMANN;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPMANN2;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.gibIndex;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.ENDE;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.RUECKZUGSOLL;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.START;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.ZIEL;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.normalisierterString;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.statusOhneNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import de.haw.digitale_atemschutzueberwachung.Datenhaltung.CronjobSpeichern;
import de.haw.digitale_atemschutzueberwachung.InjectorManager;
import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;
import de.haw.digitale_atemschutzueberwachung.R;

public class Ueberwachungstafel extends AppCompatActivity {
    TextView einsatznummer;
    TextView datum;
    TextView einsatzadresse;
    TextView einsatzleiter;
    TextView rufgruppe;
    TextView funkrufname_trupp;
    TextView label_start;
    TextView label_start_time;
    TextView label_eindrittel;
    TextView label_eindrittel_time;
    TextView label_zweidrittel;
    TextView label_zweidrittel_time;
    TextView label_rueckzugsoll;
    TextView label_rueckzugist;
    TextView label_rueckzugist_time;
    TextView label_ende;
    TextView label_ende_time;
    TextView label_ziel_time;
    TextView truppfuehrer;
    TextView truppmann;
    TextView truppmann2;
    TextView start_truppfuehrer_druck;
    TextView start_truppmann_druck;
    TextView start_truppmann2_druck;
    TextView eindrittel_truppfuehrer_druck;
    TextView eindrittel_truppmann_druck;
    TextView eindrittel_truppmann2_druck;
    TextView zweidrittel_truppfuehrer_druck;
    TextView zweidrittel_truppmann_druck;
    TextView zweidrittel_truppmann2_druck;
    TextView ziel_druck_truppfuehrer;
    TextView ziel_druck_truppmann;
    TextView ziel_druck_truppmann2;
    TextView rueckzugsoll_druck_truppfuehrer;
    TextView rueckzugsoll_druck_truppmann;
    TextView rueckzugsoll_druck_truppmann2;
    TextView rueckzugist_druck_truppfuehrer;
    TextView rueckzugist_druck_truppmann;
    TextView rueckzugist_druck_truppmann2;
    TextView endetruppfuehrer_druck;
    TextView endetruppmann_druck;
    TextView endetruppmann2_druck;
    TextView verortung;
    Button button_aktion;
    Button button_erstertrupp;
    Button button_zweitertrupp;
    Button drittertrupp;
    Button neuerTrupp;
    Handler alarmHandler;

    int aktiverAngezeigterTrupp;
    Einsatzinformationen einsatzinformationen;

    Trupp aktuellerTrupp;
    Einsatzauftrag aktuellerEinsatzauftrag;

    Map<Funktion, Map<Status, TextView>> truppStatusFelder;
    Map<Status, TextView> zeitFelder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        einsatzinformationen = InjectorManager.IM.gibEinsatzinformation();
        initFelder();
        aktualisiereAdressFelder();
        initTextViewDialogs();
        initTruppChangeListener();
        initActionListner();
        InjectorManager.IM.setzeUeberwachungstafel(this);
        speichereAutomatisch();
        reaktiviereAlarmAusDatenbank();
    }

    private void reaktiviereAlarmAusDatenbank() {
        for (Trupp trupp : einsatzinformationen.getListOfTrupps()) {
            trupp.aktiviereEinsatzAusDerDatenbank();
        }
    }

    public void speichereAutomatisch() {
        Timer timer = new Timer();
        timer.schedule(new CronjobSpeichern(), 1 * 60 * 1000, 5 * 60 * 1000);
    }

    /**
     * Dieser Dialog zeigt dem Benutzer das dieser eine falsche Eingabe getätigt hat.
     */
    private void zeigeFalscheEingabeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ungültige Eingabe");
        builder.setMessage("Bitte tätigen Sie eine gültige Eingabe");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initActionListner() {
        button_aktion.setOnClickListener(v -> {
            if (!(aktuellerTrupp.get_einsatzstatus() == Status.START)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Einsatzzeit in min eingeben");
                builder.setMessage("Bitte gebe die vorraussichtliche Einsatzzeit ein:");
                EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5), new DigitsKeyListener(false, false)});
                builder.setView(input);
                builder.setPositiveButton("Ok", (dialog, which) -> {
                    try {
                        int minutenZeit = Integer.parseInt(input.getText().toString());
                        aktuellerTrupp.setzeTimerUndAktivere(minutenZeit * 60 * 1000);
                        button_aktion.setText("ENDE");
                        button_aktion.setBackgroundColor(Color.RED);
                        setzeUhrzeit(START);
                    } catch (NumberFormatException e) {
                        zeigeFalscheEingabeDialog();
                    }

                });
                builder.setNegativeButton("Abbrechen", (dialog, which) -> {
                    // schließen :)
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Einsatz beenden");
                alertDialog.setMessage("Sind Sie sicher, dass Sie den Einsatz beenden wollen?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja",
                        (dialog, which) -> {
                            dialog.dismiss();
                            aktuellerTrupp.set_einsatzstatus(Status.ENDE);
                            button_aktion.setText("START");
                            button_aktion.setBackgroundColor(Color.GREEN);
                            setzeUhrzeit(ENDE);
                            aktuellerTrupp.beendeEinsatz();
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nein",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();

            }
        });
    }

    /**
     * Diese Methode aktualisiert die Headline mit Einsatzadresse, Einsatzleiter und Einsatznummer sowie Datum
     **/
    private void aktualisiereAdressFelder() {
        rufgruppe.setText(einsatzinformationen.getRufgruppe());
        einsatzadresse.setText(einsatzinformationen.getEinsatzstrasse() + " " + einsatzinformationen.getEinsatzhausnummer());
        einsatzleiter.setText(einsatzinformationen.getEinheitsfuehrer());
        einsatznummer.setText(einsatzinformationen.getEinsatznummer());
        Date date = einsatzinformationen.getDatum();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = dateFormat.format(date);
        datum.setText(strDate);
    }

    /**
     * Diese Methode wird von der Klasse Trupp aus bedient und aktualisert die Zeitanzeige des Countdowns
     *
     * @param zeit  Die verbleibende Zeit in Millisekunden
     * @param trupp Der Trupp dessen Zeit angezeigt werden soll
     **/
    protected void onTick(int zeit, Trupp trupp) {
        Trupp trupp1 = einsatzinformationen.getListOfTrupps().get(0);
        Trupp trupp2 = einsatzinformationen.getListOfTrupps().get(1);
        Trupp trupp3 = einsatzinformationen.getListOfTrupps().get(2);
        if (trupp.equals(aktuellerTrupp)) {
            label_ende.setText("ENDE\n" + formatMillisekunden(zeit));
            long startzeit = aktuellerTrupp.get_maximaleEinsatzzeit() - zeit;
            label_start.setText("START\n" + formatMillisekunden(startzeit));
        }
        if (trupp.equals(trupp1)) {
            button_erstertrupp.setText(trupp1.get_name() + " " + formatMillisekunden(zeit));
        } else if (trupp.equals(trupp2)) {
            button_zweitertrupp.setText(trupp2.get_name() + " " + formatMillisekunden(zeit));
        } else if (trupp.equals(trupp3)) {
            drittertrupp.setText(trupp3.get_name() + " " + formatMillisekunden(zeit));
        }
    }

    /**
     * Diese Methode wird von der Klasse Trupp aus bedient und aktualisert die Zeitanzeige des Countdowns
     *
     * @param zeit  Die verbleibende Zeit in Millisekunden
     * @param trupp Der Trupp dessen Zeit angezeigt werden soll
     **/
    protected void onZweiDrittelTick(int zeit, Trupp trupp) {
        if (trupp.equals(aktuellerTrupp)) {
            label_zweidrittel.setText("2/3\n" + formatMillisekunden(zeit));
        }
    }

    /**
     * Diese Methode wird von der Klasse Trupp aus bedient und aktualisert die Zeitanzeige des Countdowns
     *
     * @param zeit  Die verbleibende Zeit in Millisekunden
     * @param trupp Der Trupp dessen Zeit angezeigt werden soll
     **/
    protected void onEinDrittelTick(int zeit, Trupp trupp) {
        if (trupp.equals(aktuellerTrupp)) {
            label_eindrittel.setText("1/3\n" + formatMillisekunden(zeit));
        }
    }


    /**
     * Diese Methode wird beim Ändern der Drücke der Truppmitglieder aufgerufen
     *
     * @param titel     Der Titel des Dialogs
     * @param nachricht Die Nachricht des Dialogs
     * @param funktion  Funktion des Truppmitglieds
     * @param status    Aktueller Einsatzstatus
     **/
    protected void druckDialog(String titel, String nachricht, Funktion funktion, Status status) {
        TextView textView = truppStatusFelder.get(funktion).get(status);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titel);
        builder.setMessage(nachricht);
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER); // set input type to number
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5), new DigitsKeyListener(false, false)});
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String text = input.getText().toString();
                    PAGeraet pag = aktuellerTrupp.getTrupp()[gibIndex(funktion)].getPag();
                    pag.setzeDruck(status, Integer.parseInt(text));
                    textView.setText(pag.getDruck(status) + " bar");
                    setzeUhrzeit(status);
                    if (status == ZIEL) {
                        findeHöchstenDruck();
                        truppStatusFelder.get(funktion).get(RUECKZUGSOLL).setText(pag.getDruck(RUECKZUGSOLL) + " bar");
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Bitte eine gültige Eingabe tätigen!", Toast.LENGTH_SHORT).show();
                    String text = "0";
                    PAGeraet pag = aktuellerTrupp.gibTruppmitglied(funktion).getPag();
                    pag.setzeDruck(status, Integer.parseInt(text));
                    textView.setText(pag.getDruck(status) + " bar");
                }
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // schließen :)
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * Diese Methode wird zum Initalisieren der Dialoge verwendet
     */
    private void initTextViewDialogs() {

        Set<Status> statusOhneRueckzugsoll = statusOhneNull();
        statusOhneRueckzugsoll.remove(RUECKZUGSOLL);

        for (Status status : statusOhneRueckzugsoll) {
            for (Funktion funktion : Funktion.values()) {
                truppStatusFelder.get(funktion).get(status).setOnClickListener(v -> {
                    druckDialog(normalisierterString(status) + "druck eingeben", "Bitte gebe den " + normalisierterString(status) + "druck des " + Funktion.getNormalStringFromFunktion(funktion) + " in bar ein:", funktion, status);
                });
            }
        }

        initalisiereEinsatzkraftNameAendern();

        funkrufname_trupp.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Auswahl");

            final String[] options = {"Angriffstrupp", "Wassertrupp", "Schlauchtrupp", "Sicherheitstrupp"};
            builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final EditText input = new EditText(this);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String userInput = input.getText().toString();
                    if (!userInput.isEmpty()) {
                        funkrufname_trupp.setText(userInput);
                        aktuellerTrupp.set_name(userInput);
                        setzeNameFallsEinsatzNichtAktiv(aktiverAngezeigterTrupp, userInput);
                    } else {
                        int selectedOption = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        if (selectedOption >= 0 && selectedOption < options.length) {
                            funkrufname_trupp.setText(options[selectedOption]);
                            aktuellerTrupp.set_name(options[selectedOption]);
                            setzeNameFallsEinsatzNichtAktiv(aktiverAngezeigterTrupp, options[selectedOption]);
                        }

                    }
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        });

        einsatznummer.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Einsatznummer ändern:");
            builder.setMessage("Bitte gebe die Einsatznummer ein:");
            EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = input.getText().toString();
                    einsatznummer.setText(text);
                    einsatzinformationen.setEinsatznummer(text);
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // schließen :)
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        rufgruppe.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Rufgruppe ändern:");
            builder.setMessage("Bitte gebe die Rufgruppe ein:");
            EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = input.getText().toString();
                    rufgruppe.setText(text);
                    einsatzinformationen.setRufgruppe(text);
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // schließen :)
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        einsatzadresse.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Einsatzadresse ändern:");
            builder.setMessage("Bitte gebe die Einsatzadresse ein:");
            EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = input.getText().toString();
                    einsatzadresse.setText(text);
                    einsatzinformationen.setEinsatzstrasse(text);
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // schließen :)
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        einsatzleiter.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Einsatzleiter ändern:");
            builder.setMessage("Bitte gebe den Namen des Einsatzleiters ein:");
            EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = input.getText().toString();
                    einsatzleiter.setText(text);
                    einsatzinformationen.setEinheitsfuehrer(text);
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // schließen :)
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });



        verortung.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Verortung eingeben");
            builder.setMessage("Bitte gebe die Verortung des Trupps ein:");
            EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = input.getText().toString();
                    verortung.setText(text);
                    aktuellerEinsatzauftrag.setEinsatzauftrag(text);
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // schließen :)
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    /**
     * Diese Methode wird zum Initalisieren der Einsatzkräfte-Namen-Dialoge verwendet
     */
    private void initalisiereEinsatzkraftNameAendern()
    {
        truppmann.setOnClickListener(v -> truppNamenAendern("Truppmannnamen ändern:", "Bitte gebe den Namen des Truppmann ein:", TRUPPMANN, truppmann));
        truppmann2.setOnClickListener(v -> truppNamenAendern("Truppmann 2 Namen ändern:", "Bitte gebe den Namen des Truppmann 2 ein:", TRUPPMANN2, truppmann2));
        truppfuehrer.setOnClickListener(v -> truppNamenAendern("Truppführernamen ändern:", "Bitte gebe den Namen des Truppführer ein:", TRUPPFUEHRER, truppfuehrer));
    }


    /**
     * Diese Methode ändert die Namen auf den unteren Buttons, falls der Einsatz nicht aktiv ist.
     * @param aktiverAngezeigterTrupp Der aktive Trupp, der angezeigt wird
     * @param userInput Der eingegebene Name
     */
    private void setzeNameFallsEinsatzNichtAktiv(int aktiverAngezeigterTrupp, String userInput) {
        switch (aktiverAngezeigterTrupp) {
            case 1:
                if (button_erstertrupp.getText().toString().contains(" 0:00")) {
                    button_erstertrupp.setText(userInput + " 0:00");
                }
                break;
            case 2:
                if (button_zweitertrupp.getText().toString().contains(" 0:00")) {
                    button_zweitertrupp.setText(userInput + " 0:00");
                }
                break;
            case 3:
                if (drittertrupp.getText().toString().contains(" 0:00")) {
                    drittertrupp.setText(userInput + " 0:00");
                }
                break;
        }
    }

    /**
     * Diese Methode wird zum Initalisieren der Einsatzkräfte-Namen-Dialoge verwendet
     */
    void truppNamenAendern(String truppmannTitle, String truppmannMessage, Funktion truppmannType, TextView truppmannView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(truppmannTitle);
        builder.setMessage(truppmannMessage);
        EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                aktuellerTrupp.gibTruppmitglied(truppmannType).setName(text);
                truppmannView.setText(aktuellerTrupp.gibTruppmitglied(truppmannType).getName());
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // schließen :)
                    }
                    });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Diese Methode ändert die Farben der Knöpfe, um den Trupp zu wechseln
     **/
    private void initTruppChangeListener() {
        button_erstertrupp.setOnClickListener(v -> {
            aktiverAngezeigterTrupp = 1;
            button_zweitertrupp.setBackgroundColor(Color.GRAY);
            drittertrupp.setBackgroundColor(Color.GRAY);
            button_erstertrupp.setBackgroundColor(Color.BLACK);
            aktualisiereFelder(aktiverAngezeigterTrupp);
        });
        button_zweitertrupp.setOnClickListener(v -> {
            aktiverAngezeigterTrupp = 2;
            drittertrupp.setBackgroundColor(Color.GRAY);
            button_zweitertrupp.setBackgroundColor(Color.BLACK);
            button_erstertrupp.setBackgroundColor(Color.GRAY);
            aktualisiereFelder(aktiverAngezeigterTrupp);
        });
        drittertrupp.setOnClickListener(v -> {
            aktiverAngezeigterTrupp = 3;
            button_zweitertrupp.setBackgroundColor(Color.GRAY);
            drittertrupp.setBackgroundColor(Color.BLACK);
            button_erstertrupp.setBackgroundColor(Color.GRAY);
            aktualisiereFelder(aktiverAngezeigterTrupp);
        });
    }



    /**
     * Diese Methode aktualisiert alle Felder bei einem Wechsel des Trupps
     **/
    protected void aktualisiereFelder(int trupp) {
        if (alarmHandler != null) alarmHandler.removeCallbacksAndMessages(null);
        List<Trupp> trupps = einsatzinformationen.getListOfTrupps();
        aktuellerTrupp = trupps.get(trupp - 1);
        Map<Funktion, Einsatzkraft> aktuelleKraefte = new HashMap<>();
        aktuelleKraefte.put(TRUPPFUEHRER, aktuellerTrupp.getTrupp()[0]);
        aktuelleKraefte.put(TRUPPMANN, aktuellerTrupp.getTrupp()[1]);
        aktuelleKraefte.put(TRUPPMANN2, aktuellerTrupp.getTrupp()[2]);

        aktuellerEinsatzauftrag = einsatzinformationen.getTrupps().get(aktuellerTrupp);
        funkrufname_trupp.setText(aktuellerTrupp.get_name());
        verortung.setText(aktuellerEinsatzauftrag.getAuftrag());


        truppfuehrer.setText(aktuelleKraefte.get(TRUPPFUEHRER).getName());
        truppmann.setText(aktuelleKraefte.get(TRUPPMANN).getName());
        truppmann2.setText(aktuelleKraefte.get(TRUPPMANN2).getName());

        for (Funktion funktion : Funktion.values()) {

            for (Status status : statusOhneNull()) {
                TextView textView = truppStatusFelder.get(funktion).get(status);
                textView.setText(aktuelleKraefte.get(funktion).getPag().getDruck(status) + " bar");
            }

        }
        if (aktuellerTrupp.get_einsatzstatus() == Status.START) {
            button_aktion.setText("ENDE");
            button_aktion.setBackgroundColor(Color.RED);
        }
        if (aktuellerTrupp.get_einsatzstatus() == Status.ENDE) {
            button_aktion.setText("Start");
            button_aktion.setBackgroundColor(Color.GREEN);
            onTick((int) aktuellerTrupp.get_zeitEnde(), aktuellerTrupp);
            onEinDrittelTick((int) aktuellerTrupp.get_zeitEinDrittel(), aktuellerTrupp);
            onZweiDrittelTick((int) aktuellerTrupp.get_zeitZweiDrittel(), aktuellerTrupp);
        }
        if (aktuellerTrupp.get_einsatzstatus() == Status.NULL) {
            button_aktion.setText("Start");
            button_aktion.setBackgroundColor(Color.GREEN);
            onTick(0, aktuellerTrupp);
            onEinDrittelTick(0, aktuellerTrupp);
            onZweiDrittelTick(0, aktuellerTrupp);
        }
        findeHöchstenDruck();
        Map<Status, String> uhrzeitStatusMap = aktuellerTrupp.get_uhrzeitStatus();
        for (Status status : uhrzeitStatusMap.keySet()) {
            String uhrzeit = uhrzeitStatusMap.get(status);
            zeitFelder.get(status).setText(uhrzeit);
        }
        Set<Status> set = Status.gibInverseSet(uhrzeitStatusMap.keySet());
        set.remove(Status.NULL);
        set.remove(RUECKZUGSOLL);
        for (Status status : set) {
            zeitFelder.get(status).setText("--:-- Uhr");
        }

        findeHöchstenDruck();
    }


    /**
     * Diese Methode initialisiert erstmalig alle Felder in der Tafel
     **/
    private void initFelder() {
        truppStatusFelder = new HashMap<>();
        aktiverAngezeigterTrupp = 1;
        setContentView(R.layout.ueberwachungstafel);
        einsatznummer = findViewById(R.id.einsatznummer);
        datum = findViewById(R.id.datum);
        einsatzadresse = findViewById(R.id.einsatzadresse);
        einsatzleiter = findViewById(R.id.einsatzleiter);
        rufgruppe = findViewById(R.id.rufgruppe);
        funkrufname_trupp = findViewById(R.id.funkrufname);
        truppfuehrer = findViewById(R.id.truppfuehrer);
        truppmann = findViewById(R.id.truppmann);
        truppmann2 = findViewById(R.id.truppmann2);
        start_truppfuehrer_druck = findViewById(R.id.start_truppfuehrer_druck);
        start_truppmann_druck = findViewById(R.id.start_truppmann_druck);
        start_truppmann2_druck = findViewById(R.id.start_truppmann2_druck);
        eindrittel_truppfuehrer_druck = findViewById(R.id.eindrittel_truppfuehrer_druck);
        eindrittel_truppmann_druck = findViewById(R.id.eindrittel_truppmann_druck);
        eindrittel_truppmann2_druck = findViewById(R.id.eindrittel_truppmann2_druck);
        zweidrittel_truppfuehrer_druck = findViewById(R.id.zweidrittel_truppfuehrer_druck);
        zweidrittel_truppmann_druck = findViewById(R.id.zweidrittel_truppmann_druck);
        zweidrittel_truppmann2_druck = findViewById(R.id.zweidrittel_truppmann2_druck);
        ziel_druck_truppfuehrer = findViewById(R.id.ziel_druck_truppfuehrer);
        ziel_druck_truppmann = findViewById(R.id.ziel_druck_truppmann);
        ziel_druck_truppmann2 = findViewById(R.id.ziel_druck_truppmann2);
        endetruppfuehrer_druck = findViewById(R.id.ende_druck_truppfuehrer);
        endetruppmann_druck = findViewById(R.id.ende_druck_truppmann);
        endetruppmann2_druck = findViewById(R.id.ende_druck_truppmann2);
        rueckzugsoll_druck_truppfuehrer = findViewById(R.id.rueckzugsoll_druck_truppfuehrer);
        rueckzugsoll_druck_truppmann = findViewById(R.id.rueckzugsoll_druck_truppmann);
        rueckzugsoll_druck_truppmann2 = findViewById(R.id.rueckzugsoll_druck_truppmann2);
        rueckzugist_druck_truppfuehrer = findViewById(R.id.rueckzugist_druck_truppfuehrer);
        rueckzugist_druck_truppmann = findViewById(R.id.rueckzugist_druck_truppmann);
        rueckzugist_druck_truppmann2 = findViewById(R.id.rueckzugist_druck_truppmann2);

        //Map Truppfuehrer
        Map<Status, TextView> truppfuehrer = new HashMap<>();
        truppfuehrer.put(Status.START, start_truppfuehrer_druck);
        truppfuehrer.put(Status.KONTROLLE1o3, eindrittel_truppfuehrer_druck);
        truppfuehrer.put(Status.KONTROLLE2o3, zweidrittel_truppfuehrer_druck);
        truppfuehrer.put(Status.ZIEL, ziel_druck_truppfuehrer);
        truppfuehrer.put(Status.ENDE, endetruppfuehrer_druck);
        truppfuehrer.put(Status.RUECKZUGIST, rueckzugist_druck_truppfuehrer);
        truppfuehrer.put(Status.RUECKZUGSOLL, rueckzugsoll_druck_truppfuehrer);
        truppStatusFelder.put(TRUPPFUEHRER, truppfuehrer);

        //Map Truppmann
        Map<Status, TextView> truppmann = new HashMap<>();
        truppmann.put(Status.START, start_truppmann_druck);
        truppmann.put(Status.KONTROLLE1o3, eindrittel_truppmann_druck);
        truppmann.put(Status.KONTROLLE2o3, zweidrittel_truppmann_druck);
        truppmann.put(Status.ZIEL, ziel_druck_truppmann);
        truppmann.put(Status.ENDE, endetruppmann_druck);
        truppmann.put(Status.RUECKZUGIST, rueckzugist_druck_truppmann);
        truppmann.put(Status.RUECKZUGSOLL, rueckzugsoll_druck_truppmann);
        truppStatusFelder.put(TRUPPMANN, truppmann);

        //Map Truppmann2
        Map<Status, TextView> truppmann2 = new HashMap<>();
        truppmann2.put(Status.START, start_truppmann2_druck);
        truppmann2.put(Status.KONTROLLE1o3, eindrittel_truppmann2_druck);
        truppmann2.put(Status.KONTROLLE2o3, zweidrittel_truppmann2_druck);
        truppmann2.put(Status.ZIEL, ziel_druck_truppmann2);
        truppmann2.put(Status.ENDE, endetruppmann2_druck);
        truppmann2.put(Status.RUECKZUGIST, rueckzugist_druck_truppmann2);
        truppmann2.put(Status.RUECKZUGSOLL, rueckzugsoll_druck_truppmann2);
        truppStatusFelder.put(Funktion.TRUPPMANN2, truppmann2);


        verortung = findViewById(R.id.verortung);
        button_aktion = findViewById(R.id.aktion_button);
        neuerTrupp = findViewById(R.id.neuer_einsatz);
        button_erstertrupp = findViewById(R.id.erstertrupp);
        button_erstertrupp.setBackgroundColor(Color.BLACK);
        button_zweitertrupp = findViewById(R.id.zweitertrupp);
        drittertrupp = findViewById(R.id.drittertrupp);
        label_eindrittel = findViewById(R.id.label_eindrittel);
        label_zweidrittel = findViewById(R.id.label_zweidrittel);
        label_ende = findViewById(R.id.label_ende);
        label_rueckzugsoll = findViewById(R.id.label_rueckzugsoll);
        label_rueckzugist = findViewById(R.id.label_rueckzugist);
        label_start = findViewById(R.id.label_start);
        label_ende = findViewById(R.id.label_ende);

        //Zeiten
        label_start_time = findViewById(R.id.label_start_time);
        label_eindrittel_time = findViewById(R.id.label_eindrittel_time);
        label_zweidrittel_time = findViewById(R.id.label_zweidrittel_time);
        label_rueckzugist_time = findViewById(R.id.label_rueckzugist_time);
        label_ende_time = findViewById(R.id.label_ende_time);
        label_ziel_time = findViewById(R.id.label_ziel_time);
        //ZeitMap
        zeitFelder = new HashMap<>();
        zeitFelder.put(Status.START, label_start_time);
        zeitFelder.put(Status.KONTROLLE1o3, label_eindrittel_time);
        zeitFelder.put(Status.KONTROLLE2o3, label_zweidrittel_time);
        zeitFelder.put(Status.ZIEL, label_ziel_time);
        zeitFelder.put(Status.ENDE, label_ende_time);
        zeitFelder.put(Status.RUECKZUGIST, label_rueckzugist_time);

        aktualisiereFelder(aktiverAngezeigterTrupp);
        neuerTrupp.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Möchten sie wirklich einen neuen Einsatz starten? Alle bisherigen Daten gehen verloren!");
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    for (Trupp trupp : InjectorManager.IM.gibEinsatzinformation().getListOfTrupps()) {
                        trupp.beendeEinsatz();
                    }
                    InjectorManager.IM.setzeNeuerEinsatzIntent(true);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }


    /**
     * Diese Methode alarmiert den Nutzer das er beim Drücken der Zurücktaste die Applikation beenden kann, dabei wird abgeragt, ob der Nutzer wirklich beenden möchte.
     */

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Willst du die Anwendung wirklich beenden?").setCancelable(false).setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                InjectorManager.IM.gibDatenverwaltung().speichereEinsatz(null);
                System.exit(0);
            }
        }).setNegativeButton("Nein", null).show();
    }

    /**
     * Diese Methode bestimmt den höchsten Druck im Rückzugsoll und markiert dieses Feld entsprechend.
     * 0-Drücke werden nicht markiert.
     */

    protected void findeHöchstenDruck() {
        List<Funktion> niedrigsteRueckzugswerte = aktuellerTrupp.gibNiedrigsteRueckzugswerte();
        for (Funktion funktion : Funktion.values()) {
            TextView textView = truppStatusFelder.get(funktion).get(Status.RUECKZUGSOLL);
            textView.setBackgroundColor(Color.TRANSPARENT);
        }
        for (Funktion funktion : niedrigsteRueckzugswerte) {
            TextView textView = truppStatusFelder.get(funktion).get(Status.RUECKZUGSOLL);
            textView.setBackgroundColor(Color.YELLOW);
        }
        pruefeAufZuWenigRueckzugsdruck();
    }




    /**
     * Diese Methode prüft bei Eingabe des Zieldrucks darauf ab, ob genug Druck für einen Rückzug vorhanden ist
     * und weist sonst den ASÜ-Benutzer darauf hin, dass ein sofortiger Rückzug mit ggfs.
     * MAYDAY oder Meldung an den Einheitsführer erfolgen muss.
     */

    protected void pruefeAufZuWenigRueckzugsdruck() {
        List<Einsatzkraft> keinRueckzugMoeglich = aktuellerTrupp.fuerEinsatzkraftRueckzugNichtMoeglich();
        if (keinRueckzugMoeglich.isEmpty()) {
            if (alarmHandler != null) {
                alarmHandler.removeCallbacks(null);
                for (Funktion f : Funktion.values()) {
                    truppStatusFelder.get(f).get(START).setBackgroundColor(Color.TRANSPARENT);
                    truppStatusFelder.get(f).get(RUECKZUGSOLL).setBackgroundColor(Color.TRANSPARENT);
                }
            }
            return;
        }

        for (Einsatzkraft e : keinRueckzugMoeglich) {
            alarmHandler = new Handler();
            alarmHandler.postDelayed(new Runnable() {
                boolean isRed = true;

                @Override
                public void run() {
                    if (isRed) {
                        truppStatusFelder.get(e.getFunktion()).get(START).setBackgroundColor(Color.YELLOW);
                        truppStatusFelder.get(e.getFunktion()).get(RUECKZUGSOLL).setBackgroundColor(Color.YELLOW);

                    } else {
                        truppStatusFelder.get(e.getFunktion()).get(START).setBackgroundColor(Color.RED);
                        truppStatusFelder.get(e.getFunktion()).get(RUECKZUGSOLL).setBackgroundColor(Color.RED);
                    }
                    isRed = !isRed;
                    alarmHandler.postDelayed(this, 500);
                }
            }, 500);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Achtung!");
            builder.setMessage("Der Rückzugsdruck des " + Funktion.getNormalStringFromFunktion(e.getFunktion()) + " ist zu niedrig! Sofort dem Trupp vermelden und an den Einheitsführer melden!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }


    /**
     * Diese Methode setzt zu einem gegebenen Status die Uhrzeit
     *
     * @param einsatzstatus -
     *                      Der Status, zu dem die Uhrzeit gesetzt werden soll.
     */
    protected void setzeUhrzeit(Status einsatzstatus) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        time += " Uhr";
        TextView benoetigtesZeitFeld = zeitFelder.get(einsatzstatus);
        if (benoetigtesZeitFeld.getText().toString().equals("--:-- Uhr")) {
            aktuellerTrupp.fuelleUhrzeitStatus(einsatzstatus, time);
            benoetigtesZeitFeld.setText(aktuellerTrupp.get_uhrzeitStatus().get(einsatzstatus));
        }
    }
}

