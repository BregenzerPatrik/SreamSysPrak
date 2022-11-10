package Query;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.SerializationUtils;

public class CustomDeserializer<T extends Serializable> implements Deserializer<T> {

    public static final String VALUE_CLASS_NAME_CONFIG = "value.class.name";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(String topic, byte[] objectData) {
        String eventSting = new String(objectData, StandardCharsets.UTF_8);
        System.out.println(eventSting);
        System.out.println(topic);
        Gson gson = new Gson();
        Events event = null;
        if (topic.equals("ItemCreatedEvent")) {
            try{
               event = gson.fromJson(eventSting, ItemCreatedEvent.class);
            } catch (Exception e ){System.out.println(e);}
        }
        else if (topic.equals("ItemDeletedEvent")) {
            event= gson.fromJson(eventSting, ItemDeletedEvent.class);
        }
        else if (topic.equals("ItemMovedEvent")) {
            try{
            event =  gson.fromJson(eventSting, ItemMovedEvent.class);
            } catch (Exception e ){System.out.println(e);}

        }
        else if (topic.equals("ValueChangedEvent")) {
            event = gson.fromJson(eventSting, ValueChangedEvent.class);
        }
        else {
            System.out.println("ERROR event of unknown type was sent");
            return null;
        }
        System.out.println(event.toString());
        return (T) event;
    }

    @Override
    public void close() {
    }
}