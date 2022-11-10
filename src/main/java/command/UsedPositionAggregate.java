package command;

import Query.Events;
import Query.ItemCreatedEvent;
import Query.ItemDeletedEvent;
import Query.ItemMovedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.*;

public class UsedPositionAggregate {
    private static TreeMap<Long,ConsumerRecord<String, Events>>  sortedRecords=null;
    private static TreeMap<Long,ConsumerRecord<String, Events>> getTimeSortedRecords(ConsumerRecords<String,Events> inputRecords){
        TreeMap<Long,ConsumerRecord<String, Events>> sortingMap= new TreeMap<>();
        long lastTimestamp=0;
        for (ConsumerRecord<String, Events> record : inputRecords){
            sortingMap.put(record.timestamp(), record);
        }
        return sortingMap;
    }

    public static void  updateSortedEvents(){
        List<String> topics = new ArrayList<>();
        topics.add("ItemCreatedEvent");
        topics.add("ItemDeletedEvent");
        topics.add("ItemMovedEvent");
        ConsumerRecords<String, Events> records = DomainModelConsumer.getPreviousEvents("UsedPositionConsumer",
                "UsedPositionConsumer",
                topics);
        TreeMap<Long,ConsumerRecord<String, Events>> sortedRecords=getTimeSortedRecords(records);
        UsedPositionAggregate.sortedRecords = sortedRecords;
    }
    private static Map<String,Position> getCurrentState() {
         //returns a map with key = NameOfObject, Value=Position of Object

        Map<String,Position> result= new TreeMap<String,Position>() ;

        for (long timestamp : sortedRecords.keySet()) {
            ConsumerRecord<String, Events> record = sortedRecords.get(timestamp);
            if (record.key().contains("ItemCreatedEvent")){
                ItemCreatedEvent castedEvent = (ItemCreatedEvent) record.value();
                result.put(castedEvent.getName(),new Position(0,0,0));
                }
            if (record.key().contains("ItemDeletedEvent")) {
                ItemDeletedEvent castedEvent = (ItemDeletedEvent) record.value();
                result.remove(castedEvent.getName());
            }
            if(record.key().contains("ItemMovedEvent")){
                ItemMovedEvent castedEvent = (ItemMovedEvent) record.value();
                String currentName = castedEvent.getName();
                Position currentPosition= result.get(currentName);
                Position newPosition = currentPosition.add(castedEvent.getMoveCoords());
                result.remove(currentName);
                result.put(currentName,newPosition);
            }
        }
        return result;

        }

    public static boolean isPositionUsed(Position position){
        if(position==null){
            return false;
        }
        else {
            boolean result =getCurrentState().containsValue(position);
            return result;
        }
    }

    public static String getItemNameByPosition(Position position){
        if(position.isZeroMove()){
            //There may be more than one Item at Position(0,0,0)
            return null;
        }
        for (Map.Entry<String, Position> entry : getCurrentState().entrySet()) {
            if (entry.getValue().equals(position)){
                return entry.getKey();
            }
        }
        return null;
    }

    public static Position getPositionByName(String name) {
        if (getCurrentState().containsKey(name)){
            return getCurrentState().get(name);
        }
        return null;
    }
}
