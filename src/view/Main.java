
package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    
    private Stage primaryStage;
    
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
            
            GuiController guiController = loader.getController();       //Verbindung zum Controller
            guiController.setMain(this);                                // weil in Main
            
            Scene scene = new Scene(AnchorPaneGuiMain);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
