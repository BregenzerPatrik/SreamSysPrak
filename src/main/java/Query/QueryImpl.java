package Query;

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
        return ItemStore.singleInstance.getMovingItemsAtPosition(position);
    }
}
