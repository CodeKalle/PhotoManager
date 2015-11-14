package model;

import java.io.Serializable;

/**
 * Ein Foto im FotoManager besteht aus vier Komponenten.
 * Da der PhotoManager die Bilder auf dem System nicht ändern soll, wird nur der 
 * absolute Pfad des Bildes im Foto gespeichert. Des weiteren werden Name und 
 * Groesse des Bildes gespeichert.
 * Jedem Foto koennen eigene Metadaten hinzugefuegt werden, die in einem 
 * Objekt vom Typ Metadaten abgelegt werden.
 * 
 * Name, Pfad und Groesse sind lesbar, aber nicht aenderbar.
 * 
 * Des weiteren enthält das Foto Methoden für Hashwerte, 
 * damit es als Key in einer HashMap verwendet werden kann.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class Foto implements Serializable {
    private String name;
    private String pfad;
    private int groesse;
    private Metadaten metadata;

    /**
     * Standart Konstruktor
     */
    public Foto() {
    }
    
    /**
     * Konstruktor mit zwei Werten
     * @param name Name des Bildes (letzter Teil des Pfades)
     * @param pfad Kompetter Pfad
     */
    public Foto(String name, String pfad) {
        this.name = name;
        this.pfad = pfad;
    }

    /**
     * Getter fuer name;
     * @return aktueller Inhalt von name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter fuer pfad
     * @return aktueller Inhalt von pfad
     */
    public String getPfad() {
        return pfad;
    }

    /**
     * Getter fuer groesse
     * @return aktueller Wert von groesse
     */
    public int getGroesse() {
        return groesse;
    }

    /**
     * Getter fuer metadata
     * @return Objekt vom Typ Metadaten
     */
    public Metadaten getMetadata() {
        return metadata;
    }

    /**
     * Setter fuer metadata
     * @param metadata Objekt vom Typ Metadaten
     */
    public void setMetadata(Metadaten metadata) {
        this.metadata = metadata;
    }
    
    
}
