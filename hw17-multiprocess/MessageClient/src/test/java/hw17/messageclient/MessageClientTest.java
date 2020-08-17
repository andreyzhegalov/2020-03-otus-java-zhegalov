package hw17.messageclient;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import hw17.messageclient.network.NetworkClient;

public class MessageClientTest {
    private final static String CLIENT_NAME = "front";

    @Test
    public void connectToUnaviableServerTest() {
        final NetworkClient networkClientMock = Mockito.mock(NetworkClient.class);
        Mockito.doThrow(new RuntimeException()).when(networkClientMock).connect();

        final var messageClient = new MessageClient(CLIENT_NAME, networkClientMock);
        assertThatThrownBy(() -> {
            messageClient.connect();
        }).isInstanceOf(MessageClientException.class);
    }

    @Test
    public void connectingTimeOut(){
        final NetworkClient networkClientMock = Mockito.mock(NetworkClient.class);
        Mockito.doNothing().when(networkClientMock).connect();
        Mockito.doReturn(false).when(networkClientMock).isConnected();

        final var messageClient = new MessageClient(CLIENT_NAME, networkClientMock);
        MessageClient.dissableSleep();

        assertThatThrownBy(() -> {
            messageClient.connect();
        }).isInstanceOf(MessageClientException.class);
    }

    @Test
    public void registredTest(){
        final NetworkClient networkClientMock = Mockito.mock(NetworkClient.class);
        Mockito.doNothing().when(networkClientMock).connect();
        Mockito.doReturn(true).when(networkClientMock).isConnected();

        final var messageClient = new MessageClient(CLIENT_NAME, networkClientMock);
        messageClient.connect();

        Mockito.verify(networkClientMock).send("{\"from\":\"front\",\"to\":\"\",\"data\":\"\"}");
    }
}
