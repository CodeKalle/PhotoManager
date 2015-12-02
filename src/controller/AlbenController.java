package controller;

import static controller.ErrorController.changeErrorCode;
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
 * @date 02.12.2015 by Tobias: Methode getAlbum auf public gesetzt
 * @date 02.12.2015 by Tobias: Methode editAlbumTitle ergänzt
 */
public class AlbenController {
 
    /**
    * GUI-Methode
    * Diese Methode erstellt eine Stringliste aller Alben im System
    * 
    * Version-History:
    * @return Liste aller Alben
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
    * Version-History:
    * @param title Titel des Albums welches erstellt werden soll
    * @param beschreibung Beschreibung des Albums welches erstellt werden soll
    * @param sortierkennzeichen Sortierkennzeichen des Albums welches erstellt werden soll
    * @return Album welches erstellt wurde [+Fehlermeldung!!!]
    * @date 24.11.2015 by Danilo: Initialisierung
    * @date 25.11.2015 by Danilo: Initialisierung Stringprüfung und Fehlerbehandlung
    * @date 29.11.2015 by Danilo: Fehlerkorrektur bei Fehler´haftenversuch ein Album anzulegen
    */
    public static Album createNewAlbum(String title, String beschreibung, String sortierkennzeichen) {
        // Prüfen der Eingabe
        if (title.length() > 20) title = title.substring(0,20);
        if (beschreibung.length() > 200) beschreibung = beschreibung.substring(0,200);
        if (sortierkennzeichen.length() > 20) sortierkennzeichen = sortierkennzeichen.substring(0,20);
        
        int errorcode = createAlbum(title);
        if(errorcode!=0) {
            changeErrorCode(0);
            return null;
        }
        errorcode += editAlbumBeschreibung(title, beschreibung);
        errorcode += editAlbumSortierkennzeichen(title, sortierkennzeichen);
        if (errorcode!=0) {
            changeErrorCode(0);
        }
        return getAlbum(title);
    }
    
    /**
    * GUI-Methode
    * Diese Methode verändert im AlbumContainer ein bestehendes Album in Form von Titel, Beschreibung und Sortierkennzeichen
    * 
    * Version-History:
    * @param title Titel des Albums welches geändert werden soll
    * @param newTitle Neuer Titel des Albums
    * @param beschreibung Neue Beschreibung des Albums
    * @param sortierkennzeichen Neues Sortierkennzeichen des Albums
    * @return Album welches geändert wurde [+Fehlermeldung!!!]
    * @date 24.11.2015 by Danilo: Initialisierung
    * @date 25.11.2015 by Danilo: Initialisierung Stringprüfung und Fehlerbehandlung
    * @date 29.11.2015 by Danilo: Fehlerkorrektur bei Fehler´haftenversuch ein Album anzulegen
    */
    public static Album editAlbum(String title, String newTitle, String beschreibung, String sortierkennzeichen) {
        // Prüfen der Eingabe
        if (title.length() > 20) title = title.substring(0,20);
        if (newTitle.length() > 20) newTitle = newTitle.substring(0,20);
        if (beschreibung.length() > 200) beschreibung = beschreibung.substring(0,200);
        if (sortierkennzeichen.length() > 20) sortierkennzeichen = sortierkennzeichen.substring(0,20);
        
        int errorcode = editAlbumTitle(title, newTitle);
        if(errorcode!=0) {
            changeErrorCode(0);
            return null;
        }
        errorcode += editAlbumBeschreibung(newTitle, beschreibung);
        errorcode += editAlbumSortierkennzeichen(newTitle, sortierkennzeichen);
        if (errorcode!=0) {
            changeErrorCode(0);
        }
        return getAlbum(newTitle);
    }
    
    /**
    * GUI-Methode
    * Diese Methode löscht eine Liste von Alben
    * 
    * Version-History:
    * @param titlelist Liste der Albentitel die gelöscht werden sollen
    * @return Rückgabe der Anzahl der nicht vorhandenen Alben
    * @date 25.11.2015 by Danilo: Initialisierung
    */
    public static int deleteListOfAlbum(List<String> titlelist) {
        int errorcode = 0;
        for (String title : titlelist) {
            errorcode += deleteAlbum(title);
        }
        return errorcode;
    }
    
    /**
    * Diese Methode löscht im AlbumContainer ein Album
    * 
    * Version-History:
    * @param title Titel des Albums welches gelöscht werden soll
    * @return Fehlercode zur Auswertung <br> 0 = Album wurde gelöscht <br> 1 = Album nicht vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Rückgabewert geändert und Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    * @date 25.11.2015 by Danilo: Methode auf private gesetzt
    */
    private static int deleteAlbum(String title) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {
                FotoController.deleteAllFotosInAlbum(tmpAlbum);
                SystemController.getAlbumContainer().delete(tmpAlbum);
                return 0;
            }
        }
        return 1;
    }
    
    /**
    * Methode sucht nach einem Album und gibt dieses zurück
    * 
    * Version-History:
    * @param title Übergabe des gesuchten Albumtitels
    * @return Rückgabe des Albums, wenn keins gefunden dann null
    * @date 24.11.2015 by Danilo: Initialisierung
    * @date 02.12.2015 by Tobias: Setzten auf public
    */
    public static Album getAlbum(String title) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {  
                return tmpAlbum;
            }
        }
        return null;
    }
    
    /**
    * Diese Methode erstellt im AlbumContainer ein Album
    * 
    * Version-History:
    * @param title Titel vom Album
    * @return Fehlercode zur Auswertung
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    */
    private static int createAlbum(String title){
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {  
                return 1;
            }
        }
        Album newAlbum = new Album(title);
        SystemController.getAlbumContainer().add(newAlbum);
        return 0;
    }
    
    /**
    * Diese Methode ändert im AlbumContainer den Namen eines Albums
    * 
    * Version-History:
    * @param title Titel des Albums welches geändert werden soll
    * @param newTitle Neuer Titel des Albums
    * @return Fehlercode zur Auswertung <br> 0 = Albumtitel wurde geändert <br> 1 = Album nicht vorhanden <br> 2 = neuer Albumtitel schon vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    * @date 28.11.2015 by Tobias: Prüfung von newTitel hinzugefügt
    * @date 02.12.2015 by Tobias: Prüfen ob die Titel gleich sind
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
                    return 2;
                }
                if (tmpAlbum.getTitel().equals(title)) { 
                    tmpAlbum.setTitel(newTitle);
                   return 0;
                }
            }
        }
        return 1;
    }
    
    /**
    * Diese Methode ändert im AlbumContainer die Beschreibung eines Albums
    * 
    * Version-History:
    * @param title Titel des Albums welches geändert werden soll
    * @param beschreibung Neue Beschreibung des Albums
    * @return Fehlercode zur Auswertung <br> 0 = Albumbeschreibung wurde geändert <br> 1 = Album nicht vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    */
    private static int editAlbumBeschreibung(String title, String beschreibung) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) { 
                tmpAlbum.setBeschreibung(beschreibung);
                return 0;
            }
        }
        return 1;
    }
    
    /**
    * Diese Methode ändert im AlbumContainer das Sortierkennzeichen eines Albums
    * 
    * Version-History:
    * @param title Titel des Albums welches geändert werden soll
    * @param sortierkennzeichen Neues Sortierkennzeichen des Albums
    * @return Fehlercode zur Auswertung <br> 0 = Albumsortierkennzeichen wurde geändert <br> 1 = Album nicht vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    */
    private static int editAlbumSortierkennzeichen(String title, String sortierkennzeichen) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) { 
                tmpAlbum.setSortierkennzeichen(sortierkennzeichen);
                return 0;
            }
        }
        return 1;
    }
}
