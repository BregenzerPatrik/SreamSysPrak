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
    public ItemCreatedEvent(String name, Position location, int numberOfMoves, int value){
        super("ItemCreatedEvent", name);
        this.location=location;
        this.numberOfMoves = numberOfMoves;
        this.value = value;
    }

}
