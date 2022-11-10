package command;

public class MoveItemCommand extends CommandAbstract {
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
