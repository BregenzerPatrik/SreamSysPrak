package Query;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Events implements Serializable {
    protected final Timestamp timestamp;

    public String getName() {
        return name;
    }

    protected final String name;

    protected Events(String name){
        this.name = name;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    public Timestamp getTimestamp(){
        return this.timestamp;
    }
}
