package command;

import Query.CustomDeserializer;
import Query.Events;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class DomainModelConsumer {
    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092";
    private final static Map<String,KafkaConsumer<String,Events>> consumers = new HashMap<>();
    public static ConsumerRecords<String, Events> getPreviousEvents(String GroupID,String id,List<String> topics){
        KafkaConsumer<String, Events> consumer;
        if (!consumers.containsKey(id)) {
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            props.put(ConsumerConfig.CLIENT_ID_CONFIG, id);
            props.put(ConsumerConfig.GROUP_ID_CONFIG,GroupID);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class.getName());
            consumer = new KafkaConsumer<>(props);
        }else {
            consumer= consumers.get(id);
        }
        ArrayList<TopicPartition> tps = new ArrayList<>();
        topics.forEach(t-> tps.add(new TopicPartition(t,0)));
        consumer.assign(tps);
        consumer.seekToBeginning(tps);
        Duration duration1 = Duration.ofMillis(500);
        ConsumerRecords<String, Events> result = consumer.poll(duration1);
        consumer.close();
        return result;
    }
}
