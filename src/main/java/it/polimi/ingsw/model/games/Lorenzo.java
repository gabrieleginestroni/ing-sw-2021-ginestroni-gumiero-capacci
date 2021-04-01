package it.polimi.ingsw.model.games;

/**
 * Class that represents the entity of Lorenzo il Magnifico for the solo version of the game.
 * @author Tommaso Capacci
 */
public class Lorenzo{
    private int blackCross;

    public Lorenzo(){
        blackCross = 0;
    }

    /**
     * Method that consents to add a quantity of Faith Points to the BlackCross indicator of Lorenzo.
     * @param points The number of Faith Points that are going to be added to the BlackCross indicator.
     * @throws vaticanReportActivated when the BlackCross indicator activates a Vatican Report.
     */
    public void addFaithPoints(int points) throws vaticanReportActivated{
        if ((blackCross += points) < 24)
            blackCross += points;
        else
            blackCross = 24;

        if(blackCross == 8 || blackCross == 16 || blackCross == 24){
            throw new vaticanReportActivated();
        }
    }
}
