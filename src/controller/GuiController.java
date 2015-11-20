package controller;

import view.GuiMain;

import view.GuiAddAlbum;
import view.GuiAddFoto;
import view.GuiAlbumDetails;
import view.GuiFotoDetail;
import view.GuiFotoEdit;
import view.GuiPlayPresentation;
import view.GuiSelectAlbum;
import view.GuiSelectPresentation;

/**
 * Der GUIController hält sämtliche Oberflächen.
 * 
 * Version-History:
 * @date 20.11.2015 by Danilo: Initialisierung
 */
public class GuiController{
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    // GUI die als erstes geladen werden muss
    private static GuiMain guiMain;
    // GUIs die als Threads relisiert werden
    private static GuiAddAlbum guiAddAlbum;
    private static GuiAddFoto guiAddFoto;
    private static GuiAlbumDetails guiAlbumDetails;
    private static GuiFotoDetail guiFotoDetail;
    private static GuiFotoEdit guiFotoEdit;
    private static GuiPlayPresentation guiPlayPresentation;
    private static GuiSelectAlbum guiSelectAlbum;
    private static GuiSelectPresentation guiSelectPresentation;
    
    /**
     * Methode realisiert die Generierung der einzelnen GUIs in Threads.
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    public static void run() {    
        guiMain = new GuiMain();
        
        Runnable threadGuiAddAlbum = new Runnable() {
            @Override
            public void run() {
                guiAddAlbum = new GuiAddAlbum();
            }
        };

        Runnable threadGuiAddFoto = new Runnable() {
            @Override
            public void run() {
                guiAddFoto = new GuiAddFoto();
            }
        };

        Runnable threadGuiAlbumDetails = new Runnable() {
            @Override
            public void run() {
                guiAlbumDetails = new GuiAlbumDetails();
            }
        };

        Runnable threadGuiFotoDetail = new Runnable() {
            @Override
            public void run() {
                guiFotoDetail = new GuiFotoDetail();
            }
        };

        Runnable threadGuiFotoEdit = new Runnable() {
            @Override
            public void run() {
                guiFotoEdit = new GuiFotoEdit();
            }
        };

        Runnable threadGuiPlayPresentation = new Runnable() {
            @Override
            public void run() {
                guiPlayPresentation = new GuiPlayPresentation();
            }
        };

        Runnable threadGuiSelectAlbum = new Runnable() {
            @Override
            public void run() {
                guiSelectAlbum = new GuiSelectAlbum();
            }
        };

        Runnable threadGuiSelectPresentation = new Runnable() {
            @Override
            public void run() {
                guiSelectPresentation = new GuiSelectPresentation();
            }
        };
            
        // Threads zum Ausführen markieren
        new Thread(threadGuiAddAlbum).start();
        new Thread(threadGuiAddFoto).start();
        new Thread(threadGuiAlbumDetails).start();
        new Thread(threadGuiFotoDetail).start();
        new Thread(threadGuiFotoEdit).start();
        new Thread(threadGuiPlayPresentation).start();
        new Thread(threadGuiSelectAlbum).start();
        new Thread(threadGuiSelectPresentation).start();
    }
    
    /**
     * Methode realisiert die Anzeige der GUIs für den Benutzer.
     * 
     * Version-History:
     * @param window
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    public static void setWindowVisible(int window){
        guiMain.setVisible(false);
        guiAddAlbum.setVisible(false);
        guiAddFoto.setVisible(false);
        guiAlbumDetails.setVisible(false);
        guiFotoDetail.setVisible(false);
        guiFotoEdit.setVisible(false);
        guiPlayPresentation.setVisible(false);
        guiSelectAlbum.setVisible(false);
        guiSelectPresentation.setVisible(false);
        switch (window){
            case 0:
                guiMain.setVisible(true);
                break;
            case 1:
                guiAddAlbum.setVisible(true);
                break;
            case 2:
                guiAddFoto.setVisible(true);
                break;
            case 3:
                guiAlbumDetails.setVisible(true);
                break;
            case 4:
                guiFotoDetail.setVisible(true);
                break;
            case 5:
                guiFotoEdit.setVisible(true);
                break;
            case 6:
                guiPlayPresentation.setVisible(true);
                break;
            case 7:
                guiSelectAlbum.setVisible(true);
                break;
            case 8:
                guiSelectPresentation.setVisible(true);
                break;
            default:
                System.exit(0);
                break;
        }
    }
    
}
