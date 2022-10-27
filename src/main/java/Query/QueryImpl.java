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
    public Collection<MovingItemDTO> getMovingItemsAtPosition(Position position) {
        return ItemStore.singleInstance.getMovingItemsAtPosition(position);
    }
}
