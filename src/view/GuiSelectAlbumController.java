package view;

import controller.AlbenController;
import java.io.IOException;
import java.net.URL;
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
 * Diese Klasse dient zum Auswählen von Alben
 *
 * @author Tobias
 *
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische
 * Umsetzung
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class GuiSelectAlbumController implements Initializable {

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
    Button guiSelectAlbumNeuesAlbum, guiSelectAlbumAlbumWechseln, guiSelectAlbumHauptmenue;
    // Albenbereich
    @FXML
    TilePane guiAlbumOverviewTilePane;

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
        String titel;
        if (event.getSource() == guiSelectAlbumAlbumWechseln) {
            //Methodenaufruf getMarkiertesAlbum
            titel = getMarkiertesAlbum();
            if (titel == null) {
                return;   //Wenn kein oder mehrere Alben markiert sind, bleibe in der aktuellen Übersicht.
            }
            Main.speicher = titel;      //Titel zwischenspeichern, damit er im nächsten Fenster weiter verwendet werden kann.
            Main.letztesFenster = "GuiSelectAlbum.fxml";
            stage = (Stage) guiSelectAlbumAlbumWechseln.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiSelectedAlbum.fxml"));
        } else if (event.getSource() == guiSelectAlbumNeuesAlbum) {
            Main.speicher = "";
            Main.letztesFensterVorCreateAlbum = "GuiSelectAlbum.fxml";
            stage = (Stage) guiSelectAlbumNeuesAlbum.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiCreateAlbum.fxml"));
        } else {
            stage = (Stage) guiSelectAlbumHauptmenue.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));

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
        // Titel setzten
        Main.getPrimaryStage().setTitle("PhotoManager - Albenauswahl");

        //Alben laden
        for (int i = 0; i < AlbenController.getAlbumList().size(); i++) {
            //Für jedes Bild Konstrukt zusammensetzen
            Pane lpane = new Pane();
            lpane.setPrefSize(80, 100);

            Image image = new Image("/src/alben.png");

            ImageView imageView = new ImageView();
            CheckBox checkBox = new CheckBox();
            Label label = new Label();

            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(image);
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            try {
                                Main.speicher = getMarkiertesAlbum();
                                Main.letztesFenster = "GuiSelectAlbum.fxml";
                                Stage stage = (Stage) guiSelectAlbumAlbumWechseln.getScene().getWindow();
                                Parent root = FXMLLoader.load(getClass().getResource("GuiSelectedAlbum.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(GuiSelectPraesentationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (checkBox.isSelected()) {
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
            checkBox.setIndeterminate(false);

            label.setLayoutX(20.0);
            label.setLayoutY(80.0);
            label.setPrefHeight(20);
            label.setPrefWidth(80);
            label.setText(AlbenController.getAlbumList().get(i));

            lpane.getChildren().add(imageView); //ID 0
            lpane.getChildren().add(checkBox);  //ID 1
            lpane.getChildren().add(label);     //ID 2

            //Fertiges Konstrukt in Pane anzeigen
            guiAlbumOverviewTilePane.getChildren().add(i + 1, lpane);
        }
    }

    /**
     * Diese Methode gibt den Titel eines markierten Album zurück
     *
     * @return Titel des Albums, oder null wenn keines oder zu viele Alben
     * markiert waren.
     *
     * Version-History:
     * @date 01.12.2015 by Manuel Zeidler and Tobias Dahm: Initialisierung
     */
    private String getMarkiertesAlbum() {
        List<String> alben = new LinkedList();

        for (int i = 1; i < guiAlbumOverviewTilePane.getChildren().size(); i++) {
            Pane pane = (Pane) guiAlbumOverviewTilePane.getChildren().get(i);
            CheckBox checkBox = (CheckBox) pane.getChildren().get(1);
            Label label = (Label) pane.getChildren().get(2);

            if (checkBox.isSelected()) {
                alben.add(label.getText());
            }
        }

        //zu viele oder keine Alben markiert
        if (alben.isEmpty() || alben.size() > 1) {
            return null;
        } //ein Album markiert
        else {
            return alben.get(0);
        }
    }
}
