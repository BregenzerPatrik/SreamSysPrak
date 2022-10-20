package Query;

public class MovingItemDTO implements MovingItem {
    private final String name;
    private final int[] location;
    private int numberOfMoves;
    private int value;
    public MovingItemDTO(String name, int[] location, int numberOfMoves, int value  ){
        this.name=name;
        this.location=location;
        this.numberOfMoves=numberOfMoves;
        this.value=value;
    }
    public String getName(){
        return this.name;
    }
    public int[] getLocation(){
        return this.location;
    }
    public int getNumberOfMoves(){
        return this.numberOfMoves;
    }
    public int getValue(){
        return this.value;
    }
    public void move(int[] moveCoords){
        this.location[0]=this.location[0]+moveCoords[0];
        this.location[1]=this.location[1]+moveCoords[1];
        this.location[2]=this.location[2]+moveCoords[2];
        this.numberOfMoves += 1;
    }
    public void changeValue(int newValue){
        this.value=newValue;
    }

    @Override
    public String toString() {
        return name+"("+location[0]+","+location[1]+","+location[2]+")"+"value: "+value+" number of moves:"+numberOfMoves;
    }
}
