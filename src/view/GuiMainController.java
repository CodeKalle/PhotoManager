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
 *
 * @author Tobias
 */
public class GuiMainController {
    
    @FXML
    private Button guiMainButtonAlbum,guiMainButtonPraesentation,guiMainButtonFoto;

    
    @FXML
    public void handleButtonActionMain(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiMainButtonAlbum){
            stage=(Stage) guiMainButtonAlbum.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumOverview.fxml"));
        }
        else if(event.getSource()==guiMainButtonPraesentation) {                       
            stage=(Stage) guiMainButtonPraesentation.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiSelectPraesentation.fxml"));
        }
        else {
            stage=(Stage) guiMainButtonFoto.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAddFoto.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
