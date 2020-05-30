package hw08.mygson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

public class MyGsonTest {

    @Test
    public void testToJsonNull() throws IllegalArgumentException, IllegalAccessException {
        assertEquals(new Gson().toJson(null), new MyGson().toJson(null));
    }

    class Empty {
    }

    @Test
    public void testToJsonEmptyClass() throws IllegalArgumentException, IllegalAccessException {
        final var obj = new Empty();
        assertEquals(new Gson().toJson(obj), new MyGson().toJson(obj));
    }

    class WithBoolean {
        private boolean b;
        public WithBoolean(boolean b) {
            this.b = b;
        }
    }

    @Test
    public void testToJsonForBoolean() throws IllegalArgumentException, IllegalAccessException {
        final var objWithTrue = new WithBoolean(true);
        assertEquals(new Gson().toJson(objWithTrue), new MyGson().toJson(objWithTrue));
    }
}
