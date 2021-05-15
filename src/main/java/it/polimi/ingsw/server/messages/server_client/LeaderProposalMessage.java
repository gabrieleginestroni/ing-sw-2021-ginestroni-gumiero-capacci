package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class LeaderProposalMessage implements AnswerMessage{
    private final int[] proposedLeaderCards;

    public LeaderProposalMessage(int[] proposedLeaderCards) {
        this.proposedLeaderCards = proposedLeaderCards;
    }

    @Override
    public void selectView(View view) {
       view.visitLeaderProposal(proposedLeaderCards);
    }


}
