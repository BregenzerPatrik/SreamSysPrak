package main;

import Query.*;
import command.KafkaProducer;
import command.Position;

import java.util.ArrayList;
import java.util.List;

public class Main_Kafka_Test {
    public static void main(String[] args) {
       //kafkaTest test1= new kafkaTest();
       //KafkaTemplate<String,String> template = test1.kafkaTemplate();
       //kafkaTest2 test2= new kafkaTest2(template);
       //test2.produce("Hallo Test");
       //System.out.println("Produce fertig");
        try {
            List<String> topics = new ArrayList<>();
            topics.add("ItemCreatedEvent");
            topics.add("ItemMovedEvent");
            topics.add("ItemDeletedEvent");
            topics.add("ValueChangedEvent");
            KafkaConsumerLoop consumer = new KafkaConsumerLoop("Consumer123456789",topics);
            Thread thread = new Thread(consumer);
            thread.setDaemon(false);
            thread.start();
            Thread.sleep(1000);

            KafkaProducer.sendEvent(new ItemCreatedEvent("Test",new Position(0,0,0),0,0));
            KafkaProducer.sendEvent(new ItemMovedEvent("Test",new Position(1,2,3)));
            KafkaProducer.sendEvent(new ValueChangedEvent("Test",5));
            KafkaProducer.sendEvent(new ItemDeletedEvent("Test"));
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
