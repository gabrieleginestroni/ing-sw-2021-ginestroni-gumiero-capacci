package it.polimi.ingsw.model.cards;
/**
 * @author Giacomo Gumiero
 * Generic class card
 */
public class Card {
    private int id;
    private int victoryPoints;
    //private Player owner;

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
