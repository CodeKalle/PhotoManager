package controller;

import static controller.ErrorController.changeErrorCode;
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
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    public static void run() {
        File file = new File(filename);
        if (file.exists()) {
            if (file.canRead() == true) {
                checkAccess(0);
                if (file.canWrite() == false) {
                    changeErrorCode(800);
                }
            } else {
                changeErrorCode(801);
                System.exit(0);
            }
        } else {
            if (changeErrorCode(802)!=0) {
                System.exit(0);
            } else {
                try {
                    file.createNewFile();
                    initializePmSystem();
                    if (systemSpeichern()!=0) changeErrorCode(810);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Prüft Zugriff auf Datei.
     * mode:    0   Beim Systemstart
     *          1   Im laufenden Betrieb
     * 
     * Version-History:
     * @param mode
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    public static void checkAccess(int mode){

        File file = new File(filename);
        switch (mode) {			
            case 0:
                if (file.canRead() == false) {
                    changeErrorCode(801);
                } else {
                    if (systemLaden() != 0) {
                        if(changeErrorCode(803)==0) {
                            try {
                                file.createNewFile();
                                initializePmSystem();
                            } catch (IOException e) {
                                //e.printStackTrace();
                            }
                        } else {
                            changeErrorCode(811);
                            System.exit(0);
                        }
                    }			
                }
                break;
            case 1:
                if (file.canWrite() == false) {
                    changeErrorCode(800);
                } else {
                    if (systemSpeichern()!=0) changeErrorCode(810);
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * Diese Methode initialisiert die Container des Systems.
     * Diese Methode wird nur benötigt falls das System keine Datenbank zum
     * laden findet.
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    private static void initializePmSystem() {
        pmSystem.setAlben(new AlbenContainer());
        pmSystem.setFotos(new FotoContainer());
        if (pmSystem.getAlben()==null || pmSystem.getFotos()==null) changeErrorCode(815);
    }
    
    /**
     * Diese Methode speichert das gesamte System.
     * 
     * Version-History:
     * @return 
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Tobias: Setzten der Methode auf private
     */
    private static int systemSpeichern() {
        int out = 0;
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            String filePath = filename;
            fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(pmSystem);
        }
        catch (IOException e) {
            out = 1;
        }
        finally {
            if (oos != null) try { oos.close(); } catch (IOException e) {
                out = 1;
            }
            if (fos != null) try { fos.close(); } catch (IOException e) {
                out = 1;
            }
        }
        return out;
    }
    
    /**
     * Diese Methode laed das System aus einer bestehenden Datei.
     * 
     * Version-History:
     * @return 
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    private static int systemLaden() {
        int n = 0;
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
                    n = 1;
                }
            } catch (StreamCorruptedException e) {
                n = 2;
            }
            finally {
                if (ois != null) try { ois.close(); } catch (IOException e) {
                    n = 3;
                }
            }
        }
        catch (IOException e) {
                n = 4;
        }
        finally {
            if (ois != null) try { ois.close(); } catch (IOException e) {
                n = 5;
            }
            if (fis != null) try { fis.close(); } catch (IOException e) {
                n = 6; 
            }
        }
	return n;
    }
    
    /**
     * Dieser Getter holt den Albencontainer.
     * 
     * Version-History:
     * @return 
     * @date 21.11.2015 by Danilo: Initialisierung
     */
    public static AlbenContainer getAlbumContainer(){
        return pmSystem.getAlben();
    }
    
    /**
     * Dieser Getter holt den Fotocontainer.
     * 
     * Version-History:
     * @return 
     * @date 21.11.2015 by Danilo: Initialisierung
     */
    public static FotoContainer getFotoContainer(){
        return pmSystem.getFotos();
    }
}
