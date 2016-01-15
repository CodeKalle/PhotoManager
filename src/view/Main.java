package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Diese Klasse dient als Startpunkt der GUI
 * 
 * @author Tobias
 * 
 * Version-History:
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class Main extends Application {
    
    /**
    * KLASSENVARIABLEN
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    private static Stage primaryStage;
    protected static String speicher;
    protected static String letztesFensterVorCreateAlbum;
    protected static String letztesFenster;
    protected static int id;
    
    /**
    * Methode startet die GUI
    * 
    * @param primaryStage Startstage
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;    
        mainWindow();
    }
    
    /**
    * Methode setzt die Fenster
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    public void mainWindow(){
        
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("GuiMain.fxml"));
            AnchorPane AnchorPaneGuiMain = loader.load();
            
            primaryStage.setMinHeight(600.00);
            primaryStage.setMinWidth(800.00);
            primaryStage.setResizable(false); /* Codecracker Jule war hier!! Biatches! */
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../src/icon.png")));
            
            //Verbindung zum Controller
            GuiMainController guiMainController = loader.getController();
            
            Scene scene = new Scene(AnchorPaneGuiMain);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    } 

    /**
     * GETTER Holt Primäres Stage des Fensters
     * 
     * @return Primäres Stage
     * 
     * Version-History:
     * @date ??.11.2015 by Tobias: Initialisierung
     * @date 10.12.2015 by Danilo: Kommentare ergänzt
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
