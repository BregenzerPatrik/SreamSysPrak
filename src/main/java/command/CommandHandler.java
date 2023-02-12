package command;

import Query.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class CommandHandler {

    private static CommandHandler singleInstance= null;

    private static EventSender eventSender =null;

    public static void setEventSender(EventSender e){
        eventSender = e;
    }
    public CommandHandler() {
        if (singleInstance == null) {
            singleInstance=this;
        }
    }


    public static CommandHandler getSingleInstance() {
        if (CommandHandler.singleInstance== null) {
            return null;
        }
        return CommandHandler.singleInstance;
    }



    public  static void handle(ChangeValueCommand command){
        ValueChangedEvent event= new ValueChangedEvent(command.getName(),command.getValueData());
        eventSender.sendEvent(event);
    }
    public static void handle(CreateItemCommand command){
        UsedNamesAggregate.updateSortedEvents();
        if(!UsedNamesAggregate.isNameUsed(command.getName())){
            ItemCreatedEvent event=new ItemCreatedEvent(command.getName(), new Position(0,0,0),0,0);

            eventSender.sendEvent(event);
        }
    }
    public static void handle(DeleteItemCommand command){
        UsedNamesAggregate.updateSortedEvents();
        if (UsedNamesAggregate.isNameUsed(command.getName())){
            ItemDeletedEvent event = new ItemDeletedEvent(command.getName());
            eventSender.sendEvent(event);
        }
    }
    public static void handle(MoveItemCommand command) {
        UsedPositionAggregate.updateSortedEvents();
        UsedNamesAggregate.updateSortedEvents();
        if (command.getMoveCoords().isZeroMove()) {
            //atempted move with zero vector
            return;
        }
        if (NumberOfMovesAggregate.isMoveLimitReached(command.getName())) {
            //attempted movement when movement limit is reached
            new DeleteItemCommand(command.getName());
            return;
        }
        Position oldPosition = UsedPositionAggregate.getPositionByName(command.getName());
        if (oldPosition == null) {
            //attempted movement of non existent object name
            return;
        }
        Position newPosition = oldPosition.add(command.getMoveCoords());
        if (UsedPositionAggregate.isPositionUsed(newPosition)) {
            //remove old object if position is already occupied
            String nameOfOldItemAtNewPosition = UsedPositionAggregate.getItemNameByPosition(newPosition);
            new DeleteItemCommand(nameOfOldItemAtNewPosition);
        }
        ItemMovedEvent event = new ItemMovedEvent(command.getName(), command.getMoveCoords());
        //UsedPositionAggregate.changePosition(command.getName(),newPosition);
        eventSender.sendEvent(event);
    }
}
