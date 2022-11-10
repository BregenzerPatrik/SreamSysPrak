package Query;

import command.Position;

public class ItemMovedEvent extends Events{
    private Position moveCoords;

    public ItemMovedEvent(String name, Position moveCoords){
        super("ItemMovedEvent", name);
        this.name = name;
        this.moveCoords=moveCoords;

    }
    public Position getMoveCoords(){
        return this.moveCoords;
    }




}
