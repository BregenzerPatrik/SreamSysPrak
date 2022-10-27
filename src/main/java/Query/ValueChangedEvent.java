package Query;

public class ValueChangedEvent extends Events{
    private final int newValue;

    public int getNewValue(){
        return this.newValue;
    }
    public ValueChangedEvent(String name, int newValue){
        super(name);
        this.newValue = newValue;
    }
}
