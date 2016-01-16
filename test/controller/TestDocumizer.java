package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.Charset;

/**
 *
 * @author Benni
 * 
 * Version-History:
 * @date 11.01.2016 by Benni: Umbenennung von Variblen, kommentierten Code entfernt
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
    
    /**
     * Protokollierung
     * Version-History:
     * @param errorcode Übergabe des internen PM Fehlercodes - Auflösung des Fehlercodes folgt automatisch, 0 kein interner Fehlercode
     * @param advancedDescription Freier Beschreibungstext 
     * @param consoleLog Ausgabe des Logeintrags auf der Console
     * @param fileLog Logeintrag in der Testprotokolldatei erstellen
     * @date 03.12.2015 by Benni: Initialisiarung
     */
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
    
    /**
     * IntegrationTests wird verwendet für die Integrations der einzelnen UseCases,
     * bzw. der im Pflichtenheft beschriebenen Produktfunktion
     * Version-History:
     * @date 03.12.2015 by Benni: Initialisiarung
     * @date 08.12.2015 by Danilo:Hinzufügen eines Filewriters
     */
    private static void fileLogging(String logText) throws IOException{
        Path logPath = Paths.get(strLogPath);
        
        FileWriter fileWriter;
        BufferedWriter writeBuf;
        fileWriter = new FileWriter(logPath.toString());
        writeBuf = new BufferedWriter(fileWriter);
        writeBuf.write(logText);
        
        if (writeBuf != null) writeBuf.close();
        if (fileWriter != null) fileWriter.close();
    }
}
