package it.polimi.ingsw.client.view;

public class LorenzoView {
    private int blackCrossMarker;
    private String lastDrawnActionToken;

    @Override
    public String toString() {
        return "LorenzoView{" +
                "blackCrossMarker=" + blackCrossMarker +
                ", lastDrawnActionToken='" + lastDrawnActionToken + '\'' +
                '}';
    }

    public int getBlackCrossMarker() {
        return blackCrossMarker;
    }

    public String getLastDrawnActionToken() {
        return lastDrawnActionToken;
    }
}
