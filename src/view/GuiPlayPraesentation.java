package view;

import controller.FotoController;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Titel setzten
        Main.getPrimaryStage().setTitle("Photomanager - PlayPraesentation.fxml");
        
        Thread praesi = new Praesentation();
        praesi.start();
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
                    sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiPlayPraesentation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }
}
