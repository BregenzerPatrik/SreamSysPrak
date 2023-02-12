package Query;

import javax.jms.*;

public class EventConsumer implements MessageListener {
    public void onMessage(Message message) {
        if(message==null){
            return;
        }
        if (message instanceof TextMessage) {
            //System.out.println("Received message: " + message);
        }
        if (message instanceof ObjectMessage){
            try {
                ObjectMessage objectMessage=(ObjectMessage) message;
                if(objectMessage!=null){
                    Events event = (Events) objectMessage.getObject();
                    if(event!=null){
                        EventHandler.handle(event);
                    }
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
