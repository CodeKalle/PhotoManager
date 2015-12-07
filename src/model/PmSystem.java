package model;

import java.io.Serializable;

/**
 * Oberste Ebene unseres Models.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class PmSystem implements Serializable {
    private FotoContainer fotos;
    private AlbenContainer alben;
    
    /**
     * Konstruktor
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public PmSystem() {
        this.fotos = new FotoContainer();
        this.alben = new AlbenContainer();
    }
    
    /**
     * Diese Methode loescht alle Fotos aus dem FotoContainer.
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void alleFotosLoeschen() {
        
    }
    
    /**
     * Diese Methode loescht alle Alben aus dem AlbenContainer.
     * 
     * Version-History:
     * @return Fehlercode zur Auswertung <br> 0 = Alle Albem wurden gelöscht <br> 1 = Fehler
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Methode geändert und Kommentare ergänzt
     */
    public int alleAlbenLoeschen() {
        fotos = new FotoContainer();
        alben = new AlbenContainer();
        
        if (alben.anzahlAlben()==0&&fotos.anzahlFotos()==0) return 0;
        
        return 1;
    }

    /**
     * Getter fuer FotoContainer.
     * 
     * @return aktuellen FotoContainer
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public FotoContainer getFotos() {
        return fotos;
    }

    /**
     * Setter fuer FotoContainer.
     * 
     * @param fotos neuer FotoContainer
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setFotos(FotoContainer fotos) {
        this.fotos = fotos;
    }

    /**
     * Getter fuer AlbenContainer
     * 
     * @return aktuellen AlbenContainer
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public AlbenContainer getAlben() {
        return alben;
    }

    /**
     * Setter fuer AlbenContainer
     * 
     * @param alben neuer AlbenContainer
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setAlben(AlbenContainer alben) {
        this.alben = alben;
    } 
}
