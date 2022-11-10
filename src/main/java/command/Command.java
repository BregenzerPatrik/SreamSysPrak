package command;

import Query.MovingItem;

public class Command implements Commands{
    @Override
    public void createItem(MovingItem movingItem){
        CreateItemCommand com = new CreateItemCommand(movingItem.getName());
    }

    public void deleteItem(String id){
        DeleteItemCommand com = new DeleteItemCommand(id);
    }
    public void moveItem(String id, int[] vector){
        Position pos = new Position(vector[0],vector[1],vector[2]);
        MoveItemCommand com = new MoveItemCommand(id,pos);
    }
    public void changeValue(String id, int newValue){
        ChangeValueCommand com = new ChangeValueCommand(id,newValue);
    }

}
