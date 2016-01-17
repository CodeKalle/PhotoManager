package view;

import controller.FotoController;
import controller.SystemController;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

/**
 * Diese Klasse dient der Erstellung und dem Handling des Fotohinzufüge Fensters
 * 
 * @author Tobias
 * 
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische Umsetzung
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 * @date 07.01.2016 by Danilo: Update
 * @date 08.01.2016 by Danilo: Filesystem Speicherpunkt
 */
public class GuiAddFotoController implements Initializable {
    
    /**
    * KLASSENVARIABLEN
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    * @date 08.01.2016 by Danilo: Verschieben der zu merkenden Position in SystemController
    */
    @FXML
    HBox zoomBox;
    
    @FXML
    ImageView zoomImageView;
    
    @FXML
    ScrollPane guiAddFotoScrollPane;
    
    // Alle Buttons des Fensters
    @FXML
    Button guiAddFotoAbbrechen, guiAddFotoBilderHinzufuegen;
    
    // Die Baumstruktur des Filesystems
    @FXML
    TreeView<String> treeView;
    
    // Der Fotogereich
    @FXML
    TilePane guiAddFotoTilePane;
    
    // Markierung ob man sich im Root befindet
    boolean isRoot = true;
    
    // Oberstes Treeitem
    TreeItem<String> rootNode;
    
    // Fotoliste
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
    * @date 16.12.2015 by Danilo: Initialisierung
    * @date 07.01.2016 by Danilo: Update
    * @date 10.01.2016 by Danilo: Ordneraxpansion geändert
    */
    private void searchInFolder(TreeItem<String> parent) {
        parent.getChildren().clear();
        File newFile = new File(getPath(parent).toString());
        String[] directorie = newFile.list();
        for(String file:directorie){
            Path fullPath = Paths.get(newFile + File.separator + file);
            if (Files.isReadable(fullPath) && Files.isDirectory(fullPath)) {
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(file);
                parent.getChildren().add(treeItem);
                checkThatFolderHasSubfolder(treeItem);
            }
        }
    }
    
    /**
    * Methode ändert Möglichkeit der Ordneraxpansion
    * 
    * @param treeItem Ordnername
    * 
    * Version-History:
    * @date 10.01.2016 by Danilo: Initialisierung
    */
    private void checkThatFolderHasSubfolder(TreeItem<String> treeItem) {
        File newFile = new File(getPath(treeItem).toString());
        String[] directorie = newFile.list();
        for(String file:directorie){
            Path fullPath = Paths.get(newFile + File.separator + file);
            if (Files.isDirectory(fullPath)) {
                treeItem.getChildren().add(null);
                addHandler(treeItem);
                return;
            }
        }
    }
    
    /**
    * Methode die Liste der Bilder erstellt
    * 
    * @param currentfile Übergebenes TreeItem
    * @return
    * 
    * Version-History:
    * @date 07.01.2016 by Danilo: Initialisierung
    */
    private List<Path> getPathList(Path path) {
        List<Path> pathlist = new LinkedList<>();
        String[] fileExtensions = new String[] {"jpg", "jpeg", "JPG", "JPEG"};
        for (String tmpExtension : fileExtensions) {
            File newFile = new File(path.toString());
            File[] directorie = newFile.listFiles();
            try {
                for(File file:directorie){
                    if(file.getName().endsWith(tmpExtension)) {
                        pathlist.add(file.toPath());
                    }
                }
            } catch (NullPointerException e) {}
        }
        return pathlist;
    }

    /**
    * Methode die den Systempfad eines TreeItems zurück gibt
    * 
    * @param currentfile Übergebenes TreeItem
    * @return Vollständiger Pfad des TreeItems
    * 
    * Version-History:
    * @date 16.12.2015 by Danilo: Initialisierung
    */
    private Path getPath(TreeItem<String> currentfile) {
        String fullPath = "";
        if (currentfile.getParent()==null) {
        }else if(currentfile.getParent().getParent()==null) {
            fullPath = currentfile.getValue();
        } else {
            fullPath = getPath(currentfile.getParent()) + File.separator + currentfile.getValue();
        }
        return Paths.get(fullPath);
    }

    /**
    * Methode fügt Handler hinzu
    * 
    * @param treeitem Oberitem
    * 
    * Version-History:
    * @date 16.12.2015 by Danilo: Initialisierung
    * @date 07.01.2016 by Danilo: Update
    * @date 08.01.2016 by Danilo: Ordnerposition
    */
    private void addHandler(TreeItem<String> treeitem) {
        treeitem.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler(){
            @Override
            public void handle(Event e){
                if (!treeitem.getChildren().isEmpty() && treeitem.getChildren().get(0)==null) searchInFolder(treeitem);
                callTreeitemForAction(treeitem);
            }
        });
        treeitem.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler(){
            @Override
            public void handle(Event e){
                callTreeitemForAction(treeitem);
            }
        });
    }
    
    /**
    * Methode ruft Action für TreeItem und verhidert die Rekursion in der Filehierarchy
    * 
    * @param treeitem TreeItem deren Action ausgelöst werden soll
    * 
    * Version-History:
    * @date 08.01.2016 by Danilo: Initialisierung
    * @date 10.01.2016 by Danilo: Fehlerkorrektur
    */
    private void callTreeitemForAction(TreeItem<String> treeitem) {
        if (isRoot==true) {
            if (treeitem.getParent()!=rootNode) isRoot=false;
            treeView.getSelectionModel().select(treeitem);
        } else {
            if (treeitem.getParent()==rootNode) isRoot=true;
        }
    }
    
    /**
    * Methode gibt Ebene im Filesystem zurück
    * 
    * @param path Übergebener Pfad
    * 
    * Version-History:
    * @date 08.01.2016 by Danilo: Initialisierung
    */
    private int getLevelOfFile(Path path) {
        if (path==null) {
            return 0;
        } else {
            return (1 + getLevelOfFile(path.getParent()));
        }
    }
    
    /**
    * Öffnet Baummenü zur letzten gemerkten Position
    * 
    * Version-History:
    * @date 08.01.2016 by Danilo: Initialisierung
    */
    private void expandToPosition() {
        // Prüfen ob Pfad schon gemerkt wurde und Pfad ein Ornder mit Berechtigungen zum lesen ist
        Path path = SystemController.getPosition();
        if (path == null || !path.toFile().isDirectory() || !path.toFile().canRead()) return;      
        
        TreeItem<String> lastFolder = null;
        
        // Rootverzeichnis öffnen
        ObservableList<TreeItem<String>> elementlist = rootNode.getChildren();
        for (TreeItem<String> tmp : elementlist) {
            if (tmp.getValue().equals(path.getRoot().toString())) {
                tmp.setExpanded(true);
                searchInFolder(tmp);
                elementlist = tmp.getChildren();
                lastFolder = tmp;
            }
        }
        
        // Unterverzeichnisse öffnen
        int fileLevel = getLevelOfFile(path);
        for (int i = 0; i<fileLevel-1; i++) {
            for (TreeItem<String> tmp : elementlist) {
                //Ordner expandieren
                if (tmp.getValue().equals(path.getName(i).toString())) {
                    tmp.setExpanded(true);
                    searchInFolder(tmp);
                    elementlist = tmp.getChildren();
                    lastFolder = tmp;
                }
            }
        }
        
        // Bilder anzeigen und TreeItem markieren
        if (lastFolder!= null) bilderAnzeigen(getPathList(getPath(lastFolder)));
        treeView.getSelectionModel().select(lastFolder);
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
    * @date 07.01.2016 by Danilo: Update
    * @date 08.01.2016 by Danilo: Handler für Mausklick
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        // Titel setzten
        Main.getPrimaryStage().setTitle("PhotoManager - Foto hinzufügen");
        
        //Treeview füllen    
        String hostName="computer";
        try{hostName=InetAddress.getLocalHost().getHostName();}catch(UnknownHostException x){}
 
        //Root erstellen
        rootNode=new TreeItem(hostName);
        
        Iterable<Path> rootDirectories=FileSystems.getDefault().getRootDirectories();
        for(Path path:rootDirectories){
            File rootFile = new File(path.toString());
            if(rootFile.isDirectory()){
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(path.toString());
                treeItem.getChildren().add(null);
                rootNode.getChildren().add(treeItem);
                addHandler(treeItem);
            }
        }
        
        // Computerroot ausklappen und markieren
        rootNode.setExpanded(true);
        treeView.getSelectionModel().select(rootNode);
        
        // Gemerkte Position wieder herstellen
        expandToPosition();

        //Root in die TreeView setzten mit allen Unterknoten
        treeView.setRoot(rootNode);
        
        // Handler für den Mausklick der TreeView
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldVal, Object newVal) {
                TreeItem<String> treeitem = treeView.getSelectionModel().getSelectedItem();
                SystemController.setPosition(getPath(treeitem));
                treeView.getSelectionModel().select(treeitem);
                bilderAnzeigen(getPathList(getPath(treeitem)));
            }
        });
    }
    
    /**
     * Die Methode soll die Fotos im rechten TilePane der GuiAddFoto anzeigen, die sich im ausgewählten Ordner befinden.
     * 
     * @param fotos Liste mit Pfaden von Fotos, die sich im Ordner befinden.
     * 
     * Version-History:
     * @date 06.12.2015 by Tobias: Initialisierung
     * @date 15.12.2015 by Manuel: Eventhandling
     */
    public void bilderAnzeigen(List<Path> fotos) {
        guiAddFotoTilePane.getChildren().clear();

        guiAddFotoTilePane.setCursor(javafx.scene.Cursor.WAIT);

        //Fotos aus Album laden
        for (int i = 0; i < fotos.size(); i++) {
            //Für jedes Bild Konstrukt zusammensetzen
            Pane lpane = new Pane();
            lpane.setPrefSize(80, 100);

            Image image = new Image(fotos.get(i).toUri().toString());

            ImageView imageView = new ImageView();
            CheckBox checkBox = new CheckBox();
            Label name = new Label();
            Label pfad = new Label();

            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(image);

            // EventHandler Zoom bei Rechter Maustatse
            imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        zoomBox.setDisable(false);
                        zoomImageView.setDisable(false);
                        zoomImageView.setImage(image);
                        guiAddFotoTilePane.setOpacity(0.4);
                        guiAddFotoScrollPane.setId("scroll-pane1");
                    }
                }
            });

            imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        zoomImageView.setImage(null);
                        zoomImageView.setDisable(false);
                        zoomBox.setDisable(false);
                        guiAddFotoTilePane.setOpacity(1);
                        guiAddFotoScrollPane.setId("scroll-pane2");
                    }
                }
            });
            // Eventhandler Checkbox
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (checkBox.isSelected()) {
                            checkBox.setSelected(false);
                        } else {
                            checkBox.setSelected(true);
                        }
                    }
                }
            });


            checkBox.setLayoutX(56.0);
            checkBox.setLayoutY(58.0);
            checkBox.setMnemonicParsing(false);


            name.setLayoutX(20.0);
            name.setLayoutY(80.0);
            name.setPrefHeight(20);
            name.setPrefWidth(80);
            name.setText(fotos.get(i).getFileName().toString());


            pfad.setVisible(false);
            pfad.setText(fotos.get(i).toString());

            lpane.getChildren().add(imageView); //ID 0
            lpane.getChildren().add(checkBox);  //ID 1
            lpane.getChildren().add(name);      //ID 2
            lpane.getChildren().add(pfad);      //ID 3

            //Fertiges Konstrukt in Pane anzeigen
            guiAddFotoTilePane.getChildren().add(i, lpane);
        }
        
        guiAddFotoTilePane.setCursor(javafx.scene.Cursor.DEFAULT);
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
