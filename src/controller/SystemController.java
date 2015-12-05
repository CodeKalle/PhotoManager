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
 * @date 23.11.2015 by Danilo: Kommentare ergänzt
 */
public class SystemController {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    // Derzeit speichert das System die Datenbank da wo die ausführbare JAVA-Datei liegt 
    private static final String filename = "pm.jdb";
    private static PmSystem pmSystem = new PmSystem();
    
    /**
     * Methode realisiert die Generierung der benötigten Komponenten in Threads.
     * 
     * Version-History:
     * @return Rückgabe zur Fehlerauswertung
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 30.11.2015 by Danilo: Anpassung an GUI
     */
    public static int run() {
        File file = new File(filename);
        if (file.exists()) {
            if (file.canRead() == true) {
                checkAccess(0);
                if (file.canWrite() == false) {
                    return 800;
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
     * Prüft Zugriff auf Datei und speichert das System.
     * 
     * Version-History:
     * @param mode 0 = Zum Laden,<br> 1 = Zum Speichern
     * @return Rückgabe zur Fehlerauswertung
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     * @date 30.11.2015 by Danilo: Anpassung an GUI
     */
    public static int checkAccess(int mode){

        File file = new File(filename);
        switch (mode) {			
            case 0:
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
                }
                break;
            case 1:
                if (file.canWrite() == false) {
                    return 800;
                } else {
                    if (systemSave()!=0) return 805;
                }
                break;
            default:
                break;
        }
        return 0;
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
     */
    public static int initializePmSystem() {
        pmSystem.setAlben(new AlbenContainer());
        pmSystem.setFotos(new FotoContainer());
        if (pmSystem.getAlben()==null || pmSystem.getFotos()==null) return 810;
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
     * Dieser Getter holt den Albencontainer.
     * 
     * Version-History:
     * @return Rückgabe der gesamten Albenliste des Systems
     * @date 21.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     */
    public static AlbenContainer getAlbumContainer(){
        return pmSystem.getAlben();
    }
    
    /**
     * Dieser Getter holt den Fotocontainer.
     * 
     * Version-History:
     * @return Rückgabe der gesamten Fotoliste des Systems
     * @date 21.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     */
    public static FotoContainer getFotoContainer(){
        return pmSystem.getFotos();
    }
}
