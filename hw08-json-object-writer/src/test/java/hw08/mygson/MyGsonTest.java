package hw08.mygson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

public class MyGsonTest {

    @Test
    public void testToJsonNull() {
        assertEquals(new Gson().toJson(null), new MyGson().toJson(null));
    }

    class Empty {
    }

    @Test
    public void testToJsonEmptyClass() {
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
    public void testToJsonForBoolean() {
        final var objWithTrue = new WithBoolean(true);
        final var expectedJson = new Gson().toJson(objWithTrue);
        System.out.println(expectedJson);
        assertEquals(expectedJson, new MyGson().toJson(objWithTrue));
    }

    class WithInt {
        private int i;

        public WithInt(int i) {
            this.i = i;
        }
    }

    @Test
    public void testToJsonForInt() {
        final var objWithInt = new WithInt(1);
        final var expectedJson = new Gson().toJson(objWithInt);
        System.out.println(expectedJson);
        assertEquals(expectedJson, new MyGson().toJson(objWithInt));
    }

}
