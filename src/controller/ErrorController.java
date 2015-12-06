package controller;

/**
 * Der ErrorController realisiert die Fehlerbehandlung und Umsetzung zur
 * Eindeutigen Beschreibung für den Benutzer
 *
 * Version-History:
 *
 * @date 20.11.2015 by Danilo: Initialisierung
 * @date 01.12.2015 by Danilo: Klasse dient nur zur Umsetzung Fehlercode zu String
 * @date 04.12.2015 by Danilo: Fehlerkorrektur bei zu kurzen Albentiteln, Beschreibung und Sortierkennzeichen
 */
public class ErrorController {

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
     * @date 06.12.2015 by Tobias: Merge verkackt, manuelles zurücksetzten
     */
    public static String[] changeErrorCode(int errorcode){
        String title;
        String text;
        switch (errorcode) {
            case 110:
                title = "Albumanlege-Fehler";
                text = "";
                break;
            case 115:
                title = "Albumanlege-Fehler";
                text = "Albumtitel zu kurz.";
                break;
            case 116:
                title = "Albumanlege-Fehler";
                text = "Albumbeschreibung nicht initialisiert.";
                break;
            case 117:
                title = "Albumanlege-Fehler";
                text = "Albumsortierkennzeichen nicht initialisiert.";
                break;
            case 120:
                title = "Albumanlege-Fehler";
                text = "";
                break;
            case 130:
                title = "Albumanlege-Fehler";
                text = "";
                break;
            case 150:
                title = "Albumänderungs-Fehler";
                text = "";
                break;
            case 155:
                title = "Albumänderungs-Fehler";
                text = "Albumtitel zu kurz.";
                break;
            case 156:
                title = "Albumänderungs-Fehler";
                text = "Albumbeschreibung nicht initialisiert.";
                break;
            case 157:
                title = "Albumänderungs-Fehler";
                text = "Albumsortierkennzeichen nicht initialisiert.";
                break;
            case 160:
                title = "Albumänderungs-Fehler";
                text = "";
                break;
            case 170:
                title = "Albumänderungs-Fehler";
                text = "";
                break;
            case 310:
                title = "Albumlösch-Fehler";
                text = "";
                break;
            case 320:
                title = "Albumerstellen-Fehler";
                text = "";
                break;
            case 330:
                title = "Albumtiteländerungs-Fehler";
                text = "";
                break;
            case 331:
                title = "Albumtiteländerungs-Fehler";
                text = "";
                break;
            case 340:
                title = "Albumbeschreibungänderungs-Fehler";
                text = "";
                break;
            case 350:
                title = "Albumsortierkennzeichenänderungs-Fehler";
                text = "";
                break;
            case 410:
                title = "Fotospeicher-Fehler";
                text = "";
                break;
            case 420:
                title = "Fotospeicher-Fehler";
                text = "";
                break;
            case 430:
                title = "Fotospeicher-Fehler";
                text = "";
                break;
            case 450:
                title = "Fotolösch-Fehler";
                text = "";
                break;
            case 510:
                title = "Metadatenfehler";
                text = "";
                break;
            case 800:
                title = "Datenbankfehler";
                text = "Datenbank ist schreibgeschützt!";
                break;
            case 801:
                title = "Datenbankfehler";
                text = "Zugriff auf Datenbank nicht möglich!";
                break;
            case 805:
                title = "Datenbank-Speicherfehler";
                text = "Datenbanksystem konnte nicht ordnungsgemäß gespeichert werden.";
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
                text = "";
                break;
            case 821:
                title = "System-Speicherfehler";		
                text = "";		
                break;
            case 822:
                title = "System-Speicherfehler";
                text = "";
                break;
            case 830:
                title = "System-Ladefehler";
                text = "";
                break;
            case 831:
                title = "System-Ladefehler";
                text = "";
                break;
            case 832:
                title = "System-Ladefehler";
                text = "";
                break;
            case 833:
                title = "System-Ladefehler";
                text = "";
                break;
            case 834:
                title = "System-Ladefehler";
                text = "";
                break;
            case 835:
                title = "System-Ladefehler";
                text = "";
                break;
            case 850:
                title = "Dateifehler";
                text = "";
                break;
            default:
                title = "Fehler!";
                text = "Fehlercode ist nicht verfügbar!";
                break;
        }
        return new String[]{title, text};
    }
}
