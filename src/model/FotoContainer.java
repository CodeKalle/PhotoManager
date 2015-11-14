package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Der FotoContainer verwaltet im PhotoManager alle Bilder, die das System kennt.
 * Die Fotos werden dabei als Key in einer Map gespeichert und im Objekt zaehlt
 * ein Integer, wie oft das Foto im System in Alben vorkommt.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class FotoContainer implements Serializable {
    private Map<Foto, Integer> fotoMap;

    /**
     * Standart Konstruktor
     */
    public FotoContainer() {
        this.fotoMap = new HashMap<>();
    }
    
    /**
     * Diese Methode bestimmt die Anzahl der Key - Values in der Map,
     * und somit die Anzahl der vorhandenen Fotos.
     * @return Anzahl der Fotos im FotoContainer als Integer.
     */
    public int anzahlFotos() {
        return this.fotoMap.size();
    }

    /**
     * Getter fuer fotoMap.
     * @return aktuelle Map mit Fotos
     */
    public Map<Foto, Integer> getFotoMap() {
        return fotoMap;
    }

    /**
     * Setter fuer fotoMap.
     * @param fotoMap neue Map
     */
    public void setFotoMap(Map<Foto, Integer> fotoMap) {
        this.fotoMap = fotoMap;
    }
    
    
}
