package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used in the setup of the game to propose every player the Leader Cards he must choose from.
 * Every player must choose 2 Leader Cards out of the 4 proposed.
 */
public class LeaderProposalMessage implements AnswerMessage{
    private final int[] proposedLeaderCards;

    /**
     * @param proposedLeaderCards The integer array that contains the 4 cardIDs of the proposed Leader Cards.
     */
    public LeaderProposalMessage(int[] proposedLeaderCards) {
        this.proposedLeaderCards = proposedLeaderCards;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
       view.visitLeaderProposal(proposedLeaderCards);
    }
}
