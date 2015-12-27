package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Der ErrorController realisiert die Fehlerbehandlung und Umsetzung zur Eindeutigen
 * Beschreibung für den Benutzer
 * 
 * Version-History:
 * @date 20.11.2015 by Danilo: Initialisierung
 * @date 01.12.2015 by Danilo: Klasse dient nur zur Umsetzung Fehlercode zu
 * @date ??.12.2015 by Andrea: String
 * @date 04.12.2015 by Danilo: Fehlerkorrektur bei zu kurzen Albentiteln,
 * @date ??.12.2015 by Andrea: Beschreibung und Sortierkennzeichen
 * @date 06.12.2015 by Andrea: Fehlende Fehlerbeschreibung komplett hinzugefügt
 * @date 07.12.2015 by Danilo: Kommentierung geändert
 * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
 */
public class ErrorController {
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 08.12.2015 by Danilo: Initialisierung
     */
    private static List<String> debugReport = new LinkedList<String>();
    
    /**
     * Methode fügt der Debugliste einen Eintrag hinzu
     * 
     * @param errorcode Fehlercode der mitgeloggt werden muss.
     * @return Weitergabe des Fehlercodes
     * 
     * Version-History:
     * @date 08.12.2015 by Danilo: Initialisierung
     */
    public static int addDebugReport(int errorcode){
        // Aktuelle Uhrzeit erstellen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String uhrzeit = sdf.format(new Date());

        // Aufbereitung des Stings
        String text = uhrzeit + "   " + String.valueOf(errorcode) + "   ";
        text = text + changeErrorCode(errorcode)[0] + "   " + changeErrorCode(errorcode)[1];
        
        // Speichern der Fehlermeldung
        debugReport.add(text);
        
        return errorcode;
    }
    
    /**
     * Methode hollt einen Eintrag aus der Debugliste und löscht diesen
     * 
     * @return 
     * 
     * Version-History:
     * @date 08.12.2015 by Danilo: Initialisierung
     */
    public static String getDebugReport(){
        String output = debugReport.remove(0);
        return output;
    }
    
    /**
     * Methode hollt Status der Debugliste
     * 
     * @return Wahrheitswert über Status LEER?
     * 
     * Version-History:
     * @date 08.12.2015 by Danilo: Initialisierung
     */
    public static boolean isDebugReportEmpty(){
	return debugReport.isEmpty();
    }
    
    /**
     * Methode wandelt Code in Text um und generiert zum Fehlercodes die
     * Nachrichtenbox. opt: 0=Error 1=Info 2=Warning 3=Question
     *
     * @param errorcode Eingabe des Fehlercodes
     * @return Rückgabe des gedrückten Buttons der Fehlermeldung als Integer
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 24.11.2015 by Danilo: Änderung lokaler Variablen, Änderung der
     * @date ??.12.2015 by Andrea: Kommentierung
     * @date 30.11.2015 by Danilo: Anpassung an SystemController
     * @date 06.12.2015 by Tobias: Merge verkackt, manuelles zurücksetzten
     * @date 07.12.2015 by Danilo: Kommentierung geändert
     * @date 14.12.2015 by Danilo: Update 455
     * @date 27.12.2015 by Andrea: Fehlerkommentierung
     */
    public static String[] changeErrorCode(int errorcode) {
        String title;
        String text;
        switch (errorcode) {
            case 100:
                title = "Album-Fehler";
                text = "Album wurde in Datenbank nicht gefunden.";
                break;
            case 110:
                title = "Albumanlege-Fehler";
                text = "Albumtitel wurde bereits für ein Album verwendet.";
                break;
            case 115:
                title = "Albumanlege-Fehler";
                text = "Albumtitel zu kurz.";
                break;
            case 116:
                title = "Albumanlege-Fehler";
                text = "Albumbeschreibung nicht initialisiert.";
                break;
            case 120:
                title = "Albumanlege-Fehler";
                text = "Albumbeschreibung konnte nach Anlegen nicht gesetzt werden.";
                break;
            case 130:
                title = "Albumanlege-Fehler";
                text = "Sortierkennzeichen konnte nach Anlegen nicht gesetzt werden.";
                break;
            case 150:
                title = "Albumänderungs-Fehler";
                text = "Albumtitel wurde entweder schon für ein weiteres Album gesetzt\n "
                        + "oder Album wurde nicht gefunden.";
                break;
            case 155:
                title = "Albumänderungs-Fehler";
                text = "Albumtitel zu kurz.";
                break;
            case 156:
                title = "Albumänderungs-Fehler";
                text = "Albumbeschreibung nicht initialisiert.";
                break;
            case 160:
                title = "Albumänderungs-Fehler";
                text = "Albumbeschreibung konnte bei Änderung nicht gesetzt werden.";
                break;
            case 170:
                title = "Albumänderungs-Fehler";
                text = "Sortierkennzeichen konnte bei Änderung nicht gesetzt werden.";
                break;
            case 210:
                title = "Fotosortier-Fehler";
                text = "Sortierung enthält fehlerhafte Parameter.";
                break;    
            case 300:
                title = "Albumlösch-Fehler";
                text = "Mehrere Alben konnten nicht gelöscht werden.";
                break;
            case 310:
                title = "Albumlösch-Fehler";
                text = "Album wurde nicht gefunden.";
                break;
            case 320:
                title = "Albumerstellen-Fehler";
                text = "Albumtitel wurde bereitsfür ein Album verwendet.";
                break;
            case 330:
                title = "Albumtiteländerungs-Fehler";
                text = "Album wurde nicht gefunden.";
                break;
            case 331:
                title = "Albumtiteländerungs-Fehler";
                text = "Albumtitel wurde schon für ein weiteres Album gesetzt.";
                break;
            case 332:
                title = "Albumtiteländerungs-Fehler";
                text = "Albumtitel wird schon verwendet.";
                break;
            case 340:
                title = "Albumbeschreibungänderungs-Fehler";
                text = "Albumbeschreibung konnte nicht gesetzt werden.";
                break;
            case 350:
                title = "Albumsortierkennzeichenänderungs-Fehler";
                text = "Sortierkennzeichen konnte nicht gesetzt werden.";
                break;
            case 410:
                title = "Fotospeicher-Fehler";
                text = "Fotos konnten nicht korrekt dem Album hinzugefügt werden.";
                break;
            case 420:
                title = "Fotospeicher-Fehler";
                text = "Die Liste der zuzufügenden Albenpfade ist Leer.";
                break;
            case 430:
                title = "Fotospeicher-Fehler";
                text = "Album zum zugehörigen Titel konnte nicht gefunden werden.";
                break;
            case 450:
                title = "Fotolösch-Fehler";
                text = "Nicht alle Fotos konnten aus dem Album entfernt werden.";
                break;
            case 455:
                title = "Fotolösch-Fehler";
                text = "Ein Foto konnte nicht aus dem Fotocontainer gelöscht werden.";
                break;
            case 510:
                title = "Metadatenfehler";
                text = "Metadaten konnten nicht gesetzt werden.";
                break;
            case 800:
                title = "Datenbankfehler";
                text = "Datenbank ist schreibgeschützt!";
                break;
            case 801:
                title = "Datenbankfehler";
                text = "Zugriff auf Datenbank nicht möglich!";
                break;
            case 806:
                title = "Datenbank-Ladefehler";
                text = "Datenbanksystem konnte nicht ordnungsgemäß geladen werden.";
                break;
            case 810:
                title = "Systeminitialisierung";
                text = "Das System konnte nicht richtig initialisiert werden.";
                break;
            case 820:
                title = "System-Speicherfehler";
                text = "Die Dateien zum Speichern der Datenbank konnten nichtgeöffnet werden.";
                break;
            case 821:
                title = "System-Speicherfehler";
                text = "Das System konnte die Ausgabestreams zur Albendatenbank nicht schließen.";
                break;
            case 822:
                title = "System-Speicherfehler";
                text = "Das System konnte die Ausgabestreams zur Albendatenbank nicht schließen.";
                break;
            case 830:
                title = "System-Ladefehler";
                text = "Das System konnte Objekt der Albendatenbank nicht finden.";
                break;
            case 831:
                title = "System-Ladefehler";
                text = "Das System konnte die Albendatenbankdatei nicht in das Albendatenbankobjekt lesen.";
                break;
            case 832:
                title = "System-Ladefehler";
                text = "Das System konnte den Lesestream zur Albendatenbank nicht schließen.";
                break;
            case 833:
                title = "System-Ladefehler";
                text = "Das System konnte den Lesestream zur Albendatenbank nicht öffnen.";
                break;
            case 834:
                title = "System-Ladefehler";
                text = "Das System konnte den Lesestream zur Albendatenbank nicht schließen.";
                break;
            case 835:
                title = "System-Ladefehler";
                text = "Das System konnte den Lesestream zur Albendatenbank nicht schließen.";
                break;
            case 850:
                title = "Dateifehler";
                text = "Datei/Pfadname zu kurz.";
                break;
            default:
                title = "Fehler!";
                text = "Fehlercode [" + errorcode + "] ist nicht verfügbar!";
                break;
        }
        return new String[]{title, text};
    }
}
