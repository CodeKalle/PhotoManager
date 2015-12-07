/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.Charset;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 *
 * @author Benni
 */

public class TestDocumizer {
    //Einstellung des Timer 
    private static long timeUsing;
    private static long timeGeneral;
    //Einstellung des Logging
    private static final String strLogPath = "TestLog.txt";
    private static final Charset charset = Charset.forName("UTF-8");
    
    public static void startTimer(){
        timeUsing = System.nanoTime();
    }
    
    public static long stopTimer(){
        long time = System.nanoTime();
        timeGeneral += (time - timeUsing);
        return timeGeneral;
    }
    
    public static void logging(int errorcode, String advancedDescription, boolean consoleLog, boolean fileLog) throws IOException{
        String logString = "";
        if (errorcode <= 0){
            logString = advancedDescription + "\n";
        }else{
            logString = "errorcode: " + String.valueOf(errorcode) +  " ";
            String[] tmpErrorMessage;
            tmpErrorMessage = ErrorController.changeErrorCode(errorcode);
            for (String tmp : tmpErrorMessage){
                logString = logString + tmp + " | ";
            }
            logString = logString + advancedDescription + "\n";
        }
        
        if(consoleLog == true){
            consoleLogging(logString);
        }
        
        if(fileLog == true){
            fileLogging(logString);
        }
    }
    
    private static void consoleLogging(String logText){
        System.out.println(logText);
    }
    
    private static void fileLogging(String logText) throws IOException{
        Path logPath = Paths.get(strLogPath);
        
        BufferedWriter writeBuf = Files.newBufferedWriter(logPath, charset, CREATE, APPEND);
        writeBuf.write(logText);
    }
}
