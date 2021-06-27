package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.ControllerFactory;
import it.polimi.ingsw.server.controller.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents a game currently hosted in the server. It is identified by a game id and contains references to
 * the controller and to all the players of the match, making it possible to handle clients disconnessions.
 */
public class Lobby {
    private final String gameID;
    private final List<Player> players;
    private final List<Player> disconnectedPlayers;
    private int size;
    private Controller controller;
    private boolean gameStarted;

    /**
     * Creates and empty lobby
     * @param gameID Id of the game which the lobby is associated with
     */
    public Lobby(String gameID) {
        this.players = new ArrayList<>();
        this.disconnectedPlayers = new ArrayList<>();
        this.size = 0;
        this.gameID = gameID;
        this.controller = null;
        this.gameStarted = false;
    }

    /**
     * Notifies a client disconnection to the game's controller and sets the player as disconnected, making it possible
     * to handle a future player reconnection
     * @param clientHandler Client handler of the player involved in the disconnection
     */
    public void notifyClientDisconnection(ClientHandler clientHandler){
        Optional<Player> disconnectedPlayer = players.stream().filter(p -> p.getClientHandler().equals(clientHandler)).findFirst();
        disconnectedPlayer.ifPresent(this::disconnectPlayer);
    }

    /**
     * @see #notifyClientDisconnection(ClientHandler)
     * @param player Disconnected player
     */
    private void disconnectPlayer(Player player){
        players.remove(player);
        disconnectedPlayers.add(player);
        controller.notifyPlayerDisconnection(player);
    }

    /**
     * Checks if a nickname belongs to any player currently disconnected from the game
     * @param nickname Nickname to check
     * @return True if the nickname belongs to any player currently disconnected from the game, false otherwise
     */
    public boolean isPlayerDisconnected(String nickname){
        return disconnectedPlayers.stream().anyMatch(p -> p.getNickname().equals(nickname));
    }

    /**
     * Reconnects a client to the match, notifying the controller
     * @param nickname Nickname of the reconnected client
     * @param newClientHandler Client handler of the reconnected player
     */
    public void reconnectClient(String nickname, ClientHandler newClientHandler){
        Optional<Player> reconnectedPlayer = disconnectedPlayers.stream().filter(p -> p.getNickname().equals(nickname)).findFirst();
        reconnectedPlayer.ifPresent(p -> {
            p.refreshClientHandler(newClientHandler);
            controller.notifyPlayerReconnection(p);
            players.add(p);
            disconnectedPlayers.remove(p);
        });
    }

    /**
     *
     * @return List of the currently connected players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @return Size of the game
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @return The game's controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     *
     * @return Game id of the match
     */
    public String getGameID() {
        return gameID;
    }

    /**
     *
     * @return True if the lobby already reached his full players capacity
     */
    public synchronized boolean isFull(){
        return size == players.size();
    }

    /**
     * Adds a client to the lobby
     * @param nickname Nickname of the client
     * @param clientHandler Client handler of the client
     */
    public synchronized void addPlayer(String nickname, ClientHandler clientHandler){
        this.players.add(new Player(nickname,clientHandler));
    }

    /**
     * Starts the match
     */
    public void startGame(){

        controller = ControllerFactory.getController(players,gameID);
        gameStarted = true;

    }

    /**
     * Sets the lobby's players capacity
     * @param size Size of the lobby
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Checks if a nickname is unique in the lobby. This method is not case sensitive
     * @param nickname Nickname to check
     * @return True if the nickname is already used in the lobby
     */
    public boolean isNicknameUsed(String nickname){
        return players.stream().anyMatch(p -> p.getNickname().equalsIgnoreCase(nickname));
    }

    /**
     *
     * @return True if the game associated to the lobby is started
     */
    public boolean isGameStarted() {
        return gameStarted;
    }
}


