package main;

import Query.EventConsumer;
import Query.QueryImpl;
import command.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Main {
    public static void main(String[] args) {

        //Erzeuge Consumer
        try {
            System.out.println("----Erzeuge Consumer----");
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("EVENTS.Topic");
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new EventConsumer());

            System.out.println("----Erzeuge Producer----");
            CommandHandler commandHandler = CommandHandler.getSingleInstance(connection,session,destination);
        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("----Erzeuge Items----");
        QueryImpl query=new QueryImpl();
        new CreateItemCommand("Test1");
        new CreateItemCommand("Test1");
        new CreateItemCommand("Test2");
        new CreateItemCommand("Test3");
        new CreateItemCommand("Test4");
        new CreateItemCommand("Test5");
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

        new DeleteItemCommand("Test3");
        new DeleteItemCommand("Test4");

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

        new ChangeValueCommand("Test2",-1337);
        new ChangeValueCommand("Test5",69);
        new ChangeValueCommand("Test1",420);
        new ChangeValueCommand("Test3",5); //Achtung Test3 wurde bereits gelöscht

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
        Position move=new Position(1,-2,-3);
        new MoveItemCommand("Test1",move);//Test1(1,-2,-3)

        //test ob move Item funktioniert hat:
        move=new Position(1,-5,-3);
        new MoveItemCommand("Test2",move);//Test2(1,-5,-3)
        move=new Position(4,5,-3);
        new MoveItemCommand("Test5",move);//Test5(4,5,-3)
        move=new Position(4,5,0);
        new MoveItemCommand("Test1",move);//Neue Position Test1(5,3,-3)

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
        Position targetPos=new Position(5,3,-3);
        String itemName = query.getMovingItemsAtPosition(targetPos).iterator().next().getName();

        System.out.println("Correct Item At Position: "+(itemName.equals("Test1")));
        move=new Position(4,8,0);
        new MoveItemCommand("Test2",move);//Neue Position Test2(5,3,-3)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        int numberOfItems= query.getMovingItemsAtPosition(targetPos).size();
        itemName=query.getMovingItemsAtPosition(targetPos).iterator().next().getName();
        System.out.println("Correct Item At Position: "+(itemName.equals("Test2")&&numberOfItems==1));



        System.out.println("----Bewege Objekt bis es gelöscht wird----");

        new CreateItemCommand("Test6");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i=0;i<20;++i){
            new MoveItemCommand("Test6",new Position(1,1,1));
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Item Test6 deleted after 20 moves: "+(query.getMovingItemByName("Test6")==null));
        System.out.println("----Lösche Items----");
        new DeleteItemCommand("Test1");
        new DeleteItemCommand("Test2");
        new DeleteItemCommand("Test5");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //teste, ob alle Items gelöscht wurden:
        System.out.println("Test1 has been deleted:"+ (query.getMovingItemByName("Test1")==null));
        System.out.println("Test2 has been deleted:"+ (query.getMovingItemByName("Test2")==null));
        System.out.println("Test5 has been deleted:"+ (query.getMovingItemByName("Test5")==null));


        System.out.println("----Random Bewegungen Items----");

        //Viele zufällige Bewegungen
        moveTest moveTest = new moveTest();
        moveTest.create_MovingObjects();
        moveTest.moveObjects();
        moveTest.printExpectedPositions();
        moveTest.printTruePositions();
    }
}
