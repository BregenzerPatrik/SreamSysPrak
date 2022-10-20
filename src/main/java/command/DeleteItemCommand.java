package command;

import Query.EventStore;
import Query.ItemDeletedEvent;

public class DeleteItemCommand extends Command{
    public DeleteItemCommand(String name){
        ItemDeletedEvent event = new ItemDeletedEvent(name);
        EventStore.singleInstance.store(event);
    }
}
