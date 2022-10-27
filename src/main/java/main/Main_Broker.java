package main;
import org.apache.activemq.broker.BrokerService;

public class Main_Broker {

    public static void main(String[] args){
        try {
            BrokerService broker = new BrokerService();
            broker.setBrokerName("eventsBroker");
            broker.setDataDirectory("");
            broker.addConnector("tcp://localhost:61616");
            broker.start();
            System.out.println("press enter to stop the brocker");
            System.in.read();
        } catch (Exception e){System.out.println(e);}
    }
}
