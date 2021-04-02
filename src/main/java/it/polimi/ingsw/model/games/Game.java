package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Tommaso Capacci
 * Class that represents the concept of th Game and the basic data structures that the Mupltiplayer version and the Solo version of the Game have in common.
 */
public abstract class Game {

    String gameId;
    Date gameDate;
    Market market;
    LeaderCard[] leaderCards;
    DevelopmentCard[] devCards;
    DevelopmentCardGrid devCardsGrid;
    boolean gameOver;

    public void initializeGame(){} //PROBABILMENTE ABSTRACT

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
}
