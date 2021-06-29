package it.polimi.ingsw.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class used to handle the players' shift schedule inside every single round of a Multiplayer Game.
 */
public class TurnHandler {
    private final ArrayList<Player> players;
    int currentPlayer;
    private final Map<Player,Integer> disconnectedPlayers;

    /**
     * @param players The list of the players that are playing in the game controlled
     *                by the MultiplayerController which this TurnHandler belongs to.
     */
    public TurnHandler(ArrayList<Player> players) {
        this.players = players;
        this.disconnectedPlayers = new HashMap<>();
        currentPlayer = 0;
    }

    /**
     * Method used at the end of the turn of a player to pass it to the next one in the
     * round: if the previous player was the last in the round the turn passes to the first.
     */
    public void nextPlayer(){
        if(isRoundOver())
            currentPlayer = 0;
        else
            currentPlayer++;
    }

    /**
     * Method used to handle the disconnection of a player by removing him from the players list
     * and shifting 1 place back all the following players to get a playable shift schedule.
     * @param player The disconnected player.
     */
    public void notifyPlayerDisconnection(Player player){
        int index = players.indexOf(player);
        players.remove(player);
        disconnectedPlayers.put(player,index);
        if(index < currentPlayer)
            currentPlayer--;
    }

    /**
     * Method used to handle the reconnection of a player reinserting him back in the players list
     * at the index he originally occupied.
     * @param player The reconnected player.
     */
    public void notifyPlayerReconnection(Player player){
        int index = disconnectedPlayers.get(player);
        disconnectedPlayers.remove(player);
        players.add(index,player);
        if(index <= currentPlayer)
            this.nextPlayer();
    }

    /**
     * @return TRUE only if the current player is the last player in the shift schedule.
     */
    public boolean isRoundOver(){
        return currentPlayer == players.size() - 1;
    }

    /**
     * @return The current player.
     */
    public Player getCurrentPlayer(){
        if (currentPlayer == players.size())
            currentPlayer = 0;
        return players.get(currentPlayer);
    }

    /**
     * @return The list that contains all other players than the current.
     */
    public List<Player> getOtherPlayers(){
        return this.players.stream().filter(p -> p != getCurrentPlayer()).collect(Collectors.toList());
    }

    /**
     * @return The number of players actually connected: if someone disconnected during the game this number won't coincide
     * with the number of players the lobby was made for.
     */
    public int getConnectedPlayersNumber(){
        return this.players.size();
    }

    /**
     * @return The list of players actually connected with the lobby.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
