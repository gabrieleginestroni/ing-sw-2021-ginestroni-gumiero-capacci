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
 * Class that represents the concept and the basic data structures of a Game.
 */
public abstract class Game {

    String gameId;
    Date gameDate;
    PopeTile[] popeTiles;
    LeaderCard[] leaderCards;
    DevelopmentCard[] devCards;
    DevelopmentCardGrid devCardsGrid;
    boolean gameOver;

    //public abstract void giveMarketResource(Player p);

    //public abstract void giveProductionResource(Player p);

    public void discard2Cards(Color color){
        //this.devCardsGrid.discard2Cards(color); GESTIRE L'ECCEZIONE!
    }

    //public abstract void buyCard(int column, int row, Player p);

    public void gameIsOver(){

        this.gameOver = true;

    }
}
