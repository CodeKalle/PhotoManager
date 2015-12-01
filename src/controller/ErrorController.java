package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Der ErrorController realisiert die Fehlerbehandlung und Umsetzung zur
 * Eindeutigen Beschreibung für den Benutzer
 *
 * Version-History:
 *
 * @date 20.11.2015 by Danilo: Initialisierung
 */
public class ErrorController {

    /**
     * Methode generiert die Nachrichtenboxen.
     *
     * Version-History:
     *
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    private static int generateMessageBox(String title, String text, String[] buttons, int opt) {
        return JOptionPane.showOptionDialog(null, title, text, JOptionPane.DEFAULT_OPTION, opt, null, buttons, buttons[0]);
    }

    /**
     * Methode wandelt Code in Text um und generiert zum Fehlercodes die
     * Nachrichtenbox. opt: 0=Error 1=Info 2=Warning 3=Question
     *
     * Version-History:
     *
     * @param errorcode Eingabe des Fehlercodes
     * @return Rückgabe des gedrückten Buttons der Fehlermeldung als Integer
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 24.11.2015 by Danilo: Änderung lokaler Variablen, Änderung der
     * Kommentierung
     * @date 30.11.2015 by Danilo: Anpassung an SystemController
     */
    public static int changeErrorCode(int errorcode) {
        String title;
        String text;
        List<String> buttonlist = new ArrayList<>();
        int opt;
        switch (errorcode) {
            case
            
            case 800:
                title = "Datenbankfehler";
                text = "Datenbank ist schreibgeschützt!";
                opt = 1;
                buttonlist.add("Abbrechen");
                break;
            case 801:
                title = "Datenbankfehler";
                text = "Zugriff auf Datenbank nicht möglich!";
                opt = 0;
                buttonlist.add("Abbrechen");
                break;
            case 805:
                title = "Datenbank-Speicherfehler";
                text = "Datenbanksystem konnte nicht ordnungsgemäß gespeichert werden.";
                opt = 0;
                buttonlist.add("Abbrechen");
                break;
            case 806:
                title = "Datenbank-Ladefehler";
                text = "Datenbanksystem konnte nicht ordnungsgemäß geladen werden.";
                opt = 0;
                buttonlist.add("OK");
                break;
            case 810:
                title = "Systeminitialisierung";
                text = "Das System konnte nicht richtig initialisiert werden.";
                opt = 0;
                buttonlist.add("OK");
                break;

            case 820:
                title = "Datenbank speichern";
                text = "Das System konnte Albendatenbank nicht schreiben.";
                opt = 0;
                buttonlist.add("OK");
                break;
            case 821:
            case 822:
                title = "Datenbank speichern";
                text = "Das System konnte die Ausgabestreams zur Albendatenbank nicht schließen.";
                opt = 0;
                buttonlist.add("OK");
                break;

            case 825:
                title = "Datenbank laden";
                text = "Das System konnte Objekt der Albendatenbank nicht finden.";
                opt = 0;
                buttonlist.add("OK");
                break;
            case 826:
                title = "Datenbank laden";
                text = "Das System konnte die Albendatenbankdatei nicht in das Albendatenbankobjekt lesen.";
                opt = 0;
                buttonlist.add("OK");
                break;
            case 828:
                title = "Datenbank laden";
                text = "Das System konnte den Lesestream zur Albendatenbank nicht öffnen.";
                opt = 0;
                buttonlist.add("OK");
                break;
            case 827:
            case 829:
            case 830:
                title = "Datenbank laden";
                text = "Das System konnte den Lesestream zur Albendatenbank nicht schließen.";
                opt = 0;
                buttonlist.add("OK");
                break;

            default:
                title = "Fehler!";
                text = "Fehlercode ist nicht verfügbar!";
                opt = 0;
                buttonlist.add("OK");
                break;
        }
        String[] buttons = buttonlist.toArray(new String[buttonlist.size()]);
        return generateMessageBox(title, text, buttons, opt);
    }
}
