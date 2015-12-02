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
import javafx.stage.Stage;

/**
 *
 * @author targhed
 */
public class GuiFotoDetailController implements Initializable{
    
    @FXML
    Button guiFotoDetailAbbrechen, guiFotoDetailBearbeiten;
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiFotoDetailBearbeiten){
            stage=(Stage) guiFotoDetailBearbeiten.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumBearbeiten.fxml"));
        }
        else {
            stage=(Stage) guiFotoDetailAbbrechen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getPrimaryStage().setTitle("Photomanager - FotoDetail.fxml");
    }
    
}
