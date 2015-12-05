/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import controller.SystemController;
import java.nio.file.*;
import java.io.IOException;

/**
 * @author Benni
 * SystemController Testerklasse
 * Version-History:
 * @date 03.12.2015 by Benni: Grundgerüst angefangen
 */
public class SystemControllerTest {
    private static final String filename = "pm.jdb";
    private static final String bakfilename = "pm.jdb.bak";
    
    public SystemControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass()throws IOException {
        Path database = Paths.get(filename);
        Path databasecopy = Paths.get(bakfilename);
        
        if (Files.exists(database)&& Files.exists(databasecopy))
        {
            if(!Files.isSameFile(database, databasecopy)){
                Files.copy(databasecopy, database, StandardCopyOption.REPLACE_EXISTING);
            }   
        }
    }
    
    @Before
    public static void setUp() throws IOException {
        // Sichern der "Datenbank"
        Path database = Paths.get(filename);
        Path databasecopy = Paths.get(bakfilename);
        
        if (Files.exists(database))
        {
            if(Files.isReadable(database) && Files.notExists(databasecopy) || !Files.isSameFile(database, databasecopy)){
                Files.copy(database, databasecopy, StandardCopyOption.REPLACE_EXISTING);
            }   
        }
    }
    
    @After
    public void tearDown() throws IOException{

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
