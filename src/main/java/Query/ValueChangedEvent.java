package Query;

public class ValueChangedEvent extends Events{
    private int newValue;

    public int getNewValue(){
        return this.newValue;
    }
    public ValueChangedEvent(String name, int newValue){
        super("ValueChangedEvent", name);
        this.newValue = newValue;
    }
}
