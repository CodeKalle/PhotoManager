/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

/**
 *
 * @author Tobias
 */
public class GuiAddFotoController implements Initializable{
    @FXML
    Button guiAddFotoAbbrechen, guiAddFotoBilderHinzufuegen;
    
    @FXML
    TreeView<String> treeView;

    
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
        
        
        
        
        //Treeview füllen    
        String hostName="computer";
        try{hostName=InetAddress.getLocalHost().getHostName();}catch(UnknownHostException x){}
         
        //Root erstellen
        TreeItem<String> rootNode=new TreeItem(hostName);//,new ImageView(new Image(ClassLoader.getSystemResourceAsStream("/src/dummy1.jpg"))));

        //Unterknoten von Root füllen: Laufwerke
            Iterable<Path> rootDirectories=FileSystems.getDefault().getRootDirectories();
            for(Path name:rootDirectories){
                FilePathTreeItem treeNode=new FilePathTreeItem(name);
                //Unterknoten bekommen ein leeres Child, damit sie Aufklappbar werden. KEINE GUTE LÖSUNG!!
                if(treeNode.isDirectory())
                    treeNode.getChildren().add(null);
                rootNode.getChildren().add(treeNode);
            }
            
            rootNode.setExpanded(false);
            
            //Root in die TreeView setzten mit allen Unterknoten
            treeView.setRoot(rootNode);

    }
}
