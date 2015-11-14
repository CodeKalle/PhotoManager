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
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class Album implements Serializable {
    private String titel;
    private String beschreibung;
    private Date erstellungdatum;
    private String sortierkennzeichen;
    private List fotoListe;
    
    /**
     * Standart Konstruktor
     */
    private Album() {
    }

    /**
     * Konstruktor
     * @param titel Titel des Albums
     */
    public Album(String titel) {
        this.titel = titel;
        this.erstellungdatum = Date.from(Instant.EPOCH);
        this.fotoListe = new LinkedList();
    }

    /**
     * Getter fuer titel
     * @return Inhalt von titel
     */
    public String getTitel() {
        return titel;
    }

    /**
     * Setter fuer titel
     * @param titel neuer Inhalt fuer titel
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * Getter fuer beschreibung
     * @return Inhalt von beschreibung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setter fuer beschreibung
     * @param beschreibung neuer Inhalt fuer beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Getter fuer erstellungsdatum
     * @return Inhalt von erstellungsdatum
     */
    public Date getErstellungdatum() {
        return erstellungdatum;
    }

    /**
     * Getter fuer sortierkennzeichen
     * @return Inhalt von sortierkennzeichen
     */
    public String getSortierkennzeichen() {
        return sortierkennzeichen;
    }

    /**
     * Setter fuer sortierkennzeichen
     * @param sortierkennzeichen neuer Inhalt von sortierkennzeichen
     */
    public void setSortierkennzeichen(String sortierkennzeichen) {
        this.sortierkennzeichen = sortierkennzeichen;
    }

    /**
     * Getter fuer fotoListe
     * @return Liste mit Hashwerten von Bildpfaden
     */
    public List getFotoListe() {
        return fotoListe;
    }

    /**
     * Setter fuer fotoListe
     * @param fotoListe neue Liste
     */
    public void setFotoListe(List fotoListe) {
        this.fotoListe = fotoListe;
    }
    
    
}
