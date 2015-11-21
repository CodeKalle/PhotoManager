package controller;

import model.Album;

/**
 * Der AlbenController kuemmert sich um saemtliche Aufgaben, die sich um die
 * Verwaltung der Alben befassen.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung
 */
public class AlbenController {
    
    /**
    * Diese Methode erstellt im AlbumContainer ein Album
    * 
    * Version-History:
    * @param title
    * @return 
    * @date 21.11.2015 by Danilo: Initialisierung
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
    * @param title
    * @return 
    * @date 21.11.2015 by Danilo: Initialisierung
    */
    public int deleteAlbum(String title){
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {
                SystemController.getAlbumContainer().delete(tmpAlbum);
                return 0;
            }
        }
        return 0;
    }
    
    /**
    * Diese Methode ändert im AlbumContainer den Namen eines Albums
    * 
    * Version-History:
    * @param title
    * @param newTitle
    * @return 
    * @date 21.11.2015 by Danilo: Initialisierung
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
    * @param title
    * @param beschreibung
    * @return 
    * @date 21.11.2015 by Danilo: Initialisierung
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
    * @param title
    * @param sortierkennzeichen
    * @return 
    * @date 21.11.2015 by Danilo: Initialisierung
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
