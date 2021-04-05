package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;

public class CardSlot {
    private final ArrayList<DevelopmentCard> devCards;

    public CardSlot() {
        this.devCards = new ArrayList<>();
    }

    public void addCard(DevelopmentCard card) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException {


        if(devCards.size()==3){
            throw new developmentCardSlotLimitExceededException();
        }

        if(devCards.size()==0){
            if(card.getLevel()==1) devCards.add(0,card);
            else throw new invalidDevelopmentCardLevelPlacementException();
        }
        else if((devCards.get(0).getLevel() + 1) != card.getLevel()) {
            throw new invalidDevelopmentCardLevelPlacementException();
        }
        this.devCards.add(0,card);

    }

    public DevelopmentCard getTopCard(){
        return this.devCards.get(0);
    }

    public int getCardNumber(int level, Color color){
        return this.devCards.stream().filter(c->c.getLevel()>=level && c.getType() == color).mapToInt(c->1).sum();
    }

}
