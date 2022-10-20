package command;

import Query.EventStore;
import Query.ValueChangedEvent;

public class ChangeValueCommand extends Command{
    public ChangeValueCommand(String name, int valueDelta){
        ValueChangedEvent event= new ValueChangedEvent(name,valueDelta);
        EventStore.singleInstance.store(event);
    }
}
