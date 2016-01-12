package controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static java.util.Optional.empty;
import model.Album;
import model.Foto;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * In dieser Klasse werden alle Akzeptanztests durchgeführt.
 * 
 * @author Daniel
 * 
 * Version-History:
 * @date 03.01.2016 by Daniel: Initialisierung
 * @date 04.01.2016 by Daniel: Grundgerüst aufgebaut
 * @date 09.01.2016 by Daniel: Setup bearbeitet, testFotoHinzufügen hinzugefügt
 */
public class Akzeptanztests {
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 04.01.2016 by Daniel: Initialisierung
     */
    // Setup-Daten
    private static String filename;
    private static String originFilename;
    
    // Albumdaten
    private static List<Album> listOfAlbum;
    private static String title;
    private static String beschreibung;
    private static int sortierkennzeichen;
    
    // Fotodaten
    private static Map<Integer, Foto> mapOfFotos;
    private static List listOfPathes;
    private static List listOfFotos;
    private static Path pathOfFoto1;
    private static Path pathOfFoto2;
    private static Path pathOfFoto3;
    private static Path pathOfFoto4;
    
    public Akzeptanztests() {
    }
    
    /**
     * Methode startet zu Begin der Klasse
     * 
     * Version-History:
     * @date 04.01.2016 by Daniel: Initialisierung
     * @date 09.01.2016 by Daniel: Fotodaten
     */
    @BeforeClass
    public static void setUpClass() {
        originFilename = SystemController.getFilename();
        filename = "database.jdb";
        SystemController.setFilename(filename);
        SystemController.initializePmSystem();
        listOfAlbum = SystemController.getAlbumContainer().getAlbenListe();
        mapOfFotos = SystemController.getFotoContainer().getFotoMap();
        
        title = "Urlaubsalbum";
        beschreibung = "Italien 2005";
        sortierkennzeichen = 1;
        
        pathOfFoto1 = Paths.get("test/testdaten/Urlaubsfoto1.jpg");
        pathOfFoto2 = Paths.get("test/testdaten/Urlaubsfoto2.jpg");
        pathOfFoto3 = Paths.get("test/testdaten/Urlaubsfoto3.jpg");
        pathOfFoto4 = Paths.get("test/testdaten/Urlaubsfoto4.jpg");

        listOfFotos = new LinkedList<>();
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto1);
        listOfPathes.add(pathOfFoto2);
        listOfPathes.add(pathOfFoto3);
        listOfPathes.add(pathOfFoto4); 
    }
    
    /**
     * Methode startet zum Ende der Klasse
     * 
     * Version-History:
     * @date 04.01.2016 by Daniel: Initialisierung
     */
    @AfterClass
    public static void tearDownClass() {
        File storeFile = new File(filename);
        if (storeFile.exists()) storeFile.delete();
        SystemController.setFilename(originFilename);
        System.out.println("Fotos, Meta- und Exifdaten werden in der GUI angezeigt");
    }
    
    /**
     * Leeren der Datenbank
     * 
     * Version-History:
     * @date 04.01.2016 by Daniel: Initialisierung
     */
    @Before
    public void setUp() {
        mapOfFotos.clear();
        listOfAlbum.clear();
        SystemController.initializePmSystem();
    }
    
    /**
     * Formatierung Ausgabe
     * 
     * Version-History:
     * @date 10.01.2016 by Daniel: Initialisierung
     */
    @After
    public void tearDown() {
        System.out.println("");
    }
    
    /**
     * Testet, ob das Urlaubsalbum angelegt werden kann.
     * 
     * Version-History:
     * @date 04.01.2016 by Daniel: Initialisierung
     */
    @Test
    public void testAlbumAnlegen() {
        System.out.println("*Album anlegen*");
        
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
     * Testet, ob das Urlaubsalbum gelöscht werden kann.
     * 
     * Version-History:
     * @date 10.01.2016 by Daniel: Initialisierung
     */
    @Test
    public void testAlbumLöschen() {
        System.out.println("*Album löschen*");
        
        // Album wird angelegt
        AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Urlaubsalbum wurde zum Löschen markiert
        List<String> deleteList = new LinkedList<>();
        deleteList.add(title);
        
        // Löschen des Urlaubsalbums
        int errorcode = AlbenController.deleteListOfAlbum(deleteList);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }
        
        // Ob das Urlaubsalbum gelöscht wurde
        assertThat(SystemController.getAlbumContainer().anzahlAlben(), is(0));
    }
    
    /**
     * Testet, ob dem Urlaubsalbum Fotos hinzugefügt werden können.
     * 
     * Version-History:
     * @date 09.01.2016 by Daniel: Initialisierung
     * @date 10.01.2016 by Daniel: Prüft Anzahl der Fotos, Album wird nun hier angelegt
     */
    @Test
    public void testFotosHinzufuegen() {
        System.out.println("*Fotos hinzufügen*");
        
        // Album wird angelegt
        AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Errorcode != 0, wenn ein Fehler aufgetreten ist
        int errorcode = FotoController.addListOfFotosToAlbum(title, listOfPathes);
        if (errorcode != 0) {
            fail(ErrorController.changeErrorCode(errorcode)[1]);
        }

        // Das Urlaubsalbum enthält 4 Fotos
        assertThat(SystemController.getFotoContainer().anzahlFotos(), is(4));
        
        // Prüfen der Fotocounter
        for (Foto tmpFoto : AlbenController.getAlbum(title).getFotoListe()) {
            assertThat(tmpFoto.getCounter(), is(1));
        }
    }
    
    /**
     * Testet, ob die Fotos des Urlaubsalbums gelöscht werden können.
     * 
     * Version-History:
     * @date 10.01.2016 by Daniel: Initialisierung
     */
    @Test
    public void testFotosLoeschen() {
        System.out.println("*Fotos löschen*");
        
        // Album wird angelegt
        AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        assertThat(AlbenController.getAlbum(title), is(notNullValue()));
        
        // Liste von Fotos hinzugefügt
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        assertThat(FotoController.getFotosFromAlbum(title), not(empty()));
        
        // Fotos wurden gelöscht
        FotoController.deleteAllFotosInAlbum(AlbenController.getAlbum(title));
        List<Path> expectList = new LinkedList<>();
        List<Path> resultList = FotoController.getFotosFromAlbum(title);
        assertEquals(expectList, resultList);
    }

}