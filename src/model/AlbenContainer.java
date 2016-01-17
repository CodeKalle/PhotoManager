package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Der AlbenContainer verwaltet im System alle Alben. Jedes Album ist eindeutig
 * bestimmt über seinen Titel.
 *
 * Version-History:
 *
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 * @date 08.12.2015 by Danilo: Löschen nicht verwendeter Methoden
 * [setAlbenListe]
 */
public class AlbenContainer implements Serializable {

    private List<Album> albenListe;

    /**
     * Konstruktor
     *
     * Version-History:
     *
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public AlbenContainer() {
        this.albenListe = new LinkedList<>();
    }

    /**
     * Diese Methode gibt die Anzahl der Alben in der Liste zurück
     *
     * @return Anzahl von Alben
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public int anzahlAlben() {
        return this.albenListe.size();
    }

    /**
     * Getter fuer albenListe
     *
     * @return Liste von Alben
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public List<Album> getAlbenListe() {
        return albenListe;
    }

    /**
     * Diese Methode fügt dem Container ein Album hinzu
     *
     * @param album
     *
     * Version-History:
     * @date 21.11.2015 by Danilo: Initialisierung
     */
    public void add(Album album) {
        this.albenListe.add(album);
    }

    /**
     * Diese Methode löscht aus dem Container ein Album.
     *
     * @param album
     *
     * Version-History:
     * @date 21.11.2015 by Danilo: Initialisierung
     */
    public void delete(Album album) {
        this.albenListe.remove(album);
    }
}
