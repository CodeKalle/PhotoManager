/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javax.swing.Action;

/**
 * FXML Controller class
 *
 * @author targhed
 */
public class GuiController implements Initializable {
    
    public Main main;
    
    @FXML                                                               // GuiSelectPresentation
    private ComboBox<String> guiSelectPresentationComboBox;
    
    /*
    @FXML
    private void handleComboBoxAction(Action event){
        
    } 
    */
    
    @FXML
    private Button ButtonAnlegen,mainButtonStart,ButtonEinpflegen;
    @FXML
    private Button ButtonZurueckHauptmenue;

    @FXML
    private void handleButtonActionMain(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        if(event.getSource()==ButtonAnlegen){
            stage=(Stage) ButtonAnlegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiSelectPresentation.fxml"));
        }
        else if(event.getSource()==mainButtonStart) {                       
            stage=(Stage) mainButtonStart.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAlbumOverview.fxml"));
        }
        else if(event.getSource()==ButtonZurueckHauptmenue) {
            stage=(Stage) ButtonZurueckHauptmenue.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
        }
        else {
            stage=(Stage) ButtonEinpflegen.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GuiAddFoto.fxml"));
            
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setMain(Main main){             // Verbindung zur Mainclass
        this.main = main;
        

    }
    /** 
     * Initializes the controller class.
     */
    @Override   
    public void initialize(URL url, ResourceBundle rb) {
        
      //  Image img = new Image("src/dummy1.jpg");
      //  ImageView img1 = new ImageView(img);
        
            
    }
    

    
}
