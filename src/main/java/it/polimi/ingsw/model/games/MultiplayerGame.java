package it.polimi.ingsw.model.games;

import it.polimi.ingsw.controller.Player;

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
    public MultiplayerGame(List<Player> players){
        super();

        this.players = players;
        for(Player player : players)
            player.buildBoard(this,virtualView);
        super.virtualView.setPlayers(players);
    }

}
