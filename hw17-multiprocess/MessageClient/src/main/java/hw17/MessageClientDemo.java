package hw17;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.messageclient.MessageClient;
import hw17.messageclient.network.ClientNIO;
import hw17.messageclient.network.NetworkClient;

public class MessageClientDemo {
    private final static Logger logger = LoggerFactory.getLogger(MessageClientDemo.class);

    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 8080;
        final NetworkClient client = new ClientNIO(HOST, PORT);
        final var messageClient  = new MessageClient("front", client);

        messageClient.connect();

        // needed send response {"response":"REGISTRED"}
        logger.info("Waiting response .... ");

        sleep(5);

        messageClient.send("db", "test");

        // client.setResponseHandler(data->clientResponseHandler(data));
        // client.connect();
        //
        // while(!client.isConnected()){
        //     sleep();
        // }

    }

    private static void clientResponseHandler(String response){
        logger.info("response form client {}", response);
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

