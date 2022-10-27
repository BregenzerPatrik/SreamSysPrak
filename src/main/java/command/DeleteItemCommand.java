package command;

import Query.EventStore;
import Query.ItemDeletedEvent;

public class DeleteItemCommand extends Command{
    public String getName() {
        return name;
    }

    private String name;
    public DeleteItemCommand(String name){
        this.name=name;
        CommandHandler.handle(this);
    }
}
