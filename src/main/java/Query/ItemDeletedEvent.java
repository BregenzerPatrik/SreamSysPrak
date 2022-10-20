package Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ItemDeletedEvent extends Events{
    private final String targetId;
    private final Timestamp timestamp;

    public String getTargetId() {
        return targetId;
    }

    public ItemDeletedEvent(String name){
        this.targetId=name;
        this.timestamp=Timestamp.valueOf(LocalDateTime.now());
    }
    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }



}
