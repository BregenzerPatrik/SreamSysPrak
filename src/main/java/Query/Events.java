package Query;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Events implements Serializable {

    public String getEventType() {
        return eventType;
    }

    protected String eventType;

    public String getName() {
        return name;
    }

    protected String name;

    protected Events(String eventType, String name){
        this.eventType = eventType;
        this.name = name;
    }

    @Override
    public String toString() {
        return eventType+" :"+name;
    }
}
