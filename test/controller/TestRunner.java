/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import controller.SystemController;

/**
 * @author Benni
 * TestRunner wird verwendet für die Integrations der einzelnen UseCases,
 * bzw. der im Pflichtenheft beschriebenen Produktfunktion
 * Version-History:
 * @date 03.12.2015 by Benni: Grundgerüst angefangen
 */
public class TestRunner {
    private TestDataController TestDataCtrl;
    private Path PmDatabasePath;

    /**
     *
     */
    public TestRunner() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException{
        //Sicherung der aktueller "Datenbank"
        SystemControllerTest.setUp();
        
        //Generiung Testdaten
        TestDataCtrl = new TestDataController("Pfad");
        
    }
    
    @After
    public void tearDown() throws IOException{
        //Wiederherstellung alter Daten vor Test
        SystemControllerTest.tearDownClass();
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void startTests()
    {
        
    }
    
    /*
    Testet den UseCase Album anlegen und danach editieren
    */
    private void createAndEditAlbum()
    {
        
    }
    
    /*
    Testet den UseCase Foto(s) hinzufügen und danach editieren
    */
    private void addAndEditFoto()
    {
        
    }
    
    /*
    Testet den UseCase Bildschirmpräsentation bearbeiten und starten
    */
    private void startAndEditPresentation()
    {
        
    }
}
