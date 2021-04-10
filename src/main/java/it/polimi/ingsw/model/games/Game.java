package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/**
 * @author Tommaso Capacci
 * Class that represents the concept of th Game and the basic data structures that the Mupltiplayer version and the Solo version of the Game have in common.
 */
public abstract class Game {

    String gameId;
    Date gameDate;
    Market market;
    List<LeaderCard> leaderCards;
    DevelopmentCard[] devCards;
    DevelopmentCardGrid devCardsGrid;
    boolean gameOver;
    boolean section1Reported;
    boolean section2Reported;
    boolean section3Reported;

    public Game(){

        gameId = "test id";
        gameDate = new Date();

        market = new Market();

        LeaderCard[] tempArray;
        Gson gson = new Gson();
        try{

            //Reading LeaderCards
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
            tempArray = gson.fromJson(reader, LeaderCard[].class);
            leaderCards = Arrays.asList(tempArray);

            //Reading DevelopmentCards
            reader = Files.newBufferedReader(Paths.get("src/main/resources/DevelopmentCards.json"));
            devCards = gson.fromJson(reader, DevelopmentCard[].class);

            reader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }

        Collections.shuffle(leaderCards);

        devCardsGrid = new DevelopmentCardGrid(devCards);

        gameOver = false;

        section1Reported = false;
        section2Reported = false;
        section3Reported = false;

    }

    /**
     * Method used in the initialization phase of the game to get 4 random Leader Cards.
     * @return The list that contains 4 Leader Cards
     */
    public List<LeaderCard> get4LeaderCards(){
        List<LeaderCard> temp = new ArrayList<>();
        for (int i = 0; i<4; i++)
            if(!leaderCards.isEmpty())
                temp.add(leaderCards.remove(leaderCards.size() - 1));
        return temp;
    }

    /**
     * Method that propagates the request to buy the last card from a certain slot of the Development Card grid.
     * @param row The row of the grid which the slot belongs to.
     * @param col The column of the grid which the slot belongs to.
     * @return The last card of the selected slot.
     * @throws emptyDevCardGridSlotSelected Thrown when the selected slot is already empty.
     */
    public DevelopmentCard getCardFromGrid(int row, int col) throws emptyDevCardGridSlotSelected {

        return devCardsGrid.getCard(row, col);

    }

    /**
     * Method that propagates the request to delete the last card from a certain slot of the Development Card grid.
     * @param row The row of the grid which the slot belongs to.
     * @param col The column of the grid which the slot belongs to.
     * @throws emptyDevCardGridSlotSelected Thrown when the selected slot is already empty.
     */
    public void removeCardFromGrid(int row, int col) throws emptyDevCardGridSlotSelected {

        devCardsGrid.removeCard(row, col);

    }

    /**
     * Method that propagates the request for doing a horizontal move on a certain row in the Market.
     * @param row The row the player wants to move.
     * @return A Map containing the quantity of resources (and also "FAITH" for Faith Points and "WHITE" for the White Marbles) gained from the move in the Market.
     */
    public Map<Resource, Integer> doHorizontalMoveMarket(int row){

        return market.doHorizontalMove(row);

    }

    /**
     * Method that propagates the request for doing a vertical move on a certain column in the Market.
     * @param col The column the player wants to move.
     * @return A Map containing the quantity of resources (and also "WHITE" for the White Marbles) gained from the move in the Market.
     */
    public Map<Resource, Integer> doVerticalMoveMarket(int col){

        return market.doVerticalMove(col);

    }

    /**
     * Sets game status to "TRUE".
     */
    public void gameIsOver(){

        this.gameOver = true;

    }

    /**
     * Game status getter.
     * @return Game status.
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Sets the first Pope Space of the Faith Track as reported.
     */
    public void setSection1Reported() {
        this.section1Reported = true;
    }

    /**
     * Sets the intermediate Pope Space of the Faith Track as reported.
     */
    public void setSection2Reported() {
        this.section2Reported = true;
    }

    /**
     * Sets the last Pope Space of the Faith Track as reported.
     */
    public void setSection3Reported() {
        this.section3Reported = true;
    }

    /**
     * Gets the report status of the first Pope Space of the Faith Track.
     * @return "TRUE" only if the first Pope Space has been reported.
     */
    public boolean isSection1Reported() {
        return section1Reported;
    }

    /**
     * Gets the report status of the intermediate Pope Space of the Faith Track.
     * @return "TRUE" only if the intermediate Pope Space has been reported.
     */
    public boolean isSection2Reported() {
        return section2Reported;
    }

    /**
     * Gets the report status of the last Pope Space of the Faith Track.
     * @return "TRUE" only if the last Pope Space has been reported.
     */
    public boolean isSection3Reported() {
        return section3Reported;
    }
}
