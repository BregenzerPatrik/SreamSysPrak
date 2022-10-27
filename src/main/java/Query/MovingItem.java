package Query;

import command.Position;

/**
 * Hello world!
 *
 */
public interface MovingItem
{
    String getName();
    Position getLocation();
    int getNumberOfMoves();
    int getValue();
}
