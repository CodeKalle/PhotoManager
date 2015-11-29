package controller;

import view.GuiMain;

/**
 * Der GUIController hält sämtliche Oberflächen.
 * 
 * Version-History:
 * @date 20.11.2015 by Danilo: Initialisierung
 * @date 23.11.2015 by Danilo: Kommentare ergänzt
 * @date 29.11.2015 by Danilo: Anpassung an aktuellen Stand der GUI
 */
public class GuiController{
    
    /**
     * Klassenvariablen
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 29.11.2015 by Danilo: Anpassung an aktuellen Stand der GUI
     */
    // GUI die als erstes geladen werden muss
    private static GuiMain guiMain;
    
    /**
     * Methode realisiert die Generierung der einzelnen GUIs in Threads.
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 29.11.2015 by Danilo: Anpassung an aktuellen Stand der GUI
     */
    public static void run() {    
        guiMain = new GuiMain();
    }
}
