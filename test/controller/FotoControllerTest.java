/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static java.util.Optional.empty;
import model.Album;
import model.Foto;
import model.Metadaten;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Der FotoControllerTest testet alle Methoden der Klasse FotoController.
 * 
 * @author Daniel
 * 
 * @date 01.12.2015 by Daniel: Neustrukturierung für Kompatibilität mit zukünftigem TestRunner; testAddListOfFotosToAlbum bearbeitet; testDeleteAllFotosInAlbum hinzugefügt
 * @date 02.12.2015 by Daniel: testdaten, testGetFotosFromAlbum hinzugefügt; tearDown löscht container, metadaten funktioniert nicht (Kurztitel=null)
 * @date 03.12.2015 by Daniel: keine Verbesserung nach dem hinzufügen von foto zum container 
 * @date 04.12.2015 by Daniel: Neue Strukturierung der Tests
 */
public class FotoControllerTest {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 01.12.2015 by Daniel: Anlegen neuer Klassenvariablen
     * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
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
    private static Path pathOfFoto;
    private static List listOfPathes;
    private static List listOfFotos;
    private static Metadaten meta;
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
     * Initialisierung der Fixen Testdaten für Fotos zum Klassenstart
     * 
     * Version-History:
     * @date 05.12.2015 by Daniel: Initialisierung
     * @date 06.12.2015 by Daniel: Album 
     */
    @BeforeClass
    public static void prepareResourcesToTest(){
        SystemController.initializePmSystem();
        mapOfFotos = SystemController.getFotoContainer().getFotoMap();
        listOfAlbum = SystemController.getAlbumContainer().getAlbenListe();
        // Testalbum anlegen
        title = "Testtitel";
        beschreibung = "Testbeschreibung";
        sortierkennzeichen = 2;
        // Fixe Testdaten
        name = "Name";
        pathOfFoto = Paths.get("test/testdaten/Testbild43.jpeg");
        testFoto = new Foto(name, pathOfFoto.toString());
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
        listOfFotos = new LinkedList<>();
        listOfFotos.add(testFoto);
        meta = new Metadaten();
        meta.setzeWert(kurztitel, kurztitel);
        // Neue fixe Testdaten
        newName = "Neuer Name";
    }
    
    /**
     * Methode startet zu Begin der Klasse
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 04.12.2015 by Daniel: Setzen der Kommentare
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
     */
    @Test
    public void testSetMetaInFoto() {
        System.out.println("testSetMetaInFoto");
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        System.out.println(pathOfFoto + " : " + meta.getDaten());
        System.out.println(listOfPathes);
        System.out.println(listOfFotos);
        
        int errorcode = FotoController.setMetaInFoto(pathOfFoto, meta);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        Map<String, Object> expectMeta = meta.getDaten();
        System.out.println(expectMeta);
        
        Map<String, Object> resultMeta = FotoController.getMetaFromFoto(pathOfFoto).getDaten();
        System.out.println(resultMeta);
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
     */
    @Test
    public void testGetMetaFromFoto() {
        System.out.println("testGetMetaFromFoto");
        // Album wurde angelegt
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        

        Map<String, Object> expectMeta = meta.getDaten();
        Map<String, Object> resultMeta = FotoController.getMetaFromFoto(pathOfFoto).getDaten();
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
        System.out.println(listOfFotos.get(0));
        // Listen der lokalen Fotos und übergebenen Fotos stimmen überein
        assertEquals(expectList, resultList);
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
}
