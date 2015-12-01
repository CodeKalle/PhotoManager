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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class FotoControllerTest {
    
    private Album testAlbum;
    private Foto testFoto;
    private Path pathOfFoto;
    private List<Path> listOfPathes;
    private List<Foto> listOfFotos;
    private String title = "title";
    private Metadaten meta;
    private String beschreibung = "beschr";
    private String sortierkennzeichen = "kennz";
    private List<String> titelListe;
        
    public FotoControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        pathOfFoto = Paths.get("testOrdner/testBild.jpg");
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
        /*title = "Testalbum";
        testAlbum = new Album(title);
        
        pathOfFoto = Paths.get("testOrdner/testBild.jpg");
        try {
            Files.createDirectories(pathOfFoto.getParent());
            Files.createFile(pathOfFoto);
        } catch (IOException ex) {
            System.out.println("testBild.jpg konnte nicht erstellt werden");
        }
        testFoto = new Foto("Fototitel", pathOfFoto);
        
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
        
        meta = new Metadaten();
        //meta.setDaten(Daten);*/
    }
    
    @After
    public void tearDown() {
        titelListe.add(title);
        AlbenController.deleteListOfAlbum(titelListe);
        /*testAlbum = null;
        try {
            Files.deleteIfExists(pathOfFoto);
        } catch (IOException ex) {
            System.out.println("testBild.jpg konnte nicht gelöscht werden");
        }
        meta = null;*/
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
    public void testSetMetaInFoto() {
        System.out.println("setMetaInFoto");
        Metadaten expResult = meta;
        
        FotoController.setMetaInFoto(pathOfFoto, meta);
        Metadaten result = FotoController.getMetaFromFoto(pathOfFoto);
        assertEquals(expResult, result);
        cleanFotoContainer();
    }

    /**
     * Test of getMetaFromFoto method, of class FotoController.
     */
    @Test
    public void testGetMetaFromFoto() {
        System.out.println("getMetaFromFoto");
        Metadaten expResult = meta;
        Metadaten result = FotoController.getMetaFromFoto(pathOfFoto);
        assertEquals(expResult, result);
        cleanFotoContainer();
    }

    /**
     * Test of getFotosFromAlbum method, of class FotoController.
     */
    @Test
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
     * Test of addListOfFotosToAlbum method, of class FotoController.
     */
    @Test
    public void testAddListOfFotosToAlbum() {
        System.out.println("addListOfFotosToAlbum");
        //String title = "Titel";
        //List<Path> listOfPathes = listOfPathes;
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        List expResult = listOfPathes;
        List result = getAlbum(title).getFotoListe();
        assertEquals(expResult, result);
        System.out.println(result);
        System.out.println(listOfPathes + " : " + pathOfFoto);
        System.out.println(getAlbum(title));
    }

    /**
     * Test of deleteAllFotosInAlbum method, of class FotoController.
     */
    @Test
    public void testDeleteAllFotosInAlbum() {
        System.out.println("deleteAllFotosInAlbum");
        Album album = null;
        int expResult = 0;
        int result = FotoController.deleteAllFotosInAlbum(album);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
