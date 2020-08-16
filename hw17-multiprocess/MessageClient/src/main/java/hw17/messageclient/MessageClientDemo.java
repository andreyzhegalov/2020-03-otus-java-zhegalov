package hw17.messageclient;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.messageclient.network.ClientNIO;

public class MessageClientDemo {
    private final static Logger logger = LoggerFactory.getLogger(MessageClientDemo.class);

    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 8080;
        final ClientNIO client = new ClientNIO(HOST, PORT, data->clientResponseHandler(data));
        client.connect();

        while(!client.isConnected()){
            sleep();
        }

        client.send("test");
    }

    private static void clientResponseHandler(String response){
        logger.info("response form client {}", response);
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

