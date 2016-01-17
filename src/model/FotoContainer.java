package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Der FotoContainer verwaltet im PhotoManager alle Bilder, die das System
 * kennt. Die Fotos werden dabei als Key in einer Map gespeichert und im Objekt
 * zaehlt ein Integer, wie oft das Foto im System in Alben vorkommt. Der Integer
 * ist dabei der Hashwert des Fotos.
 *
 * Version-History:
 *
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 * @date 23.11.2015 by Tobias: Ändern der Fotomap.
 * @date 08.12.2015 by Danilo: Löschen nicht benötigter Methoden [setFotoMap]
 */
public class FotoContainer implements Serializable {

    private Map<Integer, Foto> fotoMap;

    /**
     * Konstruktor
     *
     * Version-History:
     *
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public FotoContainer() {
        this.fotoMap = new HashMap<>();
    }

    /**
     * Diese Methode bestimmt die Anzahl der Key - Values in der Map, und somit
     * die Anzahl der vorhandenen Fotos.
     *
     * @return Anzahl der Fotos im FotoContainer als Integer.
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public int anzahlFotos() {
        return this.fotoMap.size();
    }

    /**
     * Getter fuer fotoMap
     *
     * @return aktuelle Map mit Fotos
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public Map<Integer, Foto> getFotoMap() {
        return fotoMap;
    }
}
