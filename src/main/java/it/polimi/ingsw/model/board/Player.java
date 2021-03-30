package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class Player {
    private String nickName;
    private final Board board;
    private final ArrayList<Resource> whiteMarbles;
    private final ArrayList<Resource> discount;
    private final ArrayList<Resource> productionEffectInput;

    public Player(String nickName) {
        this.nickName = nickName;
        this.board = new Board();
        this.discount = new ArrayList<Resource>();
        this.productionEffectInput = new ArrayList<Resource>();
        this.whiteMarbles = new ArrayList<Resource>();
    }

    public void addWhiteMarblePowerEffect(Resource resource){
        whiteMarbles.add(resource);


    }
    public void addDiscountPowerEffect(Resource resource){
        discount.add(resource);

    }
    public void addProductionPowerEffect(Resource resource){
        productionEffectInput.add(resource);

    }
    public void addLeaderStorageDepot(Resource resource){
        

    }

    public void giveInkwell(){

    }
}
