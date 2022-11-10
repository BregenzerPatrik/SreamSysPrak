package command;

import Query.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class CommandHandler {
    private MessageProducer producer = null;
    private static CommandHandler singleInstance= null;
    private  Session session = null;
    private Destination destination=null;
    private Connection connection = null;
    private static final String brokerURL="tcp://localhost:61616";
    private static final String topicString="EVENTS.Topic";

    public CommandHandler(Connection connection, Session session, Destination destination) {
        if (singleInstance == null) {
            this.connection=connection;
            this.session=session;
            this.destination=destination;
            try {
                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                this.producer = producer;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static CommandHandler getSingleInstance(Connection connection, Session session, Destination destination) {
        if (CommandHandler.singleInstance== null) {
            CommandHandler.singleInstance = new CommandHandler(connection,session,destination);

        }
        return CommandHandler.singleInstance;
    }
    public static CommandHandler getSingleInstance() {
        if (CommandHandler.singleInstance== null) {
            return null;
        }
        return CommandHandler.singleInstance;
    }



    public  static void handle(ChangeValueCommand command){
    ValueChangedEvent event= new ValueChangedEvent(command.getName(),command.getValueData());
        try{
            KafkaProducer.sendEvent(event);
        }catch(Exception e){System.out.println(e);}
    }
    public static void handle(CreateItemCommand command){
        UsedNamesAggregate.updateSortedEvents();
        if(!UsedNamesAggregate.isNameUsed(command.getName())){
            ItemCreatedEvent event=new ItemCreatedEvent(command.getName(), new Position(0,0,0),0,0);

            try{
                KafkaProducer.sendEvent(event);
            }catch(Exception e){System.out.println(e);}


        }
    }
    public static void handle(DeleteItemCommand command){
        UsedNamesAggregate.updateSortedEvents();
        if (UsedNamesAggregate.isNameUsed(command.getName())){
            ItemDeletedEvent event = new ItemDeletedEvent(command.getName());
            try{
                KafkaProducer.sendEvent(event);

            }catch(Exception e){System.out.println(e);}
        }
    }
    public static void handle(MoveItemCommand command){
        UsedPositionAggregate.updateSortedEvents();
        UsedNamesAggregate.updateSortedEvents();
        if (command.getMoveCoords().isZeroMove()){
            //atempted move with zero vector
            return;
        }
        if(NumberOfMovesAggregate.isMoveLimitReached(command.getName())){
            //attempted movement when movement limit is reached
            new DeleteItemCommand(command.getName());
            return;
        }
        Position oldPosition= UsedPositionAggregate.getPositionByName(command.getName());
        if(oldPosition==null){
            //attempted movement of non existent object name
            return;
        }
        Position newPosition= oldPosition.add(command.getMoveCoords());
        if(UsedPositionAggregate.isPositionUsed(newPosition)){
            //remove old object if position is already occupied
            String nameOfOldItemAtNewPosition = UsedPositionAggregate.getItemNameByPosition(newPosition);
            new DeleteItemCommand(nameOfOldItemAtNewPosition);
        }
        ItemMovedEvent event=new ItemMovedEvent(command.getName(), command.getMoveCoords());
        //UsedPositionAggregate.changePosition(command.getName(),newPosition);
        try{
            KafkaProducer.sendEvent(event);

        }catch(Exception e){System.out.println(e);}    }
}
