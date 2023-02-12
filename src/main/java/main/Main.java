package main;

import Query.*;
import command.*;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //kill l4j output
        System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "off");
        org.slf4j.Logger log = LoggerFactory.getLogger(org.apache.kafka.clients.NetworkClient.class);
        //new CommandHandler();
        CommandHandler.setEventSender(new KafkaSender());
        List<String> topics = new ArrayList<>();
        topics.add("ItemCreatedEvent");
        topics.add("ItemMovedEvent");
        topics.add("ItemDeletedEvent");
        topics.add("ValueChangedEvent");
        KafkaConsumerLoop consumer = new KafkaConsumerLoop("Consumer123456789",topics);
        Thread consum = new Thread(consumer);
        consum.setDaemon(true);
        consum.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());;
        }
        Command command =new Command();
        //try {
//
        //    System.out.println("----Erzeuge Consumer----");
        //    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //    Connection connection = connectionFactory.createConnection();
        //    connection.start();
        //    Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //    Destination destination = session.createTopic("EVENTS.Topic");
        //    MessageConsumer consumer = session.createConsumer(destination);
        //    consumer.setMessageListener(new EventConsumer());
//
        //    System.out.println("----Erzeuge Producer----");
        //    CommandHandler commandHandler = CommandHandler.getSingleInstance(connection,session,destination);
        //} catch (JMSException e) {
        //    System.out.println(e.getMessage());
        //}

        QueryImpl query=new QueryImpl();

        command.createItem(new MovingItemDTO("TestDel",null,0,0));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("TestDel still exists : "+(query.getMovingItemByName("TestDel")!=null));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        command.deleteItem("TestDel");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("TestDel still deleted : "+(query.getMovingItemByName("TestDel")==null));

        command.createItem(new MovingItemDTO("TestDel",null,0,0));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("TestDel still exists : "+(query.getMovingItemByName("TestDel")!=null));

        command.deleteItem("TestDel");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("TestDel still deleted : "+(query.getMovingItemByName("TestDel")==null));

        System.out.println("----Erzeuge Items----");
        command.createItem(new MovingItemDTO("Test1",null,0,0));
        command.createItem(new MovingItemDTO("Test2",null,0,0));
        command.createItem(new MovingItemDTO("Test3",null,0,0));
        command.createItem(new MovingItemDTO("Test4",null,0,0));
        command.createItem(new MovingItemDTO("Test5",null,0,0));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        //Test ob Create funktioniert:
        System.out.println("Test1 was created: "+(query.getMovingItemByName("Test1")!=null));
        System.out.println("Test2 was created: "+(query.getMovingItemByName("Test2")!=null));
        System.out.println("Test3 was created: "+(query.getMovingItemByName("Test3")!=null));
        System.out.println("Test4 was created: "+(query.getMovingItemByName("Test4")!=null));
        System.out.println("Test5 was created: "+(query.getMovingItemByName("Test5")!=null));
        System.out.println("All created: "+(query.getMovingItems().size()==5));
        System.out.println("----Lösche Items----");

        command.deleteItem("Test3");
        command.deleteItem("Test4");


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //Test ob Delete funktioniert hat:
        System.out.println("Test3 was deleted: "+(query.getMovingItemByName("Test3")==null));
        System.out.println("Test4 was deleted: "+(query.getMovingItemByName("Test4")==null));


        System.out.println("----Ändere Value Items----");
        new ChangeValueCommand("Test1",123);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //Test ob Change Value funktioniert hat:
        System.out.println("Test1 has new Value: "+(query.getMovingItemByName("Test1").getValue()==123));

        command.changeValue("Test2",-1337);
        command.changeValue("Test5",69);
        command.changeValue("Test1",420);
        command.changeValue("Test3",5); //Achtung Test3 wurde bereits gelöscht

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //Test ob Change Value funktioniert hat:
        System.out.println("Test1 has new Value: "+(query.getMovingItemByName("Test1").getValue()==420));
        System.out.println("Test2 has new Value: "+(query.getMovingItemByName("Test2").getValue()==-1337));
        System.out.println("Test5 has new Value: "+(query.getMovingItemByName("Test5").getValue()==69));
        System.out.println("Test3 still deleted: "+(query.getMovingItemByName("Test3")==null));


        System.out.println("----Bewege Items----");

        command.moveItem("Test1", new int[]{1, -2, - 3});//Test1(1,-2,-3)
        //test ob move Item funktioniert hat:

        command.moveItem("Test2", new int[]{1, -5, - 3});//Test2(1,-5,-3)

        command.moveItem("Test5",new int[]{4,5,-3});//Test5(4,5,-3)

        command.moveItem("Test1",new int[]{4,5,0});//Neue Position Test1(5,3,-3)

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //test ob move Item funktioniert hat:
        Position test1Location=query.getMovingItemByName("Test1").getLocation();
        boolean test1PositionCorrect=test1Location.equals(new Position(5,3,-3));
        Position test2Location=query.getMovingItemByName("Test2").getLocation();
        boolean test2PositionCorrect=test2Location.equals(new Position(1,-5,-3));
        Position test5Location=query.getMovingItemByName("Test5").getLocation();
        boolean test5PositionCorrect=test5Location.equals(new Position(4,5,-3));
        System.out.println("Test1 has Correct Position :"+test1PositionCorrect);
        System.out.println("Test2 has Correct Position :"+test2PositionCorrect);
        System.out.println("Test5 has Correct Position :"+test5PositionCorrect);

        System.out.println("----Teste Get Item At Position----");
        //Teste, ob Get Item at position funktioniert:
        int[] targetPos= new int[3];
        targetPos[0]=5;
        targetPos[1]=3;
        targetPos[2]=-3;
        String itemName = query.getMovingItemsAtPosition(targetPos).iterator().next().getName();

        System.out.println("Correct Item At Position: "+(itemName.equals("Test1")));
        command.moveItem("Test2",new int[]{4,8,0});//Neue Position Test2(5,3,-3)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        int numberOfItems= query.getMovingItemsAtPosition(targetPos).size();
        itemName=query.getMovingItemsAtPosition(targetPos).iterator().next().getName();
        System.out.println("Correct Item At Position: "+(itemName.equals("Test2")&&numberOfItems==1));



        System.out.println("----Bewege Objekt bis es gelöscht wird----");

        command.createItem(new MovingItemDTO("Test6",null,0,0));

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i=0;i<20;++i){
            command.moveItem("Test6",new int[]{1,1,1});
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Item Test6 deleted after 20 moves: "+(query.getMovingItemByName("Test6")==null));
        System.out.println("----Lösche Items----");
        command.deleteItem("Test1");
        command.deleteItem("Test2");
        command.deleteItem("Test5");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //teste, ob alle Items gelöscht wurden:
        System.out.println("Test1 has been deleted:"+ (query.getMovingItemByName("Test1")==null));
        System.out.println("Test2 has been deleted:"+ (query.getMovingItemByName("Test2")==null));
        System.out.println("Test5 has been deleted:"+ (query.getMovingItemByName("Test5")==null));

        System.out.println("---- Nothing will change form here on out ----");
        System.out.println("Test1 still deleted: "+(query.getMovingItemByName("Test1")==null));
        System.out.println("Test2 still deleted: "+(query.getMovingItemByName("Test2")==null));
        System.out.println("Test3 still deleted: "+(query.getMovingItemByName("Test3")==null));
        System.out.println("Test4 still deleted: "+(query.getMovingItemByName("Test4")==null));
        System.out.println("Test5 still deleted: "+(query.getMovingItemByName("Test5")==null));
        System.out.println("Test6 still deleted: "+(query.getMovingItemByName("Test6")==null));

        System.out.println("----End of Programm----");


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Viele zufällige Bewegungen
        //moveTest moveTest = new moveTest();
        //moveTest.create_MovingObjects();
        //moveTest.moveObjects();
        //moveTest.printExpectedPositions();
        //moveTest.printTruePositions();
    }
}
