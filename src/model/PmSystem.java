package model;

import java.io.Serializable;

/**
 * Oberste Ebene unseres Models.
 *
 * Version-History:
 *
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 * @date 08.12.2015 by Danilo: Löschen nicht benötigter Funktionen
 * [alleFotosLoeschen, alleAlbenLoeschen, setFotos, setAlben] da Controller für
 * die Logik zuständig sind
 */
public class PmSystem implements Serializable {

    private FotoContainer fotos;
    private AlbenContainer alben;

    /**
     * Konstruktor
     *
     * Version-History:
     *
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public PmSystem() {
        this.fotos = new FotoContainer();
        this.alben = new AlbenContainer();
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
}
