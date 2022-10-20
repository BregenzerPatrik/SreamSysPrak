package command;

import Query.EventStore;
import Query.ItemMovedEvent;

public class MoveItemCommand extends Command{
    public MoveItemCommand(String name, int[] moveCoords){
        ItemMovedEvent event=new ItemMovedEvent(name,moveCoords);
        EventStore.singleInstance.store(event);
    }
}
