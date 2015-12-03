/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import model.Album;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Der AlbenControllerTest testet alle Methoden der Klasse AlbenController.
 * 
 * @author Daniel
 * 
 * @date 01.12.2015 by Daniel: Ignores für nicht-bearbeitete Tests gesetzt.
 * @date 02.12.2015 by Daniel: tearDown löscht container
 * @date 03.12.2015 by Daniel: editAlbum, createNewAlbumIsExpectedAlbum, deleteAlbum, deleteListOfAlbums, createNewAlbum hinzugefügt
 */
public class AlbenControllerTest {
    
    private String title = "titel";
    private String newTitle = "neuer titel";
    private String beschreibung = "beschr";
    private String newBeschreibung = "neue beschr";
    private String sortierkennzeichen = "kennz";
    private String newSortierkennzeichen = "neues kennz";
    
    public AlbenControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        SystemController.getAlbumContainer().getAlbenListe().clear();
        SystemController.getFotoContainer().getFotoMap().clear();
    }

    /**
     * Gibt alle Attribute eines Albums aus.
     * 
     * @param result auszugebendes Album
     */
    private void printResult(Album result) {
        System.out.println(result);
        System.out.println("Titel: " + result.getTitel());
        System.out.println("Beschreibung: " + result.getBeschreibung());
        System.out.println("Sortierkennzeichen: " + result.getSortierkennzeichen());
        System.out.println("Erstellungdatum: " + result.getErstellungdatum());
        System.out.println("FotoListe: " + result.getFotoListe());
    }

    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob ein Album angelegt wird.
     */
    @Test
    public void testCreateNewAlbum() {
        System.out.println("createNewAlbum");
        
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (tmpAlbum.getTitel().equals(title)) {
                fail("Album existiert bereits");
            }
        }
        Album result = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            if (!tmpAlbum.getTitel().equals(result.getTitel()))
                fail("Album wurde nicht angelegt");
            assertEquals(title, result.getTitel());
        } 
    }
        
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob die Attribute des angelgten Albums mit einem, durch direkten Aufruf erstellten Album übereinstimmen.
     */
    @Test
    public void testCreateNewAlbumIsExpectedAlbum() {
        System.out.println("createNewAlbumIsExpectedAlbum");
        
        Album expResult = new Album(title);
        expResult.setBeschreibung(beschreibung);
        expResult.setSortierkennzeichen(sortierkennzeichen);
        Album result = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        
        assertEquals(expResult.getTitel(), result.getTitel());
        assertEquals(expResult.getBeschreibung(), result.getBeschreibung());
        assertEquals(expResult.getSortierkennzeichen(), result.getSortierkennzeichen());
        assertEquals(expResult.getErstellungdatum(), result.getErstellungdatum());
        assertEquals(expResult.getFotoListe(), result.getFotoListe());
    }

    /**
     * Testet die Methode testEditAlbum der Klasse AlbenController.
     * Testet, ob das editierte Album die neuen Attribute übernommen hab.
     */
    @Test
    public void testEditAlbum() {
        System.out.println("editAlbum");
        Album preAlbum = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        assertEquals(preAlbum.getTitel(), preAlbum.getTitel());
        
        Album expResult = new Album(newTitle);
        expResult.setBeschreibung(newBeschreibung);
        expResult.setSortierkennzeichen(newSortierkennzeichen);
        Album result = AlbenController.editAlbum(title, newTitle, newBeschreibung, newSortierkennzeichen);
        assertEquals(expResult.getTitel(), result.getTitel());
        assertEquals(expResult.getBeschreibung(), result.getBeschreibung());
        assertEquals(expResult.getSortierkennzeichen(), result.getSortierkennzeichen());
        assertEquals(expResult.getErstellungdatum(), result.getErstellungdatum());
        assertEquals(expResult.getFotoListe(), result.getFotoListe());
    }

    /**
     * Testet die Methode testGetAlbum der Klasse AlbenController.
     * Testet, ob das übergebene Album mit dem erstellten Album übereinstimt.
     */
    @Test
    public void testGetAlbum() {
        System.out.println("getAlbum");
        Album expResult = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        assertEquals(expResult.getTitel(), expResult.getTitel());
        
        Album result = AlbenController.getAlbum(title);
        assertEquals(expResult.getTitel(), result.getTitel());
        assertEquals(expResult.getBeschreibung(), result.getBeschreibung());
        assertEquals(expResult.getSortierkennzeichen(), result.getSortierkennzeichen());
        assertEquals(expResult.getErstellungdatum(), result.getErstellungdatum());
        assertEquals(expResult.getFotoListe(), result.getFotoListe());
    }

    /**
     * Testet die Methode testDeleteListOfAlbum der Klasse AlbenController.
     * Testet, ob das Album gelöscht wurde.
     */
    @Test
    public void testDeleteAnAlbum() {
        System.out.println("deleteAlbum");
        Album tmpAlbum = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        assertEquals(tmpAlbum.getTitel(), tmpAlbum.getTitel());
        
        List<String> titlelist = new LinkedList<>();
        titlelist.add(title);
        assertThat(titlelist, hasItem(title));
        
        AlbenController.deleteListOfAlbum(titlelist);
        Album expResult = null;
        Album result = AlbenController.getAlbum(title);
        assertEquals(expResult, result);
    }
    
    /**
     * Testet die Methode testDeleteListOfAlbum der Klasse AlbenController.
     * Testet, ob alle Alben gelöscht wurden.
     */
    @Test
    public void testDeleteListOfAlbum() {
        System.out.println("deleteListOfAlbums");
        Album tmpAlbum1 = AlbenController.createNewAlbum(title + 1, beschreibung, sortierkennzeichen);
        assertEquals(tmpAlbum1.getTitel(), tmpAlbum1.getTitel());
        Album tmpAlbum2 = AlbenController.createNewAlbum(title + 2, beschreibung, sortierkennzeichen);
        assertEquals(tmpAlbum2.getTitel(), tmpAlbum2.getTitel());
        Album tmpAlbum3 = AlbenController.createNewAlbum(title + 3, beschreibung, sortierkennzeichen);
        assertEquals(tmpAlbum3.getTitel(), tmpAlbum3.getTitel());
        
        List<String> titlelist = new LinkedList<>();
        titlelist.add(title + 1);
        titlelist.add(title + 2);
        titlelist.add(title + 3);
        assertThat(titlelist, hasItems(title + 1, title + 2, title + 3));
        
        AlbenController.deleteListOfAlbum(titlelist);
        Album expResult = null;
        Album result = AlbenController.getAlbum(title);
        assertEquals(expResult, result);
    }
    
}
