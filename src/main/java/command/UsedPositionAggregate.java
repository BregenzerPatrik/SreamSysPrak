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
            boolean result =getCurrentState().containsValue(position);
            return result;
        }
    }

    public static String getItemNameByPosition(Position position){
        if(position.isZeroMove()){
            //There may be more than one Item at Position(0,0,0)
            return null;
        }
        for (Map.Entry<String, Position> entry : getCurrentState().entrySet()) {
            if (entry.getValue().equals(position)){
                return entry.getKey();
            }
        }
        return null;
    }

    public static Position getPositionByName(String name) {
        if (getCurrentState().containsKey(name)){
            return getCurrentState().get(name);
        }
        return null;
    }
}
