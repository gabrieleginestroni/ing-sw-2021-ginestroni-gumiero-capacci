package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

import java.util.HashMap;
import java.util.Map;

public class StrongBox {
    private final Map<Resource,Integer> resources;

    public StrongBox() {
        this.resources = new HashMap<>();
    }

    public void addResource(Resource res,int quantity){
        int newQuantity;
        newQuantity = resources.getOrDefault(res,0) + quantity;
        resources.put(res,newQuantity);
    }

    public int getResource(Resource res){
        return resources.getOrDefault(res,0);
    }
    public void removeResource(Resource res,int quantity) throws invalidStrongBoxRemoveException {
        int newQuantity;
        newQuantity = resources.getOrDefault(res,0) - quantity;
        if(newQuantity<0){
            throw new invalidStrongBoxRemoveException();
        }
        resources.put(res,newQuantity);
    }
}
