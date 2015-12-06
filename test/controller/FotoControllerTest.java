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
import model.Album;
import model.Foto;
import model.Metadaten;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import static org.hamcrest.CoreMatchers.is;
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
 * @date 01.12.2015 by Daniel: Neue Strukturierung der Tests
 */
public class FotoControllerTest {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     * @date 01.12.2015 by Daniel: Anlegen neuer Klassenvariablen
     */
    private static Map<Integer, Foto> mapOfFotos;
    private static long timeUsing;
    private static long timeGeneral;
    // Daten des Testalbums
    private static String title;
    private static String beschreibung;
    private static String sortierkennzeichen;
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
     * @date 04.12.2015 by Daniel: Initialisierung
     */
    @BeforeClass
    public static void prepareResourcesToTest(){
        SystemController.initializePmSystem();
        mapOfFotos = SystemController.getFotoContainer().getFotoMap();
        //Testalbum anlegen
        title = "Testtitel";
        beschreibung = "Testbeschreibung";
        sortierkennzeichen = "Testkennzeichen";
        AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        // Fixe Testdaten
        name = "Name";
        pathOfFoto = Paths.get("test/testdaten/Testbild43.jpeg");
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
        // Testfoto anlegen und zum Album hinzufügen
       /* Foto testFoto = new Foto(name, pathOfFoto);
        listOfFotos = new LinkedList<>();
        listOfFotos.add(testFoto);
        AlbenController.getAlbum(title).setFotoListe(listOfFotos);*/
        // Metadaten anlegen und zum Foto hinzufügen
        //meta = new Metadaten();
        //meta.setzeWert(kurztitel, kurztitel);
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
        AlbenController.getAlbumList().clear();
        System.out.println("\n=== FotoControllerTest ===\nTestzeit der Klasse: " + (timeGeneral/1000000) + " ms [" + (timeGeneral/1000) + " us]\n");
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
        mapOfFotos.clear();
        SystemController.initializePmSystem();
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
     * Methode sucht nach einem Album und gibt dieses zurück
     * INFO: Protected da FotoContainer diese nutzen muss
     * 
     * Version-History:
     * @param title Übergabe des gesuchten Albumtitels
     * @return Rückgabe des Albums, wenn keins gefunden dann null
     *  
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    private Album getAlbum(String title) {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {  
                return tmpAlbum;
            }
        }
        return null;
    }
    
    /**
     * Methode sucht nach einem Foto und gibt dieses zurück
     * INFO: Protected da FotoContainer diese nutzen muss
     * 
     * Version-History:
     * @param title Übergabe des gesuchten Albumtitels
     * @return Rückgabe des Albums, wenn keins gefunden dann null
     * 
     * Version-History:
     * @date 05.12.2015 by Daniel: Initialisierung
     */
    private Foto getFoto(String title) {
        for (Foto tmpFoto : SystemController.getFotoContainer().getFotoMap().values()) {
            if (tmpFoto.getName().equals(name)) {  
                return tmpFoto;
            }
        }
        return null;
    }
    
    /**
     * Testet die Methode testSetMetaInFoto der Klasse FotoController.
     * Testet, ob die gesetzten Metadaten des Fotos mit der lokal erstellten Metadaten übereinstimmen.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    @Test
    @Ignore
    public void testSetMetaInFoto() {
        System.out.println("setMetaInFoto");
        System.out.println(pathOfFoto + " : " + meta.getDaten());
        
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        FotoController.setMetaInFoto(pathOfFoto, meta);
        Map<String, Object> expResult = meta.getDaten();
        System.out.println(expResult);
        
        Map<String, Object> result = FotoController.getMetaFromFoto(pathOfFoto).getDaten();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Testet die Methode testGetFotosFromAlbum der Klasse FotoController.
     * Testet, ob die geholten Metadaten des Fotos mit den lokal erstellten Metadaten übereinstimmen.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    @Test
    @Ignore
    public void testGetMetaFromFoto() {
        System.out.println("getMetaFromFoto");
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        Metadaten expResult = meta;
        Metadaten result = FotoController.getMetaFromFoto(pathOfFoto);
        assertEquals(expResult, result);
    }

    /**
     * Testet die Methode testGetFotosFromAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums mit der lokal erstellten Liste übereinstimmt.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    @Test
    public void testGetFotosFromAlbum() {
        System.out.println("getFotosFromAlbum");
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        assertThat(getAlbum(title), is(notNullValue()));
        
        List expectList = listOfPathes;
        //System.out.println(listOfFotos.get(0));
        List resultList = FotoController.getFotosFromAlbum(title);
        assertEquals(expectList, resultList);
    }

    /**
     * Testet die Methode testAddListOfFotosToAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums mit der übergebenen Liste übereinstimmt.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    @Test
    @Ignore
    public void testAddListOfFotosToAlbum() {
        System.out.println("addListOfFotosToAlbum");
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        List expectList = listOfPathes;
        List resultList = getAlbum(title).getFotoListe();
        assertEquals(expectList, resultList);
    }

    /**
     * Testet die Methode testDeleteAllFotosInAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums gelöscht wurde.
     * 
     * Version-History:
     * @date 01.12.2015 by Daniel: Initialisierung
     */
    @Test
    @Ignore
    public void testDeleteAllFotosInAlbum() {
        System.out.println("deleteAllFotosInAlbum"); 
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        assertThat(getAlbum(title).getFotoListe(), is(notNullValue()));
        
        FotoController.deleteAllFotosInAlbum(getAlbum(title));
        List<Foto> expectList = new LinkedList<>();
        List<Foto> resultList = AlbenController.getAlbum(title).getFotoListe();
        assertEquals(expectList, resultList);
    }
}
