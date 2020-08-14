package hw17.server.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ReciveMessageTest {
    private final static String TEST_JSON =  "{\"from\":\"front\",\"to\":\"db\",\"data\":\"content\"}";
    @Test
    public void formJsonForEmptyStringTest() {
        assertTrue( ReciveMessage.fromJson("").isEmpty());
    }

    @Test
    public void formJson() {
        assertTrue( ReciveMessage.fromJson(TEST_JSON).isPresent());
    }

    @Test
    public void toJsonTest(){
        final ReciveMessage message = new ReciveMessage("front", "db", "content");
        assertEquals(TEST_JSON, message.toJson());
    }
}
