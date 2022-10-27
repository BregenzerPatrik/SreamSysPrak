package command;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UsedPositionAggregate {
    private static Map<String,Position> allUsedPositions = new TreeMap<>();

    public static boolean isPositionUsed(Position position){
        if(position==null){
            return false;
        }
        else {
            boolean result =allUsedPositions.containsValue(position);
            return result;
        }
    }

    public static String getItemNameByPosition(Position position){
        if(position.isZeroMove()){
            //There may be more than one Item at Position(0,0,0)
            return null;
        }
        for (Map.Entry<String, Position> entry : allUsedPositions.entrySet()) {
            if (entry.getValue().equals(position)){
                return entry.getKey();
            }
        }
        return null;
    }

    public static void changePosition(String name,Position newPosition){
         String nameOfObjectAtTargetPosition = getItemNameByPosition(newPosition);

        if(nameOfObjectAtTargetPosition!=null){
            UsedPositionAggregate.removeItem(nameOfObjectAtTargetPosition);
        }
        UsedPositionAggregate.removeItem(name);
        allUsedPositions.put(name, newPosition);
    }

    public static void addUnmovedItem(String name){
        if(!allUsedPositions.containsKey(name)){
            allUsedPositions.put(name,new Position(0,0,0));
        }
    }
    public static void removeItem(String name){
        if(allUsedPositions.containsKey(name)){
            allUsedPositions.remove(name);
        }
    }

    public static Position getPositionByName(String name) {
        if (allUsedPositions.containsKey(name)){
            return allUsedPositions.get(name);
        }
        return null;
    }
}
