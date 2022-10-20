package Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ItemCreatedEvent extends Events{
    private final Timestamp timestamp;
    private final  String name;
    private final int[] location;
    private final int numberOfMoves;
    private final int value;

    public String getName(){
        return this.name;
    }
    public int[] getLocation(){
        return this.location;
    }
    public int getNumberOfMoves(){
        return this.numberOfMoves;
    }
    public int getValue(){
        return this.value;
    }
    public ItemCreatedEvent(String ItemName, int[] ItemLocation, int ItemNumberOfMoves, int ItemValue){
        this.name=ItemName;
        this.location=ItemLocation;
        this.numberOfMoves = ItemNumberOfMoves;
        this.value = ItemValue;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}