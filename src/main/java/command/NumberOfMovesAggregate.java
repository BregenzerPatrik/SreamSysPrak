package command;

import java.util.HashMap;

public class NumberOfMovesAggregate {
    private final static HashMap<String,Integer> nameToMoveCount = new HashMap<>();
    private final static int limit = 19;
    public static void addNewItem(String name){
        nameToMoveCount.put(name,0);
    }
    public static boolean isMoveLimitReached(String name){
        return getMovesOfObject(name)>=limit;
    }
    public static int getMovesOfObject(String name){
        return nameToMoveCount.get(name);
    }
    public static void incrementMoveCount(String name){
        if (nameToMoveCount.containsKey(name)) {
            int currentCount = nameToMoveCount.get(name);
            NumberOfMovesAggregate.removeItem(name);
            nameToMoveCount.put(name,currentCount+1);
        }
    }
    public static void removeItem(String name){
        if(nameToMoveCount.containsKey(name)){
            nameToMoveCount.remove(name);
        }
    }



}
