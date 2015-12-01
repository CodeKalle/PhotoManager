/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import model.Album;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
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
public class AlbenControllerTest {
    
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
     * Leert den Albencontainer.
     */
    private void cleanAlbumContainer() {
        for (Album tmpAlbum : SystemController.getAlbumContainer().getAlbenListe()) {
            SystemController.getAlbumContainer().getAlbenListe().remove(tmpAlbum);
        }
    }
    
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Ein Album wird angelegt, falls es noch nicht im AlbenContainer existiert.
     */
    @Test
    public void testCreateNewAlbum() {
        System.out.println("createNewAlbum");
        String title = "title";
        String beschreibung = "beschr";
        String sortierkennzeichen = "kennz";
        
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
        cleanAlbumContainer();
    }
        
    /**
     * Testet die Methode createNewAlbum der Klasse AlbenController.
     * Testet, ob die Attribute des angelgten Albums mit einem, durch direkten Aufruf erstellten Album übereinstimmen.
     */
    @Test
    public void testCreateNewAlbumIsExpectedAlbum() {
        System.out.println("createNewAlbumIsExpectedAlbum");
        String title = "title";
        String beschreibung = "beschr";
        String sortierkennzeichen = "kennz";
        
        Album expResult = new Album(title);
        expResult.setBeschreibung(beschreibung);
        expResult.setSortierkennzeichen(sortierkennzeichen);
        Album result = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        
        assertEquals(expResult.getTitel(), result.getTitel());
        assertEquals(expResult.getBeschreibung(), result.getBeschreibung());
        assertEquals(expResult.getSortierkennzeichen(), result.getSortierkennzeichen());
        assertEquals(expResult.getErstellungdatum(), result.getErstellungdatum());
        assertEquals(expResult.getFotoListe(), result.getFotoListe());
        cleanAlbumContainer();
    }

    /**
     * Test of editAlbum method, of class AlbenController.
     */
    @Test
    public void testEditAlbum() {
        System.out.println("editAlbum");
        String title = "alter title";
        String newTitle = "neuer titel";
        String beschreibung = "beschr";
        String sortierkennzeichen = "kennz";
        cleanAlbumContainer();
        
        Album expResult = null;
        Album result = AlbenController.editAlbum(title, newTitle, beschreibung, sortierkennzeichen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAlbum method, of class AlbenController.
     */
    @Test
    public void testGetAlbum() {
        System.out.println("getAlbum");
        String title = "";
        Album expResult = null;
        Album result = AlbenController.getAlbum(title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAlbum method, of class AlbenController.
     */
    @Test
    public void testDeleteListOfAlbum() {
        System.out.println("deleteAlbum");
        String title = "";
        List<String> titlelist = new LinkedList<>();
        int expResult = 0;
        int result = AlbenController.deleteListOfAlbum(titlelist);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
