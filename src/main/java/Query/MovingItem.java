package Query;

import command.Position;


public interface MovingItem
{
    String getName();
    Position getLocation();
    int getNumberOfMoves();
    int getValue();
}
