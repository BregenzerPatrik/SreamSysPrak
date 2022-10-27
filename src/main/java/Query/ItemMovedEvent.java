package Query;

import command.Position;

public class ItemMovedEvent extends Events{
    private final Position moveCoords;
    public String name;

    public ItemMovedEvent(String name, Position moveCoords){
        super(name);
        this.name = name;
        this.moveCoords=moveCoords;

    }
    public Position getMoveCoords(){
        return this.moveCoords;
    }




}
