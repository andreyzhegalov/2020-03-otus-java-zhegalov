package hw08.mygson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
    public void testToJsonForBooleanTrue() {
        final var objWithTrue = new WithBoolean(true);
        final var expectedJson = new Gson().toJson(objWithTrue);
        System.out.println(expectedJson);
        assertEquals(expectedJson, new MyGson().toJson(objWithTrue));
    }

    @Test
    public void testToJsonForBooleanFalse() {
        final var objWithFalse = new WithBoolean(false);
        final var expectedJson = new Gson().toJson(objWithFalse);
        System.out.println(expectedJson);
        assertEquals(expectedJson, new MyGson().toJson(objWithFalse));
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

    class WithObjectArray {
        private Object[] array;

        WithObjectArray(Object[] array) {
            this.array = array;
        }
    }

    @Test
    public void testToJsonFromObjectArray() {
        final Integer[] array = { 1, 2, 3 };
        final var objWithArray = new WithObjectArray(array);
        final var expectedJson = new Gson().toJson(objWithArray);
        System.out.println(expectedJson);
        assertEquals(expectedJson, new MyGson().toJson(objWithArray));
    }

    class WithIntegerArray {
        private int[] array;

        WithIntegerArray(int[] array) {
            this.array = array;
        }
    }

    @Test
    public void testToJsonFromPrimitiveArray() {
        final int[] array = { 1, 2, 3 };
        final var objWithArray = new WithIntegerArray(array);
        final var expectedJson = new Gson().toJson(objWithArray);
        System.out.println(expectedJson);
        assertEquals(expectedJson, new MyGson().toJson(objWithArray));
    }

    class WithCollectionList {
        private ArrayList<?> list;
        WithCollectionList( List<?> list){
            this.list = (ArrayList<?>)list;
        }
    }

    @Test
    public void testToJsonFromCollectionList(){
        final List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        final var objWithCollection = new WithCollectionList(list);
        final var expectedJson = new Gson().toJson(objWithCollection);
        System.out.println(expectedJson);
    }
}
