package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.games.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Class that represents the player's personal board. It contains one faith track, a warehouse and a strongbox,
 * three development card slots and the player's leader cards
 */
public class Board {


    private final ArrayList<Resource> whiteMarbles;
    private final ArrayList<Resource> discount;

    private final CardSlot[] cardSlot;
    private final Warehouse wareHouse;
    private final StrongBox strongBox;
    private final ArrayList<LeaderCard> hand;
    private final ArrayList<LeaderCard> activeLeaders;
    private boolean inkwell;
    private final FaithTrack faithTrack;
    private final  Game game;

    /**
     * Constructor of an empty Board.
     * @param game Game which the board belongs to
     */
    public Board(Game game) {


        this.discount = new ArrayList<>();
        this.whiteMarbles = new ArrayList<>();

        this.wareHouse = new Warehouse();
        this.cardSlot = new CardSlot[3];
        cardSlot[0] = new CardSlot();
        cardSlot[1] = new CardSlot();
        cardSlot[2] = new CardSlot();
        this.strongBox = new StrongBox();
        this.inkwell = false;
        this.hand = new ArrayList<>();
        this.activeLeaders = new ArrayList<>();
        this.faithTrack = new FaithTrack(game);
        this.game = game;



    }

    /**
     * Getter of a warehouse depot's stored quantity
     * @param index Index of the warehouse depot between 0 and 2
     * @return Resource quantity
     */
    public int getWarehouseDepotResourceNumber(int index){
        return wareHouse.getWarehouseDepotResourceNumber(index) ;
    }

    /**
     * Getter of a leader depot's stored quantity
     * @param index Index of the leader depot
     * @return Resource quantity
     */
    public int getLeaderDepotResourceNumber(int index){
        return wareHouse.getLeaderDepotResourceNumber(index) ;
    }

    /**
     * Getter of a warehouse depot's resource type
     * @param index Index of the warehouse depot between 0 and 2
     * @return Resource type
     */
    public Resource getWarehouseDepotResourceType(int index){
        return wareHouse.getWarehouseDepotResourceType(index) ;
    }

    /**
     * Getter of a leader depot's resource type
     * @param index Index of the leader depot
     * @return Resource type
     */
    public Resource getLeaderDepotResourceType(int index){
        return wareHouse.getLeaderDepotResourceType(index) ;
    }

    /**
     * Creates an empty leader depot
     * @param res Resource type of the leader depot
     */
    public void addLeaderDepot(Resource res){ this.wareHouse.createLeaderDepot(res); }

    /**
     * Adds a white marble power to the player
     * @param res Resource type of the white marble power
     */
    public void addWhiteMarble(Resource res){ whiteMarbles.add(res); }

    /**
     * Adds a discount power to the player
     * @param res Resource type of the discount power
     */
    public void addDiscount(Resource res){ discount.add(res); }

    /**
     * Returns a copy of the list of white marbles resources powers
     * @return Copy of the list of white marbles resources powers
     */
    public ArrayList<Resource> getWhiteMarbles() { return new ArrayList<>(this.whiteMarbles); }

    /**
     * Returns a copy of the list of discounted resources
     * @return Copy of the list of discounted resources
     */
    public ArrayList<Resource> getDiscount() { return new ArrayList<>(this.discount); }

    /**
     * Computes player's total amount of a specific resource (warehouse and strongbox)
     * @param res Resource type
     * @return Total amount of the resource
     */
    public int getResourceNumber(Resource res){
        return  getStrongBoxResource(res) + getWarehouseResource(res);
    }

    /**
     * Returns player's amount of a specific resource stored in the strongbox
     * @param res Resource type
     * @return Resource amount stored in the strongbox
     */
    public int getStrongBoxResource( Resource res){
        return strongBox.getResource(res);
    }

    /**
     * Returns player's amount of a specific resource stored in the warehouse
     * @param res Resource type
     * @return Resource amount stored in the warehouse
     */
    public int getWarehouseResource(Resource res){ return wareHouse.getTotalWarehouseQuantity(res); }

    /**
     * Computes the total number of development cards, owned by the player, that match the level and color
     * @param level Level number
     * @param color Color of the card
     * @return Number of development cards that match level and color
     */
    public int getCardNumber(int level, Color color){
        return Arrays.stream(this.cardSlot).mapToInt(s->s.getCardNumber(level,color)).sum();
    }

    /**
     * Returns the faith marker of the player
     * @return Faith marker of the player
     */
    public int getFaithPoints(){
        return this.faithTrack.getFaithMarker();
    }

    //TODO
    //REMOVE
    public ArrayList<LeaderCard> getHand() {
        return hand;
    }


    /**
     * Returns the development card located on top of the specified card slot
     * @param cardSlotIndex Index of the card slot
     * @return Development card located on top of the specified card slot
     * @throws IndexOutOfBoundsException In case index not between 0 and 2
     */
    public DevelopmentCard getTopCard(int cardSlotIndex) throws IndexOutOfBoundsException {
        return this.cardSlot[cardSlotIndex].getTopCard();
    }

    public void addDevelopmentCard(DevelopmentCard card, int cardSlotIndex) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException{


        this.cardSlot[cardSlotIndex].addCard(card);
        int totalDevCardNumber = Arrays.stream(this.cardSlot).mapToInt(CardSlot::getGenericCardNumber).sum();
        if(totalDevCardNumber == 7) this.game.gameIsOver();

    }



    public void setInkwell(){
        this.inkwell = true;
    }



    public void giveFaithPoints(int steps)  {
        this.faithTrack.addFaith(steps); }



    public void addWarehouseDepotResource(int warehouseDepotIndex,Resource res, int quantity) throws addResourceLimitExceededException, invalidResourceTypeException, duplicatedWarehouseTypeException {
        this.wareHouse.addWarehouseDepotResource(warehouseDepotIndex,res,quantity);
    }

    public void removeWarehouseDepotResource(int warehouseDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException {
        this.wareHouse.removeWarehouseDepotResource(warehouseDepotIndex,res,quantity);
    }


    public void addLeaderDepotResource(int leaderDepotIndex,Resource res, int quantity) throws addResourceLimitExceededException,
            invalidResourceTypeException, IndexOutOfBoundsException {
        this.wareHouse.addLeaderDepotResource(leaderDepotIndex,res,quantity);
    }

    public void removeLeaderDepotResource(int leaderDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException, IndexOutOfBoundsException {
        this.wareHouse.removeLeaderDepotResource(leaderDepotIndex,res,quantity);
    }

    public void swapDepot(int warehouseDepot1Index,int warehouseDepot2Index) throws invalidSwapException {
        this.wareHouse.swapDepot(warehouseDepot1Index,warehouseDepot2Index);
    }

    public void addStrongboxResource(Resource res,int quantity){
        this.strongBox.addResource(res,quantity);

    }

    public void removeStrongboxResource(Resource res,int quantity) throws invalidStrongBoxRemoveException {
        this.strongBox.removeResource(res,quantity);

    }


    //TODO
    //REMOVE
    public CardSlot[] getCardSlot(){
        return this.cardSlot;
    }

    //TODO
    //REMOVE
    public Warehouse getWareHouse() {
        return wareHouse;
    }

    public int computeVictoryPoints() {

        int faithTot = this.faithTrack.getVictoryPoints();
        int resTot = (this.wareHouse.getGenericResourceNumber() + this.strongBox.getGenericResourceNumber() ) /5;
        int leaderTot = this.hand.stream().filter(LeaderCard::isActive).mapToInt(LeaderCard::getVictoryPoints).sum();
        int devTot = Arrays.stream(this.cardSlot).mapToInt(CardSlot::getVictoryPoints).sum();

        return faithTot + resTot + leaderTot + devTot;
    }

    public void computeActivationPopeTile(int index){
        this.faithTrack.computeActivationPopeTile(index);
    }

    public void addLeaderCard(LeaderCard card){
        this.hand.add(card);
        card.setOwner(this);
    }

    public void activateLeaderCard(int cardIndex) throws IndexOutOfBoundsException {

        LeaderCard leaderToActivate = this.hand.get(cardIndex);
        this.hand.remove(leaderToActivate);
        activeLeaders.add(leaderToActivate);
        leaderToActivate.activateCard();

    }



    public void discardLeaderCard(int cardIndex) throws IndexOutOfBoundsException {

        LeaderCard leaderToDiscard =  this.hand.get(cardIndex);
        this.hand.remove(leaderToDiscard);
        leaderToDiscard.discardCard();
        giveFaithPoints(1);
    }
}


