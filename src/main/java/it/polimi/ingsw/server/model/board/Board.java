package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exceptions.*;

import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.Game;

import it.polimi.ingsw.server.virtual_view.BoardObserver;

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
    private final BoardObserver boardObserver;

    /**
     * Constructor of an empty Board.
     * @param game Game which the board belongs to
     */
    public Board(Game game, BoardObserver boardObserver) {

        this.boardObserver = boardObserver;
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
        this.faithTrack = new FaithTrack(game,boardObserver);
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
    public int getLeaderDepotResourceNumber(int index) throws IndexOutOfBoundsException{
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
    public Resource getLeaderDepotResourceType(int index) throws IndexOutOfBoundsException{
        return wareHouse.getLeaderDepotResourceType(index) ;
    }

    /**
     * Creates an empty leader depot
     * @param res Resource type of the leader depot
     */
    public void addLeaderDepot(Resource res){
        this.wareHouse.createLeaderDepot(res);
        boardObserver.notifyLeaderDepotCreation(res.toString());

    }

    /**
     * Adds a white marble power to the player
     * @param res Resource type of the white marble power
     */
    public void addWhiteMarble(Resource res){
        whiteMarbles.add(res);
        this.boardObserver.notifyWhiteMarble(res.toString());
    }

    /**
     * Adds a discount power to the player
     * @param res Resource type of the discount power
     */
    public void addDiscount(Resource res){
        discount.add(res);
        this.boardObserver.notifyDiscount(res.toString());
    }

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
     * @param cardSlotIndex Index of the card slot (between 0 and 2)
     * @return Development card located on top of the specified card slot
     * @throws IndexOutOfBoundsException In case index not between 0 and 2
     */
    public DevelopmentCard getTopCard(int cardSlotIndex) throws IndexOutOfBoundsException {
        return this.cardSlot[cardSlotIndex].getTopCard();
    }

    /**
     * Checks if the DevelopmentCard can be placed in the cardSlot
     * @param cardSlotIndex Index of the card slot (between 0 and 2)
     * @param developmentCard Development Card to check
     * @return true if the card can be placed, false otherwise
     */
    public boolean canAddDevCard(int cardSlotIndex, DevelopmentCard developmentCard){
        if(cardSlotIndex < 0 || cardSlotIndex > 2)
            return false;
        return this.cardSlot[cardSlotIndex].canAddDevCard(developmentCard);
    }

    /**
     * Adds a Development card to the specified card slot
     * @param card Development card to insert
     * @param cardSlotIndex Index of the slot where to insert the card (between 0 and 2)
     * @throws developmentCardSlotLimitExceededException In case of placement of the fourth card in the same card slot
     * @throws invalidDevelopmentCardLevelPlacementException In case of placement that does not meet the level requirement
     */
    public void addDevelopmentCard(DevelopmentCard card, int cardSlotIndex) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException {


        this.cardSlot[cardSlotIndex].addCard(card);
        boardObserver.notifyDevelopmentCardPlacement(card.getId(),cardSlotIndex);
        int totalDevCardNumber = Arrays.stream(this.cardSlot).mapToInt(CardSlot::getGenericCardNumber).sum();
        if(totalDevCardNumber == 7) this.game.gameIsOver();

    }

    /**
     * Sets the inkwell to "TRUE"
     */

    public void setInkwell(){
        this.inkwell = true;
        boardObserver.notifyInkwellSet();
    }


    /**
     * Increments the player's faith marker. The method can eventually trigger the report of a faith track section.
     * @param steps Amount of faith points to add
     * @return Index of the eventually activated section. Returns -1 if no section has been activated.
     */
    public int giveFaithPoints(int steps)  {
        int sectionReportedCode = this.faithTrack.addFaith(steps);
        this.boardObserver.notifyFaithMarkerUpdate(getFaithPoints());
        return sectionReportedCode;
    }


    /**
     * Adds an amount of the specified type resource to the warehouse depot that corresponds to the index.
     * @param warehouseDepotIndex Index of the warehouse depot between 0 and 2
     * @param res Resource type to add
     * @param quantity Quantity of resource to add
     * @throws addResourceLimitExceededException In case new quantity exceeds the storage limit
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws duplicatedWarehouseTypeException In case the add resource action tries to set a duplicated warehouse
     */
    public void addWarehouseDepotResource(Resource res, int quantity, int warehouseDepotIndex) throws addResourceLimitExceededException, invalidResourceTypeException, duplicatedWarehouseTypeException {
        this.wareHouse.addWarehouseDepotResource(warehouseDepotIndex,res,quantity);
        boardObserver.notifyWarehouseDepotUpdate(res.toString(), wareHouse.getWarehouseDepotResourceNumber(warehouseDepotIndex),warehouseDepotIndex);
    }

    /**
     * Removes an amount of the specified type resource to the warehouse depot that corresponds to the index.
     * Sets the resource type to null if the new quantity is zero.
     * @param warehouseDepotIndex Index of the warehouse depot between 0 and 2
     * @param res Resource type to remove
     * @param quantity Quantity of resource to remove
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws removeResourceLimitExceededException In case the quantity to remove is greater than the actual
     * stored quantity
     */
    public void removeWarehouseDepotResource(Resource res, int quantity, int warehouseDepotIndex) throws invalidResourceTypeException,
            removeResourceLimitExceededException {
        this.wareHouse.removeWarehouseDepotResource(warehouseDepotIndex,res,quantity);

        Optional<Resource> resourceTypeTemp = Optional.ofNullable(wareHouse.getWarehouseDepotResourceType(warehouseDepotIndex));
        String resourceType = resourceTypeTemp.map(Resource::toString).orElse("NULL");

        boardObserver.notifyWarehouseDepotUpdate(resourceType, wareHouse.getWarehouseDepotResourceNumber(warehouseDepotIndex),warehouseDepotIndex);
    }

    /**
     * Adds an amount of the specified type resource to the leader depot that corresponds to the index.
     * @param leaderDepotIndex Index of the leader depot (between 0 and 1)
     * @param res Resource type to add
     * @param quantity Quantity of resource to add
     * @throws addResourceLimitExceededException In case new quantity exceeds the storage limit
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws IndexOutOfBoundsException In case a leader depot with the specified index doesn't exist
     */
    public void addLeaderDepotResource(Resource res, int quantity, int leaderDepotIndex) throws addResourceLimitExceededException,
            invalidResourceTypeException, IndexOutOfBoundsException {
        this.wareHouse.addLeaderDepotResource(leaderDepotIndex,res,quantity);
        boardObserver.notifyLeaderDepotUpdate(wareHouse.getLeaderDepotResourceNumber(leaderDepotIndex),leaderDepotIndex);
    }

    /**
     * Removes an amount of the specified type resource to the leader depot that corresponds to the index.
     * @param leaderDepotIndex Index of the leader depot between 0 and 1
     * @param res Resource type to remove
     * @param quantity Quantity of resource to remove
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws removeResourceLimitExceededException In case the quantity to remove is greater than the actual quantity
     * stored
     * @throws IndexOutOfBoundsException In case a leader depot with the specified index doesn't exist
     */
    public void removeLeaderDepotResource(Resource res, int quantity, int leaderDepotIndex) throws invalidResourceTypeException,
            removeResourceLimitExceededException, IndexOutOfBoundsException {
        this.wareHouse.removeLeaderDepotResource(leaderDepotIndex,res,quantity);
        boardObserver.notifyLeaderDepotUpdate(wareHouse.getLeaderDepotResourceNumber(leaderDepotIndex),leaderDepotIndex);
    }

    /**
     * Swaps two warehouse depot's resources if the storages limits permit the action and changes the resource
     * types accordingly.
     * @param warehouseDepot1Index Index of the first warehouse depot
     * @param warehouseDepot2Index Index of the seconds warehouse depot
     * @throws invalidSwapException In case the swap isn't allow by the game rules
     */
    public void swapDepot(int warehouseDepot1Index,int warehouseDepot2Index) throws invalidSwapException {

        this.wareHouse.swapDepot(warehouseDepot1Index,warehouseDepot2Index);

        Optional<Resource> resourceTypeTemp = Optional.ofNullable(wareHouse.getWarehouseDepotResourceType(warehouseDepot1Index));
        String resourceType = resourceTypeTemp.map(Resource::toString).orElse("NULL");

        boardObserver.notifyWarehouseDepotUpdate(resourceType, wareHouse.getWarehouseDepotResourceNumber(warehouseDepot1Index),warehouseDepot1Index);

        resourceTypeTemp = Optional.ofNullable(wareHouse.getWarehouseDepotResourceType(warehouseDepot2Index));
        resourceType = resourceTypeTemp.map(Resource::toString).orElse("NULL");

        boardObserver.notifyWarehouseDepotUpdate(resourceType, wareHouse.getWarehouseDepotResourceNumber(warehouseDepot2Index),warehouseDepot2Index);
    }

    /**
     * Adds an amount of resource to the strongbox
     * @param res Type of resource
     * @param quantity Resource amount to add
     */
    public void addStrongboxResource(Resource res,int quantity){
        this.strongBox.addResource(res,quantity);
        boardObserver.notifyStrongboxUpdate(res.toString(),strongBox.getResource(res));

    }

    /**
     * Removes an amount of resource from the strongbox
     * @param res Type of resource
     * @param quantity Resource amount to remove
     * @throws invalidStrongBoxRemoveException In case the quantity to remove is greater than the resource amount stored
     */
    public void removeStrongboxResource(Resource res,int quantity) throws invalidStrongBoxRemoveException {
        this.strongBox.removeResource(res,quantity);
        boardObserver.notifyStrongboxUpdate(res.toString(),strongBox.getResource(res));

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

    /**
     * Computes the player's total amount of victory points obtained from leader cards, development cards, faith track
     * and resources.
     * @return Total amount of victory points
     */
    public int computeVictoryPoints() {

        int faithTot = this.faithTrack.getVictoryPoints();
        int resTot = (this.wareHouse.getGenericResourceNumber() + this.strongBox.getGenericResourceNumber() ) /5;
        int leaderTot = this.activeLeaders.stream().mapToInt(LeaderCard::getVictoryPoints).sum();
        int devTot = Arrays.stream(this.cardSlot).mapToInt(CardSlot::getVictoryPoints).sum();

        return faithTot + resTot + leaderTot + devTot;
    }

    /**
     * Triggers the potential activation of the Pope Tiles that belongs to the track section which corresponds to the
     * index
     * @param index Index of the track section reported by any player
     */
    public void computeActivationPopeTile(int index){
        this.faithTrack.computeActivationPopeTile(index);
    }

    /**
     * Adds an inactive leader card to the player's hand.
     * @param card Leader card to add to the player
     */
    public void addLeaderCard(LeaderCard card){
        this.hand.add(card);
        card.setOwner(this);
        boardObserver.notifyAddLeader(card.getId());
    }

    /**
     * Activates a leader card and moves the card to the list of active leader cards.
     * @param cardIndex Index of the leader card to activate (referred to the hand)
     * @throws IndexOutOfBoundsException In case no leader card has that index in the hand
     */
    public void activateLeaderCard(int cardIndex) throws IndexOutOfBoundsException {

        LeaderCard leaderToActivate = this.hand.get(cardIndex);
        this.hand.remove(leaderToActivate);
        activeLeaders.add(leaderToActivate);
        boardObserver.notifyLeaderActivation(cardIndex);
        leaderToActivate.activateCard();

    }


    /**
     * Discards a leader card from the hand and gives one faith point to the player.
     * @param cardIndex Index of the card to discard (referred to the hand)
     * @throws IndexOutOfBoundsException In case no leader card has that index in the hand
     * @return Index of the eventually activated section. Returns -1 if no section has been activated.
     */
    public int discardLeaderCard(int cardIndex) throws IndexOutOfBoundsException {

        LeaderCard leaderToDiscard =  this.hand.get(cardIndex);
        this.hand.remove(leaderToDiscard);
        leaderToDiscard.discardCard();
        boardObserver.notifyLeaderDiscard(cardIndex);
        return giveFaithPoints(1);
    }

    //TODO
    //REMOVE
    /**
     * Returns a copy of the list of active leader cards
     * @return Copy of the list of active leader cards
     */
    public ArrayList<LeaderCard> getActiveLeaderCard(){
        return new ArrayList<>(this.activeLeaders);
    }

    //TODO
    //REMOVE
    /**
     * Returns a copy of the list of inactive leader cards
     * @return Copy of the list of inactive leader cards
     */
    public ArrayList<LeaderCard> getInactiveLeaderCard(){
        return new ArrayList<>(this.hand);
    }

    //TODO
    //REMOVE
    /**
     * Returns a copy of the list of development cards placed in the card slot
     * @param cardSlotIndex Index of the card slot
     * @return Copy of the list of development cards placed in the card slot
     */
    public ArrayList<DevelopmentCard> getCardSlotDevelopmentCards(int cardSlotIndex){
        return cardSlot[cardSlotIndex].getCardSlotDevelopmentCards();
    }
}


