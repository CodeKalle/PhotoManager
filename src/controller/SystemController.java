package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import model.AlbenContainer;
import model.FotoContainer;
import model.PmSystem;

/**
 * Der SystemController realisiert das Laden, speichern sowie die Datenhaltung.
 * 
 * Version-History:
 * @date 20.11.2015 by Danilo: Initialisierung
 * @date 21.11.2015 by Danilo: Änderungen
 * @date 23.11.2015 by Danilo: Kommentare ergänzt
 * @date 24.11.2015 by Danilo: Änderungen
 * @date 30.11.2015 by Danilo: Anpassung an GUI
 * @date 01.12.2015 by Danilo: Fehlerkorrektur
 * @date 04.12.2015 by Danilo: Änderungen
 * @date 06.12.2015 by Danilo: Änderungen
 * @date 07.12.2015 by Danilo: Anpassung an Systemtests
 */
public class SystemController {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 06.12.2015 by Danilo: Änderung des fileattributes
     */
    // Derzeit speichert das System die Datenbank da wo die ausführbare JAVA-Datei liegt 
    private static String filename = "pm.jdb";
    private static PmSystem pmSystem = new PmSystem();
    private static Object SystemControllerTest;
    
    /**
     * Methode startet das System mit standard Dateiinformation
     * 
     * Version-History:
     * @return Rückgabe zur Fehlerauswertung
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    public static int run() {
        return startSystem();
    }
    
    /**
     * Methode startet das System mit Dateiinformation
     * 
     * Version-History:
     * @param filename Dateiname zum Speichern
     * @return Rückgabe zur Fehlerauswertung
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    public static int run(String filename) {
        if (filename == null || filename.length() < 3) return 850;
        if (filename.length() > 20) filename = filename.substring(0,20);
        setFilename(filename);
        return startSystem();
    }
    
    /**
     * Methode realisiert die Generierung der benötigten Komponenten in Threads.
     * 
     * Version-History:
     * @return Rückgabe zur Fehlerauswertung
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 30.11.2015 by Danilo: Anpassung an GUI
     * @date 06.12.2015 by Danilo: Umbennenung,Anpassung an Test und setzen auf private
     * @date 07.12.2015 by Danilo: Anpassung an Systemtests
     */
    private static int startSystem() {
        File file = new File(filename);
        if (file.exists()) {
            if (file.canRead() == true) {
                int error = loadOrSave(true);
                if (error == 0) {    
                    if (file.canWrite() == false) return 800;
                } else {
                    return error;
                }
            } else {
                initializePmSystem();
                return 801;
            }
        } else {
                try {
                    file.createNewFile();
                    initializePmSystem();
                    if (systemSave()!=0) return 806;
                } catch (IOException e) { }
        }
        return 0;
    }
    
    /**
     * Prüft Zugriff auf Datei und lädt oder speichert das System.
     * 
     * Version-History:
     * @param mode true = Zum Laden,<br> false = Zum Speichern
     * @return Rückgabe zur Fehlerauswertung
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     * @date 30.11.2015 by Danilo: Anpassung an GUI
     * @date 07.12.2015 by Danilo: Anpassung an Systemtests und Umbenennung
     */
    protected static int loadOrSave(boolean mode){
        File file = new File(filename);
        if(mode) {
            if (file.canRead() == false) {
                    return 801;
                } else {
                    if (systemLoad() != 0) {
                        try {
                            file.createNewFile();
                            initializePmSystem();
                        } catch (IOException e) {}
                        return 806;
                    }
                    return 0;
                }
        } else {
            return systemSave();
        }
    }
    
    /**
     * Diese Methode initialisiert die Container des Systems.
     * Diese Methode wird nur benötigt falls das System keine Datenbank zum
     * laden findet.
     * 
     * Version-History:
     * @return Rückgabe zur Fehlerauswertung
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 30.11.2015 by Danilo: Anpassung an GUI
     * @date 04.12.2015 by Danilo: Public da Methode vom Test gebraucht wird
     * @date 07.12.2015 by Danilo: Anpassung an PmSystem
     */
    public static int initializePmSystem() {
        pmSystem = new PmSystem();
        if (pmSystem.getAlben().anzahlAlben()!=0 || pmSystem.getFotos().anzahlFotos()!=0) return 810;
        return 0;
    }
    
    /**
     * Diese Methode speichert das gesamte System.
     * 
     * Version-History:
     * @return Fehlercode zur Auswertung
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Tobias: Setzten der Methode auf private
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     * @date 24.11.2015 by Danilo: Methodenname geändert und lokale Variablen
     * @date 30.11.2015 by Danilo: Anpassen der Fehlercodes
     */
    private static int systemSave() {
        int errorcode = 0;
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            String filePath = filename;
            fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(pmSystem);
        }
        catch (IOException e) {
            errorcode = 820;
        }
        finally {
            if (oos != null) try { oos.close(); } catch (IOException e) {
                errorcode = 821;
            }
            if (fos != null) try { fos.close(); } catch (IOException e) {
                errorcode = 822;
            }
        }
        return errorcode;
    }
    
    /**
     * Diese Methode laed das System aus einer bestehenden Datei.
     * 
     * Version-History:
     * @return Fehlercode zur Auswertung
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     * @date 24.11.2015 by Danilo: Methodenname geändert und lokale Variablen
     * @date 30.11.2015 by Danilo: Anpassen der Fehlercodes
     * @date 01.12.2015 by Danilo: Fehlerkorrektur
     */
    private static int systemLoad() {
        int errorcode = 0;
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
            String filePath = filename;
            fis = new FileInputStream(filePath);
            try {
                ois = new ObjectInputStream(fis);
                try {
                    PmSystem tmpDB = (PmSystem) ois.readObject();
                    pmSystem = tmpDB;
                } catch (ClassNotFoundException e) {
                    errorcode = 830;
                }
            } catch (StreamCorruptedException e) {
                errorcode = 831;
            }
            finally {
                if (ois != null) try { ois.close(); } catch (IOException e) {
                    errorcode = 832;
                }
            }
        }
        catch (IOException e) {
                errorcode = 833;
        }
        finally {
            if (ois != null) try { ois.close(); } catch (IOException e) {
                errorcode = 834;
            }
            if (fis != null) try { fis.close(); } catch (IOException e) {
                errorcode = 835; 
            }
        }
	return errorcode;
    }
    
    /**
     * Dieser Setter setzt den Dateipfad.
     * 
     * Version-History:
     * @param filename Dateipfad
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    public static void setFilename(String filename) {
        SystemController.filename = filename;
    }

    /**
     * Dieser Getter holt den Dateipfad.
     * 
     * Version-History:
     * @return Rückgabe des Dateipfades
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    public static String getFilename() {
        return filename;
    }
    
    /**
     * Dieser Getter holt den Albencontainer.
     * 
     * Version-History:
     * @return Rückgabe der gesamten Albenliste des Systems
     * @date 21.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     * @date 06.12.2015 by Danilo: Status auf protected
     */
    protected static AlbenContainer getAlbumContainer(){
        return pmSystem.getAlben();
    }
    
    /**
     * Dieser Getter holt den Fotocontainer.
     * 
     * Version-History:
     * @return Rückgabe der gesamten Fotoliste des Systems
     * @date 21.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     * @date 06.12.2015 by Danilo: Status auf protected
     */
    protected static FotoContainer getFotoContainer(){
        return pmSystem.getFotos();
    }
}
