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
        super.gridObserver.notifyDevelopmentGridChange(super.devCardsGrid.getGridStatus());

        super.virtualView.updateBoardVirtualView();
    }

    @Override
    public void vaticanReport(int sectionIndex) {
        players.stream().forEach(p -> p.getBoard().computeActivationPopeTile(sectionIndex));
    }

    @Override
    public int addFaithLorenzo(int points) {
        return 0;
    }

    @Override
    public int drawFromTokenPile() {
        return 0;
    }
}