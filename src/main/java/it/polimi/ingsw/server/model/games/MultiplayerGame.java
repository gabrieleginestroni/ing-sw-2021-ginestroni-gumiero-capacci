package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.List;

/**
 * @author Tommaso Capacci
 * Class that represents the multiplayer version of the game.
 */
public class MultiplayerGame extends Game{

    private final List<Player> players;

    /**
     * This constructor requires the list of the players that are going to play in this Multiplayer Game.
     * @param players The list of the players that will participate in this game.
     */
    public MultiplayerGame(List<Player> players, VirtualView vv){
        super(vv);

        this.players = players;
        for(Player player : players)
            player.buildBoard(this,super.virtualView);
        super.virtualView.setPlayers(players);

        super.marketObserver.notifyMarketChange(market.getColorLayout(), market.getFreeMarble().getColor());

        //TODO
        //UPDATE ALL OBSERVERS
        super.virtualView.updateMarketVirtualView();
    }

}