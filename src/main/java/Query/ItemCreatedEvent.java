package Query;

import command.Position;

public class ItemCreatedEvent extends Events{

    private final Position location;
    private final int numberOfMoves;
    private final int value;

    public Position getLocation(){
        return this.location;
    }
    public int getNumberOfMoves(){
        return this.numberOfMoves;
    }
    public int getValue(){
        return this.value;
    }
    public ItemCreatedEvent(String ItemName, Position ItemLocation, int ItemNumberOfMoves, int ItemValue){
        super(ItemName);
        this.location=ItemLocation;
        this.numberOfMoves = ItemNumberOfMoves;
        this.value = ItemValue;
    }

}
