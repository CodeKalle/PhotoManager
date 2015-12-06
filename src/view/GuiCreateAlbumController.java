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

    
    String origTitel, titel, beschreibung;
    Boolean bearbeitungsmodus = false;
    
    
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
            if(AlbumErstellen() != 0) return;
            stage=(Stage) guiCreateAlbumOk.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Main.letztesFensterVorCreateAlbum));
        }
        else {
            stage=(Stage) guiCreateAlbumAbbrechen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Main.letztesFensterVorCreateAlbum));
            
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
        
        this.origTitel = Main.speicher;
        
        if(!this.origTitel.isEmpty()) {
            this.bearbeitungsmodus = true;
            Album album = AlbenController.getAlbum(origTitel);
            
            guiCreateAlbumName.setText(album.getTitel());
            guiCreateAlbumBeschreibung.setText(album.getBeschreibung());
            guiCreateAlbumComboBox.getSelectionModel().select(0);
        }
    }    

    private int AlbumErstellen() {
        String titel = guiCreateAlbumName.getText();
        String beschreibung = guiCreateAlbumBeschreibung.getText();
        int sortierkennzeichen = guiCreateAlbumComboBox.getSelectionModel().getSelectedIndex();
        
        
        if(!bearbeitungsmodus)
            return AlbenController.createNewAlbum(titel, beschreibung, Integer.toString(sortierkennzeichen));
        else {
            //Wenn Album erfolgreich editiert, speicher den Titel des Albums in die globale Variable.
            int err = AlbenController.editAlbum(origTitel, titel, beschreibung, Integer.toString(sortierkennzeichen));

            if(err != 0){
                return 0;
            }
            else {
                Main.speicher = titel;
                return err;
            }  
        }
    }
    
}