package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.*;

/**
 * @author Giacomo Gumiero
 * Generic class card
 */
public class Card {
    private int id;
    private Player owner;
    private int victoryPoints;


    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", victoryPoints=" + victoryPoints +
                '}';
    }
}
