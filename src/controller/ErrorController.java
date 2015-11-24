package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Der ErrorController realisiert die Fehlerbehandlung und Umsetzung zur Eindeutigen
 * Beschreibung für den Benutzer
 * 
 * Version-History:
 * @date 20.11.2015 by Danilo: Initialisierung
 */
public class ErrorController {
    
    /**
     * Methode generiert die Nachrichtenboxen.
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    private static int generateMessageBox(String title, String text, String[] buttons, int opt){
        return JOptionPane.showOptionDialog(null, title, text, JOptionPane.DEFAULT_OPTION, opt, null, buttons, buttons[0]);
    }
    
    /**
     * Methode wandelt Code in Text um und generiert zum Fehlercodes die Nachrichtenbox.
     * opt: 0=Error
     *      1=Info
     *      2=Warning
     *      3=Question
     * 
     * Version-History:
     * @param errorcode Eingabe des Fehlercodes
     * @return Rückgabe des gedrückten Buttons der Fehlermeldung als Integer
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 24.11.2015 by Danilo: Änderung lokaler Variablen, Änderung der Kommentierung
     */
    public static int changeErrorCode(int errorcode){
        String title;
        String text;
        List<String> buttonlist = new ArrayList<>();
        int opt;
        switch (errorcode){
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
            case 802:
                title = "Datenbankfehler";
                text = "Wollen sie eine neue Datenbank anlegen?";
                opt = 0;
                buttonlist.add("Anlegen");
                buttonlist.add("Abbrechen");
                break;
            case 803:
                title = "Datenbankfehler";
                text = "Datenbank ist defekt!" + "\n" + "Wollen sie eine neue Datenbank anlegen?";
                opt = 0;
                buttonlist.add("Anlegen");
                buttonlist.add("Schließen");
                break;
            case 810:
                title = "Datenbank-Speicherfehler";
                text = "Datenbanksystem konnte nicht ordnungsgemäß gespeichert werden.";
                opt = 0;
                buttonlist.add("Abbrechen");
                break;
            case 811:
                title = "Datenbank-Ladefehler";
                text = "Datenbanksystem konnte nicht ordnungsgemäß geladen werden.";
                opt = 0;
                buttonlist.add("OK");
                break;
            case 815:
                title = "Systeminitialisierung";
                text = "Das System konnte nicht richtig initialisiert werden.";
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
