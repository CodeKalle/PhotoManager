package model;

import java.io.Serializable;

/**
 * Oberste Ebene unseres Models.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class System implements Serializable {
    private FotoContainer fotos;
    private AlbenContainer alben;
    
    /**
     * Standart Konstruktor.
     */
    public System() {
        this.fotos = new FotoContainer();
        this.alben = new AlbenContainer();
    }
    
    /**
     * Diese Methode loescht alle Fotos aus dem FotoContainer.
     */
    public void alleFotosLoeschen() {
    }
    
    /**
     * Diese Methode loescht alle Alben aus dem AlbenContainer.
     */
    public void alleAlbenLoeschen() {
    }
    
    /**
     * Diese Methode speichert das gesamte System.
     */
    public void systemSpeichern() {
    }
    
    /**
     * Diese Methode laed das System aus einer bestehenden Datei.
     */
    public void systemLaden() {
    }

    /**
     * Getter fuer FotoContainer.
     * @return aktuellen FotoContainer
     */
    public FotoContainer getFotos() {
        return fotos;
    }

    /**
     * Setter fuer FotoContainer.
     * @param fotos neuer FotoContainer
     */
    public void setFotos(FotoContainer fotos) {
        this.fotos = fotos;
    }

    /**
     * Getter fuer AlbenContainer
     * @return aktuellen AlbenContainer
     */
    public AlbenContainer getAlben() {
        return alben;
    }

    /**
     * Setter fuer AlbenContainer.
     * @param alben neuer AlbenContainer
     */
    public void setAlben(AlbenContainer alben) {
        this.alben = alben;
    }
    
    
}
