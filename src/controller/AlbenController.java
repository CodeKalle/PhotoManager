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
 */
public class AlbenController {
    
    /**
    * Diese Methode erstellt im AlbumContainer ein Album
    * 
    * Version-History:
    * @param title Titel vom Album
    * @return Fehlercode zur Auswertung
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Kommentar angepasst
    */
    public int createAlbum(String title){
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
    * Diese Methode löscht im AlbumContainer ein Album
    * 
    * Version-History:
    * @param title Titel des Albums welches gelöscht werden soll
    * @return Fehlercode zur Auswertung <br> 0 = Album wurde gelöscht <br> 1 = Album nicht vorhanden
    * @date 21.11.2015 by Danilo: Initialisierung
    * @date 23.11.2015 by Danilo: Rückgabewert geändert und Kommentar angepasst
    */
    public int deleteAlbum(String title){
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
    */
    public int editAlbumTitle(String title, String newTitle){
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
    */
    public int editAlbumBeschreibung(String title, String beschreibung){
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
    */
    public int editAlbumSortierkennzeichen(String title, String sortierkennzeichen){
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) { 
                tmpAlbum.setSortierkennzeichen(sortierkennzeichen);
                return 0;
            }
        }
        return 1;
    }
}
