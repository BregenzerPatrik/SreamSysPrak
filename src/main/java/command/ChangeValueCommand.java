package command;

import Query.EventStore;
import Query.ValueChangedEvent;

public class ChangeValueCommand extends Command{
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
