package de.haw.digitale_atemschutzueberwachung.Kollektivdaten;

import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPFUEHRER;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPMANN;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.TRUPPMANN2;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion.gibIndex;

import android.app.AlertDialog;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.haw.digitale_atemschutzueberwachung.InjectorManager;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

public class Trupp {

    /**
     * Diese Variable hält den Namen des Trupps fest.
     **/
    String _name;

    /**
     * Dieses Array hält die drei möglichen Einsatzkräfte fest.
     **/
    Einsatzkraft[] _trupp;

    /**
     * Die folgenden Werte halten die Zeiten für die Timer fest.
     */
    long _maximaleEinsatzzeit;

    private CountDownTimer _zeitEndeTimer;
    long _zeitEnde;

    private CountDownTimer _zeitEinDrittelTimer;
    long _zeitEinDrittel;

    private CountDownTimer _zeitZweiDrittelTimer;
    long _zeitZweiDrittel;
    /**
     * Diese Map hält fest um wieviel Uhr der Trupp die Werte durchgegeben hat
     */
    Map<Status, String> _uhrzeitStatus;


    /**
     * Dieser Status legt fest, ob ein Trupp bereits den Einsatz gestartet hat [START; ENDE; NULL]
     **/
    private Status _einsatzstatus;


    /**
     * Diese Variable hält this fest, da über this nicht in der inneren Klasse aufgerufen werden kann
     **/
    Trupp hier;


    public Map<Status, String> get_uhrzeitStatus() {
        return _uhrzeitStatus;
    }

    public void set_uhrzeitStatus(Map<Status, String> _uhrzeitStatus) {
        this._uhrzeitStatus = _uhrzeitStatus;
    }

    //OCL 3: Der Einsatzstatus darf nur START, ENDE oder NULL sein
    public void set_einsatzstatus(Status _einsatzstatus) {
        if (_einsatzstatus != Status.START && _einsatzstatus != Status.ENDE && _einsatzstatus != Status.NULL)
            throw new IllegalArgumentException("Der Status muss START, ENDE oder NULL sein");
        this._einsatzstatus = _einsatzstatus;
    }

    public Status get_einsatzstatus() {
        return _einsatzstatus;
    }


    public void fuelleUhrzeitStatus(Status status, String uhrzeit) {
        _uhrzeitStatus.put(status, uhrzeit);
    }


    public long get_zeitEnde() {
        return _zeitEnde;
    }

    public void set_zeitEnde(long _zeitEnde) {
        this._zeitEnde = _zeitEnde;
    }

    public long get_zeitEinDrittel() {
        return _zeitEinDrittel;
    }

    public void set_zeitEinDrittel(long _zeitEinDrittel) {
        this._zeitEinDrittel = _zeitEinDrittel;
    }

    public long get_zeitZweiDrittel() {
        return _zeitZweiDrittel;
    }

    public void set_zeitZweiDrittel(long _zeitZweiDrittel) {
        this._zeitZweiDrittel = _zeitZweiDrittel;
    }

    public CountDownTimer get_zeitZweiDrittelTimer() {
        return _zeitZweiDrittelTimer;
    }

    public long get_maximaleEinsatzzeit() {
        return _maximaleEinsatzzeit;
    }

    public void set_maximaleEinsatzzeit(long _maximaleEinsatzzeit) {
        this._maximaleEinsatzzeit = _maximaleEinsatzzeit;
    }


    //OCL2: Bedingt durch den Konstruktor kann kein Exemplar ohne das Attribut name erzeugt werden.
    //OCL5: Beim Erzeugen des Exemplars mit den Parametern der Einsatzkräfte wird mittels assert die Funktion abegeprüft.
    public Trupp(String name, Einsatzkraft truppfuehrer, Einsatzkraft truppmann1, Einsatzkraft truppmann2) {
        assert (truppfuehrer != null);
        assert (truppmann1 != null);
        assert (truppfuehrer.getFunktion() == Funktion.TRUPPFUEHRER);
        assert (truppmann1.getFunktion() == Funktion.TRUPPMANN);
        assert (truppmann2.getFunktion() == Funktion.TRUPPMANN2);
        hier = this;
        this._name = name;
        _trupp = new Einsatzkraft[3];
        _trupp[gibIndex(TRUPPFUEHRER)] = truppfuehrer;
        _trupp[gibIndex(TRUPPMANN)] = truppmann1;
        _trupp[gibIndex(TRUPPMANN2)] = truppmann2;
        _uhrzeitStatus = new HashMap<>();
        _einsatzstatus = Status.NULL;
    }

    //OCL2: Bedingt durch den Konstruktor kann kein Exemplar ohne das Attribut name erzeugt werden.
    public Trupp(String name) {
        hier = this;
        this._name = name;
        _trupp = new Einsatzkraft[3];
        _uhrzeitStatus = new HashMap<>();
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }


    /**
     * Der Trupp besteht aus mindestens einem Truppführer und einem Truppmann Dabei stellt das nullte Element grundsätzlich den Truppführer dar.
     */

    public Einsatzkraft[] getTrupp() {
        return _trupp;
    }

    public Einsatzkraft gibTruppmitglied(Funktion funktion) {
        return _trupp[gibIndex(funktion)];
    }


    //OCL4: bedingt durch die privaten Attribute kann der Timer nur von hier geändert werden und erfüllt somit den Constraint.
    public void setzeTimerUndAktivere(int einsatzzeitzeit) {
        if (einsatzzeitzeit != 0) {
            _einsatzstatus = Status.START;
            if (_zeitEndeTimer != null) _zeitEndeTimer.cancel();
            _zeitEndeTimer = null;
            _maximaleEinsatzzeit = einsatzzeitzeit;
            _zeitEnde = einsatzzeitzeit;
            _zeitEinDrittel = einsatzzeitzeit / 3;
            _zeitZweiDrittel = einsatzzeitzeit / 3 * 2;
            _zeitEndeTimer = (new CountDownTimer(_zeitEnde, 1000) {


                public void onTick(long millisUntilFinished) {
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onTick((int) millisUntilFinished, hier);
                    _zeitEnde = millisUntilFinished;
                }

                public void onFinish() {
                    ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_HIGH_PBX_S_X4, -1);

                    AlertDialog.Builder builder = new AlertDialog.Builder(InjectorManager.IM.gibUeberwachungstafel());
                    builder.setTitle("Einsatzzeit abgelaufen!");
                    builder.setMessage("Der Trupp " + _name + " hat die maximale Einsatzzeit überschritten!");
                    builder.setPositiveButton("OK", (dialog, id) -> {
                        toneGenerator.stopTone();
                    });
                    builder.show();
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onTick(0, hier);
                }
            }.start());
            _zeitEinDrittelTimer = (new CountDownTimer(_zeitEinDrittel, 1000) {

                public void onTick(long millisUntilFinished) {
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onEinDrittelTick((int) millisUntilFinished, hier);
                    _zeitEinDrittel = millisUntilFinished;
                }

                public void onFinish() {
                    if (!einDrittelDruckabfrage()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InjectorManager.IM.gibUeberwachungstafel());
                        builder.setTitle("Druckabfrage erforderlich!");
                        builder.setMessage("Der Trupp " + _name + " benötigt eine Druckabfrage!");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onEinDrittelTick(0, hier);
                }
            }.start());
            _zeitZweiDrittelTimer = (new CountDownTimer(_zeitZweiDrittel, 1000) {

                public void onTick(long millisUntilFinished) {
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onZweiDrittelTick((int) millisUntilFinished, hier);
                    _zeitZweiDrittel = millisUntilFinished;
                }

                public void onFinish() {
                    if (!zweiDrittelDruckabfrage()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InjectorManager.IM.gibUeberwachungstafel());
                        builder.setTitle("Druckabfrage erforderlich!");
                        builder.setMessage("Der Trupp " + _name + " benötigt eine Druckabfrage!");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onZweiDrittelTick(0, hier);
                }
            }.start());
        }

    }

    public CountDownTimer get_zeitEndeTimer() {
        return _zeitEndeTimer;
    }

    public CountDownTimer get_zeitEinDrittelTimer() {
        return _zeitEinDrittelTimer;
    }


    private boolean einDrittelDruckabfrage() {
        boolean erfolgt = false;
        for (int i = 0; i < 2; i++) {
            if (_trupp[i].getPag().getDruck(Status.KONTROLLE1o3) > 0) {
                erfolgt = true;
            } else {
                erfolgt = false;
            }
        }
        return erfolgt;
    }

    private boolean zweiDrittelDruckabfrage() {
        boolean erfolgt = false;
        for (int i = 0; i < 2; i++) {
            erfolgt = _trupp[i].getPag().getDruck(Status.KONTROLLE2o3) > 0;
        }
        return erfolgt;
    }

    public void beendeEinsatz() {
        if (_zeitEndeTimer != null) _zeitEndeTimer.cancel();
        if (_zeitEinDrittelTimer != null) _zeitEinDrittelTimer.cancel();
        if (_zeitZweiDrittelTimer != null) _zeitZweiDrittelTimer.cancel();
    }

    /**
     * Diese Methode wird aufgerufen, um gestartete Timer wiederherzustellen, nachdem die App geöffnet wurde.
     **/
    public void aktiviereEinsatzAusDerDatenbank() {
        if (_einsatzstatus == Status.START) {
            _zeitEndeTimer = null;
            _zeitEndeTimer = (new CountDownTimer(_zeitEnde, 1000) {

                public void onTick(long millisUntilFinished) {
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onTick((int) millisUntilFinished, hier);
                    _zeitEnde = millisUntilFinished;
                }

                public void onFinish() {
                    ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_HIGH_PBX_S_X4, -1);

                    AlertDialog.Builder builder = new AlertDialog.Builder(InjectorManager.IM.gibUeberwachungstafel());
                    builder.setTitle("Einsatzzeit abgelaufen!");
                    builder.setMessage("Der Trupp " + _name + " hat die maximale Einsatzzeit überschritten!");
                    builder.setPositiveButton("OK", (dialog, id) -> {
                        toneGenerator.stopTone();
                    });
                    builder.show();
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onTick(0, hier);
                }
            }.start());
            _zeitEinDrittelTimer = (new CountDownTimer(_zeitEinDrittel, 1000) {

                public void onTick(long millisUntilFinished) {
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onEinDrittelTick((int) millisUntilFinished, hier);
                    _zeitEinDrittel = millisUntilFinished;
                }

                public void onFinish() {
                    if (!einDrittelDruckabfrage()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InjectorManager.IM.gibUeberwachungstafel());
                        builder.setTitle("Druckabfrage erforderlich!");
                        builder.setMessage("Der Trupp " + _name + " benötigt eine Druckabfrage!");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onEinDrittelTick(0, hier);
                }
            }.start());
            _zeitZweiDrittelTimer = (new CountDownTimer(_zeitZweiDrittel, 1000) {

                public void onTick(long millisUntilFinished) {
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onZweiDrittelTick((int) millisUntilFinished, hier);
                    _zeitZweiDrittel = millisUntilFinished;
                }

                public void onFinish() {
                    if (!zweiDrittelDruckabfrage()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InjectorManager.IM.gibUeberwachungstafel());
                        builder.setTitle("Druckabfrage erforderlich!");
                        builder.setMessage("Der Trupp " + _name + " benötigt eine Druckabfrage!");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                    InjectorManager.IM.gibUeberwachungstafelAdapter().onZweiDrittelTick(0, hier);
                }
            }.start());
        }
        if (_einsatzstatus == Status.ENDE) {
            InjectorManager.IM.gibUeberwachungstafelAdapter().onTick((int) _zeitEnde, hier);
            InjectorManager.IM.gibUeberwachungstafelAdapter().onEinDrittelTick((int) _zeitEinDrittel, hier);
            InjectorManager.IM.gibUeberwachungstafelAdapter().onZweiDrittelTick((int) _zeitZweiDrittel, hier);
        }
    }


    public List<Einsatzkraft> fuerEinsatzkraftRueckzugNichtMoeglich() {
        List<Einsatzkraft> rueckzugNichtmoeglich = new ArrayList<>();
        for (Einsatzkraft e : _trupp) {
            if (!e.istRueckzugMoeglich()) rueckzugNichtmoeglich.add(e);
        }
        return rueckzugNichtmoeglich;
    }

    /**
     * Diese Methode gibt die Funktionen der Einsatzkräfte zurück, die den höchsten Rückzugswert haben.
     *
     * @return Liste der Funktionen der Einsatzkräfte mit dem höchsten Rückzugswert.
     */
    public List<Funktion> gibNiedrigsteRueckzugswerte() {
        List<Funktion> niedrigesteRueckzugsDruecke = new ArrayList<>();
        int max = Integer.max(_trupp[gibIndex(TRUPPFUEHRER)].rueckzugsollwert(), Integer.max(_trupp[gibIndex(TRUPPMANN)].rueckzugsollwert(), _trupp[gibIndex(TRUPPMANN2)].rueckzugsollwert()));
        String uhrzeit = _uhrzeitStatus.get(Status.ZIEL);
        if (uhrzeit == null) return niedrigesteRueckzugsDruecke;
        if (max != 0) {
            for (Einsatzkraft e : _trupp) {
                if (max == e.rueckzugsollwert()) niedrigesteRueckzugsDruecke.add(e.getFunktion());
            }
        }
        return niedrigesteRueckzugsDruecke;
    }

}


