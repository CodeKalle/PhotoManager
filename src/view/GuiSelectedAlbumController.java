package view;

import controller.FotoController;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Diese Klasse dient der Ansicht in einem Album
 *
 * @author Tobias
 *
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische
 * Umsetzung
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class GuiSelectedAlbumController implements Initializable {

    /**
     * KLASSENVARIABLEN
     *
     * Version-History:
     *
     * @date ??.11.2015 by Tobias: Initialisierung
     * @date 10.12.2015 by Danilo: Kommentare ergänzt
     */
    // Buttons des Fensters
    @FXML
    Button guiSelectedAlbumFotosHinzufuegen, guiSelectedAlbumFotosHinzufuegen2, guiSelectedAlbumAlbumBearbeiten, guiSelectedAlbumFotosLoeschen, guiSelectedAlbumFotosBearbeiten, guiSelectedAlbumZurueckzuAlben;
    // Fotobereich
    @FXML
    private TilePane guiSelectedAlbumTilePane;

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
        if (event.getSource() == guiSelectedAlbumAlbumBearbeiten) {
            // this album bearbeiten
            Main.letztesFensterVorCreateAlbum = "GuiSelectedAlbum.fxml";
            stage = (Stage) guiSelectedAlbumAlbumBearbeiten.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiCreateAlbum.fxml"));
        } else if (event.getSource() == guiSelectedAlbumFotosBearbeiten) {
            // selektierte fotos bearbeiten

            stage = (Stage) guiSelectedAlbumFotosBearbeiten.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiFotoDetail.fxml"));
        } else if (event.getSource() == guiSelectedAlbumFotosHinzufuegen || event.getSource() == guiSelectedAlbumFotosHinzufuegen2) {
            // fotos zu diesem album hinzufügen
            Main.letztesFensterVorCreateAlbum = "GuiSelectedAlbum.fxml";
            stage = (Stage) guiSelectedAlbumFotosHinzufuegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAddFoto.fxml"));
        } else if (event.getSource() == guiSelectedAlbumFotosLoeschen) {
            // Alle nicht selektierten Fotos in der TilePane als Fotos des Album setzten
            List<Path> nichtMarkierteFotos = getNichtMarkierteFotos();
            FotoController.deleteNotExistingFotosInListFromAlbum(Main.speicher, nichtMarkierteFotos);

            stage = (Stage) guiSelectedAlbumFotosLoeschen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiSelectedAlbum.fxml"));
        } else {
            stage = (Stage) guiSelectedAlbumZurueckzuAlben.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Main.letztesFenster));
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
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getPrimaryStage().setTitle("Photomanager - Ausgewähltes Album");

        //Fotos aus Album laden
        for (int i = 0; i < FotoController.getFotosFromAlbum(Main.speicher).size(); i++) {
            //Für jedes Bild Konstrukt zusammensetzen
            Pane lpane = new Pane();
            lpane.setPrefSize(80, 100);

            Image image = new Image(FotoController.getFotosFromAlbum(Main.speicher).get(i).toUri().toString());

            ImageView imageView = new ImageView();
            CheckBox checkBox = new CheckBox();
            Label name = new Label();
            Label pfad = new Label();

            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setId(Integer.toString(i));
            imageView.setImage(image);
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            try {
                                Main.letztesFenster = "GuiSelectedAlbum.fxml";
                                Main.id = Integer.valueOf(imageView.getId());
                                Stage stage = (Stage) guiSelectedAlbumFotosBearbeiten.getScene().getWindow();
                                Parent root = FXMLLoader.load(getClass().getResource("GuiFotoDetail.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GuiSelectPraesentationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else if (checkBox.isSelected()) {
                            checkBox.setSelected(false);
                        } else {
                            checkBox.setSelected(true);
                        }
                    }
                }
            });

            checkBox.setLayoutX(56.0);
            checkBox.setLayoutY(58.0);
            checkBox.setMnemonicParsing(false);

            name.setLayoutX(20.0);
            name.setLayoutY(80.0);
            name.setPrefHeight(20);
            name.setPrefWidth(80);
            name.setText(FotoController.getFotosFromAlbum(Main.speicher).get(i).getFileName().toString());

            pfad.setVisible(false);
            pfad.setText(FotoController.getFotosFromAlbum(Main.speicher).get(i).toString());

            lpane.getChildren().add(imageView); //ID 0
            lpane.getChildren().add(checkBox);  //ID 1
            lpane.getChildren().add(name);      //ID 2
            lpane.getChildren().add(pfad);      //ID 3

            //Fertiges Konstrukt in Pane anzeigen
            guiSelectedAlbumTilePane.getChildren().add(i + 1, lpane);
        }
    }

    /**
     * Gibt die markierten Fotos aus der TilePane zurück
     *
     * @return Liste von Pfaden, der markierten Fotos
     *
     * Version-History:
     * @date 14.12.2015 by Tobias: Initialisirung
     */
    private List<Path> getNichtMarkierteFotos() {
        List<Path> fotos = new LinkedList();

        for (int i = 1; i < guiSelectedAlbumTilePane.getChildren().size(); i++) {
            Pane pane = (Pane) guiSelectedAlbumTilePane.getChildren().get(i);
            CheckBox checkBox = (CheckBox) pane.getChildren().get(1);
            Label pfad = (Label) pane.getChildren().get(3);

            if (!checkBox.isSelected()) {
                fotos.add(Paths.get(pfad.getText()));
            }
        }

        return fotos;
    }
}
