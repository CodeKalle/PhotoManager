/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AlbenController;
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
import model.Album;

/**
 * FXML Controller class
 *
 * @author targhed
 */
public class GuiCreateAlbumController implements Initializable {
    
    @FXML
    Button guiCreateAlbumSortierkennzeichenHinzufuegen,guiCreateAlbumSortierkennzeichenLoeschen,guiCreateAlbumOk,guiCreateAlbumAbbrechen;
    @FXML
    private ComboBox guiCreateAlbumComboBox;
    @FXML
    private TextField guiCreateAlbumName;
    @FXML
    private TextArea guiCreateAlbumBeschreibung;

    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiCreateAlbumSortierkennzeichenHinzufuegen){
            //Neues Sortierkennzeichen zur Combobox hinzufügen, gui neu laden
            stage=(Stage) guiCreateAlbumSortierkennzeichenHinzufuegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiCreateAlbum.fxml"));
        }
        else if(event.getSource()==guiCreateAlbumSortierkennzeichenLoeschen) {
            // Sortierkennzeichen aus der Combobox löschen, gui neu laden
            stage=(Stage) guiCreateAlbumSortierkennzeichenLoeschen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiCreateAlbum.fxml"));
        }
        else if(event.getSource()==guiCreateAlbumOk) {      
            // get value guiCreateAlbumName, guiCreateAlbumBeschreibung, guiCreateAlbumComboBox; create Album(); wechsel zu --- albenübersicht oder in das neue album
            if(AlbumErstellen() == null) return;
            stage=(Stage) guiCreateAlbumOk.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumOverview.fxml"));
        }
        else {
            stage=(Stage) guiCreateAlbumAbbrechen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumOverview.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getPrimaryStage().setTitle("Photomanager - CreateAlbum.fxml");
        // TODO
    }    

    private Album AlbumErstellen() {
        String titel = guiCreateAlbumName.getText();
        String beschreibung = guiCreateAlbumBeschreibung.getText();
        int sortierkennzeichen = guiCreateAlbumComboBox.getSelectionModel().getSelectedIndex();
        
        
        return AlbenController.createNewAlbum(titel, beschreibung, Integer.toString(sortierkennzeichen));
    }
    
}
