package command;

import Query.EventStore;
import Query.ItemCreatedEvent;

public class CreateItemCommand extends Command{
    public CreateItemCommand(String name){
        ItemCreatedEvent event=new ItemCreatedEvent(name,new int[3],0,0);
        EventStore.singleInstance.store(event);
    }
}
