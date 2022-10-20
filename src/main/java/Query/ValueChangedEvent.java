package Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ValueChangedEvent extends Events{
    private final String name;
    private final int newValue;
    private final Timestamp timestamp;

    public String getName(){
        return this.name;
    }
    public int getNewValue(){
        return this.newValue;
    }
    public ValueChangedEvent(String name, int newValue){
        this.name = name;
        this.newValue = newValue;
        this.timestamp=Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
