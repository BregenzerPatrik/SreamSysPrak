package main;

import Query.MovingItemDTO;
import Query.QueryImpl;
import command.CreateItemCommand;
import command.MoveItemCommand;
import command.Position;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class moveTest {
    private final int move_min=-100;
    private final int move_max= 100;
    private int num_of_MovingObjects=5;
    private int num_of_rounds=19;
    private int[][] positions= new int[num_of_MovingObjects][3];
    private LinkedList<String> movingObjects;

    public void create_MovingObjects(){
        movingObjects = new LinkedList<>();
        QueryImpl query= new QueryImpl();
        for(int i = 0; i<num_of_MovingObjects; i++){
            String currentName="Test"+i;
            new CreateItemCommand(currentName);
            movingObjects.add(currentName);
        }
    }
    public void moveObjects(){
        for (int i=0;i<num_of_rounds;i++){
            for(int n=0;n<num_of_MovingObjects;n++){
                int randomNum1 = ThreadLocalRandom.current().nextInt(this.move_min, this.move_max + 1);
                int randomNum2 = ThreadLocalRandom.current().nextInt(this.move_min, this.move_max + 1);
                int randomNum3 = ThreadLocalRandom.current().nextInt(this.move_min, this.move_max + 1);
                Position movement_Data= new Position(randomNum1,randomNum2,randomNum3);
                positions[n][0]+=randomNum1;
                positions[n][1]+=randomNum2;
                positions[n][2]+=randomNum3;
                new MoveItemCommand(movingObjects.get(n),movement_Data);
            }

        }


    }
    public void printExpectedPositions(){
        for(int n=0;n<num_of_MovingObjects;++n) {
            System.out.print("Object " + n + ": ");
            for (int i = 0; i < 3; ++i) {
                System.out.print(this.positions[n][i]+" ");
            }
            System.out.println();
        }
    }
    public void printTruePositions(){
        QueryImpl query= new QueryImpl();
        for(int n=0;n<num_of_MovingObjects;++n){
            MovingItemDTO currentItem=query.getMovingItemByName("Test"+n);
            System.out.println("Object:"+ currentItem.toString());
        }
    }

}
