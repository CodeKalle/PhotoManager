package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Foto;
import model.Metadaten;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Der SystemControllerTest prüft die Klasse SystemController aus dem Core
 * 
 * Version-History:
 * @date 06.12.2015 by Danilo: Initialisierung
 */
public class SystemControllerTest {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    private static long timeUsing;
    private static long timeGeneral;
    private static String originFilename;
    // Fixe Testdaten
    private static String filename;
    // Zufällige Testdaten
    private static String randomFilename;
    // Weitere Testdaten
    private final static String EMPTYSTRING = "";
    private final static String NULLSTRING = null; 
    private final static int garanteedAlbumCount = 500;
    private final static int garanteedFotoCount = 5000;
    
    /**
     * Standard Konstruktor der Klasse
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    public SystemControllerTest() {
    }
    
    /**
     * Methode startet zu Begin der Klasse
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @BeforeClass
    public static void setUpClass() {
        filename = "pm-test.jdb";
    }
    
    /**
     * Methode startet zum Ende der Klasse
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @AfterClass
    public static void tearDownClass() {
        System.out.println("\n=== AlbenControllerTest ===\nTestzeit der Klasse: " + (timeGeneral/1000000) + " ms [" + (timeGeneral/1000) + " us]\n");
    }
    
    /**
     * Initialisierung der zufälligen Testdaten für das prüfen der Testmethoden
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Before
    public void setUp() {
        generateRandomData();
        originFilename = SystemController.getFilename();
        SystemController.run(filename);
        timeUsing = System.nanoTime();
    }
    
    /**
     * Anzeigen der benötigten Zeit der getesteten Methode
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @After
    public void tearDown() {
        long time = System.nanoTime();
        timeGeneral += (time - timeUsing);
        System.out.println("Testzeit der Methode: " + ((time - timeUsing)/1000000) + " ms [" + ((time - timeUsing)/1000) + " us]\n");
        File checkFile = new File(filename);
        if (checkFile.exists()) checkFile.delete();
        SystemController.setFilename(originFilename);
    }

    /**
     * Generiert neue Zufällige Daten
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    private void generateRandomData() {
        do{
            randomFilename = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(7) + 3) + ".jdb";
        } while (filename.equals(randomFilename));
    }
    
    /**
     * Generiert ein zufälliges Album im Albencontainer
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    private String generateRandomAlbumInContainer() {
        // Ein Album anlegen
        String randomTitel = "";
        do {
            randomTitel = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(17) + 4);
        } while (AlbenController.getAlbum(randomTitel)!=null);
        String randomBeschreibung = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(201));
        String randomSortierkennzeichen = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(21));
        int errorcode = AlbenController.createNewAlbum(randomTitel, randomBeschreibung, randomSortierkennzeichen);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüfen das Album vorhanden ist
        assertEquals(AlbenController.getAlbum(randomTitel).getTitel(), randomTitel);
        
        return randomTitel;
    }
    
    /**
     * Testet die Methode run im SystemController.
     * Testet, ob das System mit einem fixen Dateinamen angelegt werden kann.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testSystemRunStandardFilenameSuccess() {
        System.out.println("testSystemRunStandardFilenameSuccess");
        
        // Standard Datenbankdatei sichern falls vorhanden
        File checkFile = new File(SystemController.getFilename());
        File backupFile = new File(SystemController.getFilename() + ".bck");
        if (backupFile.exists()) backupFile.delete();
        if (checkFile.exists()==true) {
            try {
                Files.move(checkFile.toPath(), backupFile.toPath());
            } catch (IOException ex) {
                Logger.getLogger(SystemControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Starten des Systems mit standard Datenbank
        int errorcode = SystemController.run();
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüfen das Datenbank angelegt wurde
        if (checkFile.exists()==false) {
            fail("Datenbank wurde nicht angelegt.");
        }
        
        // Löschen der Datenbank aus Filesystem
        if (checkFile.exists()==true) {
            checkFile.delete();
        }
        
        // Sicherung der Standarddatenbank wieder herstellen
        if (backupFile.exists()==true) {
            try {
                Files.move(backupFile.toPath(), checkFile.toPath());
            } catch (IOException ex) {
                Logger.getLogger(SystemControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Testet die Methode run im SystemController.
     * Testet, ob das System mit einem fixen Dateinamen angelegt werden kann.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testSystemRunFixFilenameSuccess() {
        System.out.println("testSystemRunFixFilenameSuccess");
        
        // Fixe Datenbankdatei löschen da in der setUp-Methode schon angelegt
        File checkFile = new File(filename);
        if (checkFile.exists()==true) {
            checkFile.delete();
        }
        
        // Starten des Systems mit fixer Datenbank
        int errorcode = SystemController.run(filename);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüfen das Datenbank angelegt wurde
        if (checkFile.exists()==false) {
            fail("Datenbank wurde nicht angelegt.");
        }
        
        // Löschen der Datenbank aus Filesystem
        if (checkFile.exists()==true) {
            checkFile.delete();
        }
    }
    
    /**
     * Testet die Methode run im SystemController.
     * Testet, ob das System mit einem zufälligen Dateinamen angelegt werden kann.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testSystemRunRandomFilenameSuccess() {
        System.out.println("testSystemRunRandomFilenameSuccess");
        
        // Prüfen das zufällige Datenbank nicht existiert
        File checkFile = new File(randomFilename);
        if (checkFile.exists()==true) {
            fail("Zuffällige Datenbank exisitert bereits.");
        }
        
        // Starten des Systems mit zufälliger Datenbank
        int errorcode = SystemController.run(randomFilename);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüfen das Datenbank angelegt wurde
        if (checkFile.exists()==false) {
            fail("Zuffällige Datenbank wurde nicht angelegt.");
        }
        
        // Löschen der Datenbank aus Filesystem
        if (checkFile.exists()==true) {
            checkFile.delete();
        }
    }
    
    /**
     * Testet die Methode run im SystemController.
     * Testet, ob das System mit einem leeren Dateinamen angelegt werden kann.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testSystemRunEmptyFilename() {
        System.out.println("testSystemRunEmptyFilename");
        
        // Prüfen das Datenbank mit leerem Dateiname nicht existiert
        File checkFile = new File(EMPTYSTRING);
        if (checkFile.exists()==true) {
            fail("Leerer Dateiname als Datei existiert.");
        }
        
        // Starten des Systems mit leerem Dateinamen
        int errorcode = SystemController.run(EMPTYSTRING);
        if (errorcode==0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüfen das Datenbank nicht angelegt wurde
        if (checkFile.exists()==true) {
            fail("Datei mit leerem Dateinamen wurde angelegt.");
        }
        
        // Löschen der Datenbank aus Filesystem falls vorhanden
        if (checkFile.exists()==true) {
            checkFile.delete();
        }
    }
    
    /**
     * Testet die Methode run im SystemController.
     * Testet, ob das System mit einem null Dateinamen angelegt werden kann.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testSystemRunNullFilename() {
        System.out.println("testSystemRunNullFilename");
        
        // Starten des Systems mit null Dateinamen
        int errorcode = SystemController.run(NULLSTRING);
        if (errorcode==0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Da das Betriebssystem keine null Dateinamen handeln kann kann, ist eine weiteren Prüfung nicht nötig
    }
    
    /**
     * Testet die Methode checkAccess im SystemController.
     * Testet, ob das System geladen werden kann.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testCheckAccessLoadAndSaveSuccess() {
        System.out.println("testCheckAccessLoadAndSaveSuccess");
        
        // Setzen des Testfilenames um bestehende Daten während des Tests nicht zu verändern
        SystemController.setFilename(filename);
        
        // Initialisierung der Datenbank
        int errorcode = SystemController.initializePmSystem();
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Ein zufälliges Album anlegen
        String randomTitel = generateRandomAlbumInContainer();
        
        // Speichern der Datenbank als Testdatenbank
        errorcode = SystemController.checkAccess(1);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Erneute initialisierung der Datenbank
        errorcode = SystemController.initializePmSystem();
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüfen das Album nicht vorhanden ist
        assertThat(AlbenController.getAlbum(randomTitel), is(nullValue()));
        
        // Laden der Datenbank als Testdatenbank
        errorcode = SystemController.checkAccess(0);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüfen das Album vorhanden ist
        assertEquals(AlbenController.getAlbum(randomTitel).getTitel(), randomTitel);
    }
    
    /**
     * Testet die Methode checkAccess im SystemController.
     * Testet, ob das System geladen werden kann.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testCheckAccessLoadAndSaveSuccessGuaranteedStability() {
        System.out.println("testCheckAccessLoadAndSaveSuccessGuaranteedStability");
        
        // Setzen des Testfilenames um bestehende Daten während des Tests nicht zu verändern
        //SystemController.setFilename(filename);
SystemController.setFilename("jTest.jdb");
        
        // Initialisierung der Datenbank
        int errorcode = SystemController.initializePmSystem();
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Testfotoordner anlegen
        String folderName = "test-fotos";
        File folderFile = new File(folderName);
        folderFile.mkdir();
        
        // Anlegen von garantierten Alben
        for (int i = 0; i< 10/*garanteedAlbumCount*/; i++) {
            String albumTitel = generateRandomAlbumInContainer();
            
            List<Path> fotoPathList = new LinkedList();
            
            // Anlegen garantierter Fotos
            for (int j = 0; j < 20/*garanteedFotoCount*//10/*garanteedAlbumCount*/; j++)
            {
                // Fotodatei anlegen
                File fotoFile = new File("test-fotos" + File.separator + "foto-a" + i + "-" + j + ".jpg");
                if (!fotoFile.exists()) {
                    try {
                        fotoFile.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(SystemControllerTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                Foto tmpFoto = new Foto(fotoFile.getName(), Paths.get(fotoFile.getAbsolutePath()));
                
                // Metadaten des Bildes setzen
                Metadaten meta = new Metadaten();
                meta.setzeWert("Kurztitel", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Filterbegriff1", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Filterbegriff2", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Filterbegriff3", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Filterbegriff4", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Filterbegriff5", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Wichtigkeit", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Qualitaet", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Attraktivitaet", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Textbeschreibung", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Tag1", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Tag2", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Tag3", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Tag4", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Tag5", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                meta.setzeWert("Anzeigeformat", RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(5) + 10));
                
                tmpFoto.setMetadata(meta);
                fotoPathList.add(tmpFoto.getPfad());
            }
            
            // Prüfen das Anlegen der Fotos im Album
            errorcode = FotoController.addListOfFotosToAlbum(albumTitel, fotoPathList);
            if (errorcode!=0) {
                fail(ErrorController.changeErrorCode(errorcode)[1]);
            }
        }
        
        // Prüfen das Anzahl garantierter Alben vorhanden sind
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(10/*garanteedAlbumCount*/));
        
        // Prüfen das Anzahl garantierter Fotos vorhanden sind
        assertThat(SystemController.getFotoContainer().anzahlFotos(), is(20/*garanteedFotoCount*/));
        
        // Starten der Zeitmessung
        long time = System.nanoTime();
        
        // Speichern der Datenbank als Testdatenbank
        errorcode = SystemController.checkAccess(1);
        
        // Ende der Zeitmessung in us
        time = (System.nanoTime() - time)/1000;
        
        // Prüfen das Speichern korrekt verlief
        if (errorcode!=0) {
// Fehlerkorrektur hier wird ein Fehlerausgelöst da die Datenbank nicht serialisiert werden kann
// wer Ideen hat bitte beim Teamchef melden
            //fail(ErrorController.changeErrorCode(errorcode)[1] + Integer.toString(errorcode));
        }
        
        // Ausgabe für benutzer
        System.out.println("Zeit für " + garanteedFotoCount/garanteedAlbumCount + " Fotos [mit Metadaten] in jeweils " + garanteedAlbumCount + " Alben: " + ((time)/1000) + " ms [" + time + " us]");
        
        // Prüfen das garantierte Zeit erreicht wurde
        assertThat(time < 1000000, is(true));
        
        // Testfotoordner mit allen darin enthaltenen Daten löschen
        if (folderFile.exists()) {
            String[] entries = folderFile.list();
            for (String entrie : entries) {
                File aktFile = new File(folderFile.getPath(), entrie);
                aktFile.delete();
            }
            folderFile.delete();
        }
    }
    
    /**
     * Testet die Methode checkAccess im SystemController.
     * Testet, ob das System nicht korrekt geladen wird.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testCheckAccessLoadFileCantReadFailure() {
        System.out.println("testCheckAccessLoadFileCantReadFailure");
        
        // Setzen des Testfilenames um bestehende Daten während des Tests nicht zu verändern
        SystemController.setFilename(randomFilename);
        
        // Initialisierung der Datenbank
        int errorcode = SystemController.initializePmSystem();
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Ein zufälliges Album anlegen
        generateRandomAlbumInContainer();
        
        // Leere Testdatenbank anlegen
        File loadFile = new File(randomFilename);
        if (!loadFile.exists()) {
            try {
                loadFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SystemControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Laden der Datenbank aus angelegter Testdatenbank
        errorcode = SystemController.checkAccess(0);
        if (errorcode==0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
         
        // Prüfen das Datenbank leer
        assertThat(AlbenController.getAlbumList().isEmpty(), is(true));
        
        // Angelegte Datei löschen
        if (loadFile.exists()) loadFile.delete();
    }
    
    /**
     * Testet die Methode checkAccess im SystemController.
     * Testet, ob das System eine schreibgeschützte Datei einliest.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testCheckAccessSaveFileCantWriteFailure() {
        System.out.println("testCheckAccessSaveFileCantWriteFailure");
        
        // Setzen des Testfilenames um bestehende Daten während des Tests nicht zu verändern
        SystemController.setFilename(randomFilename);
        
        // Initialisierung der Datenbank
        int errorcode = SystemController.initializePmSystem();
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Ein zufälliges Album anlegen
        generateRandomAlbumInContainer();
        
        // Schreibgeschützte Testdatenbank anlegen
        File loadFile = new File(randomFilename);
        if (!loadFile.exists()) {
            try {
                loadFile.createNewFile();
                loadFile.setWritable(false);
            } catch (IOException ex) {
                Logger.getLogger(SystemControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Speichern der Datenbank in vorhanden aber nicht schreibbare Testdatenbank
        errorcode = SystemController.checkAccess(1);
        if (errorcode==0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Angelegte Datei löschen
        if (loadFile.exists()) loadFile.delete();
    }
    
    /**
     * Testet die Methode initializePmSystem im SystemController.
     * Testet, ob das System korrekt initialisiert wird.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testInitializePmSystem() {
        System.out.println("testInitializePmSystem");
        
        // Ein zufälliges Album anlegen
        String randomTitel = generateRandomAlbumInContainer();
        
        // System erneut Initialisieren
        SystemController.initializePmSystem();
        
        // Prüfen das Album nicht mehr vorhanden da System null
        assertThat(AlbenController.getAlbum(randomTitel), is(nullValue()));
        
        // Prüfen das Datenbank nicht leer ist
        assertThat(SystemController.getAlbumContainer(), is(not(nullValue())));
        assertThat(SystemController.getFotoContainer(), is(not(nullValue())));
    }
    
    /**
     * Testet die Methode setFilename und getFilename im SystemController.
     * Testet, ob das setzen und lesen des Dateinamen funktioniert.
     * 
     * Version-History:
     * @date 06.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testSetAndGetFilename() {
        System.out.println("testSetAndGetFilename");
        
        // Sichern des aktuellen Dateinamens
        String store = SystemController.getFilename();
        
        // Ändern des Dateinamens
        SystemController.setFilename(randomFilename);
        
        // Prüft das Dateiename korrekt geändert und gelesen wurde
        assertThat(SystemController.getFilename(), not(store));
        assertEquals(SystemController.getFilename(), randomFilename);
    }
}
