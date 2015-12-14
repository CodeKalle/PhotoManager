package view;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Diese Klasse dient der Erstellung und dem Handling des Fotohinzufüge Fensters
 * 
 * @author Tobias
 * 
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische Umsetzung
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class GuiAddFotoController implements Initializable{
    
    /**
    * KLASSENVARIABLEN
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    // Alle Buttons des Fensters
    @FXML
    Button guiAddFotoAbbrechen, guiAddFotoBilderHinzufuegen;
    // Die Baumstruktur des Filesystems
    @FXML
    TreeView<String> treeView;
    // Der Fotogereich
    @FXML
    TilePane guiAddFotoTilePane;

    
    List<Path> aktuelleFotos = new LinkedList();
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
        if(event.getSource()==guiAddFotoBilderHinzufuegen){
            //Fotos zu Ablum hinzufügen, Methdenaufruf FotoController
            stage=(Stage) guiAddFotoBilderHinzufuegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Main.letztesFensterVorCreateAlbum));
        }
        else {
            stage=(Stage) guiAddFotoAbbrechen.getScene().getWindow();
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
    */
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
                FilePathTreeItem treeNode;
            
                try {
                    treeNode = new FilePathTreeItem(name, this, true);

                    //Unterknoten bekommen ein leeres Child, damit sie Aufklappbar werden. KEINE GUTE LÖSUNG!!
                    if(treeNode.isDirectory())
                        treeNode.getChildren().add(null);
                    rootNode.getChildren().add(treeNode);
            
                } catch (IOException ex) {
                    Logger.getLogger(GuiAddFotoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            rootNode.setExpanded(true);
            
            //Root in die TreeView setzten mit allen Unterknoten
            treeView.setRoot(rootNode);      
    }
    
    
    /**
     * Die Methode soll die Fotos im rechten TilePane der GuiAddFoto anzeigen, die sich im ausgewählten Ordner befinden.
     * 
     * @param fotos Liste mit Pfaden von Fotos, die sich im Ordner befinden.
     * 
     * Version-History:
     * @date 06.12.2015 by Tobias: Initialisierung
     */
    public void bilderAnzeigen(List<Path> fotos){
        guiAddFotoTilePane.getChildren().clear();

        //Fotos aus Album laden
        for(int i = 0; i < fotos.size(); i++) {
            //Für jedes Bild Konstrukt zusammensetzen
            Pane lpane = new Pane();            
            lpane.setPrefSize(80, 100);

            Image image = new Image(fotos.get(i).toUri().toString());

            ImageView imageView = new ImageView();
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(image);            

            CheckBox checkBox = new CheckBox();
            checkBox.setLayoutX(56.0);
            checkBox.setLayoutY(58.0);
            checkBox.setMnemonicParsing(false);

            Label name = new Label();
            name.setLayoutX(20.0);
            name.setLayoutY(80.0);
            name.setPrefHeight(20);
            name.setPrefWidth(80);
            name.setText(fotos.get(i).getFileName().toString());

            Label pfad = new Label();
            pfad.setVisible(false);
            pfad.setText(fotos.get(i).toString());

            lpane.getChildren().add(imageView); //ID 0
            lpane.getChildren().add(checkBox);  //ID 1
            lpane.getChildren().add(name);      //ID 2
            lpane.getChildren().add(pfad);      //ID 3

            //Fertiges Konstrukt in Pane anzeigen
            guiAddFotoTilePane.getChildren().add(i, lpane);
        }
    }
}
