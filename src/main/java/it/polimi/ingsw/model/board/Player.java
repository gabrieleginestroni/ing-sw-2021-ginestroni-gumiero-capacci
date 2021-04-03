package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        return new ArrayList<Resource>(whiteMarbles);
    }

    public ArrayList<Resource> getDiscount() {
        return new ArrayList<Resource>(discount);
    }

    public void giveFaithPoints(){
        //
    }


    public void addDepotResource(Depot depot, int quantity){
        //
    }

    public void removeDepotResource(Depot depot, int quantity){
        //
    }

    public int getCardNumber(int level, Color color){
        return 0 ; //TODO
    }

    public void giveProductionResource(){
        //
    }

    public void giveDevelopmentCard(DevelopmentCard card,CardSlot cardSlot){
        //
    }

    public void giveLeaderCard(LeaderCard card){
        this.board.addLeaderCard(card);
    }

    public void setReportedSection(int index) {
        //
    }

    public int getFaithPoints(){
        return this.board.getFaithPoints();
    }



}
