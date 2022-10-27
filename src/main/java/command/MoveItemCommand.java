package command;

import Query.EventStore;
import Query.ItemMovedEvent;

public class MoveItemCommand extends Command{
    public String getName() {
        return name;
    }

    public Position getMoveCoords() {
        return moveCoords;
    }

    private String name;
    private Position moveCoords;

    public MoveItemCommand(String name, Position moveCoords){
        this.name=name;
        this.moveCoords=moveCoords;
        CommandHandler.handle(this);
    }
}
