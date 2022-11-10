package command;

import Query.Events;
import Query.EventsSerialiser;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class KafkaProducer {
    private static Producer <String,Events> producer = createProducer();
    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";

    private static Producer<String, Events> createProducer() {
        Properties props = new Properties();
        props.put("acks","all");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                EventsSerialiser.class.getName());
        return new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }


    public static void sendEvent(final Events event) throws InterruptedException {

        try {
                final ProducerRecord<String, Events> record =
                        new ProducerRecord<>(event.getEventType(), event.getClass().toString(), event);
                producer.send(record, (metadata, exception) -> {
                    System.out.println("----Event versendet----");
                });
        } finally {
            producer.flush();
        }
    }
}