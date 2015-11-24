package controller;

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
 */
public class AlbenController {
    
    /**
    * GUI-Methode
    * Diese Methode erstellt im AlbumContainer ein Album mit Titel, Beschreibung und Sortierkennzeichen
    * 
    * Version-History:
    * @param title Titel des Albums welches erstellt werden soll
    * @param beschreibung Beschreibung des Albums welches erstellt werden soll
    * @param sortierkennzeichen Sortierkennzeichen des Albums welches erstellt werden soll
    * @return Album welches erstellt wurde
    * @date 24.11.2015 by Danilo: Initialisierung
    */
    public static Album createNewAlbum(String title, String beschreibung, String sortierkennzeichen){
        createAlbum(title);
        editAlbumBeschreibung(title, beschreibung);
        editAlbumSortierkennzeichen(title, sortierkennzeichen);
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
    * @return Album welches geändert wurde
    * @date 24.11.2015 by Danilo: Initialisierung
    */
    public static Album editAlbum(String title, String newTitle, String beschreibung, String sortierkennzeichen){
        editAlbumTitle(title, newTitle);
        editAlbumBeschreibung(newTitle, beschreibung);
        editAlbumSortierkennzeichen(newTitle, sortierkennzeichen);
        return getAlbum(newTitle);
    }
    
    /**
    * Methode sucht nach einem Album und gibt dieses zurück
    * INFO: Protected da FotoContainer in Alben schauen muss
    * 
    * Version-History:
    * @param title Übergabe des gesuchten Albumtitels
    * @return Rückgabe des Albums, wenn keins gefunden dann null
    * @date 24.11.2015 by Danilo: Initialisierung
    */
    protected static Album getAlbum(String title){
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
    * GUI-Methode
    * Diese Methode löscht im AlbumContainer ein Album
    * 
    * Version-History:
    * @param title Titel des Albums welches gelöscht werden soll
    * @return Fehlercode zur Auswertung <br> 0 = Album wurde gelöscht <br> 1 = Album nicht vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Rückgabewert geändert und Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    */
    public static int deleteAlbum(String title){
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {
                SystemController.getAlbumContainer().delete(tmpAlbum);
                return 0;
            }
        }
        return 1;
    }
    
    /**
    * Diese Methode ändert im AlbumContainer den Namen eines Albums
    * 
    * Version-History:
    * @param title Titel des Albums welches geändert werden soll
    * @param newTitle Neuer Titel des Albums
    * @return Fehlercode zur Auswertung <br> 0 = Albumtitel wurde geändert <br> 1 = Album nicht vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    * @date 24.11.2015 by Danilo: Methode auf static gesetzt
    */
    private static int editAlbumTitle(String title, String newTitle){
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) { 
                tmpAlbum.setTitel(newTitle);
                return 0;
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
    private static int editAlbumBeschreibung(String title, String beschreibung){
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
    private static int editAlbumSortierkennzeichen(String title, String sortierkennzeichen){
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) { 
                tmpAlbum.setSortierkennzeichen(sortierkennzeichen);
                return 0;
            }
        }
        return 1;
    }
}
