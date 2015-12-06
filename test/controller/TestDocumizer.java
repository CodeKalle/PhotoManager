/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Benni
 */
public class TestDocumizer {
    private static long timeUsing;
    private static long timeGeneral;
    private static final String LogPath = "/test/TestLog.txt";
    
    public static void startTimer(){
        timeUsing = System.nanoTime();
    }
    
    public static void stopTimer(){
        long time = System.nanoTime();
        timeGeneral += (time - timeUsing);
        System.out.println("Testzeit der Methode: " + ((time - timeUsing)/1000000) + " ms [" + ((time - timeUsing)/1000) + " us]\n");
    }
    
    public static void logging(int errorcode, String advancedDescription, boolean consoleLog, boolean fileLog){
        String logString = "";
        if (errorcode !=0){
            
        }else{
            
        }
        
        if(consoleLog == true){
            consoleLogging(logString);
        }
        
        if(fileLog == true){
            fileLogging(logString);
        }
    }
    
    private static void consoleLogging(String logText){
        
    }
    
    private static void fileLogging(String logText){
        
    }
}
