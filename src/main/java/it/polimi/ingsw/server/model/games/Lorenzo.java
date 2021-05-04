package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.virtual_view.LorenzoObserver;

/**
 * Class that represents the entity of Lorenzo il Magnifico for the solo version of the game.
 * @author Tommaso Capacci
 */
public class Lorenzo{
    private final SoloGame solo;
    private int blackCross;
    private final LorenzoObserver lorenzoObserver;

    /**
     * This constructor needs the Solo Game which this instance of Lorenzo will be linked to and the observer that will observe its status during the game.
     * @param solo The Solo Game which this instance of Lorenzo is referring to.
     * @param lorenzoObserver The LorenzoObserver that is going to be attached to this instance of Lorenzo.
     */
    public Lorenzo(SoloGame solo, LorenzoObserver lorenzoObserver){
        this.lorenzoObserver = lorenzoObserver;
        this.solo = solo;
        blackCross = 0;
    }

    /**
     * Method that consents to add a quantity of Faith Points to the BlackCross indicator of Lorenzo.
     * @param points The number of Faith Points that are going to be added to the BlackCross indicator.
     */
    public int addFaithPoints(int points){
        int tempBC = blackCross + points;

        blackCross = Math.min(tempBC, 24);

        lorenzoObserver.notifyLorenzoStatus(blackCross);

        if(blackCross >= 8 && !solo.isSection1Reported()) {
            solo.setSection1Reported();
            return 0;
        }
        else if(blackCross >= 16 && !solo.isSection2Reported()) {
                solo.setSection2Reported();
                return 1;
            }
            else if(blackCross == 24 && !solo.isSection3Reported()) {
                    solo.setSection3Reported();
                    solo.gameIsOver();
                    return 2;
                 }
                 else
                     return -1;
    }
}
