package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;


public class Player {
    private final String nickName;
    private final Board board;
    private final ArrayList<Resource> whiteMarbles;
    private final ArrayList<Resource> discount;

    public Player(String nickName) {
        this.nickName = nickName;
        this.board = new Board();
        this.discount = new ArrayList<>();
        this.whiteMarbles = new ArrayList<>();
    }

    public void addWhiteMarble(Resource res){
        whiteMarbles.add(res);


    }
    public void addDiscount(Resource res){
        discount.add(res);

    }

    public void addLeaderDepot(Resource resource){
        this.board.addLeaderDepot(resource);

    }

    public void setInkwell(){
        this.board.setInkwell();

    }

    public int getResourceNumber(Resource res){
        return  this.board.getStrongBoxResource(res) + this.board.getWarehouseResource(res);
    }

    public int getWarehouseResource(Resource res){
        return this.board.getWarehouseResource(res);
    }

    public ArrayList<Resource> getWhiteMarbles() {
        return new ArrayList<>(whiteMarbles);
    }

    public ArrayList<Resource> getDiscount() {
        return new ArrayList<>(discount);
    }




    public void addDepotResource(Depot depot,Resource res, int quantity) throws invalidDepotTypeChangeException,
            duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException {
        this.board.addDepotResource(depot,res,quantity);
    }

    public void removeDepotResource(Depot depot,Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException {
        this.board.removeDepotResource(depot,res,quantity);
    }

    public void swapDepot(WarehouseDepot depot1,WarehouseDepot depot2) throws invalidSwapException {
        this.board.swapDepot(depot1,depot2);
    }

    public int getCardNumber(int level, Color color){
        return this.board.getCardNumber(level,color);
    }

    public void giveProductionResource(){
        //TODO
    }

    public void giveDevelopmentCard(DevelopmentCard card,CardSlot cardSlot) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException {
        this.board.addDevelopmentCard(card,cardSlot);
    }

    public void giveLeaderCard(LeaderCard card){
        this.board.addLeaderCard(card);
    }

    public void setReportedSection(int index) {
        //TODO
    }

    public int getFaithPoints(){
        return this.board.getFaithPoints();
    }

    public void giveFaithPoints(int steps) throws vaticanReportSection1Exception,
            vaticanReportSection3Exception, vaticanReportSection2Exception {
        this.board.giveFaithPoints(steps);
    }

    public void addStrongboxResource(Resource res,int quantity){
        this.board.addStrongboxResource(res,quantity);

    }

    public void removeStrongboxResource(Resource res,int quantity) throws invalidStrongBoxRemoveException {
        this.board.removeStrongboxResource(res,quantity);

    }

    public void discardLeaderCard(LeaderCard card) throws vaticanReportSection3Exception,
            vaticanReportSection2Exception, vaticanReportSection1Exception {
        this.board.discardLeaderCard(card);
    }

}
