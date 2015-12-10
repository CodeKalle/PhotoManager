package model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Ein Album im PhotoManager verweist auf eine Menge von Bildern.
 * Dabei speichert das Album nur die Hashwerte von den Bildpfaden ab. Die entsprechenden
 * Informationen ueber das Bild werden im System in Fotos abgespeichert. Die
 * Fotos werden im FotoContainer verwaltet.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 * @date 25.11.2015 by Danilo: Änderung der Liste zu Diamantoperator Foto da nur Referenzen gespeichert werden
 * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
 * @date 08.12.2015 by Danilo: Löschen nicht verwendeter Methoden [setFotoListe]
 */
public class Album implements Serializable {
    private String titel;
    private String beschreibung;
    private Date erstellungdatum;
    private int sortierkennzeichen;
    private List<Foto> fotoListe;

    /**
     * Konstruktor
     * 
     * @param titel Titel des Albums
     * 
    * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
     */
    public Album(String titel) {
        this.titel = titel;
        this.erstellungdatum = Date.from(Instant.EPOCH);
        this.fotoListe = new LinkedList();
        this.sortierkennzeichen = 0;
    }
    
    /**
     * Getter fuer titel
     * 
     * @return Inhalt von titel
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public String getTitel() {
        return titel;
    }

    /**
     * Setter fuer titel
     * 
     * @param titel neuer Inhalt fuer titel
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * Getter fuer beschreibung
     * 
     * @return Inhalt von beschreibung
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setter fuer beschreibung
     * 
     * @param beschreibung neuer Inhalt fuer beschreibung
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Getter fuer erstellungsdatum
     * 
     * @return Inhalt von erstellungsdatum
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public Date getErstellungdatum() {
        return erstellungdatum;
    }

    /**
     * Getter fuer sortierkennzeichen
     * 
     * @return Inhalt von sortierkennzeichen
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
     */
    public int getSortierkennzeichen() {
        return sortierkennzeichen;
    }

    /**
     * Setter fuer sortierkennzeichen
     * 
     * @param sortierkennzeichen neuer Inhalt von sortierkennzeichen
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 07.12.2015 by Danilo: Sortierkennzeichen Datentyp zu int
     */
    public void setSortierkennzeichen(int sortierkennzeichen) {
        this.sortierkennzeichen = sortierkennzeichen;
    }

    /**
     * Getter fuer fotoListe
     * 
     * @return Liste mit Hashwerten von Bildpfaden
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     * @date 25.11.2015 by Danilo: Diamantoperator Foto da nur Referenzen gespeichert werden
     */
    public List<Foto> getFotoListe() {
        return fotoListe;
    }
}
