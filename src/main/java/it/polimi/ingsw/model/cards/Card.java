package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.*;

/**
 * @author Giacomo Gumiero
 * Generic class card
 */
public class Card {
    private int id;
    private Board owner;
    private int victoryPoints;


    public Board getOwner() {
        return owner;
    }

    public void setOwner(Board owner) {
        this.owner = owner;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }


}
