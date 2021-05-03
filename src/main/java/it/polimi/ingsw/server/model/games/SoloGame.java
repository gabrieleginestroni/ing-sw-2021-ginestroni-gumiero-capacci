package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.exceptions.emptyDevCardGridSlotSelectedException;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.virtualview.LorenzoObserver;
import it.polimi.ingsw.server.virtualview.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SoloGame extends Game{
    private final Lorenzo lorenzo;
    private final Player player;
    private final ActionTokensPile actionTokensPile;
    private final LorenzoObserver lorenzoObserver;


    /**
     * This constructor requires the player which is playing this Solo Game.
     * @param player The player that created the game.
     */
    public SoloGame(Player player, VirtualView vv){
        super(vv);

        lorenzoObserver = new LorenzoObserver(super.virtualView);
        lorenzo = new Lorenzo(this,lorenzoObserver);

        this.player = player;
        this.player.buildBoard(this,super.virtualView);


        actionTokensPile = new ActionTokensPile();
        super.virtualView.setLorenzoObserver(lorenzoObserver);
        List<Player> players = new ArrayList<>();
        players.add(player);
        super.virtualView.setPlayers(players);

        super.marketObserver.notifyMarketChange(market.getColorLayout(), market.getFreeMarble().getColor());

        //TODO
        //UPDATE ALL OBSERVERS
        super.virtualView.updateMarketVirtualView();
    }

    /**
     * Method that adds Faith Points to the Black Cross indicator.
     * @param points The amount of points that must be added to the Black Cross indicator.
     * @return -1 if the addition of the specified amount of Faith Points to Lorenzo does not activate any Vatican Report, 0 if it activates the first Vatican Report, 1 for the second and 2 for the last one.
     */
    public int addFaithLorenzo(int points){
        return lorenzo.addFaithPoints(points);
    }

    /**
     * Method that draws and applies the effect of the next Action Token from the pile used in the specific Game.
     * @return -1 if the drawn Action Token does not activate any Vatican Report, 0 if it activates the first Vatican Report, 1 for the second and 2 for the last one.
     */
    public int drawFromTokenPile(){
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

    /**
     * This method propagates the notification of the last drawn Action Token from the pile to the instance of LorenzoObserver valid for this Solo Game.
     * @param actionTokenId The ID of the last drawn Action Token.
     */
    public void notifyDrawnActionToken(String actionTokenId){
        lorenzoObserver.notifyLastDrawnActionToken(actionTokenId);
    }

    public LorenzoObserver getLorenzoObserver(){return lorenzoObserver;}
}