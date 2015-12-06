
package model;

import java.io.Serializable;

/**
 * Ein Tag ist ein im Bild markierter Fotobereich dem eine kurze Information
 * hinzugefuegt wird.
 * Die Tags werden in den Metadaten eines Fotos gespeichert.
 * 
 * Version-History:
 * @date 14.11.2015 by Tobias: Initialisierung + Anlegen von Grundmethoden
 */
public class Tag implements Serializable {
    int xPos1;
    int yPos1;
    
    int xPos2;
    int yPos2;
    
    String information;

    /**
     * Standart Konstruktor
     */
    public Tag() {
    }
    
    /**
     * Konstruktor
     * @param xPos1 erste X-Position
     * @param yPos1 erste Y-Position
     * @param xPos2 zweite X-Position
     * @param yPos2 zweite Y-Position
     * @param information Kurzinformation zum markierten Bereich
     */
    public Tag(int xPos1, int yPos1, int xPos2, int yPos2, String information) {
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
        this.information = information;
    }

    /**
     * Getter fuer xPos1
     * @return Wert von xPos1
     */
    public int getxPos1() {
        return xPos1;
    }

    /**
     * Setter fuer xPos1
     * @param xPos1 neuer Wert fuer xPos1
     */
    public void setxPos1(int xPos1) {
        this.xPos1 = xPos1;
    }

    /**
     * Getter fuer yPos1
     * @return Wert von yPos1
     */
    public int getyPos1() {
        return yPos1;
    }

    /**
     * Setter fuer yPos1
     * @param yPos1 neuer Wert fuer yPos1
     */
    public void setyPos1(int yPos1) {
        this.yPos1 = yPos1;
    }

    /**
     * Getter fuer xPos2
     * @return Wert von xPos2
     */
    public int getxPos2() {
        return xPos2;
    }

    /**
     * Setter fuer xPos2
     * @param xPos2 neuer Wert fuer xPos2
     */
    public void setxPos2(int xPos2) {
        this.xPos2 = xPos2;
    }

    /**
     * Getter fuer yPos2
     * @return Wert von yPos2
     */
    public int getyPos2() {
        return yPos2;
    }

    /**
     * Setter fuer yPos2
     * @param yPos2 neuer Wert fuer yPos2
     */
    public void setyPos2(int yPos2) {
        this.yPos2 = yPos2;
    }

    /**
     * Getter fuer information
     * @return Inhalt von information
     */
    public String getInformation() {
        return information;
    }

    /**
     * Setter fuer information
     * @param information neuer Inhalt fuer Information
     */
    public void setInformation(String information) {
        this.information = information;
    }
    
    
}
