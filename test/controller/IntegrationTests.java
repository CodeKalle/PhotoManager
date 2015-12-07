/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import model.Album;

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
    private final String testBilderPfad = "C:\\03_Development\\workspace\\GIT\\PhotoManager\\test\\testdaten";

    /**
     *
     */
    public IntegrationTests() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp(){
        try{
            //Start Timer der Initialisierung
            TestDocumizer.startTimer();
            
            //Initialisierung System
            SystemController.run();
            
            //Generiung Testdaten
            TestDataCtrl = new TestDataController(testBilderPfad,"");
        
            //Sicherung der aktueller "Datenbank"
            TestDataCtrl.backupRestoreDatabase(true);        
            
            //Beendigung Timmer
            TestDocumizer.logging(0, "IntegrationTest init benötigte: " + String.valueOf(TestDocumizer.stopTimer()) + " ns", true, false);
        }catch (IOException io){
            System.err.format("IOException: %s%n", io);
            System.out.println("Dateifehler bei Testdatengenerierung: " + io.getMessage());
        }catch (Exception e){
            System.err.format("Exception: %s%n", e);
            System.out.println("Allgemeiner Fehler bei Testdatengenerierung: " + e.getMessage());
        }
    }
    
    @After
    public void tearDown() throws IOException{
        //Wiederherstellung alter Daten nach Test
        TestDataCtrl.backupRestoreDatabase(false);
    }
    
    /**
     * Testet den UseCase Album anlegen und danach editieren
     * 
     * Version-History:
     * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
     */
    @Test
    public void createAndEditAlbum()
    {   
        int errorcode;
        String[] createAlbumInfo;
        Album createdAlbum;
        String[] modifyAlbumInfo;
        
        try{
            TestDocumizer.startTimer();
            //01. Startbildschirm: laden und Auswahl "Alben anlegen und pflegen"
        
            //02. Albenmaske: Auswahl "neues Album"
        
            //03. Neues Album Maske: Ausfüllen der Felder "Albumnamen", "Beschreibung" (optional) Sortierkennzeichen wählen und hinzufügen
            createAlbumInfo = TestDataController.generateAlbumTestDaten();
            //04. Bestätigen oder Abbrechen
            boolean guiResponse = true;
            if (guiResponse == true){
                errorcode = AlbenController.createNewAlbum(createAlbumInfo[0], createAlbumInfo[1], Integer.valueOf(createAlbumInfo[2]));
                if(errorcode != 0){
                    assertNull(AlbenController.getAlbum(createAlbumInfo[0]));
                    TestDocumizer.logging(errorcode, "Testmethode 'createAndEditAlbum': Albumanlage fehlgeschlagen", true, true);
                }else{
                    assertNotNull(AlbenController.getAlbum(createAlbumInfo[0]));
                    TestDocumizer.logging(errorcode, "Testmethode 'createAndEditAlbum': Albumanlage erfolgreich", true, true);
                }   
            }       
            //05. Bei Bestätigung --> Meldung "Album angelegt" bestüätigen
            
            //06. Albenmaske: Auswahl "neu erstelltes Album"
            
            //07. Albuminformationen anzeigen --> Abgleich Ergebnisse
            createdAlbum = AlbenController.getAlbum(createAlbumInfo[0]);
            
            //08. Albuminformationen bearbeiten
            modifyAlbumInfo = TestDataController.generateAlbumTestDaten();
            //08a Sortierkennzeichen
            AlbenController.getAlbum(createdAlbum.getTitel()).setSortierkennzeichen(Integer.valueOf(modifyAlbumInfo[2]));
            assertEquals(AlbenController.getAlbum(createdAlbum.getTitel()).getSortierkennzeichen(), modifyAlbumInfo[2]);
            if(createdAlbum.getSortierkennzeichen()==Integer.valueOf(modifyAlbumInfo[2])){
                TestDocumizer.logging(0, "Testmethode 'createAndEditAlbum': Sortierkennzeichen erfolgreich geändert", true, true);
            }else{
                TestDocumizer.logging(0, "Testmethode 'createAndEditAlbum': Sortierkennzeichen nicht geändert", true, true);
            }
            //08b Beschreibung
            AlbenController.getAlbum(createdAlbum.getTitel()).setBeschreibung(modifyAlbumInfo[1]);
            assertEquals(AlbenController.getAlbum(createdAlbum.getTitel()).getBeschreibung(), modifyAlbumInfo[1]);
            if(AlbenController.getAlbum(createdAlbum.getTitel()).getBeschreibung().equals(modifyAlbumInfo[1])){
                TestDocumizer.logging(0, "Testmethode 'createAndEditAlbum': Beschreibung erfolgreich geändert", true, true);
            }else{
                TestDocumizer.logging(0, "Testmethode 'createAndEditAlbum': Beschreibung nicht gändert", true, true);
            }
            //08b Beschreibung
            AlbenController.getAlbum(createdAlbum.getTitel()).setTitel(modifyAlbumInfo[0]);
            assertNotNull(AlbenController.getAlbum(modifyAlbumInfo[0]));
            if(AlbenController.getAlbum(modifyAlbumInfo[0]) != null){
                TestDocumizer.logging(0, "Testmethode 'createAndEditAlbum': Titel erfolgreich geändert", true, true);
            }else{
                TestDocumizer.logging(0, "Testmethode 'createAndEditAlbum': Titel nicht geändert", true, true);
            }
            //09. Albenmaske: Auswahl "neu erstelletes Album" --> "bearbeitete Album"?!
        
            //10. Albuminformationen anzeigen --> Abgleich Ergebnisse
            
            //Beendigung Timer für Testdurchlauf
            TestDocumizer.logging(0, "createAndEditAlbum benötigte: " + String.valueOf(TestDocumizer.stopTimer()) + " ns", true, false);
        }catch(IOException io){
            System.err.format("IOException: %s%n", io);
            System.out.println("Dateifehler bei Testmethode 'createAndEditAlbum': " + io.getMessage());
        }catch(Exception e){
            System.out.println("Allgemeiner Fehler bei Testmethode 'createAndEditAlbum: " + e.getMessage());
        }
    }
    
    /*
    Testet den UseCase Foto(s) hinzufügen und danach editieren
    */
    @Test
    @Ignore
    public void addAndEditFoto()
    {
        
    }
    
    /*
    Testet den UseCase Bildschirmpräsentation bearbeiten und starten
    */
    @Test
    @Ignore
    public void startAndEditPresentation()
    {
        
    }
}
