package Query;

import command.Position;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ItemMovedEvent extends Events{
    private final Timestamp timestamp;
    private final Position moveCoords;
    public String name;

    public ItemMovedEvent(String name, Position moveCoords){
        this.name = name;
        this.moveCoords=moveCoords;
        this.timestamp=Timestamp.valueOf(LocalDateTime.now());

    }
    public Position getMoveCoords(){
        return this.moveCoords;
    }

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }



}
