package it.polimi.ingsw.server.model.games;

/**
 * @author Tommaso Capacci
 * Interface that represents the effect of all the possible types of Action Tokens used in the Solo version of the Game.
 */
public interface ActionToken {

    int activateEffect(SoloGame solo);

    String getId();

}
