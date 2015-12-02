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
 * @date 02.12.2015 by Daniel: testdaten, testGetFotosFromAlbum hinzugefügt; tearDown löscht container, metadaten funktioniert nicht (Kurztitel=null)
 */
public class FotoControllerTest {
    
    private Album testAlbum;
    private Foto testFoto;
    private Path pathOfFoto;
    private Metadaten meta;
    private List<Path> listOfPathes;
    private String title = "title";
    private String beschreibung = "beschr";
    private String sortierkennzeichen = "kennz";
    private String fototitel = "Fototitel";
    private String kurztitel = "Kurztitel";
        
    public FotoControllerTest() {
        testAlbum = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        pathOfFoto = Paths.get("test/testdaten/Testbild43.jpeg");
        testFoto = new Foto(fototitel, pathOfFoto);
        listOfPathes = new LinkedList<>();
        listOfPathes.add(pathOfFoto);
        meta = new Metadaten();
        meta.setzeWert(kurztitel, kurztitel);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        SystemController.getAlbumContainer().getAlbenListe().clear();
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
        System.out.println("Metadata: " + result.getMetadata().getDaten());
        System.out.println("Counter: " + result.getCounter());
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
     * Testet die Methode testSetMetaInFoto der Klasse FotoController.
     * Testet, ob die gesetzten Metadaten des Fotos mit der lokal erstellten Metadaten übereinstimmen.
     */
    @Test
    @Ignore
    public void testSetMetaInFoto() {
        System.out.println("setMetaInFoto");
        System.out.println(pathOfFoto + " : " + meta.getDaten());
        
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
     */
    @Test
    @Ignore
    public void testGetMetaFromFoto() {
        System.out.println("getMetaFromFoto");
        Metadaten expResult = meta;
        Metadaten result = FotoController.getMetaFromFoto(pathOfFoto);
        assertEquals(expResult, result);
    }

    /**
     * Testet die Methode testGetFotosFromAlbum der Klasse FotoController.
     * Testet, ob die Fotoliste des Albums mit der lokal erstellten Liste übereinstimmt.
     */
    @Test
    public void testGetFotosFromAlbum() {
        System.out.println("getFotosFromAlbum");
        FotoController.addListOfFotosToAlbum(title, listOfPathes);
        List expResult = listOfPathes;
        List result = FotoController.getFotosFromAlbum(title);
        assertEquals(expResult, result);
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
