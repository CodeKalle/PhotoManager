package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

/**
 * Diese Klasse dient der Erstellung der einzellnen Knoten um Ordner und darin
 * befindliche Bilddateien anzuzeigen.
 * 
 * @author Tobias
 * 
 * Version History:
 * @date 06.12.2015 by Tobias: branchExpandedEvent() Erweitert und Boolean für isJPEG hinzugefügt
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 */
public class FilePathTreeItem extends TreeItem<String>{
    
    /**
    * KLASSENVARIABLEN
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    // Vollständiger Pfad der Datei oder des Ordners
    private String fullPath;
    // Information über Ordner
    private boolean isDirectory;
    // Information über Bild
    private boolean isJPEG;
    
    /**
    * GETTER Holt vollständiger Pfad der Datei oder des Ordners
    *
    * @return Vollständiger Pfad
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    public String getFullPath() {
        return(this.fullPath);
    }
  
    /**
    * GETTER Holt Information ob Ordners
    *
    * @return Wahrheitswert ob Ordner
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    public boolean isDirectory() {
        return(this.isDirectory);
    }
    
    /**
    * GETTER Holt Information ob JPG-Datei
    *
    * @return Wahrheitswert ob JPG
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    public boolean isJPEG() {
        return(this.isJPEG);
    }
    
    /**
    * KONSTRUKTOR
    *
    * @param file Pfad der Datei
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    public FilePathTreeItem(Path file) throws IOException{
        super(file.toString());
        this.fullPath=file.toString();
        
        Path jpg = Paths.get(".jpg");
        
        // Testet ob es ein Ordner ist und setzt das Icon
        if(Files.isDirectory(file, LinkOption.NOFOLLOW_LINKS) && !Files.isHidden(file)){
            this.isDirectory=true;
            this.isJPEG=false;
            //this.setGraphic(new ImageView(folderCollapseImage));
        }else if(file.toString().endsWith(jpg.toString())){
            this.isDirectory=false;
            this.isJPEG=true;
        }else{
            this.isDirectory=false;
            this.isJPEG=false;
            //this.setGraphic(new ImageView(fileImage));
            // Hier folgt Auswertung für unterschiedliche Icons der Dateien
        }
    
        // Setzen der Werte
        if(!fullPath.endsWith(File.separator)){
            // Was ist was und Anzeige im Baum
            String value=file.toString();
            int indexOf=value.lastIndexOf(File.separator);
                if(indexOf>0){
                this.setValue(value.substring(indexOf+1));
            }else{
                this.setValue(value);
            }
        }

        this.addEventHandler(TreeItem.branchExpandedEvent(),new EventHandler(){
            @Override
            public void handle(Event e){
                List<Path> fotos = new LinkedList();
                FilePathTreeItem source=(FilePathTreeItem)e.getSource();
                    if(source.isDirectory()&&source.isExpanded()){
                        ImageView iv=(ImageView)source.getGraphic();
                        //iv.setImage(folderExpandImage);
                    }
                try{
                    source.getChildren().remove(0, source.getChildren().size());
                    Path path=Paths.get(source.getFullPath());
                    BasicFileAttributes attribs=Files.readAttributes(path,BasicFileAttributes.class);
                    if(attribs.isDirectory()){
                        DirectoryStream<Path> dir=Files.newDirectoryStream(path);
                        for(Path file:dir){
                            FilePathTreeItem treeNode=new FilePathTreeItem(file);
                            if(treeNode.isDirectory()){
                                // Wenn Ordner ausklappbar machen
                                treeNode.getChildren().add(null);
                                source.getChildren().add(treeNode);
                            }
                            // Wenn .jpg anzeigen
                            else if(treeNode.isJPEG()){
                                fotos.add(file);
                                source.getChildren().add(treeNode);
                                // Für jedes Bild aufruf in GuiAddFotoController, um Bild rechts anzuzeigen
                            }
                        }
                    }
                    
                }catch(IOException x){
                    x.printStackTrace();
                }
            }
        });
     
        this.addEventHandler(TreeItem.branchCollapsedEvent(),new EventHandler(){
            @Override
            public void handle(Event e){
                FilePathTreeItem source=(FilePathTreeItem)e.getSource();
                if(source.isDirectory()&&!source.isExpanded()){
                    ImageView iv=(ImageView)source.getGraphic();
                    //iv.setImage(folderCollapseImage);
                }
            }
        });
    }
}
