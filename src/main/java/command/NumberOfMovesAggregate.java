package command;

import Query.Events;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberOfMovesAggregate {
    private final static int limit = 19;
    public static boolean isMoveLimitReached(String name){
        List<String> topics= new ArrayList<>();
        topics.add("ItemMovedEvent");
        ConsumerRecords<String, Events> records
                = DomainModelConsumer.getPreviousEvents("NumberOfMovesConsumer","NumberOfMovesConsumer",topics);
        AtomicInteger movedEventsCouter = new AtomicInteger();
        records.forEach(rec->{
            if(rec.value().getName().equals(name)){
                movedEventsCouter.getAndIncrement();
            }
        });

        return movedEventsCouter.get()>=limit;
    }
}
