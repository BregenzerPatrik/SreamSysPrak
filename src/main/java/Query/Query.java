package Query;

import java.util.Collection;

public interface Query {
    MovingItemDTO getMovingItemByName(String name);
    Collection<MovingItemDTO> getMovingItems();
    Collection<MovingItemDTO> getMovingItemsAtPosition(int[] position);


}