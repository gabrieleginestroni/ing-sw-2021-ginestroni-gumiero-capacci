package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.states.*;

import it.polimi.ingsw.server.exceptions.addResourceLimitExceededException;
import it.polimi.ingsw.server.exceptions.duplicatedWarehouseTypeException;
import it.polimi.ingsw.server.exceptions.invalidResourceTypeException;

import it.polimi.ingsw.server.messages.client_server.Message;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.Game;
import it.polimi.ingsw.server.model.games.MultiplayerGame;

import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * The controller class of a Multiplayer Game.
 */
public class MultiplayerController extends Controller{
    private final MultiplayerGame model;
    private MultiplayerState currentState;
    private final List<Player> players;
    private final CommunicationMediator mediator;
    private final TurnHandler turnHandler;

    public static final MultiplayerState startTurnState = new StartTurnState();
    public static final MultiplayerState marketState = new MarketState();
    public static final MultiplayerState devCardSaleState = new DevCardSaleState();
    public static final MultiplayerState leaderActionState = new LeaderActionState();
    public static final MultiplayerState activateProductionState = new ActivateProductionState();
    public static final MultiplayerState middleTurnState = new MiddleTurnState();
    public static final MultiplayerState endTurnState = new EndTurnState();
    public static final MultiplayerState mainActionState = new MainActionState();
    public static final MultiplayerState endGameState = new EndGameState();
    public static final MultiplayerState resourceManagementState = new ResourceManagementState();
    public static final MultiplayerState swapState = new SwapState();
    public static final MultiplayerState whiteMarbleState = new WhiteMarbleState();

    /**
     * @param players The list of the players that are going to play in the relative Multiplayer Game.
     * @param gameID The gameID of the Multiplayer Game.
     */
    public MultiplayerController(List<Player> players,String gameID) {
        super(new VirtualView(),gameID);

        this.players = players;

        ArrayList<Player> shuffledPlayers = new ArrayList<>(players);
        Collections.shuffle(shuffledPlayers);

        turnHandler = new TurnHandler(shuffledPlayers);


        model = new MultiplayerGame(shuffledPlayers,this.virtualView);

        mediator = new CommunicationMediator();

        ArrayList<Thread> threads = new ArrayList<>();
        for(Player player : players) {
           threads.add(new Thread(()-> {
               List<LeaderCard> chosenLeaders = virtualView.propose4Leader(model.get4LeaderCards(), player);
               try{
                   for(LeaderCard leaderCard : chosenLeaders)
                       player.getBoard().addLeaderCard(leaderCard);
               }catch (NullPointerException e){
                    virtualView.gameAbort();
                    Server.lobbies.remove(this.gameID);
                    killAll(this.players);
                    Thread.currentThread().interrupt();
                }
           }));
           threads.get(threads.size() - 1).start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        shuffledPlayers.get(0).getBoard().setInkwell();

        threads.clear();

        for(int i=0; i < shuffledPlayers.size(); i++){
            if(i == 1 || i == 2) {
                Player player = shuffledPlayers.get(i);
                threads.add(new Thread(() -> {
                    Map<Resource, Integer> resMap = virtualView.proposeInitialResources(1, player);
                    try {
                        for (Map.Entry<Resource, Integer> res : resMap.entrySet())
                            player.getBoard().addWarehouseDepotResource(res.getKey(), 1, res.getValue());
                    } catch (addResourceLimitExceededException | invalidResourceTypeException | duplicatedWarehouseTypeException ignored) {
                    }catch (NullPointerException e){
                        virtualView.gameAbort();
                        Server.lobbies.remove(this.gameID);
                        killAll(this.players);
                        Thread.currentThread().interrupt();
                    }
                }));
            }

            if(i == 2 || i == 3) {
                Player player = shuffledPlayers.get(i);
                player.getBoard().giveFaithPoints(1);
            }

            if(i == 3) {
                Player player = shuffledPlayers.get(i);
                threads.add(new Thread(() -> {
                    Map<Resource, Integer> resMap = virtualView.proposeInitialResources(2, player);

                    try {
                        for (Map.Entry<Resource, Integer> res : resMap.entrySet()) {
                            if(resMap.size() == 1)
                                player.getBoard().addWarehouseDepotResource(res.getKey(), 2, res.getValue());
                            else
                                player.getBoard().addWarehouseDepotResource(res.getKey(), 1, res.getValue());
                        }
                    }catch (addResourceLimitExceededException | invalidResourceTypeException | duplicatedWarehouseTypeException ignored) {
                    }catch (NullPointerException e){
                        virtualView.gameAbort();
                        Server.lobbies.remove(this.gameID);
                        killAll(this.players);
                        Thread.currentThread().interrupt();
                    }
                }));
            }
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        virtualView.gameStarted();

        currentState = startTurnState;
        virtualView.startTurn(this.turnHandler.getCurrentPlayer().getNickname(),null);
    }

    /**
     * This method is used to notify the disconnection of the specified player in the game which can lead to 2 events:
     * if the number of remaining players is >=2 the Multiplayer Game continues updating the list of connected players in the
     * VirtualView and simply skipping disconnected players' turns, otherwise the game is aborted.
     * @param player The disconnected player.
     */
    @Override
    public void notifyPlayerDisconnection(Player player) {
        if(!Server.lobbies.get(gameID).isGameStarted()){
            virtualView.gameAbort();
            Server.lobbies.remove(gameID);
            Thread.currentThread().interrupt();
        }

        if(turnHandler.getConnectedPlayersNumber() == 2) {
            Server.lobbies.remove(gameID);
            List<Player> playersList = turnHandler.getPlayers();
            playersList.remove(player);
            try {
                virtualView.setPlayers(playersList);
                virtualView.gameAbort();
                killAll(playersList);
            } catch (Exception ignored) {
            }
        }
        else {
            Player exCurrentPlayer = this.getCurrentPlayer();
            turnHandler.notifyPlayerDisconnection(player);
            virtualView.setPlayers(turnHandler.getPlayers());
            virtualView.notifyPlayerDisconnection(player.getNickname());

            if(exCurrentPlayer.equals(player)) {
                this.setCurrentState(this.getStartTurnState());
                virtualView.startTurn(getCurrentPlayer().getNickname(), null);
            }
        }
    }

    /**
     * This method is used to safely close the socket connection of every ClientHandler involved in the game.
     * @param playerList The list of the players actually connected with the lobby.
     */
    private void killAll(List<Player> playerList){
        playerList.forEach(p -> {
            try {
                p.getClientHandler().getClientSocket().close();
            } catch (IOException ignored) {
            }
        });
    }

    /**
     * @return The CommunicationMediator valid for this Multiplayer Controller.
     */
    public CommunicationMediator getMediator() {
        return mediator;
    }

    /**
     * This method is used to notify every player that a specified player reconnected with success to the match,
     * updates the list of connected players in the VirtualView and forces the complete update for the reconnected player's view.
     * @param player The reconnected player.
     */
    @Override
    public void notifyPlayerReconnection(Player player) {
        turnHandler.notifyPlayerReconnection(player);
        virtualView.setPlayers(turnHandler.getPlayers());
        virtualView.forcedReconnectionUpdate(player);
    }

    /**
     * This method is used to resolve double dispatching problems delegating the handling of a message to the
     * actual current state of the controller: that allows to execute the right code for the specific dynamic type of the message
     * and the specific dynamic type of the state.
     * @param message The message that the ClientHandler of a certain player just received.
     */
    @Override
    public void handleMessage(Message message) {
        message.handleMessage(this.currentState,this);
    }

    /**
     * @return TRUE only if someone arrived at the end of the FaithTrack or purchased the 7th Development Card.
     */
    @Override
    public boolean isGameOver() {
        return model.isGameOver();
    }

    /**
     * @return TRUE only if the current state is the EndTurnState and the current player is the last player in the shift schedule.
     */
    @Override
    public boolean isRoundOver() {
        if(currentState instanceof EndTurnState)
            return turnHandler.isRoundOver();
        else
            return false;
    }

    /**
     * @return The current player.
     */
    @Override
    public Player getCurrentPlayer() {
        return this.turnHandler.getCurrentPlayer();
    }

    /**
     * Passes the turn to the next player.
     */
    @Override
    public void nextPlayer() {
        this.turnHandler.nextPlayer();
    }

    /**
     * @return The list that contains all other players than the current.
     */
    @Override
    public List<Player> othersPlayers() {
        return this.turnHandler.getOtherPlayers();
    }

    /**
     * @param state The new current state.
     */
    @Override
    public void setCurrentState(State state) {
        this.currentState = (MultiplayerState) state;
    }

    @Override
    public Game getModel() { return model; }

    @Override
    public State getMarketState() { return marketState; }

    @Override
    public State getActivateProductionState() { return activateProductionState; }

    @Override
    public State getLeaderActionState() { return leaderActionState; }

    @Override
    public State getDevCardSaleState() { return devCardSaleState; }

    @Override
    public State getResourceManagementState() {return resourceManagementState; }

    @Override
    public State getSwapState() {return swapState; }

    @Override
    public State getWhiteMarbleState() {return whiteMarbleState; }

    @Override
    public State getStartTurnState() { return startTurnState; }

    @Override
    public State getMiddleTurnState() { return middleTurnState; }

    @Override
    public State getEndTurnState() { return endTurnState; }

    @Override
    public State getMainActionState() {return mainActionState; }

    @Override
    public State getEndGameState() { return endGameState; }
}
