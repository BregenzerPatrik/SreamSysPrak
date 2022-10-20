package Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ItemMovedEvent extends Events{
    private final Timestamp timestamp;
    private final int[] moveCoords;
    public String name;

    public ItemMovedEvent(String name, int[] moveCoords){
        this.name = name;
        this.moveCoords=moveCoords;
        this.timestamp=Timestamp.valueOf(LocalDateTime.now());

    }
    public int[] getMoveCoords(){
        return this.moveCoords;
    }

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }



}
