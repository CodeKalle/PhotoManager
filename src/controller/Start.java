package controller;

/**
 * Start kümmert sich um die Initialisierung der Software und die Aufteilung in Threats.
 * 
 * Version-History:
 * @date 20.11.2015 by Danilo: Initialisierung
 */
public class Start {
    
    /**
     * Einsprungpunkt der Software und starten aller benötigten Komponenten.
     * 
     * Version-History:
     * @param args
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    public static void main(String[] args) {
        SystemController.run();
        GuiController.run();
    }
    
}
