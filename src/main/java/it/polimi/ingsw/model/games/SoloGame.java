package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;

public class SoloGame extends Game{
    //private Lorenzo lorenzo;
    //private Player player;
    //private ActionTokensPile actionTokensPile;

    public void discard2Cards(Color color) throws gameEndsException{
        this.devCardsGrid.discard2Cards(color);
    }
}
