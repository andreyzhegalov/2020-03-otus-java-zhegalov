package hw17.server.message;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InterprocessMessageTest {

    @Test
    public void fromJsonTest() {
        assertTrue(InterprocessMessage.fromJson("{'response':'RECIVED'}").isEmpty());
    }
}
