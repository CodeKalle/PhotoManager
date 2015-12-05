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
 * @author Tobias
 */
public class GuiAddFotoController implements Initializable{
    @FXML
    Button guiAddFotoAbbrechen, guiAddFotoBilderHinzufuegen;

    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;        
        if(event.getSource()==guiAddFotoBilderHinzufuegen){
            //Fotos zu Ablum hinzufügen, Methdenaufruf FotoController
            stage=(Stage) guiAddFotoBilderHinzufuegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiFotoEdit.fxml"));
        }
        else {
            stage=(Stage) guiAddFotoAbbrechen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        // Titel setzten
        Main.getPrimaryStage().setTitle("Photomanager - AddFoto.fxml");
        
        
        //Treeview füllen:
        
    }
}
