package Query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class EventsSerialiser implements Serializer {
    private final ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public byte[] serialize(String topic, Object event) {
        Events data = (Events) event;
        try {
            if (data == null){
                System.out.println("Null received at serializing");
                return null;
            }
            //System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing MessageDto to byte[]");
        }
    }

    @Override
    public void close() {
    }
}