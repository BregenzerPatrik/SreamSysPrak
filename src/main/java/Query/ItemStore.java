package Query;

import command.Position;

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
    public Collection<MovingItemDTO> getMovingItemsAtPosition(Position position) {
        Collection<MovingItemDTO> result= new ArrayList<>();
        for (MovingItemDTO item:items.values()) {
            if (item.getLocation().getX()==position.getX() && item.getLocation().getY()==position.getY() && item.getLocation().getZ()==position.getZ()){
                result.add(item);
            }
        }
        return result;
    }
}
