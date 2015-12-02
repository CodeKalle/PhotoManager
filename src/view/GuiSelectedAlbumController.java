/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.FotoController;
import java.io.IOException;
import java.net.URL;
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
 * FXML Controller class
 *
 * @author Tobias
 */
public class GuiSelectedAlbumController implements Initializable { 
    @FXML
    Button guiSelectedAlbumFotosHinzufuegen, guiSelectedAlbumAlbumBearbeiten, guiSelectedAlbumFotosLoeschen, guiSelectedAlbumFotosBearbeiten, guiSelectedAlbumZurueckzuAlben;
    @FXML
    private TilePane guiSelectedAlbumTilePane;
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiSelectedAlbumAlbumBearbeiten){
            // this album bearbeiten
            Main.letztesFensterVorCreateAlbum = "GuiSelectedAlbum.fxml";
            stage=(Stage) guiSelectedAlbumAlbumBearbeiten.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiCreateAlbum.fxml"));
        }
        else if(event.getSource()==guiSelectedAlbumFotosBearbeiten){
            // selektierte fotos bearbeiten
            stage=(Stage) guiSelectedAlbumFotosBearbeiten.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiFotoEdit.fxml"));
        }
        else if(event.getSource()==guiSelectedAlbumFotosHinzufuegen){
            // fotos zu diesem album hinzufügen
            stage=(Stage) guiSelectedAlbumFotosHinzufuegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAddFoto.fxml"));
        }
        else if(event.getSource()==guiSelectedAlbumFotosLoeschen){
            // selektierte fotos aus deisem album löschen, dann diese gui refresh
            //FotosLoeschen();
            stage=(Stage) guiSelectedAlbumFotosLoeschen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiSelectedAlbum.fxml"));
        }
        else {
            stage=(Stage) guiSelectedAlbumZurueckzuAlben.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Main.letztesFenster));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getPrimaryStage().setTitle("Photomanager - SelectedAlbum");
        
        //Fotos aus Album laden
        for(int i = 0; i < FotoController.getFotosFromAlbum(Main.speicher).size(); i++) {
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
            lpane.getChildren().add(checkBox);  //ID 1
            lpane.getChildren().add(name);      //ID 2
            lpane.getChildren().add(pfad);      //ID 3
            
            
            //Fertiges Konstrukt in Pane anzeigen
            guiSelectedAlbumTilePane.getChildren().add(i + 1, lpane);
        }
    }

    /**private void FotosLoeschen() {
        List<Path> alben = new LinkedList();
        
        for(int i = 1; i < guiSelectedAlbumTilePane.getChildren().size(); i++){
            Pane pane = (Pane) guiSelectedAlbumTilePane.getChildren().get(i);
            CheckBox checkBox = (CheckBox) pane.getChildren().get(1);
            Label pfad = (Label) pane.getChildren().get(3);
            
            if(checkBox.isSelected())
                alben.add(new Path(pfad));
        }

        if(alben.isEmpty()) {
            return 1;
        }
        else {
            FotoController.
            return 0;
        }
    }*/
}
