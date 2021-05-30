package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exceptions.developmentCardSlotLimitExceededException;
import it.polimi.ingsw.server.exceptions.invalidDevelopmentCardLevelPlacementException;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;

import java.util.ArrayList;

/**
 * @author Gabriele Ginestroni
 * Class that represents a player's board's Development Card pile. The card slot can contain upto three development cards.
 */
public class CardSlot {
    private final ArrayList<DevelopmentCard> devCards;

    /**
     * Constructor of an empty card slot
     */
    public CardSlot() {
        this.devCards = new ArrayList<>();
    }

    /**
     * Places a development card on top of the pile if the card's level is 1-level higher than the actual top card of
     * pile.
     * @param card Development card to add
     * @throws developmentCardSlotLimitExceededException In case of placement of the fourth card
     * @throws invalidDevelopmentCardLevelPlacementException In case of placement that does not meet the level requirement
     *
     *
     */
    public void addCard(DevelopmentCard card) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException {


        if(devCards.size()==3){
            throw new developmentCardSlotLimitExceededException();
        }

        if(devCards.size()==0){
            if(card.getLevel()!=1) throw new invalidDevelopmentCardLevelPlacementException();

        }
        else if((devCards.get(0).getLevel() + 1) != card.getLevel()) {
            throw new invalidDevelopmentCardLevelPlacementException();
        }
        this.devCards.add(0,card);

    }

    /**
     * Gets the top card of the pile
     * @return Pile's top development card, Null is card slot is empty
     */
    public DevelopmentCard getTopCard(){
        if(this.devCards.isEmpty())
            return null;
        return this.devCards.get(0);
    }

    /**
     * Computes the pile's total number of Development Card that meet the parameters criteria
     * @param level Level of Development Card
     * @param color Color of Development Card
     * @return Pile's total number of Development Card that meet the parameters criteria
     */
    public int getCardNumber(int level, Color color){
        return this.devCards.stream().filter(c->c.getLevel()>=level && c.getType() == color).mapToInt(c->1).sum();
    }

    /**
     * Computes total number of victory points of the card pile
     * @return Sum of all victory points of the pile's development cards
     */
    public int getVictoryPoints(){
        return this.devCards.stream().mapToInt(DevelopmentCard::getVictoryPoints).sum();
    }

    /**
     * Computes the number of cards in the pile
     * @return  number of Development cards in the pile
     */
    public int getGenericCardNumber(){
        return devCards.size();
    }

    /**
     * Checks if a development card can be placed in the card slot
     * @param developmentCard Development card to check
     * @return True if card meets the level placement criteria, False otherwise
     */
    public boolean canAddDevCard(DevelopmentCard developmentCard){
        if(getTopCard() == null) {
            if (developmentCard.getLevel() == 1)
                return true;
        }
        else if(developmentCard.getLevel()-1 == getTopCard().getLevel())
            return true;

        return false;
    }

    //TODO
    //REMOVE
    /**
     * Returns a copy of the list of development cards placed in the card slot
     * @return Copy of the list of development cards placed in the card slot
     */
    public ArrayList<DevelopmentCard> getCardSlotDevelopmentCards(){
        return new ArrayList<>(devCards);
    }

}
