/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AlbenController;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 *
 * @author Tobias
 */
public class GuiSelectAlbumController implements Initializable{
  @FXML
    Button guiSelectAlbumNeuesAlbum, guiSelectAlbumAlbumWechseln, guiSelectAlbumHauptmenue;
    @FXML
    TilePane guiAlbumOverviewTilePane;
    
    private List<String> liste = new LinkedList();
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root; 
        String titel;
        if(event.getSource()==guiSelectAlbumAlbumWechseln) {                       
            //Methodenaufruf getMarkiertesAlbum
            titel = getMarkiertesAlbum();
            if(titel == null) return;   //Wenn kein oder mehrere Alben markiert sind, bleibe in der aktuellen Übersicht.
            Main.speicher = titel;      //Titel zwischenspeichern, damit er im nächsten Fenster weiter verwendet werden kann.
            Main.letztesFenster = "GuiSelectAlbum.fxml";
            stage=(Stage) guiSelectAlbumAlbumWechseln.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiSelectedAlbum.fxml"));
        }
        else if(event.getSource()==guiSelectAlbumNeuesAlbum) {
            Main.speicher = "";
            Main.letztesFensterVorCreateAlbum = "GuiSelectAlbum.fxml";
            stage=(Stage) guiSelectAlbumNeuesAlbum.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiCreateAlbum.fxml"));
        }
        else {
            stage=(Stage) guiSelectAlbumHauptmenue.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }

    /**
     * Initialisierung wird bei jedem Aufruf der GUI ausgeführt
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        // Titel setzten
        Main.getPrimaryStage().setTitle("Photomanager - GuiSelectAlbum.fxml");
        
        //Alben laden
        for(int i = 0; i < AlbenController.getAlbumList().size(); i++) {
            //Für jedes Bild Konstrukt zusammensetzen
            Pane lpane = new Pane();            
            lpane.setPrefSize(80, 100);
            
            Image image = new Image("/src/dummy1.jpg");
            
            ImageView imageView = new ImageView();
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(image);            
            
            CheckBox checkBox = new CheckBox();
            checkBox.setLayoutX(56.0);
            checkBox.setLayoutY(58.0);
            checkBox.setMnemonicParsing(false);
            
            Label label = new Label();
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
     * Diese Methode gibt den Titel eines markierten Album zurück.
     * @return Titel des Albums, oder null wenn keines oder zu viele Alben markiert waren.
     * 
     * Version-History:
     * @date 01.12.2015 by Manuel Zeidler and Tobias Dahm: Initialisierung
     */
    private String getMarkiertesAlbum() {
        List<String> alben = new LinkedList();
        
        for(int i = 1; i < guiAlbumOverviewTilePane.getChildren().size(); i++){
            Pane pane = (Pane) guiAlbumOverviewTilePane.getChildren().get(i);
            CheckBox checkBox = (CheckBox) pane.getChildren().get(1);
            Label label = (Label) pane.getChildren().get(2);
            
            if(checkBox.isSelected())
                alben.add(label.getText());
        }
        
        //zu viele oder keine Alben markiert
        if(alben.isEmpty() || alben.size() > 1) { 
            return null;
        }
        //ein Album markiert
        else {
            return alben.get(0);
        }
    }
}
