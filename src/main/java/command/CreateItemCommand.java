package command;

public class CreateItemCommand extends CommandAbstract {
    public String getName() {
        return name;
    }

    private String name;

    public CreateItemCommand(String name){
        this.name=name;
        CommandHandler.handle(this);
    }
}
