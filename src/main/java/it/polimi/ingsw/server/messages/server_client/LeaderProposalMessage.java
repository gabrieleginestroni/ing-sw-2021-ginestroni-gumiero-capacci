package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

import java.util.Arrays;

public class LeaderProposalMessage extends AnswerMessage{
    private final int[] proposedLeaderCards;

    public LeaderProposalMessage(int[] proposedLeaderCards) {
        this.proposedLeaderCards = proposedLeaderCards;
    }

    @Override
    public void selectView(CLI cli) {
        System.out.println("Choose 2 of these 4 Leader Cards: " + Arrays.toString(proposedLeaderCards));
        System.out.println("Select 0, 1, 2 or 3 : ");
    }

    @Override
    public void selectView(GUI gui) {

    }
}
