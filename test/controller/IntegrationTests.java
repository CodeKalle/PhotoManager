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
import org.junit.Ignore;

/**
 * @author Benni
 IntegrationTests wird verwendet für die Integrations der einzelnen UseCases,
 bzw. der im Pflichtenheft beschriebenen Produktfunktion
 Version-History:
 * @date 03.12.2015 by Benni: Grundgerüst angefangen
 */
public class IntegrationTests {
    private TestDataController TestDataCtrl;
    private Path PmDatabasePath;
    private final String testBilderPfad = "Pfad";

    /**
     *
     */
    public IntegrationTests() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() throws IOException{
        //Generiung Testdaten
        TestDataCtrl = new TestDataController(testBilderPfad,"");

        //Initialisierung System
        SystemController.run();
        
        //Sicherung der aktueller "Datenbank"
        TestDataCtrl.backupRestoreDatabase(true);        
    }
    
    @After
    public void tearDown() throws IOException{
        //Wiederherstellung alter Daten vor Test
        TestDataCtrl.backupRestoreDatabase(false);
        
    }
    
    /*
    * 
    */
    @Test
    public void startTests()
    {
        
    }
    
    /*
     * Testet den UseCase Album anlegen und danach editieren
     * 01. Startbildschirm: laden und Auswahl "Alben anlegen und pflegen"
     * 02. Albenmaske: Auswahl "neues Album"
     * 03. Neues Album Maske: Ausfüllen der Felder "Albumnamen", "Beschreibung" (optional) Sortierkennzeichen wählen und hinzufügen
     * 04. Bestätigen oder Abbrechen
     * 05. Bei Bestätigung --> Meldung "Album angelegt"
     * 06. Albenmaske: Auswahl "neu erstelltes Album"
     * 07. Albuminformationen anzeigen --> Abgleich Ergebnisse
     * 08. Albuminformationen bearbeiten
     * 09. Albenmaske: Auswahl "neu erstelletes Album" --> "bearbeitete Album"?!
     * 10. Albuminformationen anzeigen --> Abgleich Ergebnisse
    */
    @Test
    @Ignore
    private void createAndEditAlbum()
    {
        
    }
    
    /*
    Testet den UseCase Foto(s) hinzufügen und danach editieren
    */
    @Test
    @Ignore
    private void addAndEditFoto()
    {
        
    }
    
    /*
    Testet den UseCase Bildschirmpräsentation bearbeiten und starten
    */
    @Test
    @Ignore
    private void startAndEditPresentation()
    {
        
    }
}
