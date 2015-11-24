package model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Objects;

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
 * @date 23.11.2015 by Tobias: Anlegen der Hash-Funktionen, hinzufügen des Counters,
 * @date 24.11.2015 by Danilo: Ändern des pfad in den Datentyp Path
 */
public class Foto implements Serializable {
    private String name;
    private Path pfad;
    private int groesse;
    private Metadaten metadata;
    private int counter;

    /**
     * Standart Konstruktor
     */
    public Foto() {
    }
    
    /**
     * Konstruktor mit zwei Werten
     * @param name Name des Bildes (letzter Teil des Pfades)
     * @param pfad Kompetter Pfad
     * Version-History:
     * @date 24.11.2015 by Danilo: Ändern des pfad in den Datentyp Path
     */
    public Foto(String name, Path pfad) {
        this.name = name;
        this.pfad = pfad;
        counter = 1;
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
     * Version-History:
     * @return aktueller Inhalt von pfad
     * @date 24.11.2015 by Danilo: Ändern des pfad in den Datentyp Path
     */
    public Path getPfad() {
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

    /**
     * Getter fuer counter
     * @return Wert von counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Setter fuer counter
     * @param counter Wert um den counter geändert werden soll
     */
    public void setCounter(int counter) {
        this.counter += counter;
    }

    
    /**
     * Generiert einen Hashwert für ein Foto aus den Attributen Pfad und Größe.
     * @return HashCode von einem Foto
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.pfad);
        hash = 97 * hash + this.groesse;
        return hash;
    }

    /**
     * Vergleicht den Hashwert des Fotos mit dem eines anderen, um zu prüfen, ob es sich
     * um das gleiche Foto handelt.
     * @param obj Foto, bei dem geprüft werden soll, ob es gleich ist.
     * @return true wenn gleich, false wenn nicht
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Foto other = (Foto) obj;
        if (!Objects.equals(this.pfad, other.pfad)) {
            return false;
        }
        if (this.groesse != other.groesse) {
            return false;
        }
        return true;
    }
    
    
    
}
