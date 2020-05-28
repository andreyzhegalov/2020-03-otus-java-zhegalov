package hw08.mygson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MyGsonTest {

    @Test
    public void testToJsonNull() {
        assertEquals("null", new MyGson().toJson(null));
    }

    @Test
    public void testToJsonEmptyClass() {
        assertEquals("{}", new MyGson().toJson(new TestEmptyClass()));
    }
}
