/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tobias
 */
public class FilePathTreeItem extends TreeItem<String>{
    //public static Image folderCollapseImage=new Image(ClassLoader.getSystemResourceAsStream("com/huguesjohnson/javafxfilebrowsedemo/folder.png"));
    //public static Image folderExpandImage=new Image(ClassLoader.getSystemResourceAsStream("com/huguesjohnson/javafxfilebrowsedemo/folder-open.png"));
    //public static Image fileImage=new Image(ClassLoader.getSystemResourceAsStream("com/huguesjohnson/javafxfilebrowsedemo/text-x-generic.png"));
  
    //this stores the full path to the file or directory
    private String fullPath;
    public String getFullPath(){return(this.fullPath);}
  
    private boolean isDirectory;
    public boolean isDirectory(){return(this.isDirectory);}
    
    private boolean isJPEG;
    public boolean isJPEG(){return(this.isJPEG);}
    
    public FilePathTreeItem(Path file) throws IOException{
        super(file.toString());
        this.fullPath=file.toString();
        
        Path jpg = Paths.get(".jpg");
        //test if this is a directory and set the icon
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
            //if you want different icons for different file types this is where you'd do it
        }
    
        //set the value
        if(!fullPath.endsWith(File.separator)){
            //set the value (which is what is displayed in the tree)
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
                                //Wenn Direcotory ausklappbar machen
                                treeNode.getChildren().add(null);
                                source.getChildren().add(treeNode);
                            }
                            //Wenn .jpg anzeigen
                            else if(treeNode.isJPEG()){
                                source.getChildren().add(treeNode);
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
