package command;

import Query.EventStore;
import Query.ItemCreatedEvent;

public class CreateItemCommand extends Command{
    public String getName() {
        return name;
    }

    private String name;

    public CreateItemCommand(String name){
        this.name=name;
        CommandHandler.handle(this);
    }
}
