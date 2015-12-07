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
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 * TestDataController erstellt Testdatenumgebung bietet Möglichkeiten zur Testdatenverwaltung
 * Version-History:
 * @date 03.12.2015 by Benni: Erstellung
 */
public class TestDataController {
    //Testdaten SystemController
    private static final String filename = "pm.jdb";
    private static final String bakfilename = "pm.jdb.bak";
    private static Path sourceDB;
    private static Path backupDB;
    //Testdaten FotoController
    private static Path testPicturePath;
    private static String fotoFilter = "*.{jpg,jpeg}";
    private static DirectoryStream<Path> testPictureDirectory;
    private static List<String> fotoList;
    //Testdaten AlbumController
    private static int albumCount = 10; //Standardwert hier hinterlegen, wird verwendet wenn Konstruktor nur mit Path aufgerufen werden
    
    
    public List<String> getfotoList(){
        return TestDataController.fotoList;
    }
    
    //@toDo 06.12.2015 by Benni - Prio: low: 
    //Standardtestbilderpfad hinterlegen, damit Kontruktor ohne Parameter aufgerufen werden kann
    public TestDataController(){ }
    
    /**
    * Konstruktor mit einem Parametern
    * @param testPicturePath Pfad für Testbilder
    * @param databasePath Pfad der Datenbank
    * @throws java.io.IOException
    * @date 03.12.2015 by Benni: Initialisierung
    */
    public TestDataController(String testPicturePath, String databasePath) throws IOException{
            setDatabasePaths(databasePath);
            generateTestData(testPicturePath);
    }
    
    /**
    * Konstruktor mit zwei Parametern
    * @param path Pfad für Testbilder
    * @param databasePath Pfad der Datenbank
    * @param count Anzahl anzulegener Alben
     * @throws java.io.IOException
    * @date 03.12.2015 by Benni: Initialisierung
    */
    public TestDataController (String path, String databasePath, int count)throws IOException{
            albumCount = count;
            setDatabasePaths(databasePath);
            generateTestData(path);
    }
    
    /**
    * Setzen der Datenbankpfade
    * @param databasePath = Pfad der Datenbank
    */
    private static void setDatabasePaths(String databasePath){
        if (databasePath.isEmpty() || databasePath.equals("")){
            sourceDB = Paths.get(filename);
            backupDB = Paths.get(bakfilename);  
        }else{
            sourceDB = Paths.get(databasePath);
            backupDB = Paths.get(databasePath + ".bak");
        }
    }

    /**
    * Diese Methode generiert die Testdaten und wird vor den Tests ausgeführt
    * @param path Pfad für Testbilder
    * @param count Anzahl anzulegener Alben
    * @date 03.12.2015 by Benni: Initialisierung
    */   
    private static void generateTestData(String path) throws IOException
    {     
        //Lösche vorhandene Daten
        for(String tmpAlbumTitle : AlbenController.getAlbumList()){
            FotoController.deleteAllFotosInAlbum(AlbenController.getAlbum(tmpAlbumTitle));
        }
        AlbenController.deleteListOfAlbum(AlbenController.getAlbumList());
        //Generierung Testdaten
        generateTestFotoContainer(path);
        generateTestAlbenContainer(); 
    }
    
    /**
     * Generiert einen neuen AlbenContainer mit Testdaten
     */
    private static void generateTestAlbenContainer() throws IOException{
        int errorcode  = 0;
        
        //Generierung TestAlbennamen
        for (int i=0; i<=albumCount; i++)
        {
            String[] tmpAlbum = generateAlbumTestDaten();
                
            if( AlbenController.getAlbum(tmpAlbum[0]) == null){
                errorcode = AlbenController.createNewAlbum(tmpAlbum[0],tmpAlbum[1], tmpAlbum[2]);
                TestDocumizer.logging(errorcode, "Albumanlage: " + tmpAlbum[0] + " erfolgreich", false, true);
            }else{
                TestDocumizer.logging(errorcode, "Albumanlage: " + tmpAlbum[0] + " schon vorhanden, wurde nicht angelegt", false, true);
            }
        }
    }
    
    /**
     * Geniert einen neuen FotoContainer für den Testlauf
     * @param path
     * @throws IOException 
     */
    private static void generateTestFotoContainer(String path) throws IOException{
        //Einlesen Testbilder aus Testverzeichnis  
        testPicturePath = Paths.get(path);
        testPictureDirectory = Files.newDirectoryStream(testPicturePath, fotoFilter);
        for (Path cur : testPictureDirectory)
        {
            fotoList.add(cur.getFileName().toAbsolutePath().toString());
        }
    }
    
    /**
     * 
     * @return 
     */
    public static String[] generateAlbumTestDaten(){
        String[] albumInfos = new String[3];
        String randomTitle;
        String randomDescription;
        String randomClassIndicator;
        
        do{
            randomTitle = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(17) + 4);
        } while (randomTitle.equals(randomTitle));
        do{
            randomDescription = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(201));
        } while (randomDescription.equals(randomDescription));
        do{
            randomClassIndicator = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(21));
        } while (randomClassIndicator.equals(randomClassIndicator));
        
        albumInfos[0] = randomTitle;
        albumInfos[1] = randomDescription;
        albumInfos[2] = randomClassIndicator;
        
        return albumInfos;
    }
    
    /**
     * Methode zum Sichern und Wiederherstellen der Datenbanken
     * @param backupMode True = Datenbank sichern, False = Datenbank wiederherstellen
     * @throws java.io.IOException
    */
    public void backupRestoreDatabase(boolean backupMode) throws IOException{       
        if(backupMode == true){
            if (Files.exists(sourceDB))
            {
                if(Files.isReadable(sourceDB) && Files.notExists(backupDB) || !Files.isSameFile(sourceDB, backupDB))
                {
                    Files.copy(sourceDB, backupDB, StandardCopyOption.REPLACE_EXISTING);
                }   
            }
        }else{
            if (Files.exists(sourceDB)&& Files.exists(backupDB))
            {
                if(!Files.isSameFile(sourceDB, backupDB) && Files.isWritable(sourceDB))
                {
                    Files.copy(backupDB, sourceDB, StandardCopyOption.REPLACE_EXISTING);
                }   
            }
        }
    }
}
