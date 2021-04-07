package it.polimi.ingsw.model.games;

/**
 * Class that represents the entity of Lorenzo il Magnifico for the solo version of the game.
 * @author Tommaso Capacci
 */
public class Lorenzo{
    private final SoloGame solo;
    private int blackCross;

    public Lorenzo(SoloGame solo){
        this.solo = solo;
        blackCross = 0;
    }

    /**
     * Method that consents to add a quantity of Faith Points to the BlackCross indicator of Lorenzo.
     * @param points The number of Faith Points that are going to be added to the BlackCross indicator.
     */
    public void addFaithPoints(int points){
        if ((blackCross += points) < 24)
            blackCross += points;
        else
            blackCross = 24;

        if(blackCross >= 8 && !solo.isSection1Reported())
            solo.setSection1Reported();
        else if(blackCross >= 16 && !solo.isSection2Reported())
                solo.setSection2Reported();
            else if(blackCross == 24 && !solo.isSection3Reported()) {
                    solo.setSection3Reported();
                    solo.gameIsOver();
                 }
    }
}
