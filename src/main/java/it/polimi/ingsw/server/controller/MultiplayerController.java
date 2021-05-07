package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.MultiplayerState;
import it.polimi.ingsw.server.exceptions.addResourceLimitExceededException;
import it.polimi.ingsw.server.exceptions.duplicatedWarehouseTypeException;
import it.polimi.ingsw.server.exceptions.invalidResourceTypeException;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.MultiplayerGame;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;

public class MultiplayerController implements Controller{
    private final MultiplayerGame model;
    private MultiplayerState currentState;
    private final List<Player> players;
    private final CommunicationMediator mediator;
    private final VirtualView virtualView;
    private final TurnHandler turnHandler;

    //TODO
    public MultiplayerController(List<Player> players) {

        this.players = players;

        ArrayList<Player> shuffledPlayers = new ArrayList<>(players);
        Collections.shuffle(shuffledPlayers);

        turnHandler = new TurnHandler(shuffledPlayers);

        virtualView = new VirtualView();

        model = new MultiplayerGame(this.players,this.virtualView);

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


        System.out.println(virtualView.toJSONString());
        //currentState = StartGameState;

    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public CommunicationMediator getMediator() {
        return mediator;
    }

    public void setCurrentState(MultiplayerState nextState) {
        currentState = nextState;
    }

}
