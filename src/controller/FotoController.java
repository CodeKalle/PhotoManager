package controller;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
 * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
 * @date 09.12.2015 by Danilo: Sortieralgorithmus implementiert, Kommentare ergänzt und setMeta und getMeta angepasst
 * @date 14.12.2015 by Danilo: Änderung an Methode addListOfFotosToAlbum [addNewFotolistInAlbum] und erstellen von deleteNotExistingFotosInListFromAlbum
 */
public class FotoController {
    /**
     * GUI-Methode
     * Diese Methode setzt die Metadaten eines Fotos
     * 
     * @param pathOfFoto Bilddateipfad eines Fotos
     * @param daten Map der Daten
     * @return Fehlercode zur Auswertung
     * 
     * Version-History:
     * @date 25.11.2015 by Danilo: Initialisierung
     * @date 29.11.2015 by Danilo: Fehlerkorrektur in Methode
     * @date 01.12.2015 by Danilo: Fehlerkorrektur
     * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
     * @date 09.12.2015 by Danilo: Zu setzeneden Wert in Map umgewandelt
     */
    public static int setMetaInFoto(Path pathOfFoto, Map<String, Object> daten) {
        // Sucht Foto im Container
        Foto searchFoto = generateFotoFromPath(pathOfFoto);
        
        // Holt zugehörige Metadaten
        Metadaten meta = searchFoto.getMetadata();
       
        if (meta == null) meta = new Metadaten();
        
        // Setzen der Daten und setzen im Foto
        meta.setDaten(daten);
        searchFoto.setMetadata(meta);
                
        // Prüft das Daten gesetzt wurden
        if(searchFoto.getMetadata().getDaten()!=daten) {
            return ErrorController.addDebugReport(510);
        }
        return 0;
    }
    
    /**
     * GUI-Methode (Eröerterung im 2. Sprint)
     * Diese Methode gibt die Metadaten eines geforderten Fotos zurück
     * 
     * @param pathOfFoto Bilddateipfad eines Fotos
     * @return Metadaten des Fotos oder null
     * 
     * Version-History:
     * @date 25.11.2015 by Danilo: Initialisierung
     * @date 29.11.2015 by Danilo: Ändern der Rückgabe der Methode
     * @date 09.12.2015 by Danilo: Rückgabewert zu Map umgewandelt
     */
    public static Map<String, Object> getMetaFromFoto(Path pathOfFoto) {
        // Foto aus Container holen
        Foto searchFoto = generateFotoFromPath(pathOfFoto);
        
        // Metdaten aus Foto holen
        Metadaten tmpMeta = searchFoto.getMetadata();
        
        // Falls keine Metadaten gesetzt, wird leere Metadaten generiert
        if (tmpMeta==null) {
            tmpMeta = new Metadaten();
        }
        return tmpMeta.getDaten();
    }
    
    /**
     * GUI-Methode
     * Diese Methode gibt eine Liste der Pfade der Fotos in einem Album zurück
     * 
     * @param title Titel des Albums, aus dem die Fotos abgerufen werden
     * @return Liste der Bilddateipfade oder Leereliste
     * 
     * Version-History:
     * @date 24.11.2015 by Danilo: Initialisierung
     * @date 09.12.2015 by Danilo: Sortieren der Fotos
     */
    public static List<Path> getFotosFromAlbum(String title) {
        // Album und Fotoliste holen
        Album tmpAlbum = AlbenController.getAlbum(title);
        List<Foto> albumFotolist = tmpAlbum.getFotoListe();
        
        // Sortieren der Fotoliste
        albumFotolist = sortListOfFotos(tmpAlbum.getSortierkennzeichen(), albumFotolist);
        
        // Pfadliste erzeugen
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
     * 
     * Version-History:
     * @date 24.11.2015 by Danilo: Initialisierung
     * @date 29.11.2015 by Danilo: Ändern der Rückgabe der Methode
     * @date 01.12.2015 by Danilo: Fehlerkorrektur
     * @date 07.12.2015 by Danilo: Anpassung der Fotoübergabeliste und transparente Speicherung
     * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
     * @date 09.12.2015 by Danilo: Kommentare ergänzt
     * @date 14.12.2015 by Danilo: Änderung der Zufügeroutine von Fotos
     */
    public static int addListOfFotosToAlbum(String title, List<Path> listOfPathes) {
        // Album holen
        Album tmpAlbum = AlbenController.getAlbum(title);
        if(tmpAlbum!=null) {
            
            // Fehlerhafte Fotos aus Liste löschen [Lesegeschützt oder nicht existent]
            listOfPathes = cleanErrorInListOfFotos(listOfPathes);
            
            // Prüft das min. 1 Foto lesbar oder existiert
            if(!listOfPathes.isEmpty()) {
                // Fotoliste aus Pfadliste generieren
                List<Foto> newFotoListe = createFotosFromList(listOfPathes);

                // Fotoliste im Album setzen
                int addSize = addNewFotolistInAlbum(tmpAlbum, newFotoListe);
                
                // Prüft das alle Fotos in Liste übernommen wurden
                if (addSize != newFotoListe.size()) {
                    return ErrorController.addDebugReport(410);
                }
                return SystemController.loadOrSave(false);
            } else {
                return ErrorController.addDebugReport(420);
            }
        } else {
            return ErrorController.addDebugReport(430);
        }
    }
    
    /**
     * GUI-Methode
     * Methode löscht alle Fotos in Album die nischt übergeben wurden
     * 
     * @param title Album welchem Fotos hinzugefügt werden sollen 
     * @param existingListOfPathes Liste der Fotos die hinzugefügt werden sollen
     * @return Fehlercode zur Auswertung
     * 
     * Version-History:
     * @date 14.11.2015 by Danilo: Initialisierung
     */
    public static int deleteNotExistingFotosInListFromAlbum(String title, List<Path> existingListOfPathes) {
        // Album holen
        Album tmpAlbum = AlbenController.getAlbum(title);
        if(tmpAlbum!=null) {
            // Falls übergebene Liste null oder leer werden alle Fotos aus Album gelöscht
            if (existingListOfPathes == null || existingListOfPathes.isEmpty()) {
                return deleteAllFotosInAlbum(tmpAlbum);
            }
            else {
                int count = 0;
                // Fotoliste holen und abgleichen
                List<Foto> albumFotolist = tmpAlbum.getFotoListe();
                List<Foto> albumNewFotolist = new LinkedList();
                for (Foto tmpFoto : albumFotolist) {
                    // Falls am Ende der Albenliste noch Fotos stehen und Merkliste leer ist, 
                    // müssen diese gelöscht werden oder falls die Pfade matchen
                    if(!(count>existingListOfPathes.size()-1) && tmpFoto.getPfad().equals(existingListOfPathes.get(count))) {
                        albumNewFotolist.add(tmpFoto);
                        count++;
                    }
                    else {
                        decrementFotoAndCheck(tmpFoto);
                    }
                }
                // Setzen der neuen Fotoliste
                tmpAlbum.setFotoListe(albumNewFotolist);
                return 0;
            }
        }
        return ErrorController.addDebugReport(430);
    }
    
    /**
     * Diese Methode erstellt aus der Pfadliste eine Fotoliste
     * 
     * @param listOfPathes Liste der Bilddateipfade
     * @return Liste von Fotos
     * 
     * Version-History:
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
     * 
     * Version-History:
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
     * 
     * Version-History:
     * @date 24.11.2015 by Danilo: Initialisierung
     * @date 06.12.2015 by Danilo: Ändern des Datentyp pfad zu String da Path nicht serialisierbar
     */
    private static Foto generateFotoFromPath(Path pathOfFoto) {
        String nameOfFoto = pathOfFoto.getFileName().toString();
        Foto newFoto = new Foto(nameOfFoto, pathOfFoto.toString());
        
        newFoto = checkIfFotoExist(newFoto);
        
        return newFoto;
    }
    
    /**
     * Diese Methode gibt das neue Foto oder bei Fund das existierende Foto zurück
     * 
     * @param foto Foto das gesucht werden soll.
     * @return Foto welches erstellt wurde oder existiert
     * 
     * Version-History:
     * @date 24.11.2015 by Danilo: Initialisierung
     * @date 06.12.2015 by Danilo: Fotolink zum Fotocontainer hinzufügen falls es neu generiert wurde
     * @date 09.12.2015 by Danilo: Kommenter ergänzt
     */
    private static Foto checkIfFotoExist(Foto foto) {
        Foto tmpFoto = SystemController.getFotoContainer().getFotoMap().get(foto.hashCode());
        if (tmpFoto != null) {
            // Übergebenes Foto auf gefundenes Foto setzen
            foto = tmpFoto;
        } else {
            // Im Fotocontainer das ertesllt Foto registrieren
            SystemController.getFotoContainer().getFotoMap().put(foto.hashCode(), foto);
        }
        return foto;
    }
    
    /**
     * Methode fügt der Albumfotoliste eine übergebene Fotoliste hinzu
     * 
     * @param album Album welchem Fotos hinzugefügt werden sollen 
     * @param newFotoListe Liste der Fotos die hinzugefügt werden sollen
     * @return Anzahl der Fotos die hinzugefügt wurden
     * 
     * Version-History:
     * @date 14.12.2015 by Danilo: Initialisierung
     */
    private static int addNewFotolistInAlbum(Album album, List<Foto> newFotoListe) { 
        // Setzen der neuen Liste
        List<Foto> albumFotolist = album.getFotoListe();
        int oldSize = albumFotolist.size();
        for (Foto tmpFoto : newFotoListe) {
            // Foto zum Album hinzufügen und Counter erhöhen
            albumFotolist.add(tmpFoto);
            tmpFoto.setCounter(1);
        }
        return albumFotolist.size()-oldSize;
    }
    
    /**
     * Methode sortiert die List der Fotoobjekte gemäß des Albumsortierkennzeichens mit Fehlerlogging
     * 
     * @param sort Sortierkennzeichen des Albums
     * @param fotoList Liste der Fotoelemente
     * 
     * Version-History:
     * @date 09.12.2015 by Danilo: Initialisierung
     */
    private static List<Foto> sortListOfFotos(int sort, List<Foto> fotoList){
        switch(sort)
        {
            case 0:     // Benutzerdefiniert
                return fotoList;
            case 1:     // nach Name
                // Sortieren der Liste nch Name in annonymer Klasse zum Code einzusparen
                Collections.sort(fotoList, new Comparator<Foto>() {
                    @Override
                    public int compare(final Foto obj1, final Foto obj2) {
                        return obj1.getName().compareTo(obj2.getName());
                    }
                });
                return fotoList;
            case 2:     // nach Datum
                // Sortieren der Liste nach Datum in annonymer Klasse zum Code einzusparen
                Collections.sort(fotoList, new Comparator<Foto>() {
                    @Override
                    public int compare(Foto obj1, Foto obj2) {
                        return Long.compare(obj1.getErstellungdatum(), obj2.getErstellungdatum());
                    }
                });
                return fotoList;
            default:    // nicht definierter Sortieralgorithmus
                ErrorController.addDebugReport(210);
                return fotoList;
        }
    }
    
    /**
     * Methode löscht alle Fotos aus dem Album
     * INFO: Protected da AlbenContainer diese nutzen muss
     * 
     * @param album Album aus welchem Fotos gelöscht werden sollen 
     * @return Fehlercode zum Auswerten
     * 
     * Version-History:
     * @date 25.11.2015 by Danilo: Initialisierung
     * @date 01.12.2015 by Danilo: Fehlerkorrektur
     * @date 08.12.2015 by Danilo: Einfügen eines Fehlerloggingsystemes
     * @date 14.12.2015 by Danilo: Foto löschen auslagern
     */
    protected static int deleteAllFotosInAlbum(Album album){
        List <Foto> tmpFotolist = album.getFotoListe();
        for (Foto tmpFoto : tmpFotolist) {
            decrementFotoAndCheck(SystemController.getFotoContainer().getFotoMap().get(tmpFoto.hashCode()));
        }
        // Alle Fotos aus Album löschen
        tmpFotolist.clear();
        if (!tmpFotolist.isEmpty()) return ErrorController.addDebugReport(450);
        
        return 0;
    }
    
    /**
     * Methode löscht decrementiert den Fotozähler und prüft Löschmöglichkeit
     * 
     * @param album Album aus welchem Fotos gelöscht werden sollen 
     * @return Fehlercode zum Auswerten
     * 
     * Version-History:
     * @date 14.12.2015 by Danilo: Initialisierung
     */
    private static int decrementFotoAndCheck(Foto foto) {
        // Fotocounter runterzählen
        foto.setCounter(-1);
        // Prüfen das Foto in kein anderes Album verlinkt ist und löschen
        if(foto.getCounter()<1) {
            SystemController.getFotoContainer().getFotoMap().remove(foto.hashCode());
        }
        if (SystemController.getFotoContainer().getFotoMap().remove(foto.hashCode())!=null) return ErrorController.addDebugReport(455);
        return 0;
    }
}
