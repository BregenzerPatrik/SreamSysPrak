package Query;

import command.Position;

import java.util.Collection;

public interface Query {
    MovingItemDTO getMovingItemByName(String name);
    Collection<MovingItemDTO> getMovingItems();
    Collection<MovingItemDTO> getMovingItemsAtPosition(Position position);


}