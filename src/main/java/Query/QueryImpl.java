package Query;

import command.Position;

import java.util.Collection;

public class QueryImpl implements Query{

    @Override
    public MovingItemDTO getMovingItemByName(String name) {
        return ItemStore.singleInstance.getMovingItemByName(name);
    }

    @Override
    public Collection<MovingItemDTO> getMovingItems() {
        return ItemStore.singleInstance.getMovingItems();
    }

    @Override
    public Collection<MovingItemDTO> getMovingItemsAtPosition(int[] position) {
        //Position pos= new Position(position[0],position[1],position[2]);
        return ItemStore.singleInstance.getMovingItemsAtPosition(position);
    }
}
