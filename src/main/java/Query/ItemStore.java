package Query;

import java.util.*;

public class ItemStore implements Query{
    private final Map<String, MovingItemDTO> items;
    public static final ItemStore singleInstance = new ItemStore();
    private ItemStore(){
        this.items= new HashMap<>();
    }
    public void addToStore(MovingItemDTO item){
        if (!(item == null)) {
            items.put(item.getName(), item);
        }
    }
    public void removeFromStore(String name ){
        if ((items.containsKey(name)) ){
            this.items.remove(name);
            }
        }

    @Override
    public MovingItemDTO getMovingItemByName(String name) {
        if(items.containsKey(name)){
            return items.get(name);
        }
        return null;
    }

    @Override
    public Collection<MovingItemDTO> getMovingItems() {
        return items.values();
    }

    @Override
    public Collection<MovingItemDTO> getMovingItemsAtPosition(int[] position) {
        Collection<MovingItemDTO> result= new ArrayList<>();
        for (MovingItemDTO item:items.values()) {
            if (item.getLocation()[0]==position[0] && item.getLocation()[1]==position[1] && item.getLocation()[2]==position[2]){
                result.add(item);
            }
        }
        return result;
    }
}
