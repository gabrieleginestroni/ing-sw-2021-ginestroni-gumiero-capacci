package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.MultiplayerState;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.MultiplayerGame;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        //shuffledPlayers.get(0).getBoard().setInkwell();

        mediator = new CommunicationMediator();

        for(Player player : players) {
           Thread t = new Thread(()-> {
               List<LeaderCard> chosenLeaders = virtualView.propose4Leader(model.get4LeaderCards(), player);

               for(LeaderCard leaderCard : chosenLeaders)
                   player.getBoard().addLeaderCard(leaderCard);

               if(player == players.get(players.size() - 1) )
                   shuffledPlayers.get(0).getBoard().setInkwell();
           });
           t.start();
           
        }

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
