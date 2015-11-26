/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
     * Test of createNewAlbum method, of class AlbenController.
     */
    @Test
    public void testCreateNewAlbum() {
        System.out.println("createNewAlbum");
        String title = "title";
        String beschreibung = "beschr";
        String sortierkennzeichen = "kennz";
        
        Album expResult = new Album(title);
        expResult.setBeschreibung(beschreibung);
        expResult.setSortierkennzeichen(sortierkennzeichen);
        System.out.println(expResult);
        System.out.println(expResult.getTitel());
        System.out.println(expResult.getBeschreibung());
        System.out.println(expResult.getSortierkennzeichen());
        System.out.println(expResult.getErstellungdatum());
        System.out.println(expResult.getFotoListe());
        
        Album result = AlbenController.createNewAlbum(title, beschreibung, sortierkennzeichen);
        System.out.println(result);
        System.out.println(result.getTitel());
        System.out.println(result.getBeschreibung());
        System.out.println(result.getSortierkennzeichen());
        System.out.println(result.getErstellungdatum());
        System.out.println(result.getFotoListe());
        
        assertEquals(expResult, result);
        //assertThat(expResult, is(result));
        //fail("testCreateNewAlbum() failed.");
    }

    /**
     * Test of editAlbum method, of class AlbenController.
     */
    @Test
    public void testEditAlbum() {
        System.out.println("editAlbum");
        String title = "";
        String newTitle = "";
        String beschreibung = "";
        String sortierkennzeichen = "";
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
    public void testDeleteAlbum() {
        System.out.println("deleteAlbum");
        String title = "";
        int expResult = 0;
        int result = AlbenController.deleteAlbum(title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
