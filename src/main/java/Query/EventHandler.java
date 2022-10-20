package Query;

public class EventHandler {


    public static void handle(ItemCreatedEvent event) {
        MovingItemDTO item = new MovingItemDTO(event.getName(),
                event.getLocation(),
                event.getNumberOfMoves(),
                event.getValue());
        ItemStore.singleInstance.addToStore(item);
    }


    public static void handle(ItemDeletedEvent event) {
        ItemStore.singleInstance.removeFromStore(event.getTargetId());
    }


    public static void handle(ItemMovedEvent event) {
        MovingItemDTO item = ItemStore.singleInstance.getMovingItemByName(event.name);
        if (item != null) {
            item.move(event.getMoveCoords());
        }
    }

    public static void handle(ValueChangedEvent event) {
        MovingItemDTO item = ItemStore.singleInstance.getMovingItemByName(event.getName());
        if (item != null) {
            item.changeValue(event.getNewValue());
        }
    }

    public static void handle(Events event) {
        if(event instanceof ValueChangedEvent){
            handle((ValueChangedEvent)event);
        }
        if(event instanceof ItemMovedEvent){
            handle((ItemMovedEvent) event);
        }
        if (event instanceof ItemDeletedEvent) {
            handle((ItemDeletedEvent) event);
        }
        if (event instanceof ItemCreatedEvent){
            handle((ItemCreatedEvent) event);
        }
    }
}
