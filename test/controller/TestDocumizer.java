package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
    
    /**
     * IntegrationTests wird verwendet für die Integrations der einzelnen UseCases,
     * bzw. der im Pflichtenheft beschriebenen Produktfunktion
     * Version-History:
     * @date 03.12.2015 by Benni: Initialisiarung
     * @date 08.12.2015 by Danilo:Hinzufügen eines Filewriters
     */
    private static void fileLogging(String logText) throws IOException{
        Path logPath = Paths.get(strLogPath);
        
        FileWriter filwWriter;
        BufferedWriter writeBuf;
        filwWriter = new FileWriter(logPath.toString());
        writeBuf = new BufferedWriter(filwWriter);
        writeBuf.write(logText);
        
        if (writeBuf != null) writeBuf.close();
        if (filwWriter != null) writeBuf.close();
    }
}
