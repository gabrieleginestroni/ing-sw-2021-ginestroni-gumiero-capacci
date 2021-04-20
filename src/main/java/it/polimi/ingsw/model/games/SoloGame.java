package it.polimi.ingsw.model.games;

import it.polimi.ingsw.controller.Player;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.virtualview.LorenzoObserver;

import java.util.Arrays;
import java.util.Optional;

public class SoloGame extends Game{
    private final Lorenzo lorenzo;
    private final Player player;
    private final ActionTokensPile actionTokensPile;
    private final LorenzoObserver lorenzoObserver;

    public SoloGame(Player player){

        super();

        this.lorenzoObserver = new LorenzoObserver();
        lorenzo = new Lorenzo(this,lorenzoObserver);

        this.player = player;
        this.player.buildBoard(this);

        actionTokensPile = new ActionTokensPile();

    }

    /**
     * Method that adds Faith Points to the Black Cross indicator.
     * @param points The amount of points that must be added to the Black Cross indicator.
     */
    public void addFaithLorenzo(int points){
        lorenzo.addFaithPoints(points);
    }

    /**
     * Method that draws and applies the effect of the next Action Token from the pile used in the specific Game.
     */
    public String drawFromTokenPile(){
        return actionTokensPile.drawPile(this);
    }

    /**
     * Method that shuffles the Action Token pile used in the specific Game.
     */
    public void shuffleTokenPile(){
        actionTokensPile.shufflePile();
    }

    /**
     * Method that propagates the request to discard 2 specific cards of a certain color from the Development Card grid.
     * @param color The color of the cards that have to be discarded.
     */
    public void discard2Cards(Color color){
        this.devCardsGrid.discard2Cards(color);
        if(devCardsGrid.thereAreNotRemainingCards(color))
            gameIsOver();
    }

    @Override
    public void removeCardFromGrid(int row, int col) throws emptyDevCardGridSlotSelectedException {
        super.removeCardFromGrid(row, col);

        Optional<Color> c = Arrays.stream(Color.values()).filter(s -> s.getColumn()==col).findFirst();

        if(c.isPresent() && devCardsGrid.thereAreNotRemainingCards(c.get()))
            gameIsOver();
    }
}
