package it.polimi.ingsw.client.view;

/**
* @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
* Class that contains Lorenzo il Magnifico info
*/
public class LorenzoView {
    private int blackCrossMarker;
    private String lastDrawnActionToken;

    /**
     * @return Lorenzo il Magnifico faith points
     */
    public int getBlackCrossMarker() {
        return blackCrossMarker;
    }

    /**
     * @return Lorenzo il Magnifico last action
     */
    public String getLastDrawnActionToken() {
        return lastDrawnActionToken;
    }

    /**
     * @return Lorenzo il Magnifico points and last action infos
     */
    @Override
    public String toString() {
        return "LorenzoView{" +
                "blackCrossMarker=" + blackCrossMarker +
                ", lastDrawnActionToken='" + lastDrawnActionToken + '\'' +
                '}';
    }
}
