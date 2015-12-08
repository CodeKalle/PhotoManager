package controller;

import java.util.LinkedList;
import java.util.List;
import model.Album;

/**
 * Der AlbenController kuemmert sich um saemtliche Aufgaben, die sich um die
 * Verwaltung der Alben befassen.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung
 * @date 21.11.2015 by Danilo: Erstellen von Methoden von UserStory Album anlegen
 * @date 23.11.2015 by Tobias: Ändern der Methode systemSpeichern
 * @date 23.11.2015 by Danilo: Kommentare ergänzt
 * @date 24.11.2015 by Danilo: Methoden createNewAlbum und editAlbum ergänzt
 * @date 25.11.2015 by Danilo: Methoden getAlbumList und deleteListOfAlbum ergänzt
 * @date 28.11.2015 by Tobias: Methode editAlbumTitle ergänzt
 * @date 29.11.2015 by Danilo: Methode createNewAlbum und editAlbum ergänzt
 * @date 01.12.2015 by Danilo: Fehlerkorrektur
 * @date 02.12.2015 by Tobias: Methode getAlbum auf public gesetzt
 * @date 02.12.2015 by Tobias: Methode editAlbumTitle ergänzt
 * @date 04.12.2015 by Danilo: Fehlerkorrektur bei zu kurzen Albentiteln, Beschreibung und Sortierkennzeichen
 * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
 * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
 */
public class AlbenController {
 
    /**
    * GUI-Methode
    * Diese Methode erstellt eine Stringliste aller Alben im System
    * 
    * @return Liste aller Alben
    * 
    * Version-History:
    * @date 25.11.2015 by Danilo: Initialisierung
    */
    public static List<String> getAlbumList() {
        List<String> albumlist = new LinkedList();
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            albumlist.add(tmpAlbum.getTitel());
        }
        return albumlist;
    }
    
    /**
    * GUI-Methode
    * Diese Methode erstellt im AlbumContainer ein Album mit Titel, Beschreibung und Sortierkennzeichen
    * 
    * @param title Titel des Albums welches erstellt werden soll
    * @param beschreibung Beschreibung des Albums welches erstellt werden soll
    * @param sortierkennzeichen Sortierkennzeichen des Albums welches erstellt werden soll
    * @return Fehlercode zur Auswertung
    * 
    * Version-History:
    * @date 24.11.2015 by Danilo: Initialisierung
    * @date 25.11.2015 by Danilo: Initialisierung Stringprüfung und Fehlerbehandlung
    * @date 29.11.2015 by Danilo: Fehlerkorrektur bei Fehler´haftenversuch ein Album anzulegen
    * @date 01.12.2015 by Danilo: Fehlerkorrektur
    * @date 04.12.2015 by Danilo: Fehlerkorrektur bei zu kurzen Albentiteln
    * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int und transparente Speicherung
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    public static int createNewAlbum(String title, String beschreibung, int sortierkennzeichen) {
        // Prüft zu kruze Albentitel
        if (title == null || title.length() <= 3) return ErrorController.addDebugReport(115);
        if (beschreibung == null) return ErrorController.addDebugReport(116);
        
        // Prüfen der Eingabe
        if (title.length() > 20) title = title.substring(0,20);
        if (beschreibung.length() > 200) beschreibung = beschreibung.substring(0,200);
        if (sortierkennzeichen < 0 || sortierkennzeichen > 2) sortierkennzeichen = 0;
        
        if (createAlbum(title)!=0) return ErrorController.addDebugReport(110);
        if (editAlbumBeschreibung(title, beschreibung)!=0) return ErrorController.addDebugReport(120);
        if (editAlbumSortierkennzeichen(title, sortierkennzeichen)!=0) return ErrorController.addDebugReport(130);
        
        // Speichern des Systemes
        return SystemController.loadOrSave(false);
    }
    
    /**
    * GUI-Methode
    * Diese Methode verändert im AlbumContainer ein bestehendes Album in Form von Titel, Beschreibung und Sortierkennzeichen
    * 
    * @param title Titel des Albums welches geändert werden soll
    * @param newTitle Neuer Titel des Albums
    * @param beschreibung Neue Beschreibung des Albums
    * @param sortierkennzeichen Neues Sortierkennzeichen des Albums
    * 
    * Version-History:
    * @return Fehlercode zur Auswertung
    * @date 24.11.2015 by Danilo: Initialisierung
    * @date 25.11.2015 by Danilo: Initialisierung Stringprüfung und Fehlerbehandlung
    * @date 29.11.2015 by Danilo: Fehlerkorrektur bei Fehler´haftenversuch ein Album anzulegen
    * @date 01.12.2015 by Danilo: Fehlerkorrektur
    * @date 04.12.2015 by Danilo: Fehlerkorrektur bei zu kurzen Albentiteln
    * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int und transparente Speicherung
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    public static int editAlbum(String title, String newTitle, String beschreibung, int sortierkennzeichen) {
        // Prüft zu kruze Albentitel
        if (newTitle == null || newTitle.length() <= 3) return ErrorController.addDebugReport(155);
        if (beschreibung == null) return ErrorController.addDebugReport(156);
        
        // Prüfen der Eingabe
        if (title.length() > 20) title = title.substring(0,20);
        if (newTitle.length() > 20) newTitle = newTitle.substring(0,20);
        if (beschreibung.length() > 200) beschreibung = beschreibung.substring(0,200);
        if (sortierkennzeichen < 0 || sortierkennzeichen > 2) sortierkennzeichen = 0;
        
        if (editAlbumTitle(title, newTitle)!=0) return ErrorController.addDebugReport(150);
        if (editAlbumBeschreibung(newTitle, beschreibung)!=0) return ErrorController.addDebugReport(160);
        if (editAlbumSortierkennzeichen(newTitle, sortierkennzeichen)!=0) return ErrorController.addDebugReport(170);
        
        // Speichern des Systems
        return SystemController.loadOrSave(false);
    }
    
    /**
    * GUI-Methode
    * Diese Methode löscht eine Liste von Alben
    * 
    * @param titlelist Liste der Albentitel die gelöscht werden sollen
    * @return Rückgabe der Anzahl der nicht vorhandenen Alben
    * 
    * Version-History:
    * @date 25.11.2015 by Danilo: Initialisierung
    * @date 07.12.2015 by Danilo: Transparente Speicherung
    * @date 08.12.2015 by Danilo: Änderung des Fehlercodes und einfügen eines Fehlerloggingsystemes
    */
    public static int deleteListOfAlbum(List<String> titlelist) {
        int errorcode = 0;
        for (String title : titlelist) {
            errorcode += deleteAlbum(title);
        }
        if (errorcode==0) return SystemController.loadOrSave(false);
        return ErrorController.addDebugReport(300);
    }
    
    /**
    * Diese Methode löscht im AlbumContainer ein Album
    * 
    * @param title Titel des Albums welches gelöscht werden soll
    * @return Fehlercode zur Auswertung <br> 0 = Album wurde gelöscht <br> 1 = Album nicht vorhanden
    * 
    * Version-History:
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Rückgabewert geändert und Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    * @date 25.11.2015 by Danilo: Methode auf private gesetzt
    * @date 01.12.2015 by Danilo: Fehlerkorrektur
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    private static int deleteAlbum(String title) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {
                FotoController.deleteAllFotosInAlbum(tmpAlbum);
                SystemController.getAlbumContainer().delete(tmpAlbum);
                return 0;
            }
        }
        return ErrorController.addDebugReport(310);
    }
    
    /**
    * Methode sucht nach einem Album und gibt dieses zurück
    * 
    * @param title Übergabe des gesuchten Albumtitels
    * @return Rückgabe des Albums, wenn keins gefunden dann null
    * 
    * Version-History:
    * @date 24.11.2015 by Danilo: Initialisierung
    * @date 02.12.2015 by Tobias: Setzten auf public
    * @date 04.12.2015 by Danilo: Fehlerkorrektur bei zu kurzen Albentiteln
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    public static Album getAlbum(String title) {
        // Prüft zu kruze Albentitel
        if (title == null || title.length() <= 3) return null;
        
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {  
                return tmpAlbum;
            }
        }
        ErrorController.addDebugReport(100);
        return null;
    }
    
    /**
    * Diese Methode erstellt im AlbumContainer ein Album
    * 
    * @param title Titel vom Album
    * @return Fehlercode zur Auswertung
    * 
    * Version-History:
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    * @date 01.12.2015 by Danilo: Fehlerkorrektur
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    private static int createAlbum(String title){
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {  
                return ErrorController.addDebugReport(320);
            }
        }
        Album newAlbum = new Album(title);
        SystemController.getAlbumContainer().add(newAlbum);
        return 0;
    }
    
    /**
    * Diese Methode ändert im AlbumContainer den Namen eines Albums
    * 
    * @param title Titel des Albums welches geändert werden soll
    * @param newTitle Neuer Titel des Albums
    * 
    * Version-History:
    * @return Fehlercode zur Auswertung <br> 0 = Albumtitel wurde geändert <br> 1 = Album nicht vorhanden <br> 2 = neuer Albumtitel schon vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    * @date 28.11.2015 by Tobias: Prüfung von newTitel hinzugefügt
    * @date 01.12.2015 by Danilo: Fehlerkorrektur
    * @date 02.12.2015 by Tobias: Prüfen ob die Titel gleich sind
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    private static int editAlbumTitle(String title, String newTitle) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            //Wenn die Titel gleich sind, muss nichts geändert werden
            if(title.equals(newTitle)) {
                return 0;
            }
            //Wenn die Titel unterschiedlich sind:
            else {
                //Prüfen ob neuer Titel schon vergeben ist
                if (tmpAlbum.getTitel().equals(newTitle)) {
                    return ErrorController.addDebugReport(331);
                }
                if (tmpAlbum.getTitel().equals(title)) { 
                    tmpAlbum.setTitel(newTitle);
                   return 0;
                }
            }
        }
        return ErrorController.addDebugReport(330);
    }
    
    /**
    * Diese Methode ändert im AlbumContainer die Beschreibung eines Albums
    * 
    * @param title Titel des Albums welches geändert werden soll
    * @param beschreibung Neue Beschreibung des Albums
    * 
    * Version-History:
    * @return Fehlercode zur Auswertung <br> 0 = Albumbeschreibung wurde geändert <br> 1 = Album nicht vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    * @date 01.12.2015 by Danilo: Fehlerkorrektur
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    private static int editAlbumBeschreibung(String title, String beschreibung) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) { 
                tmpAlbum.setBeschreibung(beschreibung);
                return 0;
            }
        }
        return ErrorController.addDebugReport(340);
    }
    
    /**
    * Diese Methode ändert im AlbumContainer das Sortierkennzeichen eines Albums
    * 
    * @param title Titel des Albums welches geändert werden soll
    * @param sortierkennzeichen Neues Sortierkennzeichen des Albums
    * @return Fehlercode zur Auswertung <br> 0 = Albumsortierkennzeichen wurde geändert <br> 1 = Album nicht vorhanden
    * 
    * Version-History:
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    * @date 01.12.2015 by Danilo: Fehlerkorrektur
    * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
    * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
    */
    private static int editAlbumSortierkennzeichen(String title, int sortierkennzeichen) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) { 
                tmpAlbum.setSortierkennzeichen(sortierkennzeichen);
                return 0;
            }
        }
        return ErrorController.addDebugReport(350);
    }
}
