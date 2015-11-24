package controller;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import model.Album;
import model.Foto;

/**
 * Der AlbenController kuemmert sich um saemtliche Aufgaben, die sich um die
 * Verwaltung der Fotos befassen.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung
 * @date 24.11.2015 by Danilo: Erstellen der Methoden zum hinzufügen einer Fotoliste zum Album
 */
public class FotoController {
    
    /**
     * GUI-Methode
     * Diese Methode gibt eine Liste der Pfade der Fotos in einem Album zurück
     * 
     * @param title Titel des Albums, aus dem die Fotos abgerufen werden
     * @return Liste der Fotos
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    public static List getFotosFromAlbum(String title){
        Album tmpAlbum = AlbenController.getAlbum(title);
        List albumFotolist = tmpAlbum.getFotoListe();
        List<Path> listOfPathes = new LinkedList<>();
        for (Object tmpFoto : albumFotolist) {
            Foto foto = (Foto) tmpFoto;
            listOfPathes.add(foto.getPfad());
        }
        return listOfPathes;
    }
    
    /**
     * GUI-Methode
     * Diese Methode fügt einem Album aus Biddateipfaden generierte Fotos hinzu
     * 
     * @param title Titel des Albums, dem die Fotos hinzugefügt werden
     * @param listOfPathes Liste der Bilddateipfade die dem Album als Foto hinzugefügt werden
     * @return Fehlercode zur Auswertung
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    public static int addListOfFotosToAlbum(String title, List<Path> listOfPathes) {
        Album tmpAlbum = AlbenController.getAlbum(title);
        if(tmpAlbum!=null)
        {
            listOfPathes = cleanErrorInListOfFotos(listOfPathes);
            
            if(!listOfPathes.isEmpty()){
                List<Foto> newFotoListe = createFotosFromList(listOfPathes);

                int addSize = addNewFotolistToExistingFotolist(tmpAlbum, newFotoListe);
                if (addSize != newFotoListe.size()) return 2;

                return 0;
            } else {
                return 3;
            }
        }
        return 1;
    }
    
    /**
     * Diese Methode erstellt aus der Pfadliste eine Fotoliste
     * 
     * @param listOfPathes Liste der Bilddateipfade
     * @return Liste von Fotos
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    private static List<Foto> createFotosFromList(List<Path> listOfPathes){
        List<Foto> fotolist = new LinkedList<>();
        for (Path tmpPath : listOfPathes) {
            fotolist.add(generateFotoFromPath(tmpPath));
        }
        return fotolist;
    }
    
    /**
     * Diese Methode löscht alle Bilddateien aus der übergebennen Fotoliste,
     * die für das Programm nicht Lesbar sind
     * 
     * @param listOfPathes Liste der Bilddateipfade die auf Leserecht überprüft werden soll
     * @return Geänderte Bilddateipfadliste
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    private static List<Path> cleanErrorInListOfFotos(List<Path> listOfPathes){
        for (int i = 0; i < listOfPathes.size(); i++) {
            File file = new File(listOfPathes.get(i).toString());     
            if (!file.canRead()) {
                listOfPathes.remove(i);
                i--;
            }
        }
        return listOfPathes;
    }
    
    /**
     * Diese Methode generiert ein Fotoobjekt aus einer Pfadangabe
     * 
     * @param pathOfFoto Pfad zur Bilddatei
     * @return Foto welches erstellt oder gefunden wurde
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    private static Foto generateFotoFromPath(Path pathOfFoto){
        String nameOfFoto = pathOfFoto.getFileName().toString();
        Foto newFoto = new Foto(nameOfFoto, pathOfFoto);
        
        newFoto = checkIfFotoExist(newFoto);
        
        return newFoto;
    }
    
    /**
     * Diese Methode gibt das neue Foto oder bei Fund das existierende Foto zurück
     * 
     * @param foto Foto das gesucht werden soll.
     * @return Foto welches erstellt wurde oder existiert
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    private static Foto checkIfFotoExist(Foto foto){
        Foto tmpFoto = SystemController.getFotoContainer().getFotoMap().get(foto.hashCode());
        if (tmpFoto != null)
        {
            foto = tmpFoto;
        }
        return foto;
    }
    
    /**
     * Methode fügt der Albumfotoliste eine übergebene Fotoliste hinzu
     * 
     * @param album Album welchem Fotos hinzugefügt werden sollen 
     * @param newFotoListe Liste der Fotos die hinzugefügt werden sollen
     * @return Anzahl der Fotos die hinzugefügt wurden
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    private static int addNewFotolistToExistingFotolist(Album album, List<Foto> newFotoListe) { 
        List albumFotolist = album.getFotoListe();
        int oldSize = albumFotolist.size();
        for (Foto tmpFoto : newFotoListe) {
            albumFotolist.add(tmpFoto);
            // Fotocounter hochzählen da das Foto ins Album verlinkt wurde
            tmpFoto.setCounter(1);
        }
        return albumFotolist.size()-oldSize;
    }
}
