/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AlbenContainer;
import model.Album;
import model.Foto;
import model.Metadaten;
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
 */
public class FotoControllerTest {
    
    private Album testAlbum;
    private Foto testFoto;
    private Path pathOfFoto;
    private List<Path> listOfPathes;
    private String title = "title";
    private String beschreibung = "beschr";
    private String sortierkennzeichen = "kennz";
        
    public FotoControllerTest() {
        testAlbum = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        pathOfFoto = Paths.get("Testbild43.jpeg");
        testFoto = new Foto("Fototitel", pathOfFoto);
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        SystemController.getFotoContainer().getFotoMap().clear();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Gibt alle Attribute eines Fotos aus.
     * 
     * @param result auszugebendes Album
     */
    private void printResult(Foto result) {
        System.out.println(result);
        System.out.println("Name: " + result.getName());
        System.out.println("Pfad: " + result.getPfad());
        System.out.println("Groesse: " + result.getGroesse());
        System.out.println("Metadata: " + result.getMetadata());
        System.out.println("Counter: " + result.getCounter());
    }
    
    /**
     * Leert den Fotocontainer.
     */
    private void cleanFotoContainer() {
        SystemController.getFotoContainer().getFotoMap().clear();
    }
    
    /**
    * Methode sucht nach einem Album und gibt dieses zurück
    * INFO: Protected da FotoContainer diese nutzen muss
    * 
    * Version-History:
    * @param title Übergabe des gesuchten Albumtitels
    * @return Rückgabe des Albums, wenn keins gefunden dann null
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
     * Test of setMetaInFoto method, of class FotoController.
     */
    @Test
    @Ignore
    public void testSetMetaInFoto() {
        System.out.println("setMetaInFoto");
      /*  Metadaten expResult = meta;
        
        FotoController.setMetaInFoto(pathOfFoto, meta);
        Metadaten result = FotoController.getMetaFromFoto(pathOfFoto);
        assertEquals(expResult, result);
        cleanFotoContainer();*/
    }

    /**
     * Test of getMetaFromFoto method, of class FotoController.
     */
    @Test
    @Ignore
    public void testGetMetaFromFoto() {
        System.out.println("getMetaFromFoto");
     //   Metadaten expResult = meta;
        Metadaten result = FotoController.getMetaFromFoto(pathOfFoto);
     //   assertEquals(expResult, result);
        cleanFotoContainer();
    }

    /**
     * Test of getFotosFromAlbum method, of class FotoController.
     */
    @Test
    @Ignore
    public void testGetFotosFromAlbum() {
        System.out.println("getFotosFromAlbum");
        String title = "";
        List<Path> expResult = null;
        List<Path> result = FotoController.getFotosFromAlbum(title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Testet die Methode testAddListOfFotosToAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums mit der übergebenen Liste übereinstimmt.
     */
    @Test
    public void testAddListOfFotosToAlbum() {
        System.out.println("addListOfFotosToAlbum");
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        List expResult = listOfPathes;
        List result = getAlbum(title).getFotoListe();
        assertEquals(expResult, result);
    }

    /**
     * Testet die Methode testDeleteAllFotosInAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums gelöscht wurde.
     */
    @Test
    public void testDeleteAllFotosInAlbum() {
        System.out.println("deleteAllFotosInAlbum"); 
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        assertThat(testAlbum.getFotoListe(), notNullValue());
        
        FotoController.deleteAllFotosInAlbum(testAlbum);
        List<Foto> expResult = new LinkedList<>();
        List<Foto> result = AlbenController.getAlbum(title).getFotoListe();
        assertEquals(expResult, result);
    }
    
}
