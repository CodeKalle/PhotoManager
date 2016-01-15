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
 * Diese Klasse dient der Erstellung eines Albums
 * 
 * @author Tobias
 * 
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische Umsetzung
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class GuiCreateAlbumController implements Initializable {
    
    /**
    * KLASSENVARIABLEN
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    // Buttons des Fensters
    @FXML
    Button guiCreateAlbumSortierkennzeichenHinzufuegen,guiCreateAlbumSortierkennzeichenLoeschen,guiCreateAlbumOk,guiCreateAlbumAbbrechen;
    // Combobox für Sortierkennzeichen
    @FXML
    private ComboBox guiCreateAlbumComboBox;
    // Textfeld für den Alben Namen
    @FXML
    private TextField guiCreateAlbumName;
    // Textbereich für die Alben Beschreibung
    @FXML
    private TextArea guiCreateAlbumBeschreibung;
    // Verwaltungsvariablen
    String origTitel, titel, beschreibung;
    Boolean bearbeitungsmodus = false;
    
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
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiCreateAlbumOk) {      
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
     * Initialisierung wird bei jedem Aufruf der GUI ausgeführt
     * 
     * @param location
     * @param resources 
     * 
     * Version-History:
     * @date ??.11.2015 by Tobias: Initialisierung
     * @date 10.12.2015 by Danilo: Kommentare ergänzt
     * @date 15.01.2016 by Tobias: Anzeige von Sortierkennzeichen ergänzt
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getPrimaryStage().setTitle("PhotoManager - Neues Album");
        
        this.origTitel = Main.speicher;
        
        if(!this.origTitel.isEmpty()) {
            this.bearbeitungsmodus = true;
            Album album = AlbenController.getAlbum(origTitel);
            
            guiCreateAlbumName.setText(album.getTitel());
            guiCreateAlbumBeschreibung.setText(album.getBeschreibung());
            guiCreateAlbumComboBox.getSelectionModel().select(0);
        }
        
        
        //Sortierkennzeichen erhalten
        if(AlbenController.getSortierkennzeichenFromAlbum(Main.speicher)==350 || AlbenController.getSortierkennzeichenFromAlbum(Main.speicher)==0){
            guiCreateAlbumComboBox.setValue("Benutzerdefiniert");
        }
        else if(AlbenController.getSortierkennzeichenFromAlbum(Main.speicher)==1){
            guiCreateAlbumComboBox.setValue("Name");
        }
        else if(AlbenController.getSortierkennzeichenFromAlbum(Main.speicher)==2){
            guiCreateAlbumComboBox.setValue("Datum");
        }
    }    

    /**
    * Erstellt ein Album im Model
    * 
    * @return Fehlercode zur Auswertung
    * 
    * Version-History:
    * @date ??.??.2015 by Tobias: Initialisierung
    * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
    * @date 10.12.2015 by Danilo: Kommentare ergänzt und lokale Variablen umbenannt
    */
    private int AlbumErstellen() {
        String tmpTitel = guiCreateAlbumName.getText();
        String tmpBeschreibung = guiCreateAlbumBeschreibung.getText();
        int tmpSortierkennzeichen = guiCreateAlbumComboBox.getSelectionModel().getSelectedIndex();
        System.out.println(tmpSortierkennzeichen);
        
        if(!bearbeitungsmodus)
            return AlbenController.createNewAlbum(tmpTitel, tmpBeschreibung, tmpSortierkennzeichen);
        else {
            //Wenn Album erfolgreich editiert, speicher den Titel des Albums in die globale Variable.
            int err = AlbenController.editAlbum(origTitel, tmpTitel, tmpBeschreibung, tmpSortierkennzeichen);

            if(err != 0){
                return 0;
            }
            else {
                Main.speicher = tmpTitel;
                return err;
            }  
        }
    }
}
