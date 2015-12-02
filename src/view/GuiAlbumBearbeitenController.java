/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Tobias
 */
public class GuiAlbumBearbeitenController implements Initializable{
    String titel;
    String beschreibung;
    String sortierkennzeichen;
    
    @FXML
    Button guiAlbumBearbeitenHinzufuegen,guiAlbumBearbeitenLoeschen,guiAlbumBearbeitenOk,guiAlbumBearbeitenAbbrechen;
    @FXML
    private TextArea guiAlbumBearbeitenBeschreibung;
    @FXML
    private TextField guiAlbumBearbeitenAlbumtitel;
    @FXML
    private ComboBox guiAlbumBearbeiten;

    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiAlbumBearbeitenOk){
            // set value titel beschreibung sortierkennzeichen, wechsel zu alben overview wenn erfolgreich
            stage=(Stage) guiAlbumBearbeitenOk.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumOverview.fxml"));
        }
        else if(event.getSource()==guiAlbumBearbeitenLoeschen){
            // Sortierkennzeichen entfernen, gui reload wenn erfolgreich
            stage=(Stage) guiAlbumBearbeitenLoeschen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumBearbeiten.fxml"));
        }
        else if(event.getSource()==guiAlbumBearbeitenHinzufuegen){
            // Sortierkennzeichen Hinzufügen, gui reload wenn erfolgreich
            stage=(Stage) guiAlbumBearbeitenHinzufuegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumBearbeiten.fxml"));
        }
        else {
            // abbruch zurück zu alben overview
            stage=(Stage) guiAlbumBearbeitenAbbrechen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumOverview.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.titel = Main.speicher;
        Main.getPrimaryStage().setTitle("Photomanager - AlbumBearbeiten.fxml");
    }
}
