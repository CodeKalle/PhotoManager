package controller;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import model.Album;
import model.Foto;
import model.Metadaten;

/**
 * Der AlbenController kuemmert sich um saemtliche Aufgaben, die sich um die
 * Verwaltung der Fotos befassen.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung
 * @date 24.11.2015 by Danilo: Erstellen der Methoden zum hinzufügen einer Fotoliste zum Album
 * @date 25.11.2015 by Danilo: Erstellen der Methode getMetaFromFoto und deleteAllFotosInAlbum, sowie setzen der Diamantoperanten
 * @date 29.11.2015 by Danilo: Methode setMetaInFoto ergänzt
 * @date 01.12.2015 by Danilo: Fehlerkorrektur
 */
public class FotoController {
    /**
     * GUI-Methode
     * Diese Methode setzt die Metadaten eines Fotos
     * 
     * @param pathOfFoto Bilddateipfad eines Fotos
     * @param meta Metadaten eines Fotos
     * @return Fehlercode zur Auswertung
     * @date 25.11.2015 by Danilo: Initialisierung
     * @date 29.11.2015 by Danilo: Fehlerkorrektur in Methode
     * @date 01.12.2015 by Danilo: Fehlerkorrektur
     */
    public static int setMetaInFoto(Path pathOfFoto, Metadaten meta) {
        Foto searchFoto = generateFotoFromPath(pathOfFoto);
        searchFoto.setMetadata(meta);
        if(searchFoto.getMetadata()!=meta) {
            return 510;
        }
        return 0;
    }
    
    /**
     * GUI-Methode
     * Diese Methode gibt die Metadaten eines geforderten Fotos zurück
     * 
     * @param pathOfFoto Bilddateipfad eines Fotos
     * @return Metadaten des Fotos oder null
     * @date 25.11.2015 by Danilo: Initialisierung
     * @date 29.11.2015 by Danilo: Ändern der Rückgabe der Methode
     */
    public static Metadaten getMetaFromFoto(Path pathOfFoto) {
        Foto searchFoto = generateFotoFromPath(pathOfFoto);
        Metadaten tmpMeta = searchFoto.getMetadata();
        if (tmpMeta==null) {
            tmpMeta = new Metadaten();
        }
        return tmpMeta;
    }
    
    /**
     * GUI-Methode
     * Diese Methode gibt eine Liste der Pfade der Fotos in einem Album zurück
     * 
     * @param title Titel des Albums, aus dem die Fotos abgerufen werden
     * @return Liste der Bilddateipfade oder Leereliste
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    public static List<Path> getFotosFromAlbum(String title) {
        Album tmpAlbum = AlbenController.getAlbum(title);
        List<Foto> albumFotolist = tmpAlbum.getFotoListe();
        List<Path> listOfPathes = new LinkedList<>();
        for (Foto tmpFoto : albumFotolist) {
            listOfPathes.add(tmpFoto.getPfad());
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
     * @date 29.11.2015 by Danilo: Ändern der Rückgabe der Methode
     * @date 01.12.2015 by Danilo: Fehlerkorrektur
     */
    public static int addListOfFotosToAlbum(String title, List<Path> listOfPathes) {
        Album tmpAlbum = AlbenController.getAlbum(title);
        if(tmpAlbum!=null) {
            listOfPathes = cleanErrorInListOfFotos(listOfPathes);
            
            if(!listOfPathes.isEmpty()) {
                List<Foto> newFotoListe = createFotosFromList(listOfPathes);

                int addSize = addNewFotolistToExistingFotolist(tmpAlbum, newFotoListe);
                if (addSize != newFotoListe.size()) {
                    return 410;
                }
                return 0;
            } else {
                return 420;
            }
        } else {
            return 430;
        }
    }
    
    /**
     * Diese Methode erstellt aus der Pfadliste eine Fotoliste
     * 
     * @param listOfPathes Liste der Bilddateipfade
     * @return Liste von Fotos
     * @date 24.11.2015 by Danilo: Initialisierung
     */
    private static List<Foto> createFotosFromList(List<Path> listOfPathes) {
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
    private static List<Path> cleanErrorInListOfFotos(List<Path> listOfPathes) {
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
    private static Foto generateFotoFromPath(Path pathOfFoto) {
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
    private static Foto checkIfFotoExist(Foto foto) {
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
     * @date 06.12.2015 by Danilo: Fotolink zum Fotocontainer hinzufügen falls es neu generiert wurde
     */
    private static int addNewFotolistToExistingFotolist(Album album, List<Foto> newFotoListe) { 
        List<Foto> albumFotolist = album.getFotoListe();
        int oldSize = albumFotolist.size();
        for (Foto tmpFoto : newFotoListe) {
            if(tmpFoto.getCounter()==0) SystemController.getFotoContainer().getFotoMap().put(tmpFoto.hashCode(), tmpFoto);
            albumFotolist.add(tmpFoto);
            tmpFoto.setCounter(1);
        }
        return albumFotolist.size()-oldSize;
    }
    
    /**
     * Methode löscht alle Fotos aus dem Album
     * INFO: Protected da AlbenContainer diese nutzen muss
     * 
     * @param album Album aus welchem Fotos gelöscht werden sollen 
     * @return Fehlercode zum Auswerten
     * @date 25.11.2015 by Danilo: Initialisierung
     * @date 01.12.2015 by Danilo: Fehlerkorrektur
     */
    protected static int deleteAllFotosInAlbum(Album album){
        List <Foto> tmpFotolist = album.getFotoListe();
        for (Foto tmpFoto : tmpFotolist) {
            Foto checkFoto = SystemController.getFotoContainer().getFotoMap().get(tmpFoto.hashCode());
            // Fotocounter runterzählen da das Foto aus dem Album gelöscht wurde
            checkFoto.setCounter(-1);
            // Prüfen das Foto in kein anderes Album verlinkt ist
            if(checkFoto.getCounter()<1) {
                SystemController.getFotoContainer().getFotoMap().remove(tmpFoto.hashCode());
            }
        }
        // Alle Fotos aus Album löschen
        tmpFotolist.clear();
        if (!tmpFotolist.isEmpty()) return 450;
        
        return 0;
    }
}
