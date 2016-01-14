package view;

import controller.FotoController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Diese Klasse dient der Ansicht eines Fotos
 * 
 * @author Tobias
 * 
 * Version-History:
 * @date ??.??.2015 by Juliane & Manuel: Konzept der GUIs und programmtechnische Umsetzung
 * @date 06.12.2015 by Tobias: Initialize erweitert
 * @date 10.12.2015 by Danilo: Kommentare ergänzt
 * @date 13.01.2016 by Manuel: rework
 */
public class GuiFotoDetailController implements Initializable{
    
    /**
    * KLASSENVARIABLEN
    * 
    * Version-History:
    * @date ??.11.2015 by Tobias: Initialisierung
    * @date 10.12.2015 by Danilo: Kommentare ergänzt
    */
    // Buttons der Klasse
    
    @FXML ScrollPane guiFotoDetailScrollPane;
    @FXML TilePane guiFotoDetailTilePane;
    @FXML Button guiFotoDetailAbbrechen, guiFotoDetailBearbeiten;
    @FXML ImageView guiFotoDetailImageView;
    @FXML TextFlow guiAddFotoDetailTextFlow;
    @FXML TextArea guiAddFotoDetailTextArea;
    
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
        Main.getPrimaryStage().setTitle("PhotoManager - Details");
        
    for(int i = 0; i < FotoController.getFotosFromAlbum(Main.speicher).size(); i++) {
            //Für jedes Bild Konstrukt zusammensetzen
            Pane lpane = new Pane();            
            lpane.setPrefSize(80, 100);
            
            Image image = new Image(FotoController.getFotosFromAlbum(Main.speicher).get(i).toUri().toString());
            
            ImageView imageView = new ImageView();
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(image);  
            
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    guiFotoDetailImageView.setImage(image);
                    guiAddFotoDetailTextArea.setVisible(true);
                    guiAddFotoDetailTextArea.setText("test 1234!");
                    

                }
            });
            
            
            Label name = new Label();
            name.setLayoutX(20.0);
            name.setLayoutY(80.0);
            name.setPrefHeight(20);
            name.setPrefWidth(80);
            name.setText(FotoController.getFotosFromAlbum(Main.speicher).get(i).getFileName().toString());
            
            Label pfad = new Label();
            pfad.setVisible(false);
            pfad.setText(FotoController.getFotosFromAlbum(Main.speicher).get(i).toString());

            lpane.getChildren().add(imageView); //ID 0
            lpane.getChildren().add(name);      //ID 1
            lpane.getChildren().add(pfad);      //ID 2
            
            //Fertiges Konstrukt in Pane anzeigen
            guiFotoDetailTilePane.getChildren().add(i, lpane);
        }
    }
    
}
