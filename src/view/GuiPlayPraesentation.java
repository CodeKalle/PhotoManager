package view;

import controller.FotoController;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Diese Klasse dient als Startbildschirm
 * 
 * 
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische Umsetzung
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class GuiPlayPraesentation implements Initializable{

    @FXML
    private ImageView guiPlayPraesentationImageView;
    @FXML
    private Button guiPlayPraesentationBack;

    Thread praesi;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Titel setzten
        Main.getPrimaryStage().setTitle("Photomanager - PlayPraesentation.fxml");
        
        praesi = new Praesentation();
        praesi.start();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException, InterruptedException{
        Stage stage;
        Parent root;        
        
        
        praesi.interrupt();
        stage=(Stage) guiPlayPraesentationBack.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("GuiSelectPraesentation.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Thread Klasse für die Präsentation
     * 
     * Version History:
     * @date 09.01.2016 by Tobias: Initialisierung
     */
    class Praesentation extends Thread{
        
        public void run() {
            List<Path> fotos = FotoController.getFotosFromAlbum(Main.speicher);
        
            for(Path foto : fotos){
                try {
                    Image image = new Image(foto.toUri().toString());
                    guiPlayPraesentationImageView.setImage(image);
                    
                    for(int i = 0; i < 50; i++){
                        sleep(100);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiPlayPraesentation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }
}
