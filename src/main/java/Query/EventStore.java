package Query;

import java.util.LinkedList;

public class EventStore {

    private final LinkedList<Events> eventHistory;
    public static final EventStore singleInstance = new EventStore();
    private EventStore(){
        eventHistory = new LinkedList<>();
    }

    public LinkedList<Events> getAllEvents(){return eventHistory;}

    public void store(Events event){
        if (!( event == null)) {
            EventHandler.handle(event);
            eventHistory.add(event);
        }
    }
}
