package hw17.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import hw17.messageservice.MessageService;
import hw17.server.message.ResponseMessage;
import hw17.server.message.ResponseType;

public class SocketHandlerTest {
    @Test
    public void constructorTest() {
        assertDoesNotThrow(() -> new SocketHandler(null));
    }

    @Test
    public void reciveWithEmptyMessage() {
        assertEquals(new ResponseMessage(ResponseType.ERROR).toJson(), new SocketHandler(null).recive(""));
    }

    @Test
    public void reciveWithNotValidJson() {
        assertEquals(new ResponseMessage(ResponseType.ERROR).toJson(), new SocketHandler(null).recive("{'from':}"));
    }

    @Test
    public void reciveRegistationClient() {
        final MessageService mockedMessageService = Mockito.mock(MessageService.class);

        final var socket = new SocketHandler(mockedMessageService);
        assertThat(socket.getName()).isEmpty();
        final String response = socket.recive("{'from':'front','to':'db', 'data':'message content'}");
        assertThat(socket.getName()).isEqualTo("front");

        Mockito.verify(mockedMessageService, Mockito.times(1)).addClient("front");
        assertEquals(response, new ResponseMessage(ResponseType.REGISTRED).toJson());
    }

    @Test
    public void sendMessageToMessageSystemTest(){
        final MessageService mockedMessageService = Mockito.mock(MessageService.class);

        final SocketHandler socketHandlerSpy = Mockito.spy(new SocketHandler(mockedMessageService));
        Mockito.doReturn("front").when(socketHandlerSpy).getName();

        final String response = socketHandlerSpy.recive("{'from':'front','to':'db', 'data':'message content'}");

        Mockito.verify(mockedMessageService, Mockito.times(1)).sendMessage(Mockito.any(), Mockito.any());
        assertEquals(response, new ResponseMessage(ResponseType.RECIVED).toJson());
    }
}
