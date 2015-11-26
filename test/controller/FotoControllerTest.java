/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.nio.file.Path;
import java.util.List;
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getFotosFromAlbum method, of class FotoController.
     */
    @Test
    public void testGetFotosFromAlbum() {
        System.out.println("getFotosFromAlbum");
        String title = "";
        List expResult = null;
        List result = FotoController.getFotosFromAlbum(title);
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
        String title = "";
        List<Path> listOfPathes = null;
        int expResult = 0;
        int result = FotoController.addListOfFotosToAlbum(title, listOfPathes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
