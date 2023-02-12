package command;

import Query.Events;

import javax.jms.*;

public class KafkaSender implements EventSender{

    public KafkaSender() {
    }
    public void  sendEvent(Events event){
        try{
            KafkaProducer.sendEvent(event);
        }catch(Exception e){System.out.println(e);}
    }
}
