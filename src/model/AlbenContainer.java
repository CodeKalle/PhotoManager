package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Der AlbenContainer verwaltet im System alle Alben.
 * Jedes Album ist eindeutig bestimmt über seinen Titel.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class AlbenContainer implements Serializable {
    private List<Album> albenListe;
    
    /**
     * Standart Konstruktor
     */
    public AlbenContainer() {
        this.albenListe = new LinkedList<>();
    }
    
    /**
     * Diese Methode gibt die Anzahl der Alben in der Liste zurück.
     * @return Anzahl von Alben
     */
    public int anzahlAlben() {
        return this.albenListe.size();
    }

    /**
     * Getter fuer albenListe.
     * @return Liste von Alben
     */
    public List<Album> getAlbenListe() {
        return albenListe;
    }

    /**
     * Setter fuer albenListe
     * @param albenListe Liste mit Alben
     */
    public void setAlbenListe(List<Album> albenListe) {
        this.albenListe = albenListe;
    }
}
