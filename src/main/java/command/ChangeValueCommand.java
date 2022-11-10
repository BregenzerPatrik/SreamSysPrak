package command;

public class ChangeValueCommand extends CommandAbstract {
    public String getName() {
        return name;
    }

    public int getValueData() {
        return valueData;
    }

    private String name;
    private int valueData;
    public ChangeValueCommand(String name, int valueDelta){
        this.name= name;
        this.valueData=valueDelta;
        CommandHandler.handle(this);
    }

    public void setName(String name){
        this.name=name;
    }
    public void setValueData(int valueData){
        this.valueData=valueData;
    }
}
