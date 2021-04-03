package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.List;

/**
 * @author Tommaso Capacci
 * Class that represents one of the decks that compose the grid in which Development Cards are organized. This class simply encapsulates the collection that implements the set of cards that belong to that specific slot.
 */
public class GridSlot {

    private List<DevelopmentCard> cards;

    /**
     * Method that adds the specified card to the slot.
     * @param devCard The card that has to be added to the collection.
     */
    public void add(DevelopmentCard devCard){
        cards.add(devCard);
    }

    /**
     * Method that consents to check if the specified slot is empty.
     * @return True only when the collection is empty.
     */
    public boolean isEmpty(){
        return cards.isEmpty();
    }

    /**
     * Method that consents to get the last card of that specific slot
     * @return the last card of the slot.
     */
    public DevelopmentCard getLast(){
        return cards.get(cards.size() - 1);
    }

    /**
     * Method that consents to get the number of the cards actually contained in the slot.
     * @return the number of the cards inside the slot.
     */
    public int size(){
        return cards.size();
    }

    /**
     * Method that consents to remove the last of the cards contained in the slot.
     * @return the removed card.
     */
    public DevelopmentCard removeLast(){
        return cards.remove(cards.size() - 1);
    }

    /**
     * Method that deletes all of the cards actually contained in the slot.
     */
    public void clear(){
        cards.clear();
    }

}
