package view;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import controller.FotoController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Diese Klasse dient der Ansicht eines Fotos
 *
 * @author Tobias
 *
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische
 * Umsetzung
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 * @date 13.01.2016 by Manuel: rework
 */
public class GuiFotoDetailController implements Initializable {

    /**
     * KLASSENVARIABLEN
     *
     * Version-History:
     *
     * @date ??.11.2015 by Tobias: Initialisierung
     * @date 10.12.2015 by Danilo: Kommentare ergänzt
     */
    // Buttons der Klasse
    @FXML
    ScrollPane guiFotoDetailScrollPane;
    @FXML
    TilePane guiFotoDetailTilePane;
    @FXML
    Button guiFotoDetailAbbrechen, guiFotoDetailBearbeiten;
    @FXML
    ImageView guiFotoDetailImageView;
    @FXML
    TextFlow guiAddFotoDetailTextFlow;
    @FXML
    TextArea guiAddFotoDetailTextArea;

    /**
     * Methode handelt die Aktionen der Buttons
     *
     * @throws java.io.IOException
     * @param event Aktion des Buttons
     *
     * Version-History:
     * @date ??.11.2015 by Tobias: Initialisierung
     * @date 10.12.2015 by Danilo: Kommentare ergänzt
     */
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == guiFotoDetailBearbeiten) {
            stage = (Stage) guiFotoDetailBearbeiten.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumBearbeiten.fxml"));
        } else {
            stage = (Stage) guiFotoDetailAbbrechen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiSelectedAlbum.fxml"));

        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialisierung wird bei jedem Aufruf der GUI ausgeführt
     *
     * @param location
     * @param resources
     *
     * Version-History:
     * @date ??.11.2015 by Tobias: Initialisierung
     * @date 10.12.2015 by Danilo: Kommentare ergänzt
     * @date 16.01.2015 by Manuel Eventhandler
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getPrimaryStage().setTitle("PhotoManager - Details");

        for (int i = 0; i < FotoController.getFotosFromAlbum(Main.speicher).size(); i++) {
            //Für jedes Bild Konstrukt zusammensetzen
            Pane lpane = new Pane();
            lpane.setPrefSize(80, 100);

            Image image = new Image(FotoController.getFotosFromAlbum(Main.speicher).get(i).toUri().toString());

            ImageView imageView = new ImageView();
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setId(Integer.toString(i));
            imageView.setImage(image);

            // Bei Click bild auswählen
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    guiFotoDetailImageView.setImage(image);
                    // Bei Mouse Over Hintergrund der Textarea ändern
                    guiAddFotoDetailTextArea.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            guiAddFotoDetailTextArea.setId("text-area1");
                        }
                    });
                    //Bei verlassen der Maus zurück
                    guiAddFotoDetailTextArea.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            guiAddFotoDetailTextArea.setId("text-area");
                        }
                    });
                    guiAddFotoDetailTextArea.setVisible(true);
                    try {
                        guiAddFotoDetailTextArea.setText(getExifData(FotoController.getFotosFromAlbum(Main.speicher).get(Integer.valueOf(imageView.getId()))));
                    } catch (ImageProcessingException ex) {
                        Logger.getLogger(GuiFotoDetailController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GuiFotoDetailController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            Label name = new Label();
            name.setLayoutX(20.0);
            name.setLayoutY(80.0);
            name.setPrefHeight(20);
            name.setPrefWidth(80);
            name.setText(FotoController.getFotosFromAlbum(Main.speicher).get(i).getFileName().toString());

            Label pfad = new Label();
            pfad.setVisible(false);
            pfad.setText(FotoController.getFotosFromAlbum(Main.speicher).get(i).toString());

            lpane.getChildren().add(imageView); //ID 0
            lpane.getChildren().add(name);      //ID 1
            lpane.getChildren().add(pfad);      //ID 2

            //Fertiges Konstrukt in Pane anzeigen
            guiFotoDetailTilePane.getChildren().add(i, lpane);
        }

        guiFotoDetailImageView.setImage(new Image(FotoController.getFotosFromAlbum(Main.speicher).get(Main.id).toUri().toString()));
    }

    private static String getExifData(Path path) throws ImageProcessingException, IOException {
        File jpegFile = new File(path.toString());

        com.drew.metadata.Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);

        String exif = "";
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                exif += tag + System.getProperty("line.separator");

            }
        }
        return exif;
    }
}
