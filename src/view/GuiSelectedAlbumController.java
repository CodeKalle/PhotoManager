/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tobias
 */
public class GuiSelectedAlbumController{ 
     @FXML
    Button guiAlbumOverviewAlbumBearbeiten, guiAlbumOverviewAlbumWechseln, guiAlbumOverviewAlbumLoeschen, guiAlbumOverviewHauptmenue, guiAlbumOverviewFotoHinzufuegen;

    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiAlbumOverviewAlbumBearbeiten){
            stage=(Stage) guiAlbumOverviewAlbumBearbeiten.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumBearbeiten.fxml"));
        }
        else {
            stage=(Stage) guiAlbumOverviewHauptmenue.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
