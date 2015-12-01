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
 * @author targhed
 */
public class GuiFotoEditController {
    

    @FXML
    Button guiFotoEditHinzufuegen,guiFotoEditLoeschen,guiFotoEditOk,guiFotoEditAbbrechen;

    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiFotoEditHinzufuegen){
            //Fotos zu Ablum hinzufügen, Methdenaufruf FotoController
            stage=(Stage) guiFotoEditHinzufuegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiFotoEdit.fxml"));
        }
        else if(event.getSource()==guiFotoEditLoeschen) {                       
            stage=(Stage) guiFotoEditLoeschen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiFotoEdit.fxml"));
        }
        else if(event.getSource()==guiFotoEditOk) {                       
            stage=(Stage) guiFotoEditOk.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiFotoEdit.fxml"));
        }
        else {
            stage=(Stage) guiFotoEditAbbrechen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}