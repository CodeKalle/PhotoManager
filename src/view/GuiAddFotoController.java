package view;

import controller.FotoController;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;

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
    
    private static String position;
    
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
            aktuelleFotos = this.getMarkierteFotos();
            if(aktuelleFotos != null){
                FotoController.addListOfFotosToAlbum(Main.speicher, aktuelleFotos);
            }
            else{
                return;
            }
            
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
    * Methode iteriert durch übergebennen Unterordner
    * 
    * @param parent Oberitem
    * @param depth Tiefe
    * 
    * Version-History:
    * @date 16.11.2015 by Danilo: Initialisierung
    */
    private void searchInFolder(TreeItem<String> parent, int depth) {
        depth++;
        if (depth>1) return;
        parent.getChildren().clear();
        File newFile = new File(parent.getValue());
        String[] directorie = newFile.list();
        for(String file:directorie){
            if (isAllowedFile(newFile.toPath())) {
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(file);
                parent.getChildren().add(treeItem);
                searchInFolder(treeItem , depth);
                if (Files.isDirectory(Paths.get(file))){
                    addHandler(treeItem);
                }
            }
        }
    }
    
    /**
    * Methode fügt Handler hinzu
    * 
    * @param treeitem Oberitem
    * 
    * Version-History:
    * @date 16.11.2015 by Danilo: Initialisierung
    */
    private void addHandler(TreeItem<String> treeitem) {
        treeitem.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler(){
            @Override
            public void handle(Event e){
                System.out.println("Expand ");
                searchInFolder(treeitem, 0);
            }
        });
        treeitem.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler(){
            @Override
            public void handle(Event e){
                System.out.println("Collaps");
            }
        });
    }
    
    /**
    * Überpfüft das Datei gültiges Format hat
    * 
    * @param file
    * 
    * Version-History:
    * @date 16.12.2015 by Danilo: Initialisierung
    */
    private boolean isAllowedFile(Path path) {
        if (Files.isReadable(path)) {
            return true;
        }
        if (Files.isDirectory(path)) {
            return true;
        }
        {
            String[] fileExtensions = new String[] {"jpg", "jpeg"};
            for (String tmpExtension : fileExtensions) {
                if(path.toFile().getName().toLowerCase().endsWith(tmpExtension)) {
                    return true;
                }
            }
        }
        return false;
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
    * @date 16.12.2015 by Danilo: Pfadprüfung geändert
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        // Titel setzten
        Main.getPrimaryStage().setTitle("Photomanager - AddFoto.fxml");
        
        //Treeview füllen    
        String hostName="computer";
        try{hostName=InetAddress.getLocalHost().getHostName();}catch(UnknownHostException x){}
 
        //Root erstellen
        TreeItem<String> rootNode=new TreeItem(hostName);
        
        Iterable<Path> rootDirectories=FileSystems.getDefault().getRootDirectories();
        for(Path path:rootDirectories){
            File rootFile = new File(path.toString());
            if(rootFile.isDirectory()){
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(path.toString());
                rootNode.getChildren().add(treeItem);
                searchInFolder(treeItem, 0);
                addHandler(treeItem);
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
    
    
    /**
     * Gibt die markierten Fotos aus der TilePane zurück
     * @return Liste von Pfaden, der markierten Fotos
     * 
     * Version-History:
     * @date 14.12.2015 by Tobias: Initialisirung
     */
    private List<Path> getMarkierteFotos() {
        List<Path> fotos = new LinkedList();
        
        for(int i = 0; i < guiAddFotoTilePane.getChildren().size(); i++){
            Pane pane = (Pane) guiAddFotoTilePane.getChildren().get(i);
            CheckBox checkBox = (CheckBox) pane.getChildren().get(1);
            Label pfad = (Label) pane.getChildren().get(3);
            
            if(checkBox.isSelected())
                fotos.add(Paths.get(pfad.getText()));
        }
        
        //keine Fotos markiert
        if(fotos.isEmpty()) { 
            return null;
        }
        else {
            return fotos;
        }
    }
}
