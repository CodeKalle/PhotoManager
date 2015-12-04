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
import java.util.ArrayList;

/**
 * @author Benni
 * TestRunner wird verwendet für die Integrations der einzelnen UseCases,
 * bzw. der
 * 
 * Version-History:
 * @date 03.12.2015 by Benni: Erstellung
 */
public class TestDataController {

    private Path testPicturePath;
    private DirectoryStream<Path> testPictureDirectory;
    private ArrayList<String> fotoList;
    private ArrayList<String> albumList;
    private int albumCount = 10; //Standardwert hier hinterlegen, wird verwendet wenn Konstruktor nur mit Path aufgerufen werden
    
    public ArrayList<String> getfotoList(){
        return this.fotoList;
    }
    
    public ArrayList<String> getAlbumList(){
        return this.albumList;
    }
    
    public TestDataController(){ }
    
    /*
    * Konstruktor mit einem Parametern
    * @param path Pfad für Testbilder
    * @date 03.12.2015 by Benni: Initialisierung
    */
    public TestDataController(String path) throws IOException{
        try{
            generateTestData(path);
        }catch(Exception e){ }
    }
    
    /*
    * Konstruktor mit zwei Parametern
    * @param path Pfad für Testbilder
    * @param count Anzahl anzulegener Alben
    * @date 03.12.2015 by Benni: Initialisierung
    */
    public TestDataController (String path, int count){
        try{
            albumCount = count;
            generateTestData(path);
        }catch(Exception e){ }
    }
    
    /*
    * Diese Methode generiert die Testdaten und wird vor den Tests ausgeführt
    * @param path Pfad für Testbilder
    * @param count Anzahl anzulegener Alben
    * @date 03.12.2015 by Benni: Initialisierung
    */
    private void generateTestData(String path) throws IOException
    {      
        //Generierung Testdaten
        //Einlesen Testbilder aus Testverzeichnis  
        testPicturePath = Paths.get(path);
        testPictureDirectory = Files.newDirectoryStream(testPicturePath, "*.{jpg,jpeg}");
        for (Path cur : testPictureDirectory)
        {
            fotoList.add(cur.getFileName().toAbsolutePath().toString());
        }
       
        //Generierung TestAlbennamen
        for (int i=0; i<=albumCount; i++)
        {
            albumList.add("Album" + String.valueOf(i));
        }
    }
}
