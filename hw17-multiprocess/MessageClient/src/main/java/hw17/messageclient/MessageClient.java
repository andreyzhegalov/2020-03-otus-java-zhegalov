package hw17.messageclient;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.messageclient.network.NetworkClient;
import hw17.server.message.ReciveMessage;
import hw17.server.message.ResponseMessage;
import hw17.server.message.ResponseType;

public class MessageClient {

    public enum ClientState {
        DISCONECTED, CONNECTED, REGISTRED
    }

    private final static Logger logger = LoggerFactory.getLogger(MessageClient.class);
    private final String name;
    private final NetworkClient networkClient;
    private static boolean dissableSleep = false; // for unit testing
    private ClientState currentState = ClientState.DISCONECTED;

    public MessageClient(String name, NetworkClient networkClient) {
        this.name = name;
        this.networkClient = networkClient;
        this.networkClient.setResponseHandler(data -> responseHandler(data));
    }

    public void connect() {
        tryConnected();
        tryRegistred();
    }

    public void send(String  toClient, String message){
        if(currentState != ClientState.REGISTRED){
            throw new MessageClientException("Unable send message from state " + currentState);
        }
        final ReciveMessage requestMessage = new ReciveMessage(name, toClient, message);
        networkClient.send(requestMessage.toJson());
    }

    public ClientState getCurrentState(){
        return currentState;
    }

    private void responseHandler(String response) {
        logger.debug("current client state {}", currentState.name());
        logger.debug("recive response {}", response);
        switch (currentState) {
            case CONNECTED:
                onConnected(response);
                break;
            case REGISTRED:
                break;
            default:
                throw new MessageClientException("Unable handled response in state " + currentState);
        }
    }

    private void onConnected(String response) {
        final var registredStr = new ResponseMessage(ResponseType.REGISTRED).toJson();
        if (response.equals(registredStr)) {
            currentState = ClientState.REGISTRED;
            logger.debug("current state changed to {}", currentState);
        }
    }

    private void tryConnected() {
        try {
            networkClient.connect();
        } catch (Exception e) {
            throw new MessageClientException("Could't connect to Message server");
        }
        for (int i = 10; i >= 0; i--) { // waiting 10 seconds
            if (networkClient.isConnected()) {
                break;
            }
            if (i == 0) {
                throw new MessageClientException("Connected to message server time out");
            }
            sleep();
        }
        currentState = ClientState.CONNECTED;
        logger.debug("current state changed to {}", currentState);
    }

    private static void sleep() {
        if (dissableSleep) { // for unit testing
            return;
        }
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void tryRegistred() {
        final ReciveMessage requestMessage = new ReciveMessage(name, "", "");
        networkClient.send(requestMessage.toJson());
    }

    public static void dissableSleep() {
        dissableSleep = true;
    }
}
