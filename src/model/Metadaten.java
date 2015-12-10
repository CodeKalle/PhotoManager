package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Ein Objekt von Metadaten enthält alle vom Benutzer angelegten Daten, die 
 * der Benutzer einem Bild anfuegen will.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class Metadaten implements Serializable {
    Map<String, Object> daten;

    /**
     * Konstruktor
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public Metadaten() {
        this.daten = new HashMap<>();
        this.mapInitialisieren();
    }
    
    /**
     * Fuellt die Map mit den Initialwerten. 
     * Keys sind die 16 im DataDictionary bestimmten Werte.
     * Value ist immer null.
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    private void mapInitialisieren() {
        this.daten.put("Kurztitel", null);
        this.daten.put("Filterbegriff1", null);
        this.daten.put("Filterbegriff2", null);
        this.daten.put("Filterbegriff3", null);
        this.daten.put("Filterbegriff4", null);
        this.daten.put("Filterbegriff5", null);
        this.daten.put("Wichtigkeit", null);
        this.daten.put("Qualitaet", null);
        this.daten.put("Attraktivitaet", null);
        this.daten.put("Textbeschreibung", null);
        this.daten.put("Tag1", null);
        this.daten.put("Tag2", null);
        this.daten.put("Tag3", null);
        this.daten.put("Tag4", null);
        this.daten.put("Tag5", null);
        this.daten.put("Anzeigeformat", null);
    }
    
    /**
     * Diese Methode prueft ob schon ein Value bei einem bestimmten Key existiert.
     * 
     * @param key der zu ueberpruefende Key
     * @return true wenn Leer, sonst false
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public boolean istLeer(String key) {
        return false;
    }

    /**
     * Getter fuer daten
     * 
     * @return aktuelle Map
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public Map<String, Object> getDaten() {
        return daten;
    }

    /**
     * Setter fuer daten
     * 
     * @param Daten Map mit Werten
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setDaten(Map<String, Object> Daten) {
        this.daten = Daten;
    }
    
    /**
     * Setzt einen Wert in der Map daten
     * 
     * @param key Wert der veraendert werden soll
     * @param value Wert der gespeichert werden soll
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void setzeWert(String key, Object value) {
        this.daten.put(key, value);
    }
    
    /**
     * Loescht einen Wert in der Map daten, indem der Value auf null gesetzt wird
     * 
     * @param key Wert der geloescht werden soll
     * 
     * Version-History:
     * @date 14.11.2015 by Tobias: Initialisierung
     */
    public void loescheWert(String key) {
        this.daten.put(key, null);
    }
}
