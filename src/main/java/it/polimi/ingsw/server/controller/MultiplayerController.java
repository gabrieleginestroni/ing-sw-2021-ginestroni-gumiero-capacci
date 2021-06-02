package it.polimi.ingsw.server.controller;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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




    //TODO
    public MultiplayerController(List<Player> players) {

        super(new VirtualView());


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

               for(LeaderCard leaderCard : chosenLeaders)
                   player.getBoard().addLeaderCard(leaderCard);

           }));
           threads.get(threads.size() - 1).start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            }catch(InterruptedException e){
                //TODO
            }
        }

        shuffledPlayers.get(0).getBoard().setInkwell();

        threads.clear();

        for(int i=0; i < shuffledPlayers.size(); i++){
            if(i == 1 || i == 2) {
                Player player = shuffledPlayers.get(i);
                threads.add(new Thread(() -> {
                    Map<Resource, Integer> resMap = virtualView.proposeInitialResources(1, player);
                    for (Map.Entry<Resource, Integer> res : resMap.entrySet()) {
                        try {
                            player.getBoard().addWarehouseDepotResource(res.getKey(), 1, res.getValue());
                        } catch (addResourceLimitExceededException | invalidResourceTypeException | duplicatedWarehouseTypeException e) {
                            //TODO
                        }
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

                    for (Map.Entry<Resource, Integer> res : resMap.entrySet()) {
                        try {
                            if(resMap.size() == 1)
                                player.getBoard().addWarehouseDepotResource(res.getKey(), 2, res.getValue());
                            else
                                player.getBoard().addWarehouseDepotResource(res.getKey(), 1, res.getValue());
                        } catch (addResourceLimitExceededException | invalidResourceTypeException | duplicatedWarehouseTypeException e) {
                            //TODO
                        }
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
                //TODO
            }
        }

        virtualView.gameStarted();

        currentState = startTurnState;
        virtualView.startTurn(this.turnHandler.getCurrentPlayer().getNickname(),null);

    }

    @Override
    public void handleMessage(Message message) {

        message.handleMessage(this.currentState,this);

        //this.currentState.handleInput(message,this);

    }

    @Override
    public boolean isGameOver() {
        return model.isGameOver();
    }

    @Override
    public boolean isRoundOver() {
        return turnHandler.isRoundOver();
    }

    @Override
    public Player getCurrentPlayer() {
        return this.turnHandler.getCurrentPlayer();
    }


    public CommunicationMediator getMediator() {
        return mediator;
    }

    @Override
    public void nextPlayer() {
        this.turnHandler.nextPlayer();
    }

    @Override
    public List<Player> othersPlayers() {
        return this.turnHandler.getOtherPlayers();
    }

    @Override
    public void setCurrentState(State state) {
        this.currentState = (MultiplayerState) state;
    }



    @Override
    public Game getModel() {
        return model;
    }

    @Override
    public State getMarketState() {
        return marketState;
    }

    @Override
    public State getActivateProductionState() {
        return activateProductionState;
    }

    @Override
    public State getLeaderActionState() {
        return leaderActionState;
    }

    @Override
    public State getDevCardSaleState() {
        return devCardSaleState;
    }

    @Override
    public State getResourceManagementState() {return resourceManagementState;    }

    @Override
    public State getSwapState() {return swapState;    }

    @Override
    public State getWhiteMarbleState() {return whiteMarbleState;    }

    @Override
    public State getStartTurnState() {
        return startTurnState;
    }

    @Override
    public State getMiddleTurnState() { return middleTurnState;
    }

    @Override
    public State getEndTurnState() { return endTurnState;
    }

    @Override
    public State getMainActionState() {return mainActionState;
    }

    @Override
    public State getEndGameState() { return endGameState;
    }


}
