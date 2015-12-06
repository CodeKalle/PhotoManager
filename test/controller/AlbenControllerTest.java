package controller;

import java.util.LinkedList;
import java.util.List;
import model.Album;
import org.junit.*;
import static org.junit.Assert.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import static org.hamcrest.CoreMatchers.*;

/**
 * Der AlbenControllerTest testet alle Methoden der Klasse AlbenController.
 * 
 * @author Daniel
 * 
 * Version-History:
 * @date 01.12.2015 by Daniel: Ignores für nicht-bearbeitete Tests gesetzt.
 * @date 02.12.2015 by Daniel: tearDown löscht container
 * @date 03.12.2015 by Daniel: editAlbum, createNewAlbumIsExpectedAlbum, deleteAlbum, deleteListOfAlbums, createNewAlbum hinzugefügt
 * @date 04.12.2015 by Danilo: Anpassung an geänderten AlbenController
 * @date 05.12.2015 by Danilo: Anpassung der Zeitausgabe bei garantierte Albumanzahl
 */
public class AlbenControllerTest {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung an geänderten AlbenController
     * @date 05.12.2015 by Danilo: Anpassung der Zeitausgabe bei garantierte Albumanzahl
     */
    private static List<Album> listOfAlbum;
    private static long timeUsing;
    private static long timeGeneral;
    // Fixe Testdaten
    private static String title;
    private static String beschreibung;
    private static String sortierkennzeichen;
    // Neue fixe Testdaten
    private static String newTitle;
    private static String newBeschreibung;
    private static String newSortierkennzeichen;
    private static String shortTitle;
    private static String longTitle;
    // Zufällige Testdaten
    private String randomTitel;
    private String randomBeschreibung;
    private String randomSortierkennzeichen;
    // Weitere Testdaten
    private final static String EMPTYSTRING = "";
    private final static String NULLSTRING = null; 
    private final static int garanteedAlbumCount = 500;
            
    /**
     * Standard Konstruktor der Klasse
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    public AlbenControllerTest() {
    }
    
    /**
     * Methode startet zu Begin der Klasse
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Setzen der Kommentare
     * @date 06.12.2015 by Danilo: prepareResourcesToTest in setUpClass umwandeln
     */
    @BeforeClass
    public static void setUpClass() {
        SystemController.initializePmSystem();
        listOfAlbum = SystemController.getAlbumContainer().getAlbenListe();
        // Fixe Testdaten
        title = "Testtitel";
        beschreibung = "Testbeschreibung";
        sortierkennzeichen = "Testkennzeichen";
        // Neue fixe Testdaten
        newTitle = "Neuer Testtitel";
        newBeschreibung = "Neue Testbeschreibung";
        newSortierkennzeichen = "Neue Kennzeichen";
        shortTitle = "T";
        longTitle = "Ein so langer Titel, ist hoffentlich zu lang";
    }
    
    /**
     * Methode startet zu Begin der Klasse
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Setzen der Kommentare
     */
    @BeforeClass
    public static void setUpClass() {  
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
        System.out.println("\n=== AlbenControllerTest ===\nTestzeit der Klasse: " + (timeGeneral/1000000) + " ms [" + (timeGeneral/1000) + " us]\n");
    }
    
    /**
     * Leeren der Datenbank und Initialisierung der zufälligen Testdaten für das prüfen der Testmethoden
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung an geänderten AlbenController
     */
    @Before
    public void setUp() {
        listOfAlbum.clear();
        SystemController.initializePmSystem();
        generateRandomData();
        timeUsing = System.nanoTime();
    }
    
    /**
     * Anzeigen der benötigten Zeit der getesteten Methode
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung an geänderten AlbenController
     */
    @After
    public void tearDown() {
        long time = System.nanoTime();
        timeGeneral += (time - timeUsing);
        System.out.println("Testzeit der Methode: " + ((time - timeUsing)/1000000) + " ms [" + ((time - timeUsing)/1000) + " us]\n");
    }

    /**
     * Generiert neue Zufällige Daten
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     */
    private void generateRandomData() {
        do{
            randomTitel = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(17) + 4);
        } while (title.equals(randomTitel));
        do{
            randomBeschreibung = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(201));
        } while (beschreibung.equals(randomBeschreibung));
        do{
            randomSortierkennzeichen = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(21));
        } while (sortierkennzeichen.equals(randomSortierkennzeichen));
    }
    
    /**
     * Legt ein zufälliges Album im AlbenContainer an und gibt den Titel zurück.
     * 
     * Version-History:
     * @return result Ausgabe des Albumtitels
     * @date 04.12.2015 by Danilo: Initialisierung
     */
    private String generateRandomAlbum() {
        // Neue Werte für Album generieren falls vorher schon genutzt
        generateRandomData();
        
        // Prüft das Album nicht existiert
        Album expectAlbum = AlbenController.getAlbum(randomTitel);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit fixen Daten
        int errorcode = AlbenController.createNewAlbum(randomTitel, randomBeschreibung, randomSortierkennzeichen);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album mit fixen Daten angelegt wurde
        expectAlbum = AlbenController.getAlbum(randomTitel);
        assertEquals(randomTitel, expectAlbum.getTitel());
        assertEquals(randomBeschreibung, expectAlbum.getBeschreibung());
        assertEquals(randomSortierkennzeichen, expectAlbum.getSortierkennzeichen());
        
        return randomTitel;
    }

    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob ein Album angelegt wird.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung auf neue Version des AlbenControllers
     */
    @Test
    public void testCreateNewAlbumSuccess() {
        System.out.println("testCreateNewAlbumSuccess");
        
        // Prüft das Datenbank leer ist
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(0));
        
        // Prüft das Album nicht existiert
        Album expectAlbum = AlbenController.getAlbum(title);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit fixen Daten
        int errorcode = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album mit fixen Daten angelegt wurde
        expectAlbum = AlbenController.getAlbum(title);
        assertEquals(title, expectAlbum.getTitel());
        assertEquals(beschreibung, expectAlbum.getBeschreibung());
        assertEquals(sortierkennzeichen, expectAlbum.getSortierkennzeichen());
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(1));
    }
    
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob ein zufälliges Album angelegt wird.
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testCreateRandomAlbumSuccess() {
        System.out.println("testCreateRandomAlbumSuccess");
        
        // Prüft das Datenbank leer ist
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(0));
        
        // Prüft das Album nicht existiert
        Album expectAlbum = AlbenController.getAlbum(randomTitel);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit zufälligen Daten
        int errorcode = AlbenController.createNewAlbum(randomTitel, randomBeschreibung, randomSortierkennzeichen);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album mit zufälligen Daten angelegt wurde
        expectAlbum = AlbenController.getAlbum(randomTitel);
        assertEquals(randomTitel, expectAlbum.getTitel());
        assertEquals(randomBeschreibung, expectAlbum.getBeschreibung());
        assertEquals(randomSortierkennzeichen, expectAlbum.getSortierkennzeichen());
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(1));
    }
    
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob ein Album mit leerem String angelegt wird.
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testCreateNewAlbumEmptyTitle() {
        System.out.println("testCreateNewAlbumEmptyTitle");
        
        // Prüft das Album mit leerem Titel nicht existiert
        Album expectAlbum = AlbenController.getAlbum(EMPTYSTRING);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit leerem Titel
        int errorcode = AlbenController.createNewAlbum(EMPTYSTRING, beschreibung, sortierkennzeichen);
        if (errorcode == 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album mit leerem Titel nicht angelegt wurde
        expectAlbum = AlbenController.getAlbum(EMPTYSTRING);
        assertThat(expectAlbum, is(nullValue()));
    }
    
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob ein Album mit null String angelegt wird.
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testCreateNewAlbumNullTitle() {
        System.out.println("testCreateNewAlbumNullTitle");
        
        // Prüft das Album mit null Titel nicht existiert
        Album expectAlbum = AlbenController.getAlbum(NULLSTRING);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit null Titel
        int errorcode = AlbenController.createNewAlbum(NULLSTRING, beschreibung, sortierkennzeichen);
        if (errorcode == 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album mit null Titel nicht angelegt wurde
        expectAlbum = AlbenController.getAlbum(NULLSTRING);
        assertThat(expectAlbum, is(nullValue()));
    }
    
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob ein Album mit zu langem Namen angelegt wird.
     * 
     * Version-History:
     * @date 06.12.2015 by Daniel: Initialisierung
     */
    @Test
    public void testCreateNewAlbumShortTitle() {
        System.out.println("testCreateNewAlbumLongTitle");
        
        // Prüft das Datenbank leer ist
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(0));
        
        // Prüft das Album mit longTitle Titel nicht existiert
        Album expectAlbum = AlbenController.getAlbum(shortTitle);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit longTitle Titel
        int errorcode = AlbenController.createNewAlbum(shortTitle, beschreibung, sortierkennzeichen);
        if (errorcode != 115) {
            fail(ErrorController.changeErrorCode(errorcode)[115]);
        }

        // Prüft das Album mit zu kurzem Titel nicht angelegt wurde
        expectAlbum = AlbenController.getAlbum(shortTitle);
        assertThat(expectAlbum, is(nullValue()));
    }
    
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob ein Album mit zu langem Namen angelegt wird.
     * 
     * Version-History:
     * @date 06.12.2015 by Daniel: Initialisierung
     */
    @Test
    public void testCreateNewAlbumLongTitle() {
        System.out.println("testCreateNewAlbumLongTitle");
        
        // Prüft das Datenbank leer ist
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(0));
        
        // Prüft das Album mit longTitle Titel nicht existiert
        Album expectAlbum = AlbenController.getAlbum(longTitle);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit longTitle Titel
        int errorcode = AlbenController.createNewAlbum(longTitle, beschreibung, sortierkennzeichen);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }

        // Prüft das Album mit dem auf 20 Stellen gekürzten Titel nicht angelegt wurde
        String gekuerzterTitel = longTitle.substring(0,20);
        expectAlbum = AlbenController.getAlbum(gekuerzterTitel);
        assertEquals(gekuerzterTitel, expectAlbum.getTitel());
        assertEquals(beschreibung, expectAlbum.getBeschreibung());
        assertEquals(sortierkennzeichen, expectAlbum.getSortierkennzeichen());
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(1));
    }
    
    /**
     * Testet die Methode editAlbum der Klasse AlbenController.
     * Testet, ob das editierte Album die neuen Attribute übernommen hat.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung auf neue Version des AlbenControllers
     */
    @Test
    public void testEditAlbumSuccess() {
        System.out.println("testEditAlbumSuccess");
        
        // Generierung eines Zufälligen Albums und merken der gesetzten Werte
        String oldTitel = generateRandomAlbum();
        Album oldAlbum = AlbenController.getAlbum(oldTitel);
        String oldBeschreibung = oldAlbum.getBeschreibung();
        String oldSortierkennzeichen = oldAlbum.getSortierkennzeichen();
        
        // Album ändern mit neuen Werten
        int errorcode = AlbenController.editAlbum(oldTitel, newTitle, newBeschreibung, newSortierkennzeichen);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Holt Album aus der Datenbank
        Album result = AlbenController.getAlbum(newTitle);
        
        // Prüft das alte werte durch neue Werte ersetzt wurden
        assertThat(result.getTitel(), is(not(oldTitel)));
        assertThat(result.getBeschreibung(), is(not(oldBeschreibung)));
        assertThat(result.getSortierkennzeichen(), is(not(oldSortierkennzeichen)));
        
        // Da nun oldAlbum und result gleiche Referenz, müssen diese gleich sein
        assertThat(oldAlbum.getTitel(), is(result.getTitel()));
        assertThat(oldAlbum.getBeschreibung(), is(result.getBeschreibung()));
        assertThat(oldAlbum.getSortierkennzeichen(), is(result.getSortierkennzeichen()));
        
        // Prüft das neuer Titel, Beschreibung und Sortierkennzeichen richtig gestzt
        assertThat(result.getTitel(), is(newTitle));
        assertThat(result.getBeschreibung(), is(newBeschreibung));
        assertThat(result.getSortierkennzeichen(), is(newSortierkennzeichen));
    }
    
    /**
     * Testet die Methode editAlbum der Klasse AlbenController.
     * Testet, ob das editierte Album leere Attribute übernommen hat, außer Titel.
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testEditAlbumEmptyValue() {
        System.out.println("testEditAlbumEmptyValue");
        
        // Generierung eines Zufälligen Albums und merken des Titels
        String oldTitel = generateRandomAlbum();
        
        // Holen des Albums welches generiert wurde
        Album result = AlbenController.getAlbum(oldTitel);
        
        // Album ändern mit leerem Titel und neuen Werten
        int errorcode = AlbenController.editAlbum(oldTitel, EMPTYSTRING, newBeschreibung, newSortierkennzeichen);
        if (errorcode == 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album nicht geändert wurde
        assertThat(result.getTitel(), is(oldTitel));
        
        // Prüft das kein Album mit leerem Textstring existiert
        Album expectAlbum = AlbenController.getAlbum(EMPTYSTRING);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album ändern mit leerer Beschreibung und neuem Sortierkennzeichen
        errorcode = AlbenController.editAlbum(oldTitel, oldTitel, EMPTYSTRING, newSortierkennzeichen);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album geändert wurde und Beschreibung leer ist
        expectAlbum = AlbenController.getAlbum(oldTitel);
        assertThat(expectAlbum.getBeschreibung().isEmpty(), is(true));
        
        // Album ändern mit leerem Titel und neuen Werten
        errorcode = AlbenController.editAlbum(oldTitel, oldTitel, newBeschreibung, EMPTYSTRING);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album geändert wurde und Sortierkennzeichen leer ist
        expectAlbum = AlbenController.getAlbum(oldTitel);
        assertThat(expectAlbum.getSortierkennzeichen().isEmpty(), is(true));
    }
    
    /**
     * Testet die Methode editAlbum der Klasse AlbenController.
     * Testet, ob das editierte Album die null Attribute nicht übernommen hat.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung auf neue Version des AlbenControllers
     */
    @Test
    public void testEditAlbumNullValue() {
        System.out.println("testEditAlbumNullValue");
        
        // Generierung eines Zufälligen Albums und merken des Titels
        String oldTitel = generateRandomAlbum();
        
        // Holen des Albums welches generiert wurde
        Album result = AlbenController.getAlbum(oldTitel);
        
        // Merken der werte
        String oldBeschreibung = result.getBeschreibung();
        String oldSortierkennzeichen = result.getSortierkennzeichen();
        
        // Album ändern mit leerem Titel und neuen Werten
        int errorcode = AlbenController.editAlbum(oldTitel, NULLSTRING, newBeschreibung, newSortierkennzeichen);
        if (errorcode == 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album nicht geändert wurde
        assertThat(result.getTitel(), is(oldTitel));
        
        // Prüft das kein Album mit null Textstring existiert
        Album expectAlbum = AlbenController.getAlbum(NULLSTRING);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album ändern mit null Beschreibung und neuem Sortierkennzeichen
        errorcode = AlbenController.editAlbum(oldTitel, oldTitel, NULLSTRING, newSortierkennzeichen);
        if (errorcode == 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album nicht geändert wurde
        expectAlbum = AlbenController.getAlbum(oldTitel);
        assertThat(expectAlbum.getBeschreibung(), is(oldBeschreibung));
        
        // Album ändern mit neuer Beschreibung und null Sortierkennzeichen
        errorcode = AlbenController.editAlbum(oldTitel, oldTitel, newBeschreibung, NULLSTRING);
        if (errorcode == 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Album nicht geändert wurde
        expectAlbum = AlbenController.getAlbum(oldTitel);
        assertThat(expectAlbum.getSortierkennzeichen(), is(oldSortierkennzeichen));
    }
    
    /**
     * Testet die Methode getAlbum der Klasse AlbenController.
     * Testet, ob ein Album aus der Datenbank gelesen werden kann.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung auf neue Version des AlbenControllers
     */
    @Test
    public void testGetAlbum() {
        System.out.println("testGetAlbum");
        
        // Prüft das Datenbank leer ist
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(0));
        
        // Prüft das Album nicht existiert
        Album expectAlbum = AlbenController.getAlbum(title);
        assertThat(expectAlbum, is(nullValue()));
        
        // Album anlegen mit fixen Daten
        int errorcode = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das holen des Albums mit getAlbum
        assertEquals(title, AlbenController.getAlbum(title).getTitel());
        assertEquals(beschreibung, AlbenController.getAlbum(title).getBeschreibung());
        assertEquals(sortierkennzeichen, AlbenController.getAlbum(title).getSortierkennzeichen());
    }
    
    /**
     * Testet die Methode getAlbum der Klasse AlbenController.
     * Testet, Zeitverzögerung des lesens der Datenbank bei garantierter Albenanzahl.
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     * @date 05.12.2015 by Danilo: Anpassung an Positionen in Liste
     */
    @Test
    public void testGetAlbumGuaranteedStability() {
        System.out.println("testGetAlbumGuaranteedStability");
        
        // Prüft das Datenbank keine Alben enthält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(0));
        
        // Albumtitel in der Linkedliste
        String[] albumTitel = new String[garanteedAlbumCount];
        
        // Anlegen von 500 Alben
        for (int i = 0; i< garanteedAlbumCount; i++) {
                albumTitel[i] = generateRandomAlbum();
        }
        
        // Prüft das Datenbank garantierte Alben enthält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(500));
        
        // Bestimmen und prüfen definierter Albennummern
        int[] checkAlbumNumber = {1, garanteedAlbumCount/2, garanteedAlbumCount};
        for (int i = 0; i < checkAlbumNumber.length; i++) {
            // Zeitmessung starten
            long time = System.nanoTime();
            
            // Album aus LinkedList holen
            Album result = AlbenController.getAlbum(albumTitel[checkAlbumNumber[i]-1]);
            if (result==null) {
                fail("Album nicht gefunden");
            }
            
            // Zeitmessung beenden
            time = System.nanoTime() - time;
            
            // Generierung der visuellen Ausgabe
            System.out.println("Zeit für " + (checkAlbumNumber[i]) + ". Album in Liste: " + ((time)/1000000) + " ms [" + ((time)/1000) + " us]");

            // Prüfen das Zeit unter einer Sekunde liegt
            assertThat((time < 1000000000), is(true));
        }
    }
    
    /**
     * Testet die Methode deleteListOfAlbum der Klasse AlbenController.
     * Testet, ob die übergebene Albenliste aus dem AlbenContainer gelöscht wird.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Danilo: Anpassung auf neue Version des AlbenControllers
     */
    @Test
    public void testDeleteListOfAlbumSuccess() {
        System.out.println("testDeleteListOfAlbumSuccess");
        
        List<String> tmpAlbenList = new LinkedList();
        List<String> deleteList = new LinkedList();
        
        // min. 5 bis max. 20 Alben
        int randomAlbenCount = (int)(Math.random()*21)+5;
        
        // Anlegen von min. 5 bis max. 20 Alben
        for (int i = 0; i< randomAlbenCount; i++) {
            tmpAlbenList.add(generateRandomAlbum());
            
            // Alle geraden Alben zum löschen merken
            if (i%2==0) deleteList.add(tmpAlbenList.get(i));
        }
        
        // Prüft das Datenbank die zufällige Anzahl an Alben hält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(randomAlbenCount));
        
        // Löschen der Alben aus dem AlbenContainer
        int errorcode = AlbenController.deleteListOfAlbum(deleteList);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Prüft das Datenbank die zufällige Anzahl an Alben minus der aus der Löschliste hält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(randomAlbenCount - deleteList.size()));
    }
    
    /**
     * Testet die Methode deleteListOfAlbum der Klasse AlbenController.
     * Testet, ob die übergebene Albenliste aus dem AlbenContainer mit garantierter Albenanzahl gelöscht wird.
     * 
     * Version-History:
     * @date 05.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testDeleteListOfAlbumGuaranteedStability() {
        System.out.println("testDeleteListOfAlbumGuaranteedStability");
        
        List<String> tmpAlbenList = new LinkedList();
        List<String> deleteList = new LinkedList();
        
        // Anlegen von garantierten Alben
        for (int i = 0; i< garanteedAlbumCount; i++) {
            tmpAlbenList.add(generateRandomAlbum());
            
            // Alle ungeraden Alben zum löschen merken
            if (i%2==1) deleteList.add(tmpAlbenList.get(i));
        }
        
        // Prüft das Datenbank die zufällige Anzahl an Alben hält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(garanteedAlbumCount));
        
        // Zeitmessung starten
        long time = System.nanoTime();
            
        // Löschen der Alben aus dem AlbenContainer
        int errorcode = AlbenController.deleteListOfAlbum(deleteList);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Zeitmessung beenden
        time = System.nanoTime() - time;
            
        // Generierung der visuellen Ausgabe
        System.out.println("Zeit für löschen aller ungeraden Alben in Liste: " + ((time)/1000000) + " ms [" + ((time)/1000) + " us]");
        
        // Prüft das Datenbank die zufällige Anzahl an Alben minus der aus der Löschliste hält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(garanteedAlbumCount - deleteList.size()));
    }
    
    /**
     * Testet die Methode deleteListOfAlbum der Klasse AlbenController.
     * Testet, ob die übergebene Albenliste aus dem AlbenContainer glöscht wird.
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testDeleteListOfAlbumMissingAlbum() {
        System.out.println("testDeleteListOfAlbumMissingAlbum");
        
        List<String> tmpAlbenList = new LinkedList();
        List<String> deleteList = new LinkedList();
        
        // min. 5 bis max. 20 Alben
        int randomAlbenCount = (int)(Math.random()*21)+5;
        
        // Anlegen von min. 5 bis max. 20 Alben
        for (int i = 0; i< randomAlbenCount; i++) {
            tmpAlbenList.add(generateRandomAlbum());
            
            // Alle geraden Alben zum löschen merken
            if (i%2==0) deleteList.add(tmpAlbenList.get(i));
        }
        
        // min. 3 bis max. 10 Nicht vorhanden Albentitel
        int randomStringCount = (int)(Math.random()*11)+3;
        
        // Anlegen von min. 3 bis max. 10 Strings die kein Albentitel sind
        for (int i = 0; i<randomStringCount; i++) {
            do {
                randomTitel = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(17) + 4);
            } while(AlbenController.getAlbum(randomTitel)!=null);
            deleteList.add(randomTitel);
        }
        
        // Prüft das Datenbank die zufällige Anzahl an Alben hält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(randomAlbenCount));
        
        // Löschen der Alben aus dem AlbenContainer
        int errorcode = AlbenController.deleteListOfAlbum(deleteList);
        if (errorcode == 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        assertThat(errorcode, is(310*randomStringCount));
        
        // Prüft das Datenbank die zufällige Anzahl an Alben minus der aus der Löschliste hält
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(not(randomAlbenCount - deleteList.size())));
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(randomAlbenCount - (deleteList.size()-randomStringCount)));
    }
    
    /**
     * Testet die Methode getAlbumList der Klasse AlbenController.
     * Testet, ob die Liste der Alben korrekt abgerufen wird.
     * 
     * Version-History:
     * @date 04.12.2015 by Danilo: Initialisierung
     * @date 05.12.2015 by Danilo: Ausgabe im Fehlerfall der Albenerstellung
     */
    @Test
    public void testGetAlbumList() {
        System.out.println("testGetAlbumList");
        
        // Holen der Albenliste aus dem AlbenContainer
        List<String> tmpList = AlbenController.getAlbumList();
        
        // Prüfen das Alpenliste leer ist
        assertThat(tmpList.isEmpty(), is(true));
        assertThat(tmpList.size(), is(0));
        
        // min. 5 bis max. 20 Alben
        int randomAlbenCount = (int)(Math.random()*21)+5;
        
        // Anlegen von min. 5 bis max. 20 Alben
        for (int i = 0; i< randomAlbenCount; i++) {
            generateRandomAlbum();
        }
        
        // Holen der Albenliste aus dem AlbenContainer
        tmpList = AlbenController.getAlbumList();
        if (tmpList == null) {
            fail("Es wurden keine Alben erstellt.");
        }
        
        // Prüfen das Alpenliste nicht leer ist
        assertThat(tmpList.isEmpty(), is(false));
        assertThat(tmpList.size(), is(randomAlbenCount));
    }
    
    /**
     * Testet die Methode getAlbumList der Klasse AlbenController.
     * Testet, ob die Liste der Alben bei garantierter Albananzahl korrekt abgerufen wird.
     * 
     * Version-History:
     * @date 05.12.2015 by Danilo: Initialisierung
     */
    @Test
    public void testGetAlbumListGuaranteedStability() {
        System.out.println("testGetAlbumListGuaranteedStability");
        
        // Holen der Albenliste aus dem AlbenContainer
        List<String> tmpList = AlbenController.getAlbumList();
        
        // Prüfen das Alpenliste leer ist
        assertThat(tmpList.isEmpty(), is(true));
        assertThat(tmpList.size(), is(0));
        
        // Anlegen von garantierten Alben
        for (int i = 0; i< garanteedAlbumCount; i++) {
            generateRandomAlbum();
        }
        
        // Zeitmessung starten
        long time = System.nanoTime();
        
        // Holen der Albenliste aus dem AlbenContainer
        tmpList = AlbenController.getAlbumList();
        if (tmpList == null) {
            fail("Es wurden keine Alben erstellt.");
        }
        
        // Zeitmessung beenden
        time = System.nanoTime() - time;
            
        // Generierung der visuellen Ausgabe
        System.out.println("Zeit für das Erhalten der Albenliste: " + ((time)/1000000) + " ms [" + ((time)/1000) + " us]");
        
        // Prüfen das Alpenliste nicht leer ist
        assertThat(tmpList.isEmpty(), is(false));
        assertThat(tmpList.size(), is(garanteedAlbumCount));
    }
}
