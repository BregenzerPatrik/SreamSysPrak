package command;

import Query.ItemCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.lang.reflect.Array;
import java.util.*;

import Query.Events;
import Query.ItemDeletedEvent;

public class UsedNamesAggregate {
    private static ArrayList<String> usedNames=new ArrayList<>();

    private static TreeMap<Long,ConsumerRecord<String, Events>> getTimeSortedRecords(ConsumerRecords<String,Events> inputRecords){
        TreeMap<Long,ConsumerRecord<String, Events>> sortingMap= new TreeMap<>();
        long lastTimestamp=0;
        for (ConsumerRecord<String, Events> record : inputRecords){
            sortingMap.put(record.timestamp(), record);
        }
        return sortingMap;
    }
    public static boolean isNameUsed(String name){
        List<String> topics = new ArrayList<>();
        topics.add("ItemCreatedEvent");
        topics.add("ItemDeletedEvent");
        ConsumerRecords<String,Events> records = DomainModelConsumer.getPreviousEvents("UsedNamesConsumer",
                                                                                "UsedNamesConsumer",
                                                                                topics);
        TreeMap<Long,ConsumerRecord<String, Events>> sortedRecords=getTimeSortedRecords(records);
        boolean nameIsUsed = false;
        for (long timestamp : sortedRecords.keySet()) {
            ConsumerRecord<String, Events> record = sortedRecords.get(timestamp);
            if (record.key().contains("ItemCreatedEvent")){
                ItemCreatedEvent castedEvent= (ItemCreatedEvent) record.value();
                if (Objects.equals(castedEvent.getName(), name)){
                    nameIsUsed = true;

                }
            }
            if (record.key().contains("ItemDeletedEvent")){
                ItemDeletedEvent castedEvent= (ItemDeletedEvent) record.value();
                if (Objects.equals(castedEvent.getName(), name)){
                    nameIsUsed = false;
                }
            }
        }
        return nameIsUsed;
    }


}
