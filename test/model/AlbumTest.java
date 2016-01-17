/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.TestDocumizer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Benni
 */
public class AlbumTest {

    //klassenvariablen
    private static Album testAlbum;
    private static String testTitel;
    private static String testBeschreibung;
    private static int testSortierkennzeichen;
    private static List<Foto> fotolist;
    private static Path testPicturePath;
    private static String appPath;
    private static String testDataPath;
    private static String pathSeparator;

    public AlbumTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        //Testdaten erstellen
        try {
            testTitel = "TestAlbumtitel";
            testBeschreibung = "TestBeschreibung";
            testSortierkennzeichen = 0;
            testAlbum = new Album(testTitel);
            pathSeparator = System.getProperty("file.separator");
            appPath = System.getProperty("user.dir");
            testDataPath = appPath + pathSeparator + "test" + pathSeparator + "testdaten";
            testPicturePath = Paths.get(testDataPath);
            DirectoryStream<Path> testPictureDirectory;
            fotolist = new ArrayList<Foto>();
            int i = 0;

            testPictureDirectory = Files.newDirectoryStream(testPicturePath, "*.{jpg,jpeg}");

            for (Path cur : testPictureDirectory) {
                Foto tmpFoto = new Foto("Bild" + String.valueOf(i), cur.toString());
                fotolist.add(tmpFoto);
                i++;
            }

            TestDocumizer.logging(0, "start jUnit Tests: Model - Album", true, true);
            TestDocumizer.startTimer();
        } catch (Exception e) {
            System.out.println("jUnit Test Album - Fehler bei Setup: " + e.getMessage());
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            TestDocumizer.logging(0, "beenden jUnit Tests: Model - Album: Dauer: " + String.valueOf(TestDocumizer.stopTimer()), true, true);

        } catch (Exception e) {
            System.out.println("jUnit Test Album - Fehler bei tearDown: " + e.getMessage());
        }
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    /**
     * Testet die Methoden getTitel und setTitel
     *
     * Version-History:
     *
     * @date 10.01.2016 by Benni: Erstellung
     *
     */
    @Test
    public void testTitel() {
        String tmpTitel = testAlbum.getTitel();
        String neuerTitel = "xyz";

        testAlbum.setTitel(neuerTitel);
        assertEquals(testAlbum.getTitel(), neuerTitel);
    }

    @Test
    public void testBeschreibung() {
        testAlbum.setBeschreibung(testBeschreibung);
        assertEquals(testAlbum.getBeschreibung(), testBeschreibung);
    }

    @Ignore
    @Test
    public void testErstellungsdatum() {
        //sinnvolle Prüfung überlegen
    }

    @Test
    public void testSortierkennzeichen() {
        testAlbum.setSortierkennzeichen(testSortierkennzeichen);
        assertEquals(testAlbum.getSortierkennzeichen(), testSortierkennzeichen);
    }

    @Test
    public void testFotoListe() {
        testAlbum.setFotoListe(fotolist);
        assertEquals(testAlbum.getFotoListe(), fotolist);
    }
}
