package hw08.mygson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

public class MyGsonTest {

    @Test
    public void testToJsonNull() {
        assertEquals(getExpectedJson(null), new MyGson().toJson(null));
    }

    class Empty {
    }

    @Test
    public void testToJsonEmptyClass() {
        final var obj = new Empty();
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithBoolean {
        private boolean b;
        public WithBoolean(boolean b) {
            this.b = b;
        }
    }

    @Test
    public void testToJsonForBooleanTrue() {
        final var obj = new WithBoolean(true);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonForBooleanFalse() {
        final var obj = new WithBoolean(false);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithInt {
        private int i;
        public WithInt(int i) {
            this.i = i;
        }
    }

    @Test
    public void testToJsonForInt() {
        final var obj = new WithInt(1);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
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
        final var obj = new WithObjectArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
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
        final var obj = new WithIntegerArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithList {
        private List<?> list;
        WithList(List<?> list) {
            this.list = list;
        }
    }

    @Test
    public void testToJsonFromList() {
        final List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        final var obj = new WithList(list);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithSet {
        final Set<?> set;
        WithSet(Set<?> set){
            this.set = set;
        }
    }

    @Test
    public void testToJsonFromSet(){
        final Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        final var obj = new WithSet(set);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithQueue{
        final Queue<?> queue;
        WithQueue(Queue<?> queue){
            this.queue = queue;
        }
    }

    @Test
    public void testToJsonFromQueue(){
        final Queue<String> queue = new ArrayDeque<>();
        queue.add("first");
        queue.add("second");
        queue.add("third");
        final var obj = new WithQueue(queue);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    private String getExpectedJson(Object obj){
        return new Gson().toJson(obj);
    }
}
