package hw17.messageclient;

import java.util.concurrent.TimeUnit;

public class MessageClientDemo {

    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 8080;
        final Client client = new Client(HOST, PORT, "");
        client.connect();

        while(!client.isConnected()){
            sleep();
        }

        client.send("test");
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

