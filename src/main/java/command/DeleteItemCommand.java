package command;

public class DeleteItemCommand extends CommandAbstract {
    public String getName() {
        return name;
    }

    private String name;
    public DeleteItemCommand(String name){
        this.name=name;
        CommandHandler.handle(this);
    }
}
