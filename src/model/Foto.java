package model;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Ein Foto im FotoManager besteht aus vier Komponenten. Da der PhotoManager die
 * Bilder auf dem System nicht ändern soll, wird nur der absolute Pfad des
 * Bildes im Foto gespeichert. Des weiteren werden Name und Groesse des Bildes
 * gespeichert. Jedem Foto koennen eigene Metadaten hinzugefuegt werden, die in
 * einem Objekt vom Typ Metadaten abgelegt werden.
 *
 * Name, Pfad und Groesse sind lesbar, aber nicht aenderbar.
 *
 * Des weiteren enthält das Foto Methoden für Hashwerte, damit es als Key in
 * einer HashMap verwendet werden kann.
 *
 * Version-History:
 *
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 * @date 23.11.2015 by Tobias: Anlegen der Hash-Funktionen, hinzufügen des
 * Counters,
 * @date 24.11.2015 by Danilo: Ändern des pfad in den Datentyp Path
 * @date 25.11.2015 by Danilo: Erstellen einer Methode zum setzen der Größe und
 * nutzen im Konstruktor
 * @date 06.12.2015 by Danilo: Ändern des Datentyp pfad zu String da Path nicht
 * serialisierbar
 * @date 07.12.2015 by Danilo: Erstellungsdatum
 * @date 08.12.2015 by Danilo: Löschen nicht benötigter Methoden [getGroesse]
 * (Größe nur für Hashwert)
 * @date 09.12.2015 by Danilo: Equalsupdate
 */
public class Foto implements Serializable {

    private String name;
    private String pfad;
    private int groesse;                        //in Byte
    private Metadaten metadata;
    private int counter;
    private long erstellungdatum;

    /**
     * Konstruktor
     *
     * @param name Name des Bildes (letzter Teil des Pfades)
     * @param pfad Kompetter Pfad
     *
     * Version-History:
     * @date 24.11.2015 by Danilo: Ändern des pfad in den Datentyp Path
     * @date 06.12.2015 by Danilo: Änderung am Counter da 0 anzeigt das dieses
     * neu erstellt wurde und ändern des Datentyp pfad zu String da Path nicht
     * serialisierbar
     * @date 07.12.2015 by Danilo: Erstellungsdatum
     */
    public Foto(String name, String pfad) {
        this.name = name;
        this.pfad = pfad;
        counter = 0;
        generateFotosize(Paths.get(pfad));
        setErstellungdatum();
    }

    /**
     * Diese Methode setzt die Größe des Fotos auf die der Bilddatei (als
     * Integer)
     *
     * @param pathOfFoto Bilddateipfad eines Fotos
     *
     * Version-History:
     * @date 25.11.2015 by Danilo: Initialisierung
     */
    private void generateFotosize(Path fotoPath) {
        File file = new File(fotoPath.toString());
        long filesize = file.length();

        int reduced = (int) Math.max(Math.min(Integer.MAX_VALUE, filesize), Integer.MIN_VALUE);

        this.groesse = reduced;
    }

    /**
     * Getter fuer name;
     *
     * @return aktueller Inhalt von name
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public String getName() {
        return name;
    }

    /**
     * Getter fuer pfad
     *
     * @return aktueller Inhalt von pfad
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 24.11.2015 by Danilo: Ändern des pfad in den Datentyp Path
     */
    public Path getPfad() {
        return Paths.get(pfad);
    }

    /**
     * Getter fuer metadata
     *
     * @return Objekt vom Typ Metadaten
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public Metadaten getMetadata() {
        return metadata;
    }

    /**
     * Setter fuer metadata
     *
     * @param metadata Objekt vom Typ Metadaten
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setMetadata(Metadaten metadata) {
        this.metadata = metadata;
    }

    /**
     * Getter fuer counter
     *
     * @return Wert von counter
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Setter fuer counter
     *
     * @param counter Wert um den counter geändert werden soll
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setCounter(int counter) {
        this.counter += counter;
    }

    /**
     * Getter fuer erstellungsdatum
     *
     * @return Erstellungsdatum als long
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 07.12.2015 by Danilo: Erstellungsdatum
     */
    public long getErstellungdatum() {
        return erstellungdatum;
    }

    /**
     * Setter fuer erstellungsdatum
     *
     * Version-History:
     *
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 07.12.2015 by Danilo: Erstellungsdatum
     */
    private void setErstellungdatum() {
        File file = new File(pfad);
        erstellungdatum = file.lastModified();
    }

    /**
     * Generiert einen Hashwert für ein Foto aus den Attributen Pfad und Größe.
     *
     * @return HashCode von einem Foto
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.pfad);
        hash = 97 * hash + this.groesse;
        return hash;
    }

    /**
     * Vergleicht den Hashwert des Fotos mit dem eines anderen, um zu prüfen, ob
     * es sich um das gleiche Foto handelt.
     *
     * @param obj Foto, bei dem geprüft werden soll, ob es gleich ist.
     * @return true wenn gleich, false wenn nicht
     *
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 09.12.2015 by Danilo: Verbesserung des Vergleiches, Kommentierung
     */
    @Override
    public boolean equals(Object obj) {
        // Vergleich auf null sofortiger Abbruch
        if (obj == null) {
            return false;
        }

        // Prüft ob Object selbst ist
        if (obj == this) {
            return true;
        }

        // Prüft ob Objekt vergleichbar ist
        if (getClass() != obj.getClass()) {
            return false;
        }

        // Übergebenes Object darf nun nicht verändert werden
        final Foto other = (Foto) obj;

        // Prüfen der Hashcodes
        return this.hashCode() == other.hashCode();
    }
}
