
package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
    
    private static Stage primaryStage;
    
    protected static String speicher;
    protected static String letztesFensterVorCreateAlbum;
    protected static String letztesFenster;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;    
        mainWindow();
    }
    
    public void mainWindow(){
        
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("GuiMain.fxml"));
            AnchorPane AnchorPaneGuiMain = loader.load();
            
            primaryStage.setMinHeight(600.00);
            primaryStage.setMinWidth(800.00);
            
            GuiMainController guiMainController = loader.getController();               //Verbindung zum Controller
            
            Scene scene = new Scene(AnchorPaneGuiMain);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    } 

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
}
