package command;

import Query.MovingItem;

public class CommandsAcces implements Commands{
    @Override
    public void createItem(MovingItem movingItem) {
        new CreateItemCommand(movingItem.getName());
    }

    @Override
    public void deleteItem(String id) {
        new DeleteItemCommand(id);
    }

    @Override
    public void moveItem(String id, int[] vector) {
        new MoveItemCommand(id,new Position(vector[0],vector[1],vector[2]));
    }

    @Override
    public void changeValue(String id, int newValue) {
        new ChangeValueCommand(id,newValue);
    }
}
