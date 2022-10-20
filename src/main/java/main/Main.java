package main;

import Query.QueryImpl;
import command.*;

public class Main {
    public static void main(String[] args) {
        QueryImpl query=new QueryImpl();
        new CreateItemCommand("Test1");
        new CreateItemCommand("Test2");
        new CreateItemCommand("Test3");
        new CreateItemCommand("Test4");
        new CreateItemCommand("Test5");

        //Test ob Create funktioniert:
        System.out.println(query.getMovingItemByName("Test1"));
        System.out.println(query.getMovingItemByName("Test2"));
        System.out.println(query.getMovingItemByName("Test3"));
        System.out.println(query.getMovingItemByName("Test4"));
        System.out.println(query.getMovingItemByName("Test5"));

        new DeleteItemCommand("Test3");
        new DeleteItemCommand("Test4");

        //Test ob Delete funktioniert hat:
        System.out.println((query.getMovingItemByName("Test3")==null));
        System.out.println((query.getMovingItemByName("Test4")==null));


        new ChangeValueCommand("Test1",123);

        //Test ob Change Value funktioniert hat:
        System.out.println(query.getMovingItemByName("Test1"));

        new ChangeValueCommand("Test2",-1337);
        new ChangeValueCommand("Test5",69);
        new ChangeValueCommand("Test1",420);
        new ChangeValueCommand("Test3",5); //Achtung Test3 wurde bereits gelöscht

        //Test ob Change Value funktioniert hat:
        System.out.println(query.getMovingItemByName("Test1"));
        System.out.println(query.getMovingItemByName("Test2"));
        System.out.println(query.getMovingItemByName("Test5"));
        System.out.println((query.getMovingItemByName("Test3")==null));

        int[] move=new int[3];
        move[0]=1;
        move[1]=-2;
        move[2]=-3;
        new MoveItemCommand("Test1",move);//Test1(1,-2,-3)

        //test ob move Item funktioniert hat:
        System.out.println(query.getMovingItemByName("Test1"));
        move[1]=5;
        new MoveItemCommand("Test2",move);//Test2(1,5,-3)
        move[0]=4;
        new MoveItemCommand("Test5",move);//Test5(4,5,-3)
        move[2]=0;
        new MoveItemCommand("Test1",move);//Neue Position Test1(5,3,-3)
        move[0]=4;
        move[1]=-2;

        //test ob move Item funktioniert hat:
        System.out.println(query.getMovingItemByName("Test1"));
        System.out.println(query.getMovingItemByName("Test2"));
        System.out.println(query.getMovingItemByName("Test5"));

        //Teste, ob Get Item at position funktioniert:
        int[] targetPos=new int[3];
        targetPos[0]=5;
        targetPos[1]=3;
        targetPos[2]=-3;
        System.out.println(query.getMovingItemsAtPosition(targetPos));
        new MoveItemCommand("Test2",move);//Neue Position Test2(5,3,-3)
        System.out.println(query.getMovingItemsAtPosition(targetPos));


        new DeleteItemCommand("Test1");
        new DeleteItemCommand("Test2");
        new DeleteItemCommand("Test5");

        //teste, ob alle Items gelöscht wurden:
        System.out.println(query.getMovingItems());


        //Viele zufällige Bewegungen
        moveTest moveTest = new moveTest();
        moveTest.create_MovingObjects();
        moveTest.moveObjects();
        moveTest.printExpectedPositions();
        moveTest.printTruePositions();
    }
}
