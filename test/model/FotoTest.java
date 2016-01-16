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
import static org.junit.Assert.assertEquals;
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
        try{
             testData = new TestDataController();
            int i=0;
            
            fotolist = new ArrayList<>();
            for (String cur : testData.getFotoList())
            {
                Foto tmpFoto = new Foto("Bild" + String.valueOf(i),cur);
                fotolist.add(tmpFoto);
                i++;
            }
            
            TestDocumizer.logging(0, "start jUnit Tests: Model - Foto", true, true);
            TestDocumizer.startTimer();
        }catch (Exception e){
            System.out.println("jUnit Test Foto - Fehler bei Setup: " + e.getMessage());
        }  
    }
    
    @AfterClass
    public static void tearDownClass() {
        try{
            TestDocumizer.logging(0, "beenden jUnit Tests: Model - Foto: Dauer: " + String.valueOf(TestDocumizer.stopTimer()), true, true);
            
        }catch (Exception e){
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
    public void testName(){
        int i = 0;
        for(Foto testBild : fotolist){
            testBild = fotolist.get(i);
            assertEquals(testBild.getName(),"Bild" + String.valueOf(i));
            i++;
        }
    }
    
    @Test
      public void testPfad(){
        int i = 0;
        for(Foto testBild : fotolist){
            testBild = fotolist.get(i);
            assertEquals(testBild.getPfad().toString(),testData.getFotoList().get(i));
            i++;
        } 
    }
    
    @Test
    public void testMetadaten(){
        int i = 0;
        for(Foto testBild : fotolist){
            testBild = fotolist.get(i);
            
            assertEquals(testBild.getPfad().toString(),testData.getFotoList().get(i));
            i++;
        } 
    }
    
    @Test
    public void testCounter(){
        
    }
    
    @Test
    public void testErstellungsdatum(){
        
    }
    
    @Test
    public void testEquals(){
        
    }
    
    @Test
    public void testHashCode(){
        
    }
    
    @Test
    public void testGenerateFotosize(){
        
    }

}
