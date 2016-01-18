/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.TestDataController;
import controller.TestDocumizer;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Benni
 */
public class FotoTest {

    private static String testName;
    private static String testPfad;
    private static int testGroesse;                        //in Byte
    private static Metadaten testMetadata;
    private static int testCounter;
    private static long testErstellungdatum;
    private static List<Foto> fotolist;
    private static TestDataController testData;

    public FotoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            testData = new TestDataController();
            int i = 0;

            fotolist = new ArrayList<>();
            for (String cur : testData.getFotoList()) {
                Foto tmpFoto = new Foto("Bild" + String.valueOf(i), cur);
                fotolist.add(tmpFoto);
                i++;
            }

            TestDocumizer.logging(0, "start jUnit Tests: Model - Foto", true, true);
            TestDocumizer.startTimer();
        } catch (Exception e) {
            System.out.println("jUnit Test Foto - Fehler bei Setup: " + e.getMessage());
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            TestDocumizer.logging(0, "beenden jUnit Tests: Model - Foto: Dauer: " + String.valueOf(TestDocumizer.stopTimer()), true, true);

        } catch (Exception e) {
            System.out.println("jUnit Test Foto - Fehler bei tearDown: " + e.getMessage());
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testName() {
        int i = 0;
        //Test setter und getter
        for (Foto testBild : fotolist) {
            testBild = fotolist.get(i);
            assertThat(testBild, is(notNullValue()));
            assertEquals(testBild.getName(), "Bild" + String.valueOf(i));
            i++;
        }
        
        //Test Zeichenlänge
        for (int j =0; j<60; j++){ //Integer.MAX_VALUE
            String testName = TestDataController.generateTestString(j);
            Foto testBild  = new Foto(testName, testData.getFotoList().get(0));
            assertThat(testBild, is(notNullValue()));
            assertEquals(testBild.getName(),testName);
        }
    }

    @Test
    public void testPfad() {
        int i = 0;
        try{
            for (Foto testBild : fotolist) {
                testBild = fotolist.get(i);
                assertThat(testBild, is(notNullValue()));
                assertEquals(testBild.getPfad().toString(), testData.getFotoList().get(i));
                i++;
            }

            //Test nicht vorhandenen Pfad
            String testPfad = TestDataController.generateTestPath("",20,3,0);
            TestDocumizer.logging(0, "Pfad: " + testPfad + " | Zeichen: " + String.valueOf(testPfad.length()), true, false);
            
            for (int j=0; j<10; j++){
               TestDocumizer.logging(0, "Pfad: " + TestDataController.generateTestPath(testPfad,0,0,5), true, false);
            }
            
            //testPfad = TestDataController.generateTestPath(0,0,8);
            //TestDocumizer.logging(0, "Pfad: " + testPfad + " | Zeichen: " + String.valueOf(testPfad.length()), true, false);
            
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        
        //Foto testBild = new Foto("TestName",)
        
        //Test Pfad >256 Zeichen
        //D:\05_Development\GIT\workspace\PhotoManager\test\testdaten\sdhabmhabfhdsabfkjhadbsfsfsdhbfjshdbfjsfjsfbsjfhdbüäiowqpejqwijeoqwiejoqwewiejoqiwejoijeqwpoejpoqwejpqwejpqowjep3popmdpjwsdipeh32jpajfdpiujepqwoejwsaejwpeoije\fdhjkldfhljkefnjenrfkjfdsfsj\Desert.jpg
        
    }

    @Test
    public void testMetadaten() {
        int i = 0;
        for (Foto testBild : fotolist) {
            testBild = fotolist.get(i);

            assertEquals(testBild.getPfad().toString(), testData.getFotoList().get(i));
            i++;
        }
    }

    @Test
    public void testCounter() {

    }

    @Test
    public void testErstellungsdatum() {

    }

    @Test
    public void testEquals() {

    }

    @Test
    public void testHashCode() {

    }

    @Test
    public void testGenerateFotosize() {

    }

}
