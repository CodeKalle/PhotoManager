package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static java.util.Optional.empty;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Album;
import model.Foto;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Der FotoControllerTest testet alle Methoden der Klasse FotoController.
 * 
 * @author Daniel
 * 
 * @date 01.12.2015 by Daniel: Neustrukturierung für Kompatibilität mit zukünftigem TestRunner; testAddListOfFotosToAlbum bearbeitet; testDeleteAllFotosInAlbum hinzugefügt
 * @date 02.12.2015 by Daniel: testdaten, testGetFotosFromAlbum hinzugefügt; tearDown löscht container, metadaten funktioniert nicht (Kurztitel=null)
 * @date 03.12.2015 by Daniel: keine Verbesserung nach dem hinzufügen von foto zum container 
 * @date 04.12.2015 by Daniel: Neue Strukturierung der Tests
 * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int und Fehlerkorrektur
 * @date 09.12.2015 by Danilo: Anpassung an Änderungen im FotoCotnroller
 * @date 14.12.2015 by Danilo: Hinzufügen des Testordnerpfades und weiterer Test neuer Methoden [testDeleteNotExistingFotosFromListInAlbum]
 */
public class FotoControllerTest {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 01.12.2015 by Daniel: Anlegen neuer Klassenvariablen
     * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
     * @date 09.12.2015 by Danilo: Anpassung an Änderungen im FotoCotnroller
     * @date 14.12.2015 by Danilo: Hinzufügen des Testordnerpfades
     */
    private static List<Album> listOfAlbum;
    private static Map<Integer, Foto> mapOfFotos;
    private static Foto testFoto;
    private static long timeUsing;
    private static long timeGeneral;
    // Daten des Testalbums
    private static String title;
    private static String beschreibung;
    private static int sortierkennzeichen;
    // Fixe Testdaten
    private static String name;
    private static Path pathOfTestfolder;
    private static Path pathOfFoto;
    private static List listOfPathes;
    private static List listOfFotos;
    private static Map<String, Object> daten = new HashMap<>();
    private static String kurztitel;
    // Neue fixe Testdaten
    private static String newName;
    // Zufällige Testdaten
    private String randomName;
    // Weitere Testdaten
    private final static String EMPTYSTRING = "";
    private final static String NULLSTRING = null; 
    private final static int garanteedFotoCount = 5000;
            
    /**
     * Standard Konstruktor der Klasse
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    public FotoControllerTest() {
    }
    
    /**
     * Methode startet zu Begin der Klasse
     * 
     * Version-History:
     * @date 05.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Album 
     * @date 07.12.2015 by Danilo: Änderung des Pfades, des Sortierkennzeichens und Fehlerkorrektur und umbennant
     * @date 09.12.2015 by Danilo: Anpassung an Änderungen im FotoCotnroller
     * @date 14.12.2015 by Danilo: Hinzufügen des Testordnerpfades
     */
    @BeforeClass
    public static void setUpClass() {
        SystemController.initializePmSystem();
        mapOfFotos = SystemController.getFotoContainer().getFotoMap();
        listOfAlbum = SystemController.getAlbumContainer().getAlbenListe();
        // Testalbum anlegen
        title = "Testtitel";
        beschreibung = "Testbeschreibung";
        sortierkennzeichen = 2;
        // Fixe Testdaten
        name = "Name";
        pathOfTestfolder = Paths.get("test/testdaten/");
        pathOfFoto = Paths.get("test/testdaten/Testbild43.jpg");
        testFoto = new Foto(name, pathOfFoto.toString());
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
        listOfFotos = new LinkedList<>();
        listOfFotos.add(testFoto);
        kurztitel = "Kurztitel";
        daten.put(kurztitel, kurztitel);
        // Neue fixe Testdaten
        newName = "Neuer Name";
    }
    
    /**
     * Methode startet zum Ende der Klasse
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Setzen der Kommentare
     */
    @AfterClass
    public static void tearDownClass() {
        System.out.println("\n=== FotoControllerTest ===\nTestzeit der Klasse: " + (timeGeneral/1000000) + " ms [" + (timeGeneral/1000) + " us]\n");
    }
    
    /**
     * Leeren der Datenbank und Initialisierung der zufälligen Testdaten für das prüfen der Testmethoden
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Anpassung an geänderten FotoController
     */
    @Before
    public void setUp() {
        mapOfFotos.clear();
        listOfAlbum.clear();
        SystemController.initializePmSystem();
        AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        generateRandomData();
        timeUsing = System.nanoTime();
    }
    
    /**
     * Anzeigen der benötigten Zeit der getesteten Methode
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 05.12.2015 by Daniel: Anpassung an geänderte Struktur
     */
    @After
    public void tearDown() {
        long time = System.nanoTime();
        timeGeneral += (time - timeUsing);
        System.out.println("Testzeit der Methode: " + ((time - timeUsing)/1000000) + " ms [" + ((time - timeUsing)/1000) + " us]\n"); 
        AlbenController.getAlbumList().clear();
    }

    /**
     * Generiert neue Zufällige Daten.
     * 
     * Version-History:
     * @date 04.12.2015 by Daniel: Initialisierung
     */
    private void generateRandomData() {
        do {
            randomName = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(17) + 4);
        } while (title.equals(randomName));
    }
    
    /**
     * Testet die Methode testSetMetaInFoto der Klasse FotoController.
     * Testet, ob die gesetzten Metadaten des Fotos mit der lokal erstellten Metadaten übereinstimmen.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Album konnte Angelegt werden, errorcode, Kommentare, übergebene Metadaten: null=null
     * @date 07.12.2015 by Danilo: Ausgabe entfernt
     * @date 09.12.2015 by Danilo: Anpassung an Änderungen im FotoCotnroller
     */
    @Test
    public void testSetMetaInFoto() {
        System.out.println("testSetMetaInFoto");
        
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        int errorcode = FotoController.setMetaInFoto(pathOfFoto, daten);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Holt daten aus Datenbank
        Map<String, Object> expectMeta = daten;
        Map<String, Object> resultMeta = FotoController.getMetaFromFoto(pathOfFoto);
        
        // Maps der lokalen Metadaten und übergebenen Metadaten stimmen überein
        assertEquals(expectMeta, resultMeta);
    }

    /**
     * Testet die Methode testGetFotosFromAlbum der Klasse FotoController.
     * Testet, ob die geholten Metadaten des Fotos mit den lokal erstellten Metadaten übereinstimmen.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Album konnte Angelegt werden, Kommentare, übergebene Metadaten: null=null
     * @date 07.12.2015 by Danilo: Fehlerkorrektur
     * @date 09.12.2015 by Danilo: Anpassung an Änderungen im FotoCotnroller
     */
    @Test
    public void testGetMetaFromFoto() {
        System.out.println("testGetMetaFromFoto");
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Setzt Metadaten in das Foto
        FotoController.setMetaInFoto(pathOfFoto, daten);

        // Holt daten aus Datenbank
        Map<String, Object> expectMeta = daten;
        Map<String, Object> resultMeta = FotoController.getMetaFromFoto(pathOfFoto);
        
        // Maps der lokalen Metadaten und übergebenen Metadaten stimmen überein
        assertEquals(expectMeta, resultMeta);
    }

    /**
     * Testet die Methode testGetFotosFromAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums mit der lokal erstellten Liste übereinstimmt.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Album konnte Angelegt werden, Kommentare
     */
    @Test
    public void testGetFotosFromAlbum() {
        System.out.println("testGetFotosFromAlbum");
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        // Fotoliste ist nicht leer?
        assertThat(AlbenController.getAlbum(title).getFotoListe(), not(empty()));
        
        List expectList = listOfPathes;
        List resultList = FotoController.getFotosFromAlbum(title);
        // Listen der lokalen Pfade und übergebenen Pfade stimmen überein
        assertEquals(expectList, resultList);
    }

    /**
     * Testet die Methode testAddListOfFotosToAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums mit der übergebenen Liste übereinstimmt.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Album konnte Angelegt werden, errorcode, Kommentare
     * @date 14.12.2015 by Danilo: Hinzufügen der Counterprüfung
     */
    @Test
    public void testAddListOfFotosToAlbum() {
        System.out.println("testAddListOfFotosToAlbum");
        
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Testweise in der Methode angelegt um Foto sicher zu erstellen, kann danach wieder weg
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
        testFoto = new Foto(name, pathOfFoto.toString());
        listOfFotos = new LinkedList<>();
        listOfFotos.add(testFoto);
        
        // addListOfFotosToAlbum geprüft, gibt 0 zurück, wenn kein Fehler entstand, errorcode != 0: AssertiationFailedError
        int errorcode = FotoController.addListOfFotosToAlbum(title, listOfPathes);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        List expectList = listOfFotos;
        List resultList = AlbenController.getAlbum(title).getFotoListe();

        // Listen der lokalen Fotos und übergebenen Fotos stimmen überein
        assertEquals(expectList, resultList);
        
        // Prüfen der Fotocounter
        for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe())
        {
            // Prüft das jedes Foto einmal verlinkt ist
            assertThat(tmpFoto.getCounter(), is(1));
        }
    }

    /**
     * Testet die Methode testDeleteAllFotosInAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums gelöscht wurde.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Album konnte Angelegt werden, Kommentare
     */
    @Test
    public void testDeleteAllFotosInAlbum() {
        System.out.println("testDeleteAllFotosInAlbum");
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        // Liste von Fotos, die gelöschtw erden sollen, wurde hinzugefügt
        assertThat(AlbenController.getAlbum(title).getFotoListe(), not(empty()));
        
        FotoController.deleteAllFotosInAlbum(AlbenController.getAlbum(title));
        List<Foto> expectList = new LinkedList<>();
        List<Foto> resultList = AlbenController.getAlbum(title).getFotoListe();
        assertEquals(expectList, resultList);
    }
    
    /**
     * Testet die Methode getFotosFromAlbumDiffrentSort der Klasse FotoController.
     * Testet, ob die Fotoliste in verschiedenen Sortierungen zurück gegeben wird.
     * 
     * Version-History:
     * @date 09.12.2015 by Dnilo: Initialisierung
     */
    @Test
    public void testGetFotosFromAlbumDiffrentSort() {
        System.out.println("testGetFotosFromAlbumDiffrentSort");
        
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Fotoliste des Fotocontainer prüfen
        assertThat(SystemController.getFotoContainer().anzahlFotos(), is(0));

        // Testfotoordner anlegen
        String folderName = "test-fotos";
        File folderFile = new File(folderName);
        folderFile.mkdir();
        
        // Temporäre Fotos anlegen
        List<Path> fotoPathList = new LinkedList();
        int fotoCount = 10;
        
        // Anlegen 10 Fotos Rückwärts
        for (int i = 1; i < fotoCount+1; i++)
        {
            // Fotodatei anlegen [speichert automatisch]
            File fotoFile = new File("test-fotos" + File.separator + "foto-" + (fotoCount+1-i) + ".jpg");
            if (!fotoFile.exists()) {
                try {
                    fotoFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(SystemControllerTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
            Foto tmpFoto = new Foto(fotoFile.getName(), Paths.get(fotoFile.getAbsolutePath()).toString());
                
            fotoPathList.add(tmpFoto.getPfad());
        } 
    
        // Prüfen das Anlegen der Fotos im Album
        int errorcode = FotoController.addListOfFotosToAlbum(title, fotoPathList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }

        // Prüfen das temporäre Fotos vorhanden sind
        assertThat(SystemController.getFotoContainer().anzahlFotos(), is(fotoCount));
        
        // Liste der Fotos ausgeben [Benutzerdefiniert]
        System.out.println("Sortieren nach Benutzerdefiniert:");
        for (Path tmpPath : FotoController.getFotosFromAlbum(title)) {
            System.out.println(">" + tmpPath.getFileName());
        }
        
        // Album auf Sortierung nach Name setzen
        AlbenController.editAlbum(title, title, beschreibung, 1);
        
        // Liste der Fotos ausgeben [Name]
        System.out.println("Sortieren nach Name:");
        for (Path tmpPath : FotoController.getFotosFromAlbum(title)) {
            System.out.println(">" + tmpPath.getFileName());
        }
        
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
     * Testet die Methode deleteNotExistingFotosFromListInAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste im Model gemäß der Änderungen geupdated wird.
     * 
     * Version-History:
     * @date 14.12.2015 by Dnilo: Initialisierung
     */
    @Test
    public void testDeleteNotExistingFotosFromListInAlbum() {
        System.out.println("testDeleteNotExistingFotosFromListInAlbum");
        
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Prüft das Album kein Foto hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(0));
        
        List<Path> fotoPathList = new LinkedList();
        List<Path> fotoNotDeleteList = new LinkedList();
        
        // Fotos zum Album hinzufügen und für das Löschen speichern [Vorwärts]
        for (int i = 0; i<5; i++) {
            Foto newFoto = new Foto("Logo" + i + ".jpg", pathOfTestfolder + File.separator + "Logo" + i + ".jpg");
            fotoPathList.add(newFoto.getPfad());
            
            // Erstes und letztes Foto löschen [hier merken der nicht zu löschenden]
            if (i>0 && i<4) {
                fotoNotDeleteList.add(newFoto.getPfad());
            }
        }
        
        // Prüfen das Anlegen der Fotos im Album
        int errorcode = FotoController.addListOfFotosToAlbum(title, fotoPathList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album fünf Foto hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(5));
        
        // Löschen der Temporären Liste
        fotoPathList.clear();
        
        // Fotos zum Album hinzufügen und für das Löschen speichern [Rückwärts]
        for (int i = 4; i>=0; i--) {
            Foto newFoto = new Foto("Logo" + i + ".jpg", pathOfTestfolder + File.separator + "Logo" + i + ".jpg");
            fotoPathList.add(newFoto.getPfad());
            
            // Alle Fotos ausser den letzten zwei löschen [hier merken der nicht zu löschenden]
            if (i<2) {
                fotoNotDeleteList.add(newFoto.getPfad());
            }
        }
        
        // Prüfen das Hinzufügen der Fotos im Album
        errorcode = FotoController.addListOfFotosToAlbum(title, fotoPathList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album zehn Foto hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(10));
        
        // Löschen der Fotos die nicht gemerkt wurden
        errorcode = FotoController.deleteNotExistingFotosInListFromAlbum(title, fotoNotDeleteList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album zehn-gemerkte Foto hält, also 5
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(5));
    }

    /**
     * Testet die kombinatio der Methoden addListOfFotosToAlbum und deleteNotExistingFotosFromListInAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste nach mehrfachen Änderungen korrekt ist.
     * 
     * Version-History:
     * @date 14.12.2015 by Dnilo: Initialisierung
     */
    @Test
    public void testReplaceFotosInAlbum() {
        System.out.println("testReplaceFotosInAlbum");
        
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Prüft das Album kein Foto hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(0));
        
        List<Path> fotoPathList = new LinkedList();
        List<Path> fotoNotDeleteList = new LinkedList();
        
        // Fotos 0-4 zum Album hinzufügen und für das Löschen speichern [Vorwärts]
        for (int i = 0; i<5; i++) {
            Foto newFoto = new Foto("Logo" + i + ".jpg", pathOfTestfolder + File.separator + "Logo" + i + ".jpg");
            fotoPathList.add(newFoto.getPfad());
            
            // Erstes und letztes Foto löschen [hier merken der nicht zu löschenden]
            if (i>0 && i<4) {
                fotoNotDeleteList.add(newFoto.getPfad());
            }
        }     
        
        // Prüfen das Anlegen der Fotos im Album
        int errorcode = FotoController.addListOfFotosToAlbum(title, fotoPathList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album fünf Foto hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(5));
        
        // Löschen der Temporären Liste
        fotoPathList.clear();

        // Merkliste bisher: Logo1, Logo2, Logo3
        // Aktuell: Logo0, Logo1, Logo2, Logo3, Logo4
        // Prüfen der Fotocounter
        for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe())
        {
            // Prüft das jedes Foto einmal verlinkt ist
            assertThat(tmpFoto.getCounter(), is(1));
        }
        
        // Fotos 5x2 zum Album hinzufügen und für das Löschen speichern
        for (int i = 0; i<5; i++) {
            Foto newFoto = new Foto("Logo2.jpg", pathOfTestfolder + File.separator + "Logo2.jpg");
            fotoPathList.add(newFoto.getPfad());
            
            // Zweites Foto nicht löschen [hier merken der nicht zu löschenden]
            if (i!=1) {
                fotoNotDeleteList.add(newFoto.getPfad());
            }
        }
        
        // Prüfen das Hinzufügen der Fotos im Album
        errorcode = FotoController.addListOfFotosToAlbum(title, fotoPathList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album zehn Foto hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(10));
        
        // Löschen der Temporären Liste
        fotoPathList.clear();
        
        // Merkliste bisher: Logo1, Logo2, Logo3, Logo2, Logo2, Logo2, Logo2
        // Aktuell: Logo0, Logo1, Logo2, Logo3, Logo4, Logo2, Logo2, Logo2, Logo2, Logo2
        // Prüfen der Fotocounter
        for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe())
        {
            // Prüft das jedes Foto einmal verlinkt ist ausser Logo2
            if (tmpFoto.getName().equals("Logo2.jpg")) {
                assertThat(tmpFoto.getCounter(), is(6));
            }
            else {
                assertThat(tmpFoto.getCounter(), is(1));
            }
        }
        
        // Fotos 0-4 zum Album hinzufügen und für das Löschen speichern [Vorwärts]
        for (int i = 0; i<5; i++) {
            Foto newFoto = new Foto("Logo" + i + ".jpg", pathOfTestfolder + File.separator + "Logo" + i + ".jpg");
            fotoPathList.add(newFoto.getPfad());
            
            // Erstes und vorletztes Foto nicht löschen [hier merken der nicht zu löschenden]
            if (i==0 || i==3) {
                fotoNotDeleteList.add(newFoto.getPfad());
            }
        }
        
        // Prüfen das Hinzufügen der Fotos im Album
        errorcode = FotoController.addListOfFotosToAlbum(title, fotoPathList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album fünfzehn Foto hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(15));
        
        // Löschen der Temporären Liste
        fotoPathList.clear();
        
        // Erwartung bisher: Logo1, Logo2, Logo3, Logo2, Logo2, Logo2, Logo2, Logo0, Logo3
        // Aktuell: Logo0, Logo1, Logo2, Logo3, Logo4, Logo2, Logo2, Logo2, Logo2, Logo2, Logo0, Logo1, Logo2, Logo3, Logo4
        // Prüfen der Fotocounter
        for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe())
        {
            // Prüft das jedes Foto zweimal verlinkt ist ausser Logo2
            if (tmpFoto.getName().equals("Logo2.jpg")) {
                assertThat(tmpFoto.getCounter(), is(7));
            }
            else {
                assertThat(tmpFoto.getCounter(), is(2));
            }
        }
        
        // Fotos zum Album hinzufügen und für das Löschen speichern [Rückwärts]
        for (int i = 4; i>=0; i--) {
            Foto newFoto = new Foto("Logo" + i + ".jpg", pathOfTestfolder + File.separator + "Logo" + i + ".jpg");
            fotoPathList.add(newFoto.getPfad());
            
            // Letztes Foto löschen [hier merken der nicht zu löschenden]
            if (i!=0) {
                fotoNotDeleteList.add(newFoto.getPfad());
            }
        }

        // Prüfen das Hinzufügen der Fotos im Album
        errorcode = FotoController.addListOfFotosToAlbum(title, fotoPathList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album zwanzig Fotos hält
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(20));
        
        // Erwartung bisher: Logo1, Logo2, Logo3, Logo2, Logo2, Logo2, Logo2, Logo0, Logo3, Logo4, Logo3, Logo2, Logo1
        // Aktuell: Logo0, Logo1, Logo2, Logo3, Logo4, Logo2, Logo2, Logo2, Logo2, Logo2, Logo0, Logo1, Logo2, Logo3, Logo4, Logo4, Logo3, Logo2, Logo1, Logo0
        // Prüfen der Fotocounter
        for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe())
        {
            // Prüft das jedes Foto dreimal verlinkt ist ausser Logo2
            if (tmpFoto.getName().equals("Logo2.jpg")) {
                assertThat(tmpFoto.getCounter(), is(8));
            }
            else {
                assertThat(tmpFoto.getCounter(), is(3));
            }
        }
        
        // Löschen der Temporären Liste
        fotoPathList.clear();
        
        // Prüft das Merkliste 13 Fotos hält
        assertThat(fotoNotDeleteList.size(), is(13));
        
        // Löschen der Fotos die nicht gemerkt wurden
        errorcode = FotoController.deleteNotExistingFotosInListFromAlbum(title, fotoNotDeleteList);
        if (errorcode!=0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album zwanzig-gemerkte Foto hält, also 13
        assertThat(AlbenController.getAlbum(title).getFotoListe().size(), is(13));
for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe())
{
    System.out.println(">" + tmpFoto.getName() + " " + tmpFoto.getCounter());
}
        // Erwartung bisher: Logo1, Logo2, Logo3, Logo2, Logo2, Logo2, Logo2, Logo0, Logo3, Logo4, Logo3, Logo2, Logo1
        // Prüfen der Fotocounter
        for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe())
        {
            // Prüft das jedes Foto dreimal verlinkt ist ausser Logo2
            if (tmpFoto.getName().equals("Logo1.jpg")) {
                assertThat(tmpFoto.getCounter(), is(2));
            }
            else if (tmpFoto.getName().equals("Logo2.jpg")) {
                assertThat(tmpFoto.getCounter(), is(6));
            }
            else if (tmpFoto.getName().equals("Logo3.jpg")) {
                assertThat(tmpFoto.getCounter(), is(3));
            }
            else {
                assertThat(tmpFoto.getCounter(), is(1));
            }
        }
    }
}
