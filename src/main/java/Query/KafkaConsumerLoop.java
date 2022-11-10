package Query;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaConsumerLoop implements Runnable {
    private final KafkaConsumer<String, Events> consumer;
    private final List<String> topics;
    private final AtomicBoolean shutdown;
    private final CountDownLatch shutdownLatch;
    private final String ConsumerGroupId = "singleGroup";

    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092";

    public KafkaConsumerLoop(String id, List<String> topics) {
        System.out.println("----Erzeuge Consumer----");

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, id);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,ConsumerGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,CustomDeserializer.class.getName());
        this.consumer = new KafkaConsumer<>(props);
        this.topics = topics;
        this.shutdown = new AtomicBoolean(false);
        this.shutdownLatch = new CountDownLatch(1);
    }


    public void run() {
        try {
            System.out.println("---Starte Consumer---");
            //consumer.subscribe(topics);
            ArrayList<TopicPartition> tps = new ArrayList<>();
            topics.forEach(t-> tps.add(new TopicPartition(t,0)));
            consumer.assign(tps);
            consumer.seekToBeginning(tps);
            Duration duration1 = Duration.ofMillis(500);
            ConsumerRecords<String, Events> records1 = consumer.poll(duration1);

            for (ConsumerRecord<String, Events> record : records1) {

                EventHandler.handle(record.value());
            }
            while (!shutdown.get()) {
                Duration duration = Duration.ofMillis(500);
                ConsumerRecords<String, Events> records = consumer.poll(duration);

                for (ConsumerRecord<String, Events> record : records) {
                    EventHandler.handle(record.value());
                }


            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---Beende Consumer---");
            consumer.close();
            shutdownLatch.countDown();
        }
    }

    public void shutdown() throws InterruptedException {
        shutdown.set(true);
        shutdownLatch.await();
    }
}
