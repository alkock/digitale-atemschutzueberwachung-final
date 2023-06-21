package de.haw.digitale_atemschutzueberwachung.Datenhaltung;

import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.ENDE;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.KONTROLLE1o3;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.KONTROLLE2o3;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.RUECKZUGIST;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.START;
import static de.haw.digitale_atemschutzueberwachung.Personendaten.Status.ZIEL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.haw.digitale_atemschutzueberwachung.InjectorManager;
import de.haw.digitale_atemschutzueberwachung.Kerndaten.Einsatzinformationen;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Einsatzauftrag;
import de.haw.digitale_atemschutzueberwachung.Kollektivdaten.Trupp;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Einsatzkraft;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Funktion;
import de.haw.digitale_atemschutzueberwachung.Personendaten.PAGeraet;
import de.haw.digitale_atemschutzueberwachung.Personendaten.Status;

public class DatenverwaltungImpl extends SQLiteOpenHelper implements Datenverwaltung {

    private Context context;
    private SQLiteDatabase IODatabase;
    public static final String DATENBANK_NAME = "Atemschutzueberwachung.db";
    public static final int DATENBANK_VERSION = 8;

    //TABELLE Einsatzinformationen
    public static final String TABELLE_EINSATZINFORMATIONEN = "Einsatzinformationen";
    public static final String EINSATZINFORMATIONEN_ID = "_id";
    public static final String EINSATZINFORMATIONEN_EINSATZSTRASSE = "Einsatzstrasse";
    public static final String EINSATZINFORMATIONEN_EINSATZNUMMER = "Einsatznummer";
    public static final String EINSATZINFORMATIONEN_RUFGRUPPE = "Rufgruppe";
    public static final String EINSATZINFORMATIONEN_EINHEITSFUEHRER = "Einheitsfuehrer";
    public static final String EINSATZINFORMATIONEN_DATUM = "Datum";

    //TABELLE Einsatzinformation_hat_Trupps
    public static final String TABELLE_EINSATZINFORMATION_HAT_TRUPPS = "Einsatzinformation_hat_Trupps";
    public static final String TRUPPS_ID = "truppid";
    public static final String EINSATZINFORMATION_ID = "einsatzinformationid";

    //TABELLE Trupp
    public static final String TABELLE_TRUPP = "Trupp";
    public static final String TRUPP_ID = "_id";
    public static final String TRUPP_NAME = "Name";
    public static final String TRUPP_EINSATZZEITEINDRITTEL = "Einsatzzzeitendrittel";
    public static final String TRUPP_EINSATZZEITZWEIDRITTEL = "Einsatzzzeitzweidrittel";
    public static final String TRUPP_EINSATZZEIT = "Einsatzzzeit";
    public static final String TRUPP_MAXIMALEEINSATZZEIT = "MaximaleEinsatzzeit";
    public static final String TRUPP_EINSATZSTATUS = "Einsatzstatus";
    public static final String FKeinsatzauftragID = "FKeinsatzauftragID";
    public static final String FKuhrzeitID = "FKuhrzeitID";

    //TABELLE Einsatzauftrag
    public static final String TABELLE_EINSATZAUFTRAG = "Einsatzauftrag";
    public static final String EINSATZAUFTRAG_ID = "_id";
    public static final String EINSATZAUFTRAG_Auftrag = "Auftrag";
    public static final String MaximaleEinsatzzeit = "MaximaleEinsatzzeit";
    public static final String Bemerkungen = "Bemerkungen";

    //TABELLE Trupp_hat_Einsatzkraft
    public static final String TABELLE_TRUPP_HAT_EINSATZKRAFT = "Trupp_hat_Einsatzkraft";
    public static final String PK_TRUPP_ID = "PK_TRUPP_ID";
    public static final String FK_EINSATZKRAFT_ID = "FK_EINSATZKRAFT_ID";

    //TABELLE Einsatzkraft
    public static final String TABELLE_EINSATZKRAFT = "Einsatzkraft";
    public static final String EINSATZKRAFT_ID = "_id";
    public static final String EINSATZKRAFT_NAME = "Einsatzkraft_Name";
    public static final String EINSATZKRAFT_FUNKTION = "Einsatzkraft_Funktion";
    public static final String FK_PAGeraetID = "FK_PAGeraetID";

    //TABELLE PAGerät
    public static final String TABELLE_PAGeraet = "PAGeraet";
    public static final String PAGeraet_ID = "_id";
    public static final String DRUCKSTART = "DRUCKSTART";
    public static final String DRUCKZIEL = "DRUCKZIEL";
    public static final String DRUCKRUECKZUGSOLL = "DRUCKRUECKZUGSOLL";
    public static final String DRUCKRUECKZUGIST = "DRUCKRUECKZUGIST";
    public static final String DRUCKKONTROLLE1o3 = "DRUCKKONTROLLE1o3";
    public static final String DRUCKKONTROLLE2o3 = "DRUCKKONTROLLE2o3";
    public static final String DRUCKENDE = "DRUCKENDE";

    //TABELLE UHRZEIT
    public static final String TABELLE_UHRZEIT = "Uhrzeit";
    public static final String UHRZEIT_ID = "_id";
    public static final String UHRZEIT_START = "Uhrzeit_Start";
    public static final String UHRZEIT_EINDRITTEL = "Uhrzeit_Eindrittel";
    public static final String UHRZEIT_ZWEIDRITTEL = "Uhrzeit_Zweidrittel";
    public static final String UHRZEIT_ZIEL = "Uhrzeit_Ziel";
    public static final String UHRZEIT_RUECKZUGIST = "Uhrzeit_Rueckzugist";
    public static final String UHRZEIT_ENDE = "Uhrzeit_Ende";


    public DatenverwaltungImpl(@Nullable Context context) {
        super(context, DATENBANK_NAME, null, DATENBANK_VERSION);
        this.context = context;
    }

    public void getDatabase() {
        if (IODatabase == null) {
            IODatabase = getWritableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Erstelle TABELLE EINSATZINFORMATIONEN
        String query = "CREATE TABLE " + TABELLE_EINSATZINFORMATIONEN + " (" +
                EINSATZINFORMATIONEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EINSATZINFORMATIONEN_EINSATZSTRASSE + " TEXT, " +
                EINSATZINFORMATIONEN_EINSATZNUMMER + " TEXT, " +
                EINSATZINFORMATIONEN_RUFGRUPPE + " TEXT, " +
                EINSATZINFORMATIONEN_EINHEITSFUEHRER + " TEXT, " +
                EINSATZINFORMATIONEN_DATUM + " TEXT" +
                ");";
        db.execSQL(query);

        //Erstelle TABELLE Einsatzinformation_hat_Trupps
        query = "CREATE TABLE " + TABELLE_EINSATZINFORMATION_HAT_TRUPPS + " (" +
                EINSATZINFORMATION_ID + " INTEGER, " +
                TRUPPS_ID + " INTEGER " +
                ");";
        db.execSQL(query);

        //Erstelle TABELLE Trupp
        query = "CREATE TABLE " + TABELLE_TRUPP + " (" +
                TRUPP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TRUPP_NAME + " TEXT, " +
                TRUPP_EINSATZZEITEINDRITTEL + " INTEGER, " +
                TRUPP_EINSATZZEITZWEIDRITTEL + " INTEGER, " +
                TRUPP_EINSATZZEIT + " INTEGER, " +
                TRUPP_MAXIMALEEINSATZZEIT + " INTEGER, " +
                TRUPP_EINSATZSTATUS + " TEXT, " +
                FKeinsatzauftragID + " INTEGER," +
                FKuhrzeitID + " INTEGER" +
                ");";
        db.execSQL(query);

        //Erstelle TABELLE Uhrzeit
        query = "CREATE TABLE " + TABELLE_UHRZEIT + " (" +
                UHRZEIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UHRZEIT_START + " TEXT, " +
                UHRZEIT_EINDRITTEL + " TEXT, " +
                UHRZEIT_ZWEIDRITTEL + " TEXT, " +
                UHRZEIT_ZIEL + " TEXT, " +
                UHRZEIT_RUECKZUGIST + " TEXT, " +
                UHRZEIT_ENDE + " TEXT, " +
                "'');";
        db.execSQL(query);

        //Erstelle TABELLE EINSATZAUFTRAG
        query = "CREATE TABLE " + TABELLE_EINSATZAUFTRAG + " (" +
                EINSATZAUFTRAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EINSATZAUFTRAG_Auftrag + " TEXT, " +
                MaximaleEinsatzzeit + " INTEGER, " +
                Bemerkungen + " TEXT, " +
                EINSATZINFORMATIONEN_EINHEITSFUEHRER + " TEXT, " +
                EINSATZINFORMATIONEN_DATUM + " TEXT" +
                ");";
        db.execSQL(query);

        //Erstelle TABELLE Trupp_hat_Einsatzkraft
        query = "CREATE TABLE " + TABELLE_TRUPP_HAT_EINSATZKRAFT + " (" +
                PK_TRUPP_ID + " INTEGER, " +
                FK_EINSATZKRAFT_ID + " INTEGER " +
                ");";
        db.execSQL(query);

        //Erstelle TABELLE Einsatzkraft
        query = "CREATE TABLE " + TABELLE_EINSATZKRAFT + " (" +
                EINSATZKRAFT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EINSATZKRAFT_NAME + " TEXT, " +
                EINSATZKRAFT_FUNKTION + " TEXT, " +
                FK_PAGeraetID + " INTEGER " +
                ");";
        db.execSQL(query);

        //Erstelle TABELLE PAGerät
        query = "CREATE TABLE " + TABELLE_PAGeraet + " (" +
                PAGeraet_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DRUCKSTART + " INTEGER, " +
                DRUCKZIEL + " INTEGER, " +
                DRUCKRUECKZUGSOLL + " INTEGER, " +
                DRUCKRUECKZUGIST + " INTEGER, " +
                DRUCKKONTROLLE1o3 + " INTEGER, " +
                DRUCKKONTROLLE2o3 + " INTEGER, " +
                DRUCKENDE + " INTEGER " +
                ");";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZAUFTRAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZKRAFT);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZINFORMATIONEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_TRUPP);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_PAGeraet);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_TRUPP_HAT_EINSATZKRAFT);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZINFORMATION_HAT_TRUPPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_UHRZEIT);
        onCreate(db);
    }

    /**
     * Diese Methode leert die Datenbank, damit ein neuer Einsatz gespeichert werden kann.
     */
    private void datenbankLeeren() {
        getDatabase();
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZAUFTRAG);
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZKRAFT);
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZINFORMATIONEN);
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_TRUPP);
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_PAGeraet);
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_TRUPP_HAT_EINSATZKRAFT);
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_EINSATZINFORMATION_HAT_TRUPPS);
        IODatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_UHRZEIT);
        onCreate(IODatabase);

    }

    /**
     * Diese Methode überprüft, ob der übergebene String ein ' enthält und ersetzt es durch ''
     *
     * @param s zu checkender String
     * @return modifizierter String / originaler String
     */
    public String checkForMaliciousInput(String s) {
        if (s == null) throw new IllegalArgumentException();
        if (s.contains("'")) {
            s = s.replace("'", "''");
        }
        return s;
    }


    /**
     * Diese Methode speichert den Einsatz in die Datenbank.
     */

    @Override
    public void speichereEinsatz(Einsatzinformationen einsatzinformationen) {
        getDatabase();

        try {
            IODatabase.beginTransaction();
            datenbankLeeren();
            if (einsatzinformationen == null)
                einsatzinformationen = InjectorManager.IM.gibEinsatzinformation();
            //Informationen für Table Einsatzinformationen

            int einsatzinformationenID = speichereEinsatzinformationen(einsatzinformationen);

            //Informationen für Tabelle Trupp
            Map<Trupp, Einsatzauftrag> trupps = einsatzinformationen.getTrupps();
            List<Trupp> truppsList = einsatzinformationen.getListOfTrupps();

            for (Trupp trupp : truppsList) {

                //Tabelle Einsatzauftrag befüllen
                Einsatzauftrag einsatzauftrag = trupps.get(trupp);
                String Auftrag = einsatzauftrag.getAuftrag();
                int maximaleEinsatzzeit = einsatzauftrag.getEinsatzzeit();
                String bemerkungen = einsatzauftrag.getBemerkungen();

                String query = "INSERT INTO " + TABELLE_EINSATZAUFTRAG + " (" +
                        EINSATZAUFTRAG_Auftrag + ", " +
                        MaximaleEinsatzzeit + ", " +
                        Bemerkungen + ") VALUES (" +
                        "'" + checkForMaliciousInput(Auftrag) + "'" + ", " +
                        "'" + maximaleEinsatzzeit + "'" + ", " +
                        "'" + checkForMaliciousInput(bemerkungen) + "');";

                IODatabase.execSQL(query);
                query = "SELECT last_insert_rowid() FROM " + TABELLE_EINSATZAUFTRAG;
                Cursor cursor = IODatabase.rawQuery(query, null);
                cursor.moveToFirst();
                int einsatzauftragID = cursor.getInt(0);
                cursor.close();
                System.out.println("EinsatzauftragID: " + einsatzauftragID);

                //Tabelle Uhrzeit befüllen
                //-1 ist der Wert der ein nichtvorhandensein in der Map impliziert.

                Map<Status, String> uhrzeitStatus = trupp.get_uhrzeitStatus();
                query = "INSERT INTO " + TABELLE_UHRZEIT + " (" +
                        UHRZEIT_START + ", " +
                        UHRZEIT_EINDRITTEL + ", " +
                        UHRZEIT_ZWEIDRITTEL + ", " +
                        UHRZEIT_ZIEL + ", " +
                        UHRZEIT_RUECKZUGIST + ", " +
                        UHRZEIT_ENDE + ") VALUES (" +
                        "'" + ((uhrzeitStatus.get(START) != null) ? uhrzeitStatus.get(START) : "-1") + "'" + ", " +
                        "'" + ((uhrzeitStatus.get(KONTROLLE1o3) != null) ? uhrzeitStatus.get(KONTROLLE1o3) : "-1") + "'" + ", " +
                        "'" + ((uhrzeitStatus.get(KONTROLLE2o3) != null) ? uhrzeitStatus.get(KONTROLLE2o3) : "-1") + "'" + ", " +
                        "'" + ((uhrzeitStatus.get(ZIEL) != null) ? uhrzeitStatus.get(ZIEL) : "-1") + "'" + ", " +
                        "'" + ((uhrzeitStatus.get(RUECKZUGIST) != null) ? uhrzeitStatus.get(RUECKZUGIST) : "-1") + "'" + ", " +
                        "'" + ((uhrzeitStatus.get(ENDE) != null) ? uhrzeitStatus.get(ENDE) : "-1") + "');";
                IODatabase.execSQL(query);
                query = "SELECT last_insert_rowid() FROM " + TABELLE_UHRZEIT;
                cursor = IODatabase.rawQuery(query, null);
                cursor.moveToFirst();
                int uhrzeitID = cursor.getInt(0);
                cursor.close();


                //Einfügen in Tabelle Trupp
                Einsatzkraft[] einsatzkraefte = trupp.getTrupp();
                String truppName = trupp.get_name();
                long einsatzzzeit = trupp.get_zeitEnde();
                long einsatzzeiteindrittel = trupp.get_zeitEinDrittel();
                long einsatzzeiteinzweidrittel = trupp.get_zeitZweiDrittel();
                long einsatzzeitmaximal = trupp.get_maximaleEinsatzzeit();
                String einsatzstatus = Status.gibStringVonStatus(trupp.get_einsatzstatus());

                query = "INSERT INTO " + TABELLE_TRUPP + " (" +
                        TRUPP_NAME + ", " +
                        TRUPP_EINSATZZEIT + ", " +
                        TRUPP_EINSATZZEITEINDRITTEL + ", " +
                        TRUPP_EINSATZZEITZWEIDRITTEL + ", " +
                        TRUPP_MAXIMALEEINSATZZEIT + ", " +
                        TRUPP_EINSATZSTATUS + ", " +
                        FKeinsatzauftragID + ", " +
                        FKuhrzeitID + ") VALUES (" +
                        "'" + checkForMaliciousInput(truppName) + "'" + ", " +
                        "'" + einsatzzzeit + "'" + ", " +
                        "'" + einsatzzeiteindrittel + "'" + ", " +
                        "'" + einsatzzeiteinzweidrittel + "'" + ", " +
                        "'" + einsatzzeitmaximal + "'" + ", " +
                        "'" + checkForMaliciousInput(einsatzstatus) + "'" + ", " +
                        "'" + einsatzauftragID + "', " +
                        "'" + uhrzeitID + "');";
                IODatabase.execSQL(query);

                query = "SELECT last_insert_rowid() FROM " + TABELLE_TRUPP;
                cursor = IODatabase.rawQuery(query, null);
                cursor.moveToFirst();
                int truppID = cursor.getInt(0);
                System.out.println("TruppID: " + truppID);
                cursor.close();

                //Tabelle Einsatzinformationen hat Trupps befüllen
                speichereBeziehungEinsatzinformationHatTrupps(einsatzinformationenID, truppID);

                //Tabelle Einsatzkraft befüllen
                for (Einsatzkraft einsatzkraft : einsatzkraefte) {
                    //Tabelle PAG befüllen
                    PAGeraet pag = einsatzkraft.getPag();
                    int pagID = speicherePAG(pag);

                    //Tabelle Einsatzkraft befüllen
                    int einsatzkraftID = speichereEinsatzkraft(einsatzkraft, pagID);

                    //Tabelle Trupp hat Einsatzkraft befüllen
                    speichereBeziehungTruppHatEinsatzkraft(truppID, einsatzkraftID);

                }
            }
            IODatabase.setTransactionSuccessful();
        } finally {
            IODatabase.endTransaction();
        }


    }

    private int speichereEinsatzinformationen(Einsatzinformationen einsatzinformationen) {
        String einsatzStrasse = einsatzinformationen.getEinsatzstrasse();
        String einsatzNummer = einsatzinformationen.getEinsatznummer();
        String rufgruppe = einsatzinformationen.getRufgruppe();
        String einheitsFuehrer = einsatzinformationen.getEinheitsfuehrer();
        Date datum = einsatzinformationen.getDatum();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDatum = dateFormat.format(datum);

        String query = "INSERT INTO " + TABELLE_EINSATZINFORMATIONEN + " (" +
                EINSATZINFORMATIONEN_EINSATZSTRASSE + ", " +
                EINSATZINFORMATIONEN_EINSATZNUMMER + ", " +
                EINSATZINFORMATIONEN_RUFGRUPPE + ", " +
                EINSATZINFORMATIONEN_EINHEITSFUEHRER + ", " +
                EINSATZINFORMATIONEN_DATUM + ") VALUES (" +
                "'" + checkForMaliciousInput(einsatzStrasse) + "'" + ", " +
                "'" + checkForMaliciousInput(einsatzNummer) + "'" + ", " +
                "'" + checkForMaliciousInput(rufgruppe) + "'" + ", " +
                "'" + checkForMaliciousInput(einheitsFuehrer) + "'" + ", " +
                "'" + checkForMaliciousInput(strDatum) + "');";


        IODatabase.execSQL(query);

        query = "SELECT last_insert_rowid() FROM " + TABELLE_EINSATZINFORMATIONEN;
        Cursor cursor = IODatabase.rawQuery(query, null);
        cursor.moveToFirst();
        int einsatzinformationenID = cursor.getInt(0);
        cursor.close();

        return einsatzinformationenID;
    }

    private void speichereBeziehungTruppHatEinsatzkraft(int truppID, int einsatzkraftID) {
        String query = "INSERT INTO " + TABELLE_TRUPP_HAT_EINSATZKRAFT + " (" +
                PK_TRUPP_ID + ", " +
                FK_EINSATZKRAFT_ID + ") VALUES (" +
                "'" + truppID + "'" + ", " +
                "'" + einsatzkraftID + "');";
        IODatabase.execSQL(query);
    }

    private int speichereEinsatzkraft(Einsatzkraft einsatzkraft, int pagID) {
        String name = einsatzkraft.getName();
        String funktion = einsatzkraft.getFunktion().toString();

        String query = "INSERT INTO " + TABELLE_EINSATZKRAFT + " (" +
                EINSATZKRAFT_NAME + ", " +
                EINSATZKRAFT_FUNKTION + ", " +
                FK_PAGeraetID + ") VALUES (" +
                "'" + checkForMaliciousInput(name) + "'" + ", " +
                "'" + checkForMaliciousInput(funktion) + "'" + ", " +
                "'" + pagID + "');";

        IODatabase.execSQL(query);

        query = "SELECT last_insert_rowid() FROM " + TABELLE_EINSATZKRAFT;
        Cursor cursor = IODatabase.rawQuery(query, null);
        cursor.moveToFirst();
        int einsatzkraftID = cursor.getInt(0);
        cursor.close();

        return einsatzkraftID;
    }

    private int speicherePAG(PAGeraet pag) {
        int druckStart = pag.getDruck(START);
        int druckZiel = pag.getDruck(ZIEL);
        int druckRueckzugsoll = pag.getDruck(Status.RUECKZUGSOLL);
        int druckRueckzugist = pag.getDruck(RUECKZUGIST);
        int druckKontrolle1o3 = pag.getDruck(Status.KONTROLLE1o3);
        int druckKontrolle2o3 = pag.getDruck(Status.KONTROLLE2o3);
        int druckEnde = pag.getDruck(ENDE);

        String query = "INSERT INTO " + TABELLE_PAGeraet + " (" +
                DRUCKSTART + ", " +
                DRUCKZIEL + ", " +
                DRUCKRUECKZUGSOLL + ", " +
                DRUCKRUECKZUGIST + ", " +
                DRUCKKONTROLLE1o3 + ", " +
                DRUCKKONTROLLE2o3 + ", " +
                DRUCKENDE + ") VALUES (" +
                "'" + druckStart + "'" + ", " +
                "'" + druckZiel + "'" + ", " +
                "'" + druckRueckzugsoll + "'" + ", " +
                "'" + druckRueckzugist + "'" + ", " +
                "'" + druckKontrolle1o3 + "'" + ", " +
                "'" + druckKontrolle2o3 + "'" + ", " +
                "'" + druckEnde + "');";
        IODatabase.execSQL(query);

        query = "SELECT last_insert_rowid() FROM " + TABELLE_PAGeraet;
        Cursor cursor = IODatabase.rawQuery(query, null);
        cursor.moveToFirst();
        int pagID = cursor.getInt(0);
        cursor.close();
        return pagID;
    }

    private void speichereBeziehungEinsatzinformationHatTrupps(int einsatzinformationenID, int truppID) {
        String query = "INSERT INTO " + TABELLE_EINSATZINFORMATION_HAT_TRUPPS + " (" +
                EINSATZINFORMATION_ID + ", " +
                TRUPPS_ID + ") VALUES (" +
                "'" + einsatzinformationenID + "'" + ", " +
                "'" + truppID + "');";
        IODatabase.execSQL(query);
    }


    @Override
    public Einsatzinformationen einsatzLaden() {
        getDatabase();
        Einsatzinformationen einsatzinformationen = null;
        try {
            IODatabase.beginTransaction();


            String query = "SELECT * FROM " + TABELLE_EINSATZINFORMATIONEN;
            Cursor cursor = IODatabase.rawQuery(query, null);
            cursor.moveToFirst();
            int einsatzinformation_id = cursor.getInt(0);
            String einsatzstrasse = cursor.getString(1);
            String einsatznummer = cursor.getString(2);
            String rufgruppe = cursor.getString(3);
            String einheitsfuehrer = cursor.getString(4);
            String datum = cursor.getString(5);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = sdf.parse(datum);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cursor.close();

            List<Trupp> tempListeTrupps = new LinkedList<Trupp>();
            Map<Trupp, Einsatzauftrag> tempMapTrupps = new HashMap<Trupp, Einsatzauftrag>();
            query = "SELECT * FROM " + TABELLE_EINSATZINFORMATION_HAT_TRUPPS + " WHERE " + EINSATZINFORMATION_ID + " = " + einsatzinformation_id;
            Cursor tabelle_einsatzinformation_hat_trupps = IODatabase.rawQuery(query, null);

            //EBENE TRUPP
            while (tabelle_einsatzinformation_hat_trupps.moveToNext()) {
                int trupp_id = tabelle_einsatzinformation_hat_trupps.getInt(1);
                /**
                 *                 TRUPP_EINSATZZEITEINDRITTEL + " INTEGER, " +
                 *                 TRUPP_EINSATZZEITZWEIDRITTEL + " INTEGER, " +
                 *                 TRUPP_EINSATZZEIT + " INTEGER, " +
                 */
                query = "SELECT * FROM " + TABELLE_TRUPP + " WHERE " + TRUPP_ID + " = " + trupp_id;
                Cursor cursor1 = IODatabase.rawQuery(query, null);
                System.out.println(cursor1.moveToNext());
                String truppName = cursor1.getString(1);
                long einsatzzzeit = cursor1.getLong(4);
                long einsatzzeitMaximal = cursor1.getLong(5);
                String einsatzstatus = cursor1.getString(6);
                long einsatzzeiteindrittel = cursor1.getLong(2);
                long einsatzzeiteinzweidrittel = cursor1.getLong(3);
                int einsatzauftragID = cursor1.getInt(7);
                int uhrzeitID = cursor1.getInt(8);
                Status status_einsatzstatus = Status.gibStatusVonString(einsatzstatus);

                cursor1.close();

                Map<Status, String> uhrzeitStatus = new HashMap<Status, String>();
                query = "SELECT * FROM " + TABELLE_UHRZEIT + " WHERE " + UHRZEIT_ID + " = " + uhrzeitID;
                Cursor tabelle_uhrzeit = IODatabase.rawQuery(query, null);
                tabelle_uhrzeit.moveToNext();
                String start = tabelle_uhrzeit.getString(1);
                if (!start.equals("-1")) uhrzeitStatus.put(Status.START, start);
                String eindrittel = tabelle_uhrzeit.getString(2);
                if (!eindrittel.equals("-1")) uhrzeitStatus.put(KONTROLLE1o3, eindrittel);
                String zweidrittel = tabelle_uhrzeit.getString(3);
                if (!zweidrittel.equals("-1")) uhrzeitStatus.put(KONTROLLE2o3, zweidrittel);
                String ziel = tabelle_uhrzeit.getString(4);
                if (!ziel.equals("-1")) uhrzeitStatus.put(Status.ZIEL, ziel);
                String rueckzugist = tabelle_uhrzeit.getString(5);
                if (!rueckzugist.equals("-1")) uhrzeitStatus.put(Status.RUECKZUGIST, rueckzugist);
                String ende = tabelle_uhrzeit.getString(6);
                if (!ende.equals("-1")) uhrzeitStatus.put(Status.ENDE, ende);

                tabelle_uhrzeit.close();


                query = "SELECT * FROM " + TABELLE_EINSATZAUFTRAG + " WHERE " + EINSATZAUFTRAG_ID + " = " + einsatzauftragID;
                Cursor tabelle_einsatzauftrag = IODatabase.rawQuery(query, null);
                tabelle_einsatzauftrag.moveToNext();
                String einsatzauftrag = tabelle_einsatzauftrag.getString(1);
                int maximaleEinsatzzeit = tabelle_einsatzauftrag.getInt(2);
                String bemerkungen = tabelle_einsatzauftrag.getString(3);
                Einsatzauftrag einsatzauftrag1 = new Einsatzauftrag(einsatzauftrag, maximaleEinsatzzeit, bemerkungen);

                tabelle_einsatzauftrag.close();

                query = "SELECT * FROM " + TABELLE_TRUPP_HAT_EINSATZKRAFT + " WHERE " + PK_TRUPP_ID + " = " + trupp_id;
                Cursor tabelle_trupp_hat_einsatzkraft = IODatabase.rawQuery(query, null);

                List<Einsatzkraft> templiste = new ArrayList<Einsatzkraft>();

                //EBENE EINSATZKRAFT
                while (tabelle_trupp_hat_einsatzkraft.moveToNext()) {
                    int einsatzkraft_id = tabelle_trupp_hat_einsatzkraft.getInt(1);
                    query = "SELECT * FROM " + TABELLE_EINSATZKRAFT + " WHERE " + EINSATZKRAFT_ID + " = " + einsatzkraft_id;
                    Cursor einsatzkraft = IODatabase.rawQuery(query, null);
                    einsatzkraft.moveToNext();
                    String name = einsatzkraft.getString(1);
                    String funktion = einsatzkraft.getString(2);
                    int pag_id = einsatzkraft.getInt(3);

                    einsatzkraft.close();

                    query = "SELECT * FROM " + TABELLE_PAGeraet + " WHERE " + PAGeraet_ID + " = " + pag_id;
                    Cursor PAG = IODatabase.rawQuery(query, null);
                    PAG.moveToNext();
                    int druckStart = PAG.getInt(1);
                    int druckZiel = PAG.getInt(2);
                    int druckRueckzugsoll = PAG.getInt(3);
                    int druckRueckzugist = PAG.getInt(4);
                    int druckKontrolle1o3 = PAG.getInt(5);
                    int druckKontrolle2o3 = PAG.getInt(6);
                    int druckEnde = PAG.getInt(7);

                    PAGeraet pag = null;

                    PAG.close();


                    try {
                        pag = new PAGeraet(300);
                        pag.setzeDruck(START, druckStart);
                        pag.setzeDruck(ZIEL, druckZiel);
                        pag.setzeDruck(Status.RUECKZUGSOLL, druckRueckzugsoll);
                        pag.setzeDruck(RUECKZUGIST, druckRueckzugist);
                        pag.setzeDruck(Status.KONTROLLE1o3, druckKontrolle1o3);
                        pag.setzeDruck(Status.KONTROLLE2o3, druckKontrolle2o3);
                        pag.setzeDruck(ENDE, druckEnde);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Einsatzkraft einsatzkraft1 = new Einsatzkraft(name, Funktion.getFromString(funktion), pag);
                    templiste.add(einsatzkraft1);

                }

                tabelle_trupp_hat_einsatzkraft.close();

                Trupp trupp = new Trupp(truppName, templiste.get(0), templiste.get(1), templiste.get(2));
                trupp.set_zeitEnde(einsatzzzeit);
                trupp.set_zeitEinDrittel(einsatzzeiteindrittel);
                trupp.set_zeitZweiDrittel(einsatzzeiteinzweidrittel);
                trupp.set_uhrzeitStatus(uhrzeitStatus);
                trupp.set_maximaleEinsatzzeit(einsatzzeitMaximal);
                trupp.set_einsatzstatus(status_einsatzstatus);
                tempListeTrupps.add(trupp);
                tempMapTrupps.put(trupp, einsatzauftrag1);

            }

            einsatzinformationen = new Einsatzinformationen(einsatzstrasse, "", einsatznummer, rufgruppe, einheitsfuehrer, date, new HashMap<>(tempMapTrupps), new LinkedList<>(tempListeTrupps));
            tabelle_einsatzinformation_hat_trupps.close();
            IODatabase.setTransactionSuccessful();
        } finally {
            IODatabase.endTransaction();
        }
        return einsatzinformationen;
    }
}
