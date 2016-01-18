package controller;

import view.Main;

/**
 * Start kümmert sich um die Initialisierung der Software und die Aufteilung in
 * Threats.
 *
 * Version-History:
 *
 * @date 20.11.2015 by Danilo: Initialisierung
 * @date 23.11.2015 by Danilo: Kommentare ergänzt
 * @date 30.11.2015 by Danilo: Anpassung an aktuellen Stand der GUI
 */
public class Start {

    /**
     * Einsprungpunkt der Software und starten aller benötigten Komponenten.
     *
     * @param args Paramtere die zum Programmstart an die Anwendung zur
     * Auswertung gegeben werden
     *
     * Version-History:
     * @date 20.11.2015 by Danilo: Initialisierung
     * @date 23.11.2015 by Danilo: Kommentar angepasst
     * @date 30.11.2015 by Danilo: Anpassung an aktuellen Stand der GUI
     */
    public static void main(String[] args) {
        SystemController.run();
        javafx.application.Application.launch(Main.class);
    }

}
