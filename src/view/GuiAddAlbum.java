package view;

/**
 * Die GuiAddAlbum dient dem Benutzer zum anzeigen und hinzufügen der Alben
 * 
 * Version-History:
 * @date 20.11.2015 by Danilo: Initialisierung
 */

public class GuiAddAlbum extends javax.swing.JFrame{
    
    /**
     * Konstruktor der Klasse, erzeugt eine leeres JavaFrame
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    public GuiAddAlbum() {
        initComponents();
    }
    
    /**
     * Methode setzt grundsätzliche Einstellungen des Frames.
     * 
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     */
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        
        setVisible(false);
        
        pack();
    }
}


