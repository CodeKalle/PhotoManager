package view;

import controller.FotoController;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

/**
 * Diese Klasse dient als Startbildschirm
 *
 *
 * Version-History:
 *
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische
 * Umsetzung
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class GuiPlayPraesentation implements Initializable {

    @FXML
    private ImageView guiPlayPraesentationImageView;
    @FXML
    private Button guiPlayPraesentationBack;

    //Wert für die Übergänge.
    private int i = 0;
    Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Titel setzten
        Main.getPrimaryStage().setTitle("PhotoManager - Präsentation");
        play();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException, InterruptedException {
        Stage stage;
        Parent root;
        stop();
        stage = (Stage) guiPlayPraesentationBack.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("GuiSelectPraesentation.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Spielt die Präsentation ab und gestaltet die Übergänge.
     *
     * Version-History:
     * @date 15.01.2015 by Manu & Tobias: Methode geschrieben
     */
    private void play() {
        timeline = new Timeline();

        KeyValue transparent = new KeyValue(guiPlayPraesentationImageView.opacityProperty(), 0.0);
        KeyValue opaque = new KeyValue(guiPlayPraesentationImageView.opacityProperty(), 1.0);

        KeyFrame startFadeIn = new KeyFrame(Duration.ZERO, e -> {
            guiPlayPraesentationImageView.setImage(new Image(FotoController.getFotosFromAlbum(Main.speicher).get(i).toUri().toString()));
            i++;
            if(i >=FotoController.getFotosFromAlbum(Main.speicher).size())
                i=0;
        }, transparent);
        KeyFrame endFadeIn = new KeyFrame(Duration.seconds(1), opaque);
        KeyFrame stay = new KeyFrame(Duration.seconds(6), opaque);
        KeyFrame startFadeOut = new KeyFrame(Duration.seconds(6), opaque);
        KeyFrame endFadeOut = new KeyFrame(Duration.seconds(7), transparent);

        timeline.getKeyFrames().addAll(startFadeIn, endFadeIn, stay, startFadeOut, endFadeOut);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void stop(){
        timeline.stop();
    }
}
